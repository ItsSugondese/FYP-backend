package fyp.canteen.fypcore.pojo.company;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDetailRequestPojo {
    private String companyName;
    private String description;
    private String address;
    private String location;
    private String emailAddress;
    private Long logoId;
    private String contactNumber;
}
