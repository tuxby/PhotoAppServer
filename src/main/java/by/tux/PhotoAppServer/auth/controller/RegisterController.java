package by.tux.PhotoAppServer.auth.controller;

import by.tux.PhotoAppServer.auth.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public String register(@RequestParam("login") String login,
                                @RequestParam("password") String password,
                                @RequestParam("name") String name,
                                @RequestParam("name") String disc,
                                @RequestParam("imageUrl") String imageUrl) {

        return userService.regiterUser(login, password, name, disc, imageUrl);
    }
}
