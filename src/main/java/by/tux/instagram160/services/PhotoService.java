package by.tux.instagram160.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import by.tux.instagram160.models.helpers.PhotoResponse;
import by.tux.instagram160.models.PhotoModel;
import by.tux.instagram160.repos.PhotoRepository;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final FirebaseImageService firebaseImageService;

    @Autowired
    public PhotoService(PhotoRepository photoRepository, FirebaseImageService firebaseImageService) {
        this.photoRepository = photoRepository;
        this.firebaseImageService = firebaseImageService;
    }

    private PhotoResponse mapToPhotoResponse(PhotoModel photoModel) {
        PhotoResponse photoResponse = new PhotoResponse(photoModel);
        return photoResponse;
    }

    public List<PhotoResponse> getAllPhoto() {
        return photoRepository.findAll()
                .stream()
                .map(this::mapToPhotoResponse)
                .collect(Collectors.toList());
    }

    public void addPhoto(MultipartFile photo, Long autorId) throws Exception {
        PhotoModel photoModel = new PhotoModel();
        String photoUrl = firebaseImageService.save(photo);
        photoModel.setPhotoUrl(photoUrl);
        photoModel.setAuthorId(autorId);
        photoModel.setLikes(0);

        photoRepository.save(photoModel);
    }

    public ResponseEntity<PhotoResponse> getPhotoById(Long id) {
        PhotoModel photoModel = photoRepository.findPhotoModelById(id);
        if (photoModel==null) {
            return null;
        }
        PhotoResponse photoResponse = new PhotoResponse(photoModel);

        return ResponseEntity.ok()
                .body(photoResponse);
    }

    public List<PhotoResponse> getPhotoByAuthorId(Long authorId) {
        return photoRepository.findPhotoModelByAuthorId(authorId)
                .stream()
                .map(this::mapToPhotoResponse)
                .collect(Collectors.toList());
    }

    public boolean addPhotoLikeById(Long id) {
        PhotoModel photoModel = photoRepository.findPhotoModelById(id);
        if (photoModel==null) {
            return false;
        }
        photoModel.setLikes(photoModel.getLikes()+1);
        photoRepository.save(photoModel);
        return true;
    }

    public void delPhotoById(Long id) {
        photoRepository.deleteById(id);
    }

    public boolean editPhotoById(Long id, MultipartFile photo, Long autorId) throws Exception {
        PhotoModel photoModel = photoRepository.findPhotoModelById(id);
        if (photoModel==null) {
            return false;
        }
        String photoUrl = firebaseImageService.save(photo);
        photoModel.setPhotoUrl(photoUrl);
        photoModel.setAuthorId(autorId);
        photoModel.setLikes(0);

        photoRepository.save(photoModel);
        return true;
    }

    public Long getPhotoLikesById(Long id) {
        PhotoModel photoModel = photoRepository.findPhotoModelById(id);
        if (photoModel==null) {
            return null;
        }
        return photoModel.getLikes();
    }
}
