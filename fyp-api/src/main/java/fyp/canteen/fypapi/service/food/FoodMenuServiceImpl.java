package fyp.canteen.fypapi.service.food;

import fyp.canteen.fypapi.mapper.foodmgmt.FoodMenuMapper;
import fyp.canteen.fypcore.enums.pojo.FoodFilter;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypapi.repository.foodmgmt.FoodMenuRepo;
import fyp.canteen.fypcore.enums.FoodType;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import fyp.canteen.fypcore.pojo.foodmgmt.FoodMenuRequestPojo;
import fyp.canteen.fypcore.pojo.foodmgmt.FoodPictureRequestPojo;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodMenuServiceImpl implements FoodMenuService{

    private final FoodMenuRepo foodMenuRepo;
    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();
    private final FoodPictureService foodPictureService;
    private final FoodMenuMapper foodMenuMapper;

    @Override
    @Transactional
    public void saveFoodMenu(FoodMenuRequestPojo requestPojo) {
        FoodMenu foodMenu = new FoodMenu();
        if(requestPojo.getId() != null)
            foodMenu = foodMenuRepo.findById(requestPojo.getId()).orElse(foodMenu);

        try {
            beanUtilsBean.copyProperties(foodMenu, requestPojo);
        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }

        foodMenu.setFoodType(requestPojo.getIsPackage()? FoodType.PACKAGE : FoodType.SINGLE);
        if(!requestPojo.getMenuItems().isEmpty())
            foodMenu.setFoodMenuItems((requestPojo.getMenuItems().toString()).replaceAll("[\\[\\]]", ""));
        else
            foodMenu.setFoodMenuItems(null);

        foodMenu.setIsAvailableToday(foodMenu.getIsAvailableToday()==null? true : foodMenu.getIsAvailableToday());
        FoodMenu savedFoodMenu = foodMenuRepo.saveAndFlush(foodMenu);

        foodPictureService.saveFoodPicture(
                FoodPictureRequestPojo
                        .builder()
                        .photoId(requestPojo.getPhotoId())
                        .build(), savedFoodMenu);

    }

    @Override
    public List<FoodMenuRequestPojo> getAllFoodMenu(FoodFilter foodFilter) {
        return foodMenuMapper.getAllFoodMenu(foodFilter.toString());
    }

    @Override
    public void getFoodPhoto(HttpServletResponse response, Long id) {
        foodPictureService.showFoodPictureById(response, id);
    }
}
