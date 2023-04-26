package by.tux.PhotoAppServer.controller;

import by.tux.PhotoAppServer.auth.model.UserResponseModel;
import by.tux.PhotoAppServer.auth.service.UserService;
import by.tux.PhotoAppServer.models.PostModel;
import by.tux.PhotoAppServer.models.PostResponseModel;
import by.tux.PhotoAppServer.services.PostService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/feed")
    public List<PostResponseModel> getFeed(){
        return postService.feed();
    }

    @GetMapping("/my")
    public List<PostResponseModel> getMyFeed(Principal principal){
        return postService.myFeed(principal.getName());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPost(@RequestParam("disc") String disc,
                                          @RequestParam("imageUrl") String imageUrl,
                                          Principal principal) {
        try {
            PostModel postModel = new PostModel();
            postModel.setDisc(disc);
            postModel.setImageUrl(imageUrl);
            postModel.setAuthorId(userService.getUserIdByLogin(principal.getName()));
            postModel.setPublishTime(System.currentTimeMillis());
            postModel.setLikes(0);
            postService.addPost(postModel);

            return ResponseEntity.status(HttpStatus.OK)
                    .body("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("fail");
        }
    }

    @GetMapping(path = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public PostResponseModel getPost(@PathVariable Long id){
        Optional<PostModel> postModelOptional = postService.getPost(id);
        if (postModelOptional.isPresent()){
            PostModel postModel = postModelOptional.get();
            UserResponseModel userResponseModel = userService.getUserInfoById(postModel.getAuthorId());
            PostResponseModel postResponseModel = new PostResponseModel(postModel,
                                                                        userResponseModel.getLogin(),
                                                                        userResponseModel.getName());
            return  postResponseModel;
        }
        return null;
    }
}
