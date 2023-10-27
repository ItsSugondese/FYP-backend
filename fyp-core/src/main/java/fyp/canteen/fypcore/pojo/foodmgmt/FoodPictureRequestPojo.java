package fyp.canteen.fypcore.pojo.foodmgmt;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodPictureRequestPojo {
    private Long photoId;
    private Long removeFileId;
}
