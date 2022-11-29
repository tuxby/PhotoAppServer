package by.tux.instagram160.services;

import by.tux.instagram160.models.UserModel;
import by.tux.instagram160.repos.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public boolean createUser(String login, String password,
                              String name, String lastName,
                              String mainPhoto, String disc){
        UserModel userModel;
        userModel = userRepository.findUserModelByLogin(login);
        if (userModel!=null){
            return false;
        }
        userModel = new UserModel();
        userModel.setLogin(login);
        userModel.setName(name);
        userModel.setPassword(password);
        userModel.setLastName(lastName);
        userModel.setMainPhoto(mainPhoto);
        userModel.setDisc(disc);

        userRepository.save(userModel);
        return true;
    }

    public UserModel login(String login, String password){
        UserModel userModel;
        return userRepository.findUserModelByLoginAndPassword(login, password);
    }
}
