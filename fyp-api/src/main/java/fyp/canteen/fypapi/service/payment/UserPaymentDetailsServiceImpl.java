package fyp.canteen.fypapi.service.payment;

import fyp.canteen.fypapi.mapper.payment.UserPaymentDetailsMapper;
import fyp.canteen.fypapi.repository.ordermgmt.OnlineOrderRepo;
import fyp.canteen.fypapi.repository.ordermgmt.OnsiteOrderRepo;
import fyp.canteen.fypapi.repository.payment.UserPaymentDetailsRepo;
import fyp.canteen.fypapi.service.ordermgmt.OnsiteOrderService;
import fyp.canteen.fypcore.enums.PayStatus;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnsiteOrder;
import fyp.canteen.fypcore.model.entity.payment.UserPaymentDetails;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import fyp.canteen.fypcore.pojo.payment.PaymentDetailsDataPojo;
import fyp.canteen.fypcore.pojo.payment.PaymentHistoryOfOrderResponsePojo;
import fyp.canteen.fypcore.pojo.payment.UserPaymentDetailsRequestPojo;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPaymentDetailsServiceImpl implements UserPaymentDetailsService{
    private final UserPaymentDetailsRepo userPaymentDetailsRepo;
    private final OnsiteOrderService onsiteOrderService;
    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();
    private final OnsiteOrderRepo onsiteOrderRepo;
    private final UserPaymentDetailsMapper userPaymentDetailsMapper;


    @Override
    @Transactional
    public void savePayment(UserPaymentDetailsRequestPojo requestPojo) {

        List<PaymentDetailsDataPojo> paritalPaidPaymentDataList = userPaymentDetailsMapper
                .getRemainingToPayTransactionOfUserByUserId(requestPojo.getUserId());

        if(requestPojo.getDueAmount() < 0)
            throw new AppException("Due amount can't be in negative");

//        if(paritalPaidPaymentDataList.isEmpty() && requestPojo.getPaidAmount() > requestPojo.getTotalAmount() )
//            throw new AppException("Paid amount is high for user having no past remaining amounts");

        OnsiteOrder onsiteOrder = onsiteOrderService.findById(requestPojo.getOnsiteOrderId());

        UserPaymentDetails userPaymentDetails = new UserPaymentDetails();

        if(requestPojo.getId() != null)
            userPaymentDetails = userPaymentDetailsRepo.findById(requestPojo.getId()).orElse(userPaymentDetails);

        try{
            beanUtilsBean.copyProperties(userPaymentDetails, requestPojo);
        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }

        userPaymentDetails.setUser(User.builder().id(requestPojo.getUserId()).build());
        userPaymentDetails.setOnsiteOrder(onsiteOrder);

        userPaymentDetailsRepo.save(userPaymentDetails);

        if(requestPojo.getDueAmount() == 0) {
            onsiteOrder.setPayStatus(PayStatus.PAID);
            userPaymentDetails.setPaidAmount(requestPojo.getTotalAmount());
        } else if(requestPojo.getPaidAmount() == 0) {
            onsiteOrder.setPayStatus(PayStatus.UNPAID);
        }else {
            onsiteOrder.setPayStatus(PayStatus.PARTIAL_PAID);
        }

        onsiteOrderRepo.save(onsiteOrder);

        if(requestPojo.getPaidAmount() > requestPojo.getTotalAmount()){
            payRemainingAmount( paritalPaidPaymentDataList, requestPojo.getPaidAmount() - requestPojo.getTotalAmount(),
                    requestPojo.getUserId());
        }

    }

    @Override
    public void payRemainingAmount(UserPaymentDetailsRequestPojo requestPojo) {
        List<PaymentDetailsDataPojo> paritalPaidPaymentDataList = userPaymentDetailsMapper
                .getRemainingToPayTransactionOfUserByUserId(requestPojo.getUserId());

        payRemainingAmount(paritalPaidPaymentDataList, requestPojo.getPaidAmount(), requestPojo.getUserId());
    }

    private void payRemainingAmount(List<PaymentDetailsDataPojo> paritalPaidPaymentDataList, double remainingCashAfterCurrent, Long userId) {
//        double remainingCashAfterCurrent = requestPojo.getPaidAmount() - requestPojo.getTotalAmount();
        for (PaymentDetailsDataPojo partialPaidPaymentData : paritalPaidPaymentDataList) {

            double dueAmount = partialPaidPaymentData.getDueAmount();
            OnsiteOrder tempOnsiteOrder = partialPaidPaymentData.getOrder();
            if (remainingCashAfterCurrent > dueAmount) {
                remainingCashAfterCurrent -= dueAmount;
                dueAmount = 0;
                updatePreviousTransactions(partialPaidPaymentData, dueAmount, tempOnsiteOrder, PayStatus.PAID);
            } else if (remainingCashAfterCurrent == dueAmount) {
                dueAmount = 0;
                updatePreviousTransactions(partialPaidPaymentData, dueAmount, tempOnsiteOrder, PayStatus.PAID);
                break;
            } else {
                dueAmount -= remainingCashAfterCurrent;
                updatePreviousTransactions(partialPaidPaymentData, dueAmount, tempOnsiteOrder, PayStatus.PARTIAL_PAID);
                break;
            }

        }

        if (remainingCashAfterCurrent > 0){
            List<OnsiteOrder> onsiteOrdersList = onsiteOrderRepo.findUnpaidOnsiteOrdersOfUserByUserId(userId);

            for (OnsiteOrder onsiteOrder : onsiteOrdersList) {
            double dueAmount = onsiteOrder.getTotalPrice();
            if (remainingCashAfterCurrent > dueAmount) {
                remainingCashAfterCurrent -= dueAmount;
                dueAmount = 0;
                addTransactionsForOnsiteOrder(dueAmount, onsiteOrder, PayStatus.PAID);
            } else if (remainingCashAfterCurrent == dueAmount) {
                dueAmount = 0;
                addTransactionsForOnsiteOrder(dueAmount, onsiteOrder, PayStatus.PAID);
                break;
            } else {
                dueAmount -= remainingCashAfterCurrent;
                addTransactionsForOnsiteOrder(dueAmount, onsiteOrder, PayStatus.PARTIAL_PAID);
                break;
            }

        }
    }
    }

    @Override
    public Double getRemainingAmountOfUser(Long id) {
        return userPaymentDetailsRepo.getTotalRemainingAmountWithUnpaidToPayOfUserByUserId(id);
    }

    @Override
    public List<PaymentHistoryOfOrderResponsePojo> getOrderHistoryOfUserByOrderId(Long id) {
        OnsiteOrder onsiteOrder = onsiteOrderService.findById(id);
        if(onsiteOrder.getPayStatus() == PayStatus.UNPAID)
            return Collections.singletonList(PaymentHistoryOfOrderResponsePojo.builder()
                            .remainingAmount(onsiteOrder.getTotalPrice())
                            .paidAmount(0D)
                            .dueAmount(onsiteOrder.getTotalPrice())
                            .paidDate(null)
                    .build());
        return userPaymentDetailsMapper.getOrderPaymentHistory(id);
    }

    private void updatePreviousTransactions(PaymentDetailsDataPojo paymentDetailsDataPojo, double dueAmount, OnsiteOrder tempOnsiteOrder, PayStatus payStatus) {
        UserPaymentDetails userPaymentDetails = new UserPaymentDetails();
        userPaymentDetails.setTotalAmount(paymentDetailsDataPojo.getDueAmount());
        userPaymentDetails.setDueAmount(dueAmount);
        userPaymentDetails.setPaidAmount(paymentDetailsDataPojo.getDueAmount() - dueAmount);
        userPaymentDetails.setOnsiteOrder(tempOnsiteOrder);
        userPaymentDetails.setUser(tempOnsiteOrder.getUser());
        tempOnsiteOrder.setPayStatus(payStatus);
        paymentDetailsDataPojo.setPaidAmount(paymentDetailsDataPojo.getTotalAmount());
        userPaymentDetailsRepo.save(userPaymentDetails);
        onsiteOrderRepo.save(tempOnsiteOrder);
    }

    private void addTransactionsForOnsiteOrder(double dueAmount, OnsiteOrder onsiteOrder, PayStatus payStatus) {
        UserPaymentDetails userPaymentDetails = new UserPaymentDetails();
        userPaymentDetails.setTotalAmount(onsiteOrder.getTotalPrice());
        userPaymentDetails.setDueAmount(dueAmount);
        userPaymentDetails.setPaidAmount(onsiteOrder.getTotalPrice() - dueAmount);
        userPaymentDetails.setOnsiteOrder(onsiteOrder);
        userPaymentDetails.setUser(onsiteOrder.getUser());
        onsiteOrder.setPayStatus(payStatus);
        userPaymentDetailsRepo.save(userPaymentDetails);
        onsiteOrderRepo.save(onsiteOrder);
    }

}
