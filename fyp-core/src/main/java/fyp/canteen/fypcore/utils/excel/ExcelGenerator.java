package fyp.canteen.fypcore.utils.excel;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
@Component

public class ExcelGenerator {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public void export(HttpServletResponse response, List<Map<String, Object>> details, String mergedHeader) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + mergedHeader + ".xlsx";
        response.setHeader(headerKey, headerValue);

        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Mock-Test-Attendence");

        writeMergedHeaderLine(details.get(0).keySet().size(), mergedHeader, getCellStyle(16, true));
        writeHeaderLine(details, getCellStyle(15, true));
        writeDataLinesForMockTestStudent(details, getCellStyle(14, false));
        workbook.write(response.getOutputStream());
        workbook.close();
    }


    private void writeHeaderLine(List<Map<String, Object>> details, CellStyle style) {
        Row row = sheet.createRow(1);

        AtomicReference<Integer> count = new AtomicReference<>(0);

        details.get(0).forEach((key, value) -> {
            createCell(row, count.get(), key, style);
            count.getAndSet(count.get() + 1);
        });
    }

    private void writeMergedHeaderLine(int columnCount, Object value, CellStyle style) {
        Row row = sheet.createRow(0);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnCount - 1));
        Cell cell = row.createCell(0);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }

        style.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(style);
    }

    private void writeDataLinesForMockTestStudent(List<Map<String, Object>> details, CellStyle style) {
        AtomicInteger rowCount = new AtomicInteger(2);

        details.forEach(e -> {
            Row row = sheet.createRow(rowCount.getAndIncrement());
            AtomicInteger columnCount = new AtomicInteger();
            e.forEach((key, value) -> {
                createCell(row, columnCount.getAndIncrement(), value, style);
            });
        });
    }


    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }

        cell.setCellStyle(style);
    }


    private CellStyle getCellStyle(int fontSize, boolean bold) {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(bold);
        font.setFontHeight(fontSize);
        style.setFont(font);

        return style;
    }
}
