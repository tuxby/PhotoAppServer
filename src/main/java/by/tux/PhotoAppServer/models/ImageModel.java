package by.tux.PhotoAppServer.models;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "photoappImages")
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String contentType;

    private Long size;

    @Lob
    private byte[] data;

}