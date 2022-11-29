package by.tux.instagram160.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "instagram160_photos")
public class PhotoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String contentType;

    private Long size;

    @Lob
    private byte[] data;

    @Column(name = "autor_id")
    private long authorId;

    @Column(name = "likes")
    private long likes;
}
