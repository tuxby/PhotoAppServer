package by.tux.PhotoAppServer.auth.service;

import by.tux.PhotoAppServer.auth.constants.Roles;
import by.tux.PhotoAppServer.auth.model.UserModel;
import by.tux.PhotoAppServer.auth.model.UserResponseModel;
import by.tux.PhotoAppServer.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseModel getUserInfoById(Long id) {
        Optional<UserModel> userModelOptional = userRepository.findById(id);
        if (userModelOptional.isPresent()) {
            return new UserResponseModel(userModelOptional.get());
        }
        else {
            return null;
        }
    }

    public UserResponseModel getUserInfoByLogin(String login) {
        return new UserResponseModel(userRepository.findByLogin(login));
    }

    public String regiterUser(String login, String password,
                               String name, String disc, String imageUrl) {
        UserModel userModel;
        userModel = userRepository.findByLogin(login);
        if (userModel != null) {
            return "LoginAlreadyUsed";
        }
        userModel = new UserModel();
        userModel.setLogin(login);
        userModel.setName(name);
        userModel.setPassword(passwordEncoder.encode(password));
        userModel.setDisc(disc);
        userModel.setRole(Roles.USER);
        userModel.setImageUrl(imageUrl);

        userRepository.save(userModel);
        return "success";
    }

    public String editUser(String login, String name, String disc, String imageUrl) {
        UserModel userModel;
        userModel = userRepository.findByLogin(login);
        if (userModel == null) {
            return "LoginNotFound";
        }
        userModel.setName(name);
        userModel.setDisc(disc);
        userModel.setImageUrl(imageUrl);

        userRepository.save(userModel);
        return "success";
    }

    public long getUserIdByLogin(String login) {
        return userRepository.findByLogin(login).getId();
    }
}
