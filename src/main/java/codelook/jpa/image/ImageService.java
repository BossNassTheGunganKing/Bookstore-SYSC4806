package codelook.jpa.image;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

@Service
public class ImageService {


    public String saveImage(String directory, MultipartFile imageFile) throws IOException {
        String generatedFileName = UUID.randomUUID() + "_" + new Date().getTime();
        if(imageFile.getContentType().contains("jpeg") || imageFile.getContentType().contains("jpg")) {
            generatedFileName += ".jpg";
        }
        if(imageFile.getContentType().contains("png")){
            generatedFileName += ".png";
        }
        Path storagePath = Path.of(directory);
        Path filePath = storagePath.resolve(generatedFileName);
        if(imageFile.getContentType() != null && imageFile.getContentType().startsWith("image")) {

            if(!Files.exists(filePath)) {
                Files.createDirectories(filePath);
            }

            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING); // replace existing just in case
            return generatedFileName;
        }
        throw new IOException(imageFile.getOriginalFilename());
    }

    public byte[] getImage(String directory, String imageName) throws IOException {
        Path storagePath = Path.of(directory);
        Path filePath = storagePath.resolve(imageName);
        if(Files.exists(filePath)) {
            return Files.readAllBytes(filePath);
        }
        throw new IOException(imageName);
    }

}
