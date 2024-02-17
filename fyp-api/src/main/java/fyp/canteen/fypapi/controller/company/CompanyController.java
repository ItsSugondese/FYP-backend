package fyp.canteen.fypapi.controller.company;

import fyp.canteen.fypapi.service.company.CompanyService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.company.CompanyDetailRequestPojo;
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
@RequestMapping("/company")
@Tag(name = ModuleNameConstants.COMPANY)
public class CompanyController extends BaseController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    @Operation(summary = "Use this api to save/update company details", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> saveCompanyDetails(@RequestBody @Valid CompanyDetailRequestPojo requestPojo){
        companyService.saveCompany(requestPojo);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName), CRUD.SAVE, true));
    }
}
