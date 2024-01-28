package fyp.canteen.fypapi.mapper.company;

import fyp.canteen.fypcore.pojo.company.CompanyDetailsForMailPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CompanyDetailMapper {

    @Select("select c.company_name as companyName, c.contact_number as contactNumber, c.location, \n" +
            "c.company_logo as logoPath from company c")
    CompanyDetailsForMailPojo getCompanyDetailsToSendMail();
}
