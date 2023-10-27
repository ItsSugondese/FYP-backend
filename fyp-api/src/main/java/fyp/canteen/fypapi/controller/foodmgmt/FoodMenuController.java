package fyp.canteen.fypapi.controller.foodmgmt;

import fyp.canteen.fypapi.service.food.FoodMenuService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.foodmgmt.FoodMenuRequestPojo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/auth")
@Tag(name = ModuleNameConstants.FOOD_MENU)
public class FoodMenuController extends BaseController {

    private final FoodMenuService foodMenuService;
    public FoodMenuController(FoodMenuService foodMenuService){
        this.foodMenuService = foodMenuService;
        this.moduleName = ModuleNameConstants.FOOD_MENU;
    }
    @PostMapping("/food-menu")
    @Operation(summary = "Use this api to save/update food menu details", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> signInWithGoogle(@RequestBody @Valid FoodMenuRequestPojo requestPojo){
        foodMenuService.saveFoodMenu(requestPojo);
        return ResponseEntity.ok(successResponse(Message.Crud(MessageConstants.SAVE, moduleName), true));
    }
}


