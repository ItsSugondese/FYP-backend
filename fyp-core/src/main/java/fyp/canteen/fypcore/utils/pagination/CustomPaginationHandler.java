package fyp.canteen.fypcore.utils.pagination;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: Santosh Paudel
 */
@Component
public class CustomPaginationHandler {

    public PaginationResponse getPaginatedData(Page<Map<String, Object>> page) {
        return PaginationResponse
                .builder()
                .content(page.getContent())
                .numberOfElements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPageIndex(page.getNumber() + 1)
                .build();
    }


}

