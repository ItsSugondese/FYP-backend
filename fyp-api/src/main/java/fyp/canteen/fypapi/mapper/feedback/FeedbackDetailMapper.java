package fyp.canteen.fypapi.mapper.feedback;

import fyp.canteen.fypcore.pojo.feedback.FeedbackResponsePojo;
import fyp.canteen.fypcore.pojo.feedback.FeedbackStatisticsResponsePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;

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
}
