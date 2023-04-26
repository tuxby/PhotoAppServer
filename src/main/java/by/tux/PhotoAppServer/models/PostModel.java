package by.tux.PhotoAppServer.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "PhotoappPosts")
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "authorId")
    private long authorId;
    @Column(name = "disc")
    private String disc;
    @Column(name = "publishTime")
    private long publishTime;
    @Column(name = "likes")
    private long likes;
    @Column(name = "imageUrl")
    private String imageUrl;

}