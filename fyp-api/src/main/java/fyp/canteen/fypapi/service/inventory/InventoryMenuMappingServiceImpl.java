package fyp.canteen.fypapi.service.inventory;

import fyp.canteen.fypapi.repository.foodmgmt.FoodMenuRepo;
import fyp.canteen.fypapi.repository.inventory.InventoryMenuMappingRepo;
import fyp.canteen.fypapi.service.food.FoodMenuService;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import fyp.canteen.fypcore.model.entity.inventory.InventoryMenuMapping;
import fyp.canteen.fypcore.pojo.inventory.InventoryMenuRequestPojo;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import fyp.canteen.fypcore.utils.pagination.CustomPaginationHandler;
import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryMenuMappingServiceImpl implements InventoryMenuMappingService{
    private final InventoryMenuMappingRepo inventoryMenuMappingRepo;
    private final FoodMenuRepo foodMenuRepo;
    private final FoodMenuService foodMenuService;
    private final CustomPaginationHandler customPaginationHandler;
    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();

    @Override
    @Transactional
    public void saveInventoryMenuMapping(InventoryMenuRequestPojo requestPojo) {
        if(requestPojo.getStock() <=0)
            throw new AppException("Instead of 0, please delete the record.");
        InventoryMenuMapping inventoryMenuMapping = new InventoryMenuMapping();
        boolean found = false;
        if(requestPojo.getId() != null)
            inventoryMenuMapping = inventoryMenuMappingRepo.findById(requestPojo.getId()).orElse(inventoryMenuMapping);

        if(inventoryMenuMapping.getId() != null)
            found = true;




        if(found){
            int differenceQuantity = Math.abs(requestPojo.getStock() - inventoryMenuMapping.getStock());


            if(requestPojo.getStock() > inventoryMenuMapping.getStock())
                requestPojo.setRemainingQuantity(inventoryMenuMapping.getRemainingQuantity() + differenceQuantity);
            else if(requestPojo.getStock() < inventoryMenuMapping.getStock())
                requestPojo.setRemainingQuantity(inventoryMenuMapping.getRemainingQuantity() - differenceQuantity);
            else
                requestPojo.setRemainingQuantity(inventoryMenuMapping.getRemainingQuantity());

            if(requestPojo.getRemainingQuantity() < 0)
                throw new AppException("Sorry but the quantity is in negative and someone already bought some");
        }else
            inventoryMenuMapping.setFoodMenu(FoodMenu.builder().id(requestPojo.getFoodId()).build());


        try{
            beanUtilsBean.copyProperties(inventoryMenuMapping, requestPojo);
        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }

        inventoryMenuMappingRepo.save(inventoryMenuMapping);

            FoodMenu foodMenu = foodMenuService.findById(requestPojo.getFoodId());
            boolean isAvailable = foodMenu.getIsAvailableToday();
        if(requestPojo.getRemainingQuantity() > 0 && !isAvailable)
            setAndSaveAvailabilityOfFoodMenu(true, foodMenu);
        else if(requestPojo.getRemainingQuantity() == 0 && isAvailable)
            setAndSaveAvailabilityOfFoodMenu(false, foodMenu);


    }

    void setAndSaveAvailabilityOfFoodMenu(boolean isAvailable, FoodMenu foodMenu){
        foodMenu.setIsAvailableToday(isAvailable);
        foodMenuRepo.save(foodMenu);
    }

    @Override
    public PaginationResponse getInventoryMenuMappingPaginated(PaginationRequest request) {
        return customPaginationHandler.getPaginatedData(inventoryMenuMappingRepo.getMenuMappingPaginated(Pageable.unpaged()));
    }
}
