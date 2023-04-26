package by.tux.PhotoAppServer.auth.model;

import lombok.Data;

@Data
public class UserResponseModel {

    private String login;
    private String name;
    private String disc;
    private String imageUrl;

    public UserResponseModel(UserModel userModel) {
        this.login = userModel.getLogin();
        this.name = userModel.getName();
        this.disc = userModel.getDisc();
        this.imageUrl = userModel.getImageUrl();
    }
}
