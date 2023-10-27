package fyp.canteen.fypcore.utils.genericfile;

import fyp.canteen.fypcore.enums.FileType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;


@Component
@RequiredArgsConstructor
public class GenericFileUtil {

    public String saveFile(MultipartFile doc, FilePathMapping moduleName, List<FileType> type) throws Exception {
        if (doc.getSize() == 0)
            throw new Exception(type + " Not Found!!");
        /**
         * Validate Extension here
         */
        Map<String, Object> extension = validateExtension(doc, type);
        try {

            /**
             * path contains fake path
             * location contains actual path of doc
             */
            String path = moduleName.getPath();
            String location = moduleName.getLocation();
            Long company = null;


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());
            String imagePath = FilePathConstants.UPLOAD_DIR + location + FilePathConstants.FILE_SEPARATOR + date + FilePathConstants.FILE_SEPARATOR;
            File file = new File(imagePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String fileName = System.currentTimeMillis() + "-" + UUID.randomUUID() + "." + extension.get("extension");
            String tempFileName = imagePath + fileName;
            doc.transferTo(new File(tempFileName));
            return path + date + FilePathConstants.FILE_SEPARATOR + fileName;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Map<String, Object> saveTempFile(MultipartFile doc, List<FileType> type) throws Exception {
        try {
            if (doc.getSize() == 0)
                throw new Exception(type + " Not Found!!");
            /**
             * Validate Extension here
             */
            Map<String, Object> extension = validateExtension(doc, type);

            String imagePath = FilePathConstants.TEMP_PATH;
            File file = new File(imagePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String fileName = System.currentTimeMillis() + "-" + UUID.randomUUID() + "." + extension.get("extension");
            String tempFileName = imagePath + fileName;
            doc.transferTo(new File(tempFileName));

            Map<String, Object> map = new HashMap<>();
            map.put("location", imagePath + fileName);
            map.put("fileType", extension.get("fileType"));
            return map;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    public void getFile(String filePath, HttpServletResponse response) throws Exception {
        String fileName = filePath.substring(filePath.lastIndexOf(FilePathConstants.FILE_SEPARATOR) + 1);
        String pathWithDate = filePath.substring(0, filePath.lastIndexOf(FilePathConstants.FILE_SEPARATOR));
        String path = pathWithDate.substring(0, pathWithDate.lastIndexOf(FilePathConstants.FILE_SEPARATOR) + 1);
        String date = pathWithDate.replace(path, "");

        if (!StringUtils.isEmpty(fileName)) {
            String fileLocation = pathWithDate + FilePathConstants.FILE_SEPARATOR + fileName;
            File file = new File(fileLocation);
            if (file.exists()) {
                String ext = FilenameUtils.getExtension(fileName);
                extensionValidator(response, fileName, ext);
                FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
            } else {
                throw new RuntimeException("Invalid File Path");
            }
        }
    }

    public void getFileFromFilePath(String filePath, HttpServletResponse response) throws Exception {
        String fileName = filePath.substring(filePath.lastIndexOf(FilePathConstants.FILE_SEPARATOR) + 1);
        if (!StringUtils.isEmpty(fileName)) {
            File file = new File(filePath);
            if (file.exists()) {
                FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
            } else {
                throw new RuntimeException("Invalid File Path");
            }
        }
    }

    private void extensionValidator(HttpServletResponse response, String fileName, String ext) {
        if (ext.equalsIgnoreCase(FileExtensionType.PNG.getValue()) ||
                ext.equalsIgnoreCase(FileExtensionType.JPG.getValue()) ||
                ext.equalsIgnoreCase(FileExtensionType.JPEG.getValue())) {
            if (response == null) return;
            response.setContentType("image/" + ext);
            response.setHeader("Content-Disposition", "inline;filename=" + fileName);
        } else if (ext.equalsIgnoreCase(FileExtensionType.PDF.getValue())) {
            if (response == null) return;
            response.setContentType("application/" + ext);
            response.setHeader("Content-Disposition", "inline;filename=" + fileName);
        } else if (ext.equalsIgnoreCase(FileExtensionType.XLSX.getValue()) || ext.equalsIgnoreCase(FileExtensionType.CSV.getValue()) || ext.equalsIgnoreCase(FileExtensionType.XLS.getValue())) {
            if (response == null) return;
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        } else if (ext.equalsIgnoreCase(FileExtensionType.DOC.getValue()) || ext.equalsIgnoreCase(FileExtensionType.DOCX.getValue())) {
            if (response == null) return;
            response.setContentType("application/msword");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        } else if (ext.equalsIgnoreCase(FileExtensionType.PDF.getValue())) {
            if (response == null) return;
            response.setContentType("application/pdf");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        } else
            throw new RuntimeException("Invalid File Extension " + ext);
    }

    public Map<String, Object> validateExtension(MultipartFile file, List<FileType> fileType) {
        Map<String, Object> map = new HashMap<>();
        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            System.out.println("Checkign extention ---" + extension);
            extensionValidator(null, file.getName(), extension);
            if (StringUtils.isEmpty(extension)) throw new RuntimeException("File have no extension");
            String message = "Invalid Extension " + extension + " for file type " + fileType;
            AtomicBoolean temp = new AtomicBoolean(false);
            fileType.forEach(type -> {
                switch (type) {
                    case IMAGE:
                        if (Arrays.asList(FileExtensionType.JPEG.getValue(),
                                FileExtensionType.JPG.getValue(),
                                FileExtensionType.PNG.getValue(),
                                FileExtensionType.SVG.getValue()).contains(extension)) {
                            temp.set(true);
                            map.put("fileType", FileType.IMAGE);
                        }
                        break;
                    case DOC:
                        if (Arrays.asList(FileExtensionType.DOC.getValue(),
                                FileExtensionType.DOCX.getValue()).contains(extension)) {
                            temp.set(true);
                            map.put("fileType", FileType.DOC);
                        }
                        break;
                    case PDF:
                        if (FileExtensionType.PDF.getValue().contains(extension)) {
                            temp.set(true);
                            map.put("fileType", FileType.PDF);
                        }
                        break;
                    case TXT:
                        if (FileExtensionType.TXT.getValue().contains(extension)) {
                            temp.set(true);
                            map.put("fileType", FileType.TXT);
                        }
                        break;
                    case EXCEL:
                        if (Arrays.asList(FileExtensionType.XLS.getValue(),
                                FileExtensionType.XLSX.getValue(),
                                FileExtensionType.CSV.getValue(),
                                FileExtensionType.ODS.getValue()).contains(extension)) {
                            temp.set(true);
                            map.put("fileType", FileType.EXCEL);
                        }
                        break;
                    default:
                        if (temp.equals(new AtomicBoolean(false)))
                            throw new RuntimeException(message);
                }
            });

            map.put("extension", extension);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public String copyFile(String filePath, FilePathMapping moduleName) throws Exception {
        String location = null;
        String permanentFileName = null;
        String pathPermanent = null;
        String permanentPath = moduleName.getPath();
        String fileNamePermanent = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String datePermanent = sdf.format(new Date());
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        String pathWithDate = filePath.substring(0, filePath.lastIndexOf("/"));
        String path = pathWithDate.substring(0, pathWithDate.lastIndexOf("/") + 1);
        String date = pathWithDate.replace(path, "");
        FilePathMapping[] filePathMappingList = FilePathMapping.values();
        for (FilePathMapping filePathMapping : filePathMappingList) {
            String mappedPath = filePathMapping.getPath();
            if (path.equals(mappedPath)) {
                location = filePathMapping.getLocation();
                break;
            }
        }
        if (!StringUtils.isEmpty(fileName)) {

            String fileLocation = FilePathConstants.UPLOAD_DIR + location + date + FilePathConstants.FILE_SEPARATOR + fileName;
            File file = new File(fileLocation);


            pathPermanent = moduleName.getPath();
            String locationPermanent = moduleName.getLocation();
            String imagePathPermanent = FilePathConstants.UPLOAD_DIR + locationPermanent + FilePathConstants.FILE_SEPARATOR + datePermanent + FilePathConstants.FILE_SEPARATOR;
            File permanentFile = new File(imagePathPermanent);
            if (!permanentFile.exists()) {
                permanentFile.mkdirs();
            }
            fileNamePermanent = System.currentTimeMillis() + "-" + UUID.randomUUID() + "." + fileName;
            permanentFileName = imagePathPermanent + fileNamePermanent;
            File permanentFileLocation = new File(permanentFileName);

            FileSystemUtils.copyRecursively(file, permanentFileLocation);
        }
        return pathPermanent + datePermanent + FilePathConstants.FILE_SEPARATOR + fileNamePermanent;
    }

    public String copyFileToServer(String filePath, FilePathMapping moduleName, String fileFromPath) throws Exception {
        String fileName = filePath.substring(filePath.lastIndexOf(FilePathConstants.FILE_SEPARATOR) + 1);
//        Long company = null;
//        if (companyId.length == 0&&userDataConfig.getCompanyId()!=null) {
//            company = userDataConfig.getCompanyId();
//        } else if (userDataConfig.getCompanyId()!=null)
//            company = companyId[0];

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        String fileFrom = fileFromPath + fileName;
        String fileTo = FilePathConstants.UPLOAD_DIR + FilePathConstants.FILE_SEPARATOR + moduleName.getLocation() + date + FilePathConstants.FILE_SEPARATOR;

        File file = new File(fileTo);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {

            Files.copy(Paths.get(fileFrom), Paths.get(fileTo + fileName), StandardCopyOption.REPLACE_EXISTING);

            return fileTo + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to copy the file");
        }
    }


    public String moveFileToServer(String filePath, FilePathMapping moduleName) throws Exception {
        String fileName = filePath.substring(filePath.lastIndexOf(FilePathConstants.FILE_SEPARATOR) + 1);
        Long company = null;


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        String fileFrom = FilePathConstants.TEMP_PATH + fileName;
        String fileTo = FilePathConstants.UPLOAD_DIR + company + FilePathConstants.FILE_SEPARATOR + moduleName.getLocation() + date + FilePathConstants.FILE_SEPARATOR;

        File file = new File(fileTo);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            Files.move(Paths.get(fileFrom), Paths.get(fileTo + fileName), StandardCopyOption.REPLACE_EXISTING);

            return fileTo + fileName;

//            return fileTo + location;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to move the file");
        }
    }

    public String moveFileToTemp(String filePath, FilePathMapping moduleName) throws Exception {
        String fileName = filePath.substring(filePath.lastIndexOf(FilePathConstants.FILE_SEPARATOR) + 1);
        Long company = null;


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        String fileTo = FilePathConstants.TEMP_PATH;
        String fileFrom = FilePathConstants.UPLOAD_DIR + company + FilePathConstants.FILE_SEPARATOR + moduleName.getLocation() + date + FilePathConstants.FILE_SEPARATOR + fileName;

        File file = new File(fileTo);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            Files.move(Paths.get(fileFrom), Paths.get(fileTo + fileName), StandardCopyOption.REPLACE_EXISTING);
            return fileTo + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to move the file");
        }
    }


    public Boolean deleteFile(String filePath, FilePathMapping moduleName) throws Exception {
        String fileName = filePath.substring(filePath.lastIndexOf(FilePathConstants.FILE_SEPARATOR) + 1);
        Long company;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        String fileLocation = FilePathConstants.UPLOAD_DIR + moduleName.getLocation() + date + FilePathConstants.FILE_SEPARATOR + fileName;
        File file = new File(fileLocation);
        try {
            file.delete();
            return true;
        } catch (Exception e) {
            throw new Exception("Failed to delete the file");
        }
    }
}

