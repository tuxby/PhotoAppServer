package by.tux.PhotoAppServer.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import by.tux.PhotoAppServer.models.ImageModel;
import by.tux.PhotoAppServer.repos.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void addImage(MultipartFile image) throws IOException {
        ImageModel photoModel = new ImageModel();
        photoModel.setName(StringUtils.cleanPath(image.getOriginalFilename()));
        photoModel.setContentType(image.getContentType());
        photoModel.setData(image.getBytes());
        photoModel.setSize(image.getSize());

        imageRepository.save(photoModel);
    }

    public Optional<ImageModel> getImage(String filename) {
        return imageRepository.findByName(filename);
//        return imageRepository.findById(id);
    }

    public List<ImageModel> getAllFiles() {
        return imageRepository.findAll();
    }

    public void delImage(Long id) {
        imageRepository.deleteById(id);
    }

    public boolean editImage(Long id, MultipartFile image) throws IOException {
        Optional<ImageModel> photoModelOptional = imageRepository.findById(id);
        if (!photoModelOptional.isPresent()) {
            return false;
        }
        ImageModel photoModel = photoModelOptional.get();
        photoModel.setName(StringUtils.cleanPath(image.getOriginalFilename()));
        photoModel.setContentType(image.getContentType());
        photoModel.setData(image.getBytes());
        photoModel.setSize(image.getSize());

        imageRepository.save(photoModel);
        return true;
    }
}