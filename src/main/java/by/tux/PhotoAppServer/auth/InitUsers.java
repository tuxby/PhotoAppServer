package by.tux.PhotoAppServer.auth;

import by.tux.PhotoAppServer.auth.model.UserModel;
import by.tux.PhotoAppServer.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

//@Service
public class InitUsers {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public InitUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void createDefaultsUsers() {

        System.out.println("Create demo users");

        UserModel userModel = new UserModel();
        userModel.setLogin("user");
        userModel.setPassword(passwordEncoder.encode("password"));
        userModel.setRole("USER");
        userRepository.save(userModel);

    }
}
