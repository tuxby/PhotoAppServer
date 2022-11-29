package by.tux.instagram160.controller;

import java.util.List;

import by.tux.instagram160.models.helpers.PhotoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import by.tux.instagram160.services.PhotoService;

@RestController
@RequestMapping("/photos")
public class PhotoController {
    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping
    public ResponseEntity<String> addPhoto(@RequestParam("photo") MultipartFile photo,@RequestParam("authorId") Long authorId) {
        try {
            photoService.addPhoto(photo, authorId);
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(String.format("File uploaded successfully: %s", photo.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(String.format("Could not upload the file: %s!", photo.getOriginalFilename()));
        }
    }

    @GetMapping
    public ResponseEntity<List<PhotoResponse>> getAllPhoto() {
        return ResponseEntity.ok()
                .body(photoService.getAllPhoto());
    }

    @GetMapping("/user={authorId}")
    public ResponseEntity<List<PhotoResponse>> listByAutorId(@PathVariable Long authorId) {
        return ResponseEntity.ok()
                .body(photoService.getPhotoByAuthorId(authorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhotoResponse> getPhotoById(@PathVariable Long id) {
        return photoService.getPhotoById(id);
    }

    @GetMapping("/del/{id}")
    public ResponseEntity<String> deletePhotoById(@PathVariable Long id){
        photoService.delPhotoById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<Long> getPhotoLikesById(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(photoService.getPhotoLikesById(id));
    }

    @GetMapping("/{id}/addlike")
    public ResponseEntity<Boolean> addPhotoLikeById(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(photoService.addPhotoLikeById(id));
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<Boolean> editPhotoById(@PathVariable Long id,@RequestParam("photo") MultipartFile photo,@RequestParam("autorId") Long autorId) {
        try{
            return ResponseEntity.ok()
                    .body(photoService.editPhotoById(id , photo, autorId));
        }
        catch (Exception ex){
            return ResponseEntity.ok()
                    .body(false);
        }
    }
}
