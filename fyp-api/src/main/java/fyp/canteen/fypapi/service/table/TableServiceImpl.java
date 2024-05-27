package fyp.canteen.fypapi.service.table;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import fyp.canteen.fypapi.repository.table.TableRepo;
import fyp.canteen.fypapi.service.dashboard.admin.AdminDashboardService;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.entity.tablemodel.CustomTable;
import fyp.canteen.fypcore.pojo.table.TableRequestPojo;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import fyp.canteen.fypcore.utils.genericfile.FilePathConstants;
import fyp.canteen.fypcore.utils.genericfile.FilePathMapping;

import fyp.canteen.fypcore.utils.genericfile.GenericFileUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {
    private final TableRepo tableRepo;
    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();
    private final GenericFileUtil genericFileUtil;
    private final AdminDashboardService adminDashboardService;

    @Override
    public CustomTable saveTable(TableRequestPojo requestPojo) {
        if(requestPojo.getTableNumber() == null)
            throw new AppException("Table number is must");
        CustomTable customTable = new CustomTable();

        try{
            beanUtilsBean.copyProperties(customTable, requestPojo);
        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }

        customTable.setGuid(UUID.randomUUID().toString());


        String combinedText = customTable.getGuid() + ":" + customTable.getTableNumber();

        // Generate QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(combinedText, BarcodeFormat.QR_CODE, 1012, 1012);
        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }

        String imagePath = FilePathConstants.UPLOAD_DIR + FilePathConstants.FILE_SEPARATOR +
                FilePathMapping.TABLE_FILE.getLocation();
        File file = new File(imagePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        // Convert BitMatrix to image and save to file
        String fileName = customTable.getGuid() + ".png";
        String filePath = imagePath + FilePathConstants.FILE_SEPARATOR + fileName;
        File qrCodeFile = new File(filePath);

        customTable.setQrPath(qrCodeFile.getAbsolutePath());
        customTable = tableRepo.save(customTable);
        try {
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", qrCodeFile.toPath());
        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }
        adminDashboardService.sendTableDataSocket();
        return customTable;
    }

    @Override
    public List<Map<String, Object>> getAllTablePaginated() {
        return tableRepo.getTablePaginated(Pageable.unpaged()).getContent();
    }

    @Override
    public void getTableQrPhoto(HttpServletResponse response, Long id) {
        String photoPath = tableRepo.findById(id).orElseThrow(() -> new RuntimeException("Table not found")).getQrPath();
        try {
            genericFileUtil.getFileFromFilePath(photoPath, response);
        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public boolean verifyOnsite(String text) {
        return tableRepo.findByGuid(text.split(":")[0]).isPresent();
    }

    @Override
    public boolean verifyOnsiteCode(String text) {
        if(tableRepo.findByGuid(text.split(":")[0]).isPresent())
            return  true;
        throw new AppException("Onsite Order verificaiton failed");
    }
    @Override
    public void deleteById(Long id) {
        tableRepo.deleteById(id);
        adminDashboardService.sendTableDataSocket();
    }
}
