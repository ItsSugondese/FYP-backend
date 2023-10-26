package fyp.canteen.fypapi.service.usermgmt;

import fyp.canteen.fypapi.repository.usermgmt.UserRepo;
import fyp.canteen.fypapi.exception.AppException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceHelperImpl implements UserServiceHelper{

    private final UserRepo userRepo;
    @Override
    public void checkForUniqueEmail(String email) {
        String checkUnique = userRepo.isUnique(email.toUpperCase());
        if (checkUnique != null)
            throw new AppException(checkUnique);
    }
}
