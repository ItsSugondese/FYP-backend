package fyp.canteen.fypapi.controller.table;

import fyp.canteen.fypapi.service.table.TableService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.table.TableRequestPojo;
import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/table")
@Tag(name = ModuleNameConstants.TABLE)
public class TableController extends BaseController {
    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
        this.moduleName = ModuleNameConstants.TABLE;
    }

    @PostMapping
    @Operation(summary = "Save table")
    public ResponseEntity<GlobalApiResponse> getAllNotificationOfMember(@RequestBody @Valid TableRequestPojo request) {
        tableService.saveTable(request);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName),
                CRUD.SAVE,
                null));
    }

    @GetMapping
    @Operation(summary = "Get table")
    public ResponseEntity<GlobalApiResponse> getAllTable() {
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET,
                tableService.getAllTablePaginated()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Get table")
    public ResponseEntity<GlobalApiResponse> deleteTable(@PathVariable("id") Long id) {
        tableService.deleteById(id);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName),
                CRUD.SAVE,
                null));
    }

    @GetMapping("/qr/{id}")
    @Operation(summary = "Get table")
    public ResponseEntity<GlobalApiResponse> getTableQr(@PathVariable("id") Long id, HttpServletResponse response) {
        tableService.getTableQrPhoto(response, id);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName),
                CRUD.SAVE,
                null));
    }
}
