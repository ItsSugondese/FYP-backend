package fyp.canteen.fypapi.controller.enums;

import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.enums.FeedbackStatus;
import fyp.canteen.fypcore.enums.FoodType;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/enums")
@Tag(name = ModuleNameConstants.ENUMS)
public class EnumController extends BaseController {

    public EnumController() {
        this.moduleName = ModuleNameConstants.ENUMS;
    }

    @GetMapping("/feedback")
    public ResponseEntity<GlobalApiResponse> getFeedbackStatus(){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName), CRUD.GET, FeedbackStatus.values()));
    }

    @GetMapping("/food-menu")
    public ResponseEntity<GlobalApiResponse> getFoodMenuTypes(){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName), CRUD.GET, Arrays.stream(FoodType.values()).map(
                FoodType::getText
        )));
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<GlobalApiResponse> getFeedbackStatusValue(){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName), CRUD.GET, Arrays.stream(FeedbackStatus.values()).map(
                FeedbackStatus::getText
        )));
    }
}
