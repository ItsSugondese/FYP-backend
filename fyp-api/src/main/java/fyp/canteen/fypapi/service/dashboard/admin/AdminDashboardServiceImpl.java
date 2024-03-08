package fyp.canteen.fypapi.service.dashboard.admin;

import fyp.canteen.fypapi.mapper.payment.UserPaymentDetailsMapper;
import fyp.canteen.fypcore.pojo.dashboard.SalesDataRequestPojo;
import fyp.canteen.fypcore.pojo.dashboard.data.SalesDataPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {
    private final UserPaymentDetailsMapper userPaymentDetailsMapper;

    @Override
    public SalesDataPojo getSalesData(SalesDataRequestPojo requestPojo) {
        SalesDataPojo salesData = new SalesDataPojo();
        List<SalesDataPojo.FoodSalesData> foodSalesData = userPaymentDetailsMapper.getSalesData(
                requestPojo.getFilterType().toString(),
                requestPojo.getFromDate(),
                requestPojo.getToDate()
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
//        foodSalesData.forEach(
//                e -> {
//                    sales.set(sales.get() + e.getSalesIncome());
//                    quantity.set(quantity.get() + e.getQuantity());
//                    label.add(e.getName());
//                    salesList.add(e.getSalesIncome());
//                    quantityList.add(e.getQuantity());
//                }
//        );

        salesData.setQuantityData(quantityList);
        salesData.setLabels(label);
        salesData.setSalesData(salesList);

        salesData.setTotalSales(sales);
        salesData.setTotalQuantity(quantity);
        return salesData;
    }
}
