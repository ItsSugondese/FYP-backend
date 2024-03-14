package fyp.canteen.fypapi.mapper.table;

import fyp.canteen.fypcore.pojo.dashboard.data.TableDataPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;

@Mapper
public interface TableMapper {

    @Select("select count(*) as total, \n" +
            "  sum(case when (cast(ct.created_date as date) between cast(#{fromDate} as date) and cast(#{toDate} as date)) \n" +
            "  \t\tthen  1 else  0 end) as latest\n" +
            "from custom_table ct")
    TableDataPojo getTableDataStatistics(@Param("fromDate")LocalDate fromDate,
                                         @Param("toDate") LocalDate toDate);
}
