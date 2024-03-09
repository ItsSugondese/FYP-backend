package fyp.canteen.fypcore.pojo.dashboard.data;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesDataPojo {
    private Double totalSales;
    private Integer totalQuantity;
    private List<String> labels;
    private List<Integer> quantityData;
    private List<Double> salesData;
    private Integer totalMenu;

//    private List<FoodSalesData> soldFood;

    @Getter
    @Setter
    public static class FoodSalesData{
        private String name;
        private Double salesIncome;
        private Integer quantity;
    }


}
