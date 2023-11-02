package fyp.canteen.fypcore.utils.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Min;

/**
 * @Author: Santosh Paudel
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequest {

    @Min(value = 1, message = "Page cannot be less than ")
    private int page = 0;

    @Min(value = 1, message = "Request Per Page cannot be less than ")
    private int row = 10;

    private Integer offset;
    private Integer limit;
    private Pageable pageable;

    public int getPage() {
        if (page == 0)
            return page;
        return page - 1;
    }

    public Integer getOffset() {
        return getPage() * row;
    }

    public Integer getLimit() {
        return row;
    }

    public Pageable getPageable() {
        return PageRequest.of(getPage(), getRow());
    }
}

