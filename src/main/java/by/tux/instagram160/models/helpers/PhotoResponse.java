package by.tux.instagram160.models.helpers;

import by.tux.instagram160.models.PhotoModel;
import by.tux.instagram160.services.FirebaseImageService;
import lombok.Data;

@Data
public class PhotoResponse {
    private Long id;
    private String photoUrl;
    private long authorId;
    private long likes;

    public PhotoResponse(PhotoModel photoModel) {
        id = photoModel.getId();
        FirebaseImageService firebaseImageService = new FirebaseImageService();
        photoUrl = firebaseImageService.getPhotoUrl(photoModel.getPhotoUrl());
        authorId = photoModel.getAuthorId();
        likes = photoModel.getLikes();
    }
}
