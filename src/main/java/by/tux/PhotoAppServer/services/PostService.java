package by.tux.PhotoAppServer.services;

import by.tux.PhotoAppServer.auth.model.UserResponseModel;
import by.tux.PhotoAppServer.auth.service.UserService;
import by.tux.PhotoAppServer.models.PostModel;
import by.tux.PhotoAppServer.models.PostResponseModel;
import by.tux.PhotoAppServer.repos.PostRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<PostResponseModel> feed() {
        List<PostResponseModel> postResponseModels = postRepository.findAll()
                .stream()
                .map(this::mapToPostResponseModel)
                .collect(Collectors.toList());
        return postResponseModels;
    }

    private PostResponseModel mapToPostResponseModel(PostModel postModel) {
        UserResponseModel userResponseModel = userService.getUserInfoById(postModel.getAuthorId());
        PostResponseModel postResponseModel = new PostResponseModel(postModel,
                                                                    userResponseModel.getLogin(),
                                                                    userResponseModel.getName());
        return postResponseModel;
    }

    public boolean addPost(PostModel postModel){
        postModel.setLikes(0);
        postRepository.save(postModel);
        return true;
    }

    public Optional<PostModel> getPost(long id){
        return postRepository.findById(id);
    }

    public List<PostResponseModel> myFeed(String login) {
        List<PostResponseModel> postResponseModels = postRepository.findByAuthorId(userService.getUserIdByLogin(login))
                .stream()
                .map(this::mapToPostResponseModel)
                .collect(Collectors.toList());
        return postResponseModels;
    }
}
