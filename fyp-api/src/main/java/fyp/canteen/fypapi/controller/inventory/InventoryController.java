package fyp.canteen.fypapi.controller.inventory;


import fyp.canteen.fypapi.service.inventory.InventoryMenuMappingService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.foodmgmt.FoodMenuPaginationRequestPojo;
import fyp.canteen.fypcore.pojo.foodmgmt.FoodMenuRequestPojo;
import fyp.canteen.fypcore.pojo.inventory.InventoryFoodPaginationRequest;
import fyp.canteen.fypcore.pojo.inventory.InventoryMenuRequestPojo;
import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/inventory")
@Tag(name = ModuleNameConstants.INVENTORY)
public class InventoryController  extends BaseController {
    private final InventoryMenuMappingService inventoryMenuMappingService;
    public InventoryController(InventoryMenuMappingService inventoryMenuMappingService){
        this.inventoryMenuMappingService = inventoryMenuMappingService;
        this.moduleName = ModuleNameConstants.INVENTORY;
    }

    @PostMapping
    @Operation(summary = "Use this api to save/update food menu details", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> saveInventoryMapping(@RequestBody @Valid InventoryMenuRequestPojo requestPojo){
        inventoryMenuMappingService.saveInventoryMenuMapping(requestPojo);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName), CRUD.SAVE, true));
    }

    @PostMapping("/paginated")
    @Operation(summary = "Use this api to save/update food menu details", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))})})
    public ResponseEntity<GlobalApiResponse> getFoodMenuPaginated(@RequestBody PaginationRequest requestPojo){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),CRUD.GET, inventoryMenuMappingService.getInventoryMenuMappingPaginated(requestPojo)));
    }

    @PostMapping("/food-menu/paginated")
    @Operation(summary = "Use this api to save/update food menu details", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))})})
    public ResponseEntity<GlobalApiResponse> getFoodMenuInventoryLogPaginated(@RequestBody InventoryFoodPaginationRequest requestPojo){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),CRUD.GET, inventoryMenuMappingService.getInventoryDataOfFoodPaginated(requestPojo)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Use this api to save/update food menu details", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))})})
    public ResponseEntity<GlobalApiResponse> deleteLog(@PathVariable("id") Long id){
        inventoryMenuMappingService.deleteInventoryLogById(id);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),CRUD.GET, null));
    }
}
