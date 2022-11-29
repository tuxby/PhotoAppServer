package by.tux.instagram160.controller;

import by.tux.instagram160.models.UserModel;
import by.tux.instagram160.repos.UserRepository;
import by.tux.instagram160.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public boolean createUser(@RequestParam String login,
                              @RequestParam String password,
                              @RequestParam String name,
                              @RequestParam String lastName,
                              @RequestParam String mainPhoto,
                              @RequestParam String disc){
        return userService.createUser(login, password,
                name, lastName, mainPhoto, disc);
    }
    @PostMapping("/login")
    public UserModel login(@RequestParam String login,
                           @RequestParam String password){
        return userService.login(login, password);
    }
    @GetMapping("/getById/{id}")
    public UserModel getById(@PathVariable long id){
        return userRepository.findById(id);
    }

    @GetMapping("/getByLogin/{login}")
    public UserModel getByLogin(@PathVariable String login){
        return userRepository.findUserModelByLogin(login);
    }

}
