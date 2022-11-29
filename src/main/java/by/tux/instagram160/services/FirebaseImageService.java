package by.tux.instagram160.services;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import java.io.FileInputStream;

@Service
public class FirebaseImageService {
    private Storage storage = StorageOptions.getDefaultInstance().getService();

    public String save(MultipartFile multipartFile) throws Exception{
        String imageName = String.valueOf(System.currentTimeMillis())
                +"."
                +StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        BlobId blobId = BlobId.of("instagram160.appspot.com", imageName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(multipartFile.getContentType())
                .build();

        storage = StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.
                        fromStream(new FileInputStream("instagram160-firebase-adminsdk-nxd8m-8a9d6f11eb.json")))
                .build()
                .getService();

        storage.create(blobInfo, multipartFile.getInputStream());
        return imageName;
    }
    public String getPhotoUrl(String name){
        return "https://firebasestorage.googleapis.com/v0/b/instagram160.appspot.com/o/" + name + "?alt=media&token=83b88a1e-eccb-4fd7-a9a0-0c1a792182cb";
    }
}
