package by.tux.PhotoAppServer.auth.controller;

import by.tux.PhotoAppServer.auth.model.UserResponseModel;
import by.tux.PhotoAppServer.auth.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserResponseModel getUserInfoById(@PathVariable Long id, Principal principal) {
        UserResponseModel userResponseModel = userService.getUserInfoById(id);
        System.out.println("\u001B[34m" + "Запрос /"+ id + " от: " + principal.getName() + "\u001B[0m");
        return userResponseModel;
    }

    @GetMapping("/myprofile")
    public UserResponseModel getMyProfile(Principal principal) {
        UserResponseModel userResponseModel = userService.getUserInfoByLogin(principal.getName());
        System.out.println("\u001B[34m" + "Запрос /myProfile от: " + principal.getName() + "\u001B[0m");
        return userResponseModel;
    }

    @PostMapping("/edit")
    public String getMyProfile(@RequestParam(name = "login") String login,
                               @RequestParam(name = "name") String name,
                               @RequestParam(name = "disc") String disc,
                               @RequestParam(name = "imageUrl") String imageUrl,
                               Principal principal) {
        if (!login.equals(principal.getName())){
            System.out.println("\u001B[34m" + "/edit : Login in token not equals with request param (" + principal.getName() + ","+ login + ") \u001B[0m");
            return "fail";
        }
        System.out.println("\u001B[34m" + "Запрос /edit от: " + principal.getName() + "\u001B[0m");
        return userService.editUser(login, name, disc, imageUrl);
    }

}
