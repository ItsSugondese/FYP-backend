package fyp.canteen.fypcore.utils.genericfile;

public enum FileExtensionType {

    PNG(1, "png"),
    JPEG(2, "jpeg"),
    JPG(3, "jpg"),
    DOCX(4, "docx"),
    DOC(5, "doc"),
    PDF(6, "pdf"),
    PPT(7, "ppt"),
    PPTX(8, "pptX"),
    XLSX(9, "xlsx"),
    XLS(10, "xls"),
    CSV(11, "csv"),
    ODS(12, "ods"),

    TXT(13, "txt"),
    SVG(14, "SVG");


    private Integer key;
    private String value;


    FileExtensionType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static FileExtensionType getByKey(Integer key) {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");
        FileExtensionType[] fileExtensionTypes = values();
        for (FileExtensionType fileExtensionType : fileExtensionTypes) {
            if (key.equals(fileExtensionType.key)) {
                return fileExtensionType;
            }
        }

        throw new RuntimeException("File Extension Type Not Found");
    }

}
