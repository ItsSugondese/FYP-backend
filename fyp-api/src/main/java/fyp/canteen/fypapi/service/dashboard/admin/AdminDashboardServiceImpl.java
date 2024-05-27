package fyp.canteen.fypapi.service.dashboard.admin;

import fyp.canteen.fypapi.mapper.foodmgmt.FoodMenuMapper;
import fyp.canteen.fypapi.mapper.ordermgmt.OnlineOrderMapper;
import fyp.canteen.fypapi.mapper.ordermgmt.OnsiteOrderMapper;
import fyp.canteen.fypapi.mapper.payment.RevenueMapper;
import fyp.canteen.fypapi.mapper.payment.UserPaymentDetailsMapper;
import fyp.canteen.fypapi.mapper.table.TableMapper;
import fyp.canteen.fypapi.mapper.usermgmt.UserDetailMapper;
import fyp.canteen.fypcore.pojo.dashboard.*;
import fyp.canteen.fypcore.pojo.dashboard.data.*;
import fyp.canteen.fypcore.utils.data.DateRangeHolder;
import fyp.canteen.fypcore.utils.data.DateTypeEnum;
import fyp.canteen.fypcore.utils.data.FromToDateGenerator;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.descriptor.java.DataHelper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {
    private final UserPaymentDetailsMapper userPaymentDetailsMapper;
    private final UserDetailMapper userDetailMapper;
    private final OnsiteOrderMapper onsiteOrderMapper;
    private final OnlineOrderMapper onlineOrderMapper;
    private final RevenueMapper revenueMapper;
    private final FoodMenuMapper foodMenuMapper;
    private final TableMapper tableMapper;
    private final SimpMessagingTemplate messagingTemplate;


    @Override
    public OrderDataPojo getOrderData(OrderDataRequestPojo requestPojo) {
        OrderDataPojo orderDataPojo = new OrderDataPojo();
        orderDataPojo.setOnsiteOrder(onsiteOrderMapper.getOnsiteOrderStatistics(requestPojo.getTimeDifference()));
        orderDataPojo.setOnlineOrder(onlineOrderMapper.getOnlineOrderStatistics(requestPojo.getTimeDifference()));

        orderDataPojo.setTotalOrder(orderDataPojo.getOnlineOrder().getTotal() + orderDataPojo.getOnsiteOrder().getTotal());
        orderDataPojo.setTotalPending(orderDataPojo.getOnlineOrder().getPending() + orderDataPojo.getOnsiteOrder().getPending());
        return orderDataPojo;
    }

    @Override
    public RevenueDataPojo getRevenueData(RevenueDataRequestPojo requestPojo) {
        FromToDateGenerator.getFromToDate(DateTypeEnum.DAY, 1, requestPojo);
        return revenueMapper.revenueAmountStatistics(requestPojo.getFromDate(), requestPojo.getToDate());
    }

    @Override
    public FoodMenuDataPojo getFoodMenuDataData(FoodMenuDataRequestPojo requestPojo) {
        FromToDateGenerator.getFromToDate(DateTypeEnum.DAY, 1, requestPojo);
        return foodMenuMapper.getFoodMenuStatistics(requestPojo.getFromDate(), requestPojo.getToDate());
    }

    @Override
    public UserDataPojo getUserData(UserDataRequestPojo requestPojo) {
        FromToDateGenerator.getFromToDate(DateTypeEnum.DAY, 1, requestPojo);
        return userDetailMapper.userCountStatistics(requestPojo.getFromDate(), requestPojo.getToDate());
    }

    @Override
    public TableDataPojo getTableData(TableDataRequestPojo requestPojo) {
        FromToDateGenerator.getFromToDate(DateTypeEnum.DAY, 1, requestPojo);
        return tableMapper.getTableDataStatistics(requestPojo.getFromDate(), requestPojo.getToDate());
    }

    @Override
    public SalesDataPojo getSalesData(SalesDataRequestPojo requestPojo) {
        FromToDateGenerator.getFromToDate(DateTypeEnum.DAY, 1, requestPojo);
        List<SalesDataPojo.FoodSalesData> foodSalesData = userPaymentDetailsMapper.getSalesData(
                requestPojo.getFilterType().toString(),
                requestPojo.getFromDate(),
                requestPojo.getToDate(),
                requestPojo.getFoodType() == null ? null : requestPojo.getFoodType().toString().toUpperCase()
        );

        boolean willHaveOthers = requestPojo.getLimit() != null && foodSalesData.size() > requestPojo.getLimit();

        double sales = 0;
        int quantity = 0;
        double otherSales = 0;
        int otherQuantity = 0;
//        AtomicReference<Double> sales = new AtomicReference<>((double) 0);
//        AtomicReference<Integer> quantity = new AtomicReference<>((int) 0);
        List<String> label = new ArrayList<>();
        List<Double> salesList = new ArrayList<>();
        List<Integer> quantityList = new ArrayList<>();

        if (foodSalesData.isEmpty()) {
            label.add("No Data");
            quantityList.add(1);
            salesList.add(0.1D);
        } else
            for (int i = 0; i < foodSalesData.size(); i++) {
                sales += foodSalesData.get(i).getSalesIncome();
                quantity += foodSalesData.get(i).getQuantity();
                if (requestPojo.getLimit() == null || i < requestPojo.getLimit()) {
                    label.add(foodSalesData.get(i).getName());
                    salesList.add(foodSalesData.get(i).getSalesIncome());
                    quantityList.add(foodSalesData.get(i).getQuantity());
                } else if (willHaveOthers && (i >= requestPojo.getLimit())) {

                    if (i == requestPojo.getLimit()) {
                        if (requestPojo.getLimit() == 0)
                            label.add("All");
                        else
                            label.add("Others");
                    }
                    otherSales += foodSalesData.get(i).getSalesIncome();
                    otherQuantity += foodSalesData.get(i).getQuantity();

                }
            }

        if (willHaveOthers) {
            salesList.add(otherSales);
            quantityList.add(otherQuantity);
        }


        return SalesDataPojo.builder()
                .quantityData(quantityList)
                .labels(label)
                .salesData(salesList)
                .totalSales(sales)
                .totalQuantity(quantity)
                .totalMenu(foodSalesData.size())
                .build();
    }

    @Override
    public void sendRevenueDataSocket(){
        messagingTemplate.convertAndSend("/topic/revenue", getRevenueData(new RevenueDataRequestPojo()));
    }

    @Override
    public void pingOrderSocket() {
        messagingTemplate.convertAndSend("/topic/ping/order", true);
    }

    @Override
    public void pingSalesDataSocket() {
        messagingTemplate.convertAndSend("/topic/ping/sales-data", true);

    }

    @Override
    public void sendTableDataSocket() {
        messagingTemplate.convertAndSend("/topic/table", getTableData(new TableDataRequestPojo()));
    }

    @Override
    public void sendFoodMenuDataSocket() {
        messagingTemplate.convertAndSend("/topic/food-menu", getFoodMenuDataData(new FoodMenuDataRequestPojo()));
    }

    @Override
    public void sendUsersDataSocket() {
        messagingTemplate.convertAndSend("/topic/users", getUserData(new UserDataRequestPojo()));
    }
}
