package fyp.canteen.fypapi.service.company;

import fyp.canteen.fypapi.mapper.temporaryattachments.TemporaryAttachmentsDetailMapper;
import fyp.canteen.fypapi.repository.company.CompanyRepo;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.entity.company.Company;
import fyp.canteen.fypcore.pojo.company.CompanyDetailRequestPojo;
import fyp.canteen.fypcore.pojo.temporaryattachments.TemporaryAttachmentsDetailResponsePojo;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import fyp.canteen.fypcore.utils.genericfile.FilePathConstants;
import fyp.canteen.fypcore.utils.genericfile.FilePathMapping;
import fyp.canteen.fypcore.utils.genericfile.GenericFileUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepo companyRepo;
    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();
    private final TemporaryAttachmentsDetailMapper temporaryAttachmentsDetailMapper;
    private final GenericFileUtil genericFileUtil;
    @Override
    public void saveCompany(CompanyDetailRequestPojo requestPojo) {
        List<Company> companyList = companyRepo.findAll();
          Company company = !(companyList).isEmpty() ? companyList.get(0) : new Company();

          try {
              beanUtilsBean.copyProperties(company, requestPojo);
          }catch (Exception e){
              throw new AppException(e.getMessage(), e);
          }

          if(requestPojo.getLogoId() == null && company.getId() == null)
              throw new AppException("Providing company logo is must");
          else if(requestPojo.getLogoId() != null)
              company.setCompanyLogo(savePictureToPath(requestPojo.getLogoId()));

          companyRepo.save(company);
    }

    private String savePictureToPath(Long id){
        try {
            TemporaryAttachmentsDetailResponsePojo temporaryAttachmentsById = temporaryAttachmentsDetailMapper.getTemporaryAttachmentsById(id);
            return genericFileUtil.copyFileToServer(temporaryAttachmentsById.getLocation(), FilePathMapping.COMPANY_FILE, FilePathConstants.TEMP_PATH);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
