package by.tux.instagram160.models;

//import jakarta.persistence.*;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "instagram160_photos")
public class PhotoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fileUrl")
    private String photoUrl;

    @Column(name = "autor_id")
    private long authorId;

    @Column(name = "likes")
    private long likes;
}
