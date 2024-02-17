package fyp.canteen.fypapi.controller.foodmgmt;

import fyp.canteen.fypapi.service.food.FoodMenuService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.enums.pojo.FoodFilter;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.foodmgmt.FoodMenuPaginationRequestPojo;
import fyp.canteen.fypcore.pojo.foodmgmt.FoodMenuRequestPojo;
import fyp.canteen.fypcore.pojo.foodmgmt.ToggleAvailableTodayRequestPojo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/food-menu")
@Tag(name = ModuleNameConstants.FOOD_MENU)
public class FoodMenuController extends BaseController {

    private final FoodMenuService foodMenuService;
    public FoodMenuController(FoodMenuService foodMenuService){
        this.foodMenuService = foodMenuService;
        this.moduleName = ModuleNameConstants.FOOD_MENU;
    }
    @PostMapping
    @Operation(summary = "Use this api to save/update food menu details", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> saveFoodMenu(@RequestBody @Valid FoodMenuRequestPojo requestPojo){
        foodMenuService.saveFoodMenu(requestPojo);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName), CRUD.SAVE, true));
    }

    @PostMapping("/pageable")
    @Operation(summary = "Use this api to save/update food menu details", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))})})
    public ResponseEntity<GlobalApiResponse> getFoodMenuPageable(@RequestBody @Valid FoodMenuPaginationRequestPojo requestPojo){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),CRUD.GET, foodMenuService.getFoodMenuPageable(requestPojo)));
    }

    @PostMapping("/toggle-availability")
    @Operation(summary = "Use this api to save/update food menu details", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))})})
    public ResponseEntity<GlobalApiResponse> getFoodMenuPageable(@RequestBody @Valid ToggleAvailableTodayRequestPojo requestPojo){
        foodMenuService.toggleAvailability(requestPojo);
        return ResponseEntity.ok(successResponse("Availability saved successfully",CRUD.SAVE,  null));
    }

    @GetMapping
    @Operation(summary = "Use this api to Get food menu details", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = FoodMenuRequestPojo.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getAllFoodMenu(@RequestParam("type")FoodFilter foodFilter){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName), CRUD.GET, foodMenuService.getAllFoodMenu(foodFilter)));
    }

    @GetMapping("/photo/{id}")
    @Operation(summary = "Use this api to save/update food menu details", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getFoodPicture(@PathVariable("id") Long id, HttpServletResponse response){
        foodMenuService.getFoodPhoto(response, id);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName), CRUD.GET, true));
    }
}


