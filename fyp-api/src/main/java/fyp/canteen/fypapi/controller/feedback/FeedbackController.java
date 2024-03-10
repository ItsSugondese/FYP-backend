package fyp.canteen.fypapi.controller.feedback;

import fyp.canteen.fypapi.service.feedback.FeedbackService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.feedback.FeedbackPaginationRequest;
import fyp.canteen.fypcore.pojo.feedback.FeedbackRequestPojo;
import fyp.canteen.fypcore.pojo.feedback.FeedbackStatisticsRequestPojo;
import fyp.canteen.fypcore.utils.data.DateRangeHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/feedback")
@Tag(name = ModuleNameConstants.FEEDBACK)
public class FeedbackController extends BaseController {
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
        this.moduleName = ModuleNameConstants.FEEDBACK;
    }

    @PostMapping
    @Operation(summary = "Use this api to save/update feedbacks", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> saveFeedbacks(@RequestBody @Valid FeedbackRequestPojo requestPojo){
        feedbackService.saveFeedback(requestPojo);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName), CRUD.SAVE, true));
    }

    @PostMapping("/paginated")
    @Operation(summary = "Use this api to save/update food menu details", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Map.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getFeedbackPaginated(@RequestBody FeedbackPaginationRequest requestPojo){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName), CRUD.GET,
                feedbackService.getFeedbackDataPaginated(requestPojo)
        ));
    }

    @PostMapping("/data")
    @Operation(summary = "Use this api to save/update food menu details", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Map.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getFeedbackStatistics(@RequestBody FeedbackStatisticsRequestPojo requestPojo){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName), CRUD.GET,
                feedbackService.getFeedbackDataDetails(requestPojo)
        ));
    }

    @GetMapping("/available-to-give")
    @Operation(summary = "Use this api to save/update food menu details", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Map.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getFoodMenuToFeedback(){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName), CRUD.GET,
                feedbackService.getAllFoodAvaiableForFeedbacksList()
        ));
    }


}
