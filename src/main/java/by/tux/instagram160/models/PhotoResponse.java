package by.tux.instagram160.models;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PhotoResponse {
    private Long id;
    private String name;
    private Long size;
    private String url;
    private String contentType;

    private long authorId;
    private long likes;
}
