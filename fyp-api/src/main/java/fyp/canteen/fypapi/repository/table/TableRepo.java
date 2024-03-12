package fyp.canteen.fypapi.repository.table;

import fyp.canteen.fypcore.model.entity.tablemodel.CustomTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface TableRepo extends JpaRepository<CustomTable, Long> {

    @Query(nativeQuery = true, value = "select ct.id, ct.table_number as \"tableNumber\" from custom_table ct")
    Page<Map<String, Object>> getTablePaginated(Pageable pageable);
}
