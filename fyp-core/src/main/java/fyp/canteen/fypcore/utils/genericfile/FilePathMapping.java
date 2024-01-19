package fyp.canteen.fypcore.utils.genericfile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FilePathMapping {
    TEMPORARY_FILE("image" + FilePathConstants.FILE_SEPARATOR + "file" + FilePathConstants.FILE_SEPARATOR + "temporary" + FilePathConstants.FILE_SEPARATOR, "fyptempdocument" + FilePathConstants.FILE_SEPARATOR + "doc" + FilePathConstants.FILE_SEPARATOR),
    FOOD_FILE("image" + FilePathConstants.FILE_SEPARATOR + "file" + FilePathConstants.FILE_SEPARATOR + "food" + FilePathConstants.FILE_SEPARATOR, "food-document" + FilePathConstants.FILE_SEPARATOR + "doc" + FilePathConstants.FILE_SEPARATOR),
    USER_FILE("image" + FilePathConstants.FILE_SEPARATOR + "file" + FilePathConstants.FILE_SEPARATOR + "user" + FilePathConstants.FILE_SEPARATOR, "user-document" + FilePathConstants.FILE_SEPARATOR + "doc" + FilePathConstants.FILE_SEPARATOR);
    private String path;


    private String location;
}
