package by.tux.instagram160.models;

import javax.persistence.*;
//import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "instagram160_user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "person_name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "disc")
    private String disc;

    @Column(name = "main_url")
    private String mainPhoto;

}
