package by.tux.PhotoAppServer.auth.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "PhotoappUsers")
@Data
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "name")
    private String name;
    @Column(name = "disc")
    private String disc;
    @Column(name = "imageUrl")
    private String imageUrl;
}
