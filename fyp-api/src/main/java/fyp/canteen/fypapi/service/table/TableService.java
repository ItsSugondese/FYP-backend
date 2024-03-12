package fyp.canteen.fypapi.service.table;

import fyp.canteen.fypcore.pojo.table.TableRequestPojo;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

public interface TableService {

    void saveTable(TableRequestPojo requestPojo);
    List<Map<String, Object>> getAllTablePaginated();

    void getTableQrPhoto(HttpServletResponse response, Long id);
    void deleteById(Long id);
}
