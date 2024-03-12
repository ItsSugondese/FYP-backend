package fyp.canteen.fypapi.mapper.feedback;

import fyp.canteen.fypcore.pojo.feedback.FeedbackRequestPojo;
import fyp.canteen.fypcore.pojo.feedback.FeedbackStatisticsResponsePojo;
import fyp.canteen.fypcore.pojo.feedback.FoodMenuToFeedbackResponsePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface FeedbackDetailMapper {

    @Select("SELECT *,\n" +
            "  ROUND(((foo.\"positiveCount\"::numeric / foo.\"totalFeedback\") * 100), 2) AS \"positivePercentage\",\n" +
            "  ROUND(((foo.\"neutralCount\"::numeric / foo.\"totalFeedback\") * 100), 2) AS \"neutralPercentage\",\n" +
            "  ROUND(((foo.\"negativeCount\"::numeric / foo.\"totalFeedback\") * 100), 2) AS \"negativePercentage\",\n" +
            "  CASE \n" +
            "    WHEN foo.\"positiveCount\" > foo.\"negativeCount\" AND foo.\"positiveCount\" > foo.\"neutralCount\" THEN 'Positive'\n" +
            "    WHEN foo.\"negativeCount\" > foo.\"positiveCount\" AND foo.\"negativeCount\" > foo.\"neutralCount\" THEN 'Negative'\n" +
            "    WHEN foo.\"neutralCount\" = foo.\"positiveCount\" AND foo.\"neutralCount\" > foo.\"negativeCount\" THEN 'Slightly Positive'\n" +
            "    WHEN foo.\"neutralCount\" = foo.\"negativeCount\" AND foo.\"neutralCount\" > foo.\"positiveCount\" THEN 'Slightly Negative'\n" +
            "    ELSE 'Neutral' \n" +
            "  END AS \"sentiment\"\n" +
            "FROM (\n" +
            "  SELECT \n" +
            "    COUNT(*) AS \"totalFeedback\",\n" +
            "    SUM(CASE WHEN f.feedback_status = 'POSITIVE' THEN 1 ELSE 0 END) AS \"positiveCount\",\n" +
            "    SUM(CASE WHEN f.feedback_status = 'NEUTRAL' THEN 1 ELSE 0 END) AS \"neutralCount\",\n" +
            "    SUM(CASE WHEN f.feedback_status = 'NEGATIVE' THEN 1 ELSE 0 END) AS \"negativeCount\" \n" +
            "  FROM feedback f \n" +
            "  WHERE f.is_active and f.food_id = #{foodId} and \n" +
            "cast(f.created_date as date) between cast(#{fromDate} as date) and cast(#{toDate} as date) \n" +
            ") foo")
    FeedbackStatisticsResponsePojo getFeedbackStatistics(@Param("foodId") Long foodId,
                                                         @Param("fromDate") LocalDate fromDate,
                                                         @Param("toDate") LocalDate toDate);


    @Select(" SELECT\n" +
            "    foodId.foodname AS \"foodName\",\n" +
            "    foodId.food_id AS \"foodId\",\n" +
            "    foodId.pictureId AS \"pictureId\",\n" +
            "    foodId.price,\n" +
            "    foodId.food_type as foodtype,\n" +
            "    COALESCE(feedback.isGiven, FALSE) AS \"isGiven\"\n" +
            "FROM\n" +
            "    (\n" +
            "        SELECT\n" +
            "            orderFood.food_id,\n" +
            "            orderfood.foodname,\n" +
            "            orderfood.pictureId,\n" +
            "            orderfood.price,\n" +
            "            orderfood.food_type\n" +
            "        FROM\n" +
            "            (\n" +
            "                SELECT\n" +
            "                    oo.id\n" +
            "                FROM\n" +
            "                    onsite_order oo\n" +
            "                WHERE oo.is_active and\n" +
            "                    oo.user_id = #{userId}\n" +
            "                    AND CAST(oo.created_date AS DATE) = CURRENT_DATE\n" +
            "                    AND oo.approval_status = 'DELIVERED'\n" +
            "            ) onsite\n" +
            "        JOIN LATERAL\n" +
            "            (\n" +
            "                SELECT\n" +
            "                    ofm.onsite_order_id AS osid,\n" +
            "                    ofm.food_id,\n" +
            "                    fm.\"name\" AS foodname,\n" +
            "                    fm.\"cost\" as price,\n" +
            "                    fm.food_type,\n" +
            "                    (\n" +
            "                        SELECT\n" +
            "                            fmp.id\n" +
            "                        FROM\n" +
            "                            food_menu_picture fmp\n" +
            "                        WHERE\n" +
            "                            fmp.is_active IS TRUE\n" +
            "                            AND fmp.food_menu_id = ofm.food_id\n" +
            "                    ) AS pictureId\n" +
            "                FROM\n" +
            "                    order_food_mapping ofm\n" +
            "                JOIN food_menu fm ON fm.id = ofm.food_id\n" +
            "                WHERE\n" +
            "                    ofm.onsite_order_id = onsite.id\n" +
            "            ) orderFood ON orderFood.osid = onsite.id\n" +
            "        GROUP BY\n" +
            "            orderFood.food_id,\n" +
            "            orderFood.foodname,\n" +
            "            orderfood.pictureId,\n" +
            "            orderfood.price,\n" +
            "            orderfood.food_type\n" +
            "    ) foodId\n" +
            "LEFT JOIN LATERAL\n" +
            "    (\n" +
            "        SELECT\n" +
            "            COALESCE(foodId.food_id, 0) AS fid,\n" +
            "            TRUE AS isGiven\n" +
            "        FROM\n" +
            "            feedback f\n" +
            "        WHERE\n" +
            "            CAST(f.created_date AS DATE) = CURRENT_DATE \n" +
            "            AND f.food_id = foodId.food_id and f.is_active\n" +
            "    ) feedback ON feedback.fid = foodId.food_id")
    List<FoodMenuToFeedbackResponsePojo> getAllListOfMenuToFeedback(@Param("userId") Long userId);


    @Select("select f.id, f.feedback_status as feedbackStatus, f.feedbacks, f.is_anon as isAnonymous\n" +
            "from feedback f where f.food_id = #{foodId} and f.user_id = #{userId} and cast(f.created_date as date) = current_date")
    FeedbackRequestPojo userOnFoodFeedbackToday(Long userId, Long foodId);
}
