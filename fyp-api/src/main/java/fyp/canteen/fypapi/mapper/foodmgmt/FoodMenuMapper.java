package fyp.canteen.fypapi.mapper.foodmgmt;

import fyp.canteen.fypcore.pojo.dashboard.data.FoodMenuDataPojo;
import fyp.canteen.fypcore.pojo.foodmgmt.FoodMenuRequestPojo;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Mapper
public interface FoodMenuMapper {

    @Select("select fm.id as id, fm.\"name\" as name, fm.\"cost\" as cost, fm.description as description, fm.food_type as \"foodType\", \n" +
            "(select fmp.id  from food_menu_picture fmp where fmp.is_active and fmp.food_menu_id = fm.id) as photoId,\n" +
            "case when fm.food_type = 'PACKAGE' then true else false end as isPackage, fm.is_available_today as isAvailableToday \n" +
            "from food_menu fm where fm.is_active and \n" +
            "case when #{type} = 'ALL' then true else fm.is_available_today end")
    List<FoodMenuRequestPojo> getAllFoodMenu(String type);


    @Select("select fm.id as id, fm.\"name\" as name, fm.food_type as \"foodType\", fm.\"cost\" as cost, fm.description as description, INITCAP(fm.food_type) as \"foodType\",  \n" +
            "(select fmp.id  from food_menu_picture fmp where fmp.is_active and fmp.food_menu_id = fm.id) as photoId,\n" +
            "fm.is_available_today as isAvailableToday \n" +
            "from food_menu fm where fm.is_active and fm.id = #{id}")
    Optional<FoodMenuRequestPojo> getFoodMenuById(@Param("id") Long id);

    @Select("select count(*) as total ,\n" +
            "sum(case when fm.is_available_today then 1 else 0 end) as today,\n" +
            "sum(case when not fm.is_available_today then 1 else 0 end)  as \"notToday\", \n" +
            "sum(case when cast(fm.created_date as date) between cast(#{fromDate} as date) and cast(#{toDate} as date) then 1 else 0 end) as latest\n" +
            "from food_menu fm where fm.is_active ")
    FoodMenuDataPojo getFoodMenuStatistics(@Param("fromDate") LocalDate fromDate,
                                           @Param("toDate") LocalDate toDate);



}
