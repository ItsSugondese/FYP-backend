package fyp.canteen.fypcore.utils.pagination;

import lombok.*;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaginationResponse {

    private List<Map<String, Object>> content;
    private int totalPages;
    private long totalElements;
    private int numberOfElements;
    private int currentPageIndex;
}

