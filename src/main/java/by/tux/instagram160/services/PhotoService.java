package by.tux.instagram160.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import by.tux.instagram160.models.PhotoModel;
import by.tux.instagram160.repos.PhotoRepository;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public void addPhoto(MultipartFile photo, Long autorId) throws IOException {
        PhotoModel photoModel = new PhotoModel();
        photoModel.setName(StringUtils.cleanPath(photo.getOriginalFilename()));
        photoModel.setContentType(photo.getContentType());
        photoModel.setData(photo.getBytes());
        photoModel.setSize(photo.getSize());

        photoModel.setAuthorId(autorId);
        photoModel.setLikes(0);

        photoRepository.save(photoModel);
    }

    public Optional<PhotoModel> getPhoto(Long id) {
        return photoRepository.findById(id);
    }

    public List<PhotoModel> getAllFiles() {
        return photoRepository.findAll();
    }

    public List<PhotoModel> getByAuthorId(Long authorId) {
        return photoRepository.findByAuthorId(authorId);
    }


    public boolean addLike(Long id) {
        Optional<PhotoModel> photoModelOptional = photoRepository.findById(id);
        if (!photoModelOptional.isPresent()) {
            return false;
        }
        PhotoModel photoModel = photoModelOptional.get();
        photoModel.setLikes(photoModel.getLikes()+1);
        photoRepository.save(photoModel);
        return true;
    }

    public void delPhoto(Long id) {
        photoRepository.deleteById(id);
    }

    public boolean editPhoto(Long id, MultipartFile photo, Long autorId) throws IOException {
        Optional<PhotoModel> photoModelOptional = photoRepository.findById(id);
        if (!photoModelOptional.isPresent()) {
            return false;
        }
        PhotoModel photoModel = photoModelOptional.get();
        photoModel.setName(StringUtils.cleanPath(photo.getOriginalFilename()));
        photoModel.setContentType(photo.getContentType());
        photoModel.setData(photo.getBytes());
        photoModel.setSize(photo.getSize());

        photoModel.setAuthorId(autorId);
        photoModel.setLikes(0);

        photoRepository.save(photoModel);
        return true;
    }
}
