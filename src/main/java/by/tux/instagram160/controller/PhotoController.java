package by.tux.instagram160.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import by.tux.instagram160.models.PhotoModel;
import by.tux.instagram160.models.PhotoResponse;
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
    public ResponseEntity<String> upload(@RequestParam("photo") MultipartFile photo,@RequestParam("authorId") Long authorId) {
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
    public List<PhotoResponse> list() {
        return photoService.getAllFiles()
                          .stream()
                          .map(this::mapToFileResponse)
                          .collect(Collectors.toList());
    }

    @GetMapping("/user={authorId}")
    public List<PhotoResponse> listByAutorId(@PathVariable Long authorId) {
        return photoService.getByAuthorId(authorId)
                .stream()
                .map(this::mapToFileResponse)
                .collect(Collectors.toList());
    }

    private PhotoResponse mapToFileResponse(PhotoModel photoModel) {
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                                                        .path("/photos/")
                                                        .path(photoModel.getId().toString())
                                                        .toUriString();
        PhotoResponse photoResponse = new PhotoResponse();
        photoResponse.setId(photoModel.getId());
        photoResponse.setName(photoModel.getName());
        photoResponse.setContentType(photoModel.getContentType());
        photoResponse.setSize(photoModel.getSize());
        photoResponse.setUrl(downloadURL);
        photoResponse.setAuthorId(photoModel.getAuthorId());
        photoResponse.setLikes(photoModel.getLikes());

        return photoResponse;
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id) {
        Optional<PhotoModel> photoModelOptional = photoService.getPhoto(id);

        if (!photoModelOptional.isPresent()) {
            return ResponseEntity.notFound()
                                 .build();
        }
        PhotoModel photoModel = photoModelOptional.get();
        return ResponseEntity.ok()
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photoModel.getName() + "\"")
                             .contentType(MediaType.valueOf(photoModel.getContentType()))
                             .body(photoModel.getData());
    }

    @GetMapping("/del/{id}")
    public ResponseEntity<String>delete(@PathVariable Long id){
        photoService.delPhoto(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<Long> getPhotoLikes(@PathVariable Long id) {
        Optional<PhotoModel> photoModelOptional = photoService.getPhoto(id);

        if (!photoModelOptional.isPresent()) {
            return ResponseEntity.notFound()
                    .build();
        }

        PhotoModel photoModel = photoModelOptional.get();
        return ResponseEntity.ok()
                .body(photoModel.getLikes());
    }
    @GetMapping("/{id}/addlike")
    public boolean photoAddlLike(@PathVariable Long id) {
        return photoService.addLike(id);
    }

    @PostMapping("/{id}/edit")
    public boolean editPhoto(@PathVariable Long id,@RequestParam("photo") MultipartFile photo,@RequestParam("autorId") Long autorId) {
        try{
            return photoService.editPhoto(id , photo, autorId);
        }
        catch (Exception ex){
            return false;
        }
    }
}
