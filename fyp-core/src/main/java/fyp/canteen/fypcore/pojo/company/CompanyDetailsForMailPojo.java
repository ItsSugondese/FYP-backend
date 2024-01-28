package fyp.canteen.fypcore.pojo.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDetailsForMailPojo {
    private String logoPath;
    private String companyName;
    private String contactNumber;
    private String location;
}
