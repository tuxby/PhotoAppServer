package by.tux.PhotoAppServer.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import by.tux.PhotoAppServer.models.ImageModel;
import by.tux.PhotoAppServer.services.ImageService;
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
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image) {
        try {
            imageService.addImage(image);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("fail"));
        }
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String fileName) {
        Optional<ImageModel> imageModelOptional = imageService.getImage(fileName);

        if (!imageModelOptional.isPresent()) {
            return ResponseEntity.notFound()
                    .build();
        }ImageModel imageModel = imageModelOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageModel.getName() + "\"")
                .contentType(MediaType.valueOf(imageModel.getContentType()))
                .body(imageModel.getData());
    }

    @GetMapping("/del/{id}")
    public ResponseEntity<String>delete(@PathVariable Long id){
        imageService.delImage(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/edit/{id}")
    public boolean editImage(@PathVariable Long id,@RequestParam("image") MultipartFile image,@RequestParam("message_id") Long messageId) {
        try{
            return imageService.editImage(id , image);
        }
        catch (Exception ex){
            return false;
        }
    }
}
