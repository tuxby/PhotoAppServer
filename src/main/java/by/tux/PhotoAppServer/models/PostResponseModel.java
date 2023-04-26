package by.tux.PhotoAppServer.models;

import lombok.Data;

@Data
public class PostResponseModel {
    private long id;
    private long authorId;
    private String  authorLogin;
    private String  authorName;
    private String disc;
    private long likes;
    private long publishTime;
    private String imageUrl;

    public PostResponseModel(PostModel postModel, String authorLogin, String authorName) {
        this.id = postModel.getId();
        this.authorId = postModel.getAuthorId();
        this.authorLogin = authorLogin;
        this.authorName = authorName;
        this.disc = postModel.getDisc();
        this.likes = postModel.getLikes();
        this.publishTime = postModel.getPublishTime();
        this.imageUrl = postModel.getImageUrl();
    }
}
