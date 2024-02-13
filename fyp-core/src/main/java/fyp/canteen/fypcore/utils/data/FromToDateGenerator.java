package fyp.canteen.fypcore.utils.data;

import java.time.LocalDate;

public class FromToDateGenerator {

    // type enum is for filtering based off type which cna be day/month/week/years. The days param is for how many i.e.
    // value of days 2 and type Week will result in filtering data of last 2 weeks. likewise month and 3 means 3 month.
    public static<T extends DateRangeHolder> T getFromToDate(DateTypeEnum typeEnum, Integer days,
                                                             T requestPojo){
        LocalDate fromDate = requestPojo.getFromDate();
        LocalDate toDate = requestPojo.getToDate();
        if(fromDate == null || toDate == null) {
            int dateToAdd = typeEnum.getNoOfDays() * days;
            if (fromDate == null && toDate == null) {
                fromDate = LocalDate.now().minusWeeks(dateToAdd);
                toDate = LocalDate.now();

            } else if (fromDate == null) {
                fromDate = toDate.minusWeeks(dateToAdd);
            } else {
                toDate = LocalDate.now();
            }
        }else{
            if(fromDate.isAfter(toDate)){
                LocalDate tempDate = toDate;
                toDate = fromDate;
                fromDate = tempDate;
            }
        }

        requestPojo.setFromDate(fromDate);
        requestPojo.setToDate(toDate);
        return requestPojo;
    }
}

