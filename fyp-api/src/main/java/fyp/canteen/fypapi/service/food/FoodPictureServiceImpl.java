package fyp.canteen.fypapi.service.food;

import fyp.canteen.fypapi.mapper.temporaryattachments.TemporaryAttachmentsDetailMapper;
import fyp.canteen.fypapi.repository.foodmgmt.FoodPictureRepo;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenuPicture;
import fyp.canteen.fypcore.pojo.foodmgmt.FoodPictureRequestPojo;
import fyp.canteen.fypcore.pojo.temporaryattachments.TemporaryAttachmentsDetailResponsePojo;
import fyp.canteen.fypcore.utils.genericfile.FilePathConstants;
import fyp.canteen.fypcore.utils.genericfile.FilePathMapping;
import fyp.canteen.fypcore.utils.genericfile.GenericFileUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodPictureServiceImpl implements FoodPictureService{

    private final TemporaryAttachmentsDetailMapper temporaryAttachmentsDetailMapper;
    private final GenericFileUtil genericFileUtil;
    private final FoodPictureRepo foodPictureRepo;
    @Override
    public void saveFoodPicture(FoodPictureRequestPojo requestPojo, FoodMenu foodMenu) {

            if(requestPojo.getPhotoId() !=null)
                savePictureToPath(requestPojo.getPhotoId(), foodMenu);

    }

    @Override
    public void deleteFoodPictureById(Long id) {
        foodPictureRepo.deleteById(id);
    }

    private void savePictureToPath(Long id, FoodMenu foodMenu){
        try {
            foodPictureRepo.deleteAllByFoodMenuId(foodMenu.getId());
            TemporaryAttachmentsDetailResponsePojo temporaryAttachmentsById = temporaryAttachmentsDetailMapper.getTemporaryAttachmentsById(id);

            String path = genericFileUtil.copyFileToServer(temporaryAttachmentsById.getLocation(), FilePathMapping.FOOD_FILE, FilePathConstants.TEMP_PATH);
            foodPictureRepo.save(FoodMenuPicture
                    .builder()
                    .fileName(temporaryAttachmentsById.getName())
                    .filePath(path)
                    .fileType(temporaryAttachmentsById.getFileType())
                    .fileSize(temporaryAttachmentsById.getFileSize())
                    .foodMenu(foodMenu)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showFoodPictureById(HttpServletResponse response, Long id) {
        String photoPath = foodPictureRepo.findById(id).orElseThrow(() -> new RuntimeException("Staff not found")).getFilePath();
        try {
            genericFileUtil.getFileFromFilePath(photoPath, response);
        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }
    }
}
