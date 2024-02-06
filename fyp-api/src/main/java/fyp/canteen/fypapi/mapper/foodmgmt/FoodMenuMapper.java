package fyp.canteen.fypapi.mapper.foodmgmt;

import fyp.canteen.fypcore.pojo.foodmgmt.FoodMenuRequestPojo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FoodMenuMapper {

    @Select("select fm.id as id, fm.\"name\" as name, fm.\"cost\" as cost, fm.description as description, \n" +
            "fm.food_menu_items as menuItemsString, \n" +
            "(select fmp.id  from food_menu_picture fmp where fmp.is_active and fmp.food_menu_id = fm.id) as photoId,\n" +
            "case when fm.food_type = 'PACKAGE' then true else false end as isPackage, fm.is_available_today as isAvailableToday \n" +
            "from food_menu fm where fm.is_active and \n" +
            "case when #{type} = 'ALL' then true else fm.is_available_today end")
//    @Results({
//            @Result(property = "menuItemsString", column = "menuItems",
//            javaType = List.class)
//    })
    List<FoodMenuRequestPojo> getAllFoodMenu(String type);


    @Select("select fm.id as id, fm.\"name\" as name, fm.food_type as \"foodType\", fm.\"cost\" as cost, fm.description as description, \n" +
            "fm.food_menu_items as menuItemsString, \n" +
            "(select fmp.id  from food_menu_picture fmp where fmp.is_active and fmp.food_menu_id = fm.id) as photoId,\n" +
            "case when fm.food_type = 'PACKAGE' then true else false end as isPackage, fm.is_available_today as isAvailableToday \n" +
            "from food_menu fm where fm.is_active and fm.id = #{id}")
    Optional<FoodMenuRequestPojo> getFoodMenuById(@Param("id") Long id);




}
