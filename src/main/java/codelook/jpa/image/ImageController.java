package codelook.jpa.image;

import codelook.jpa.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/images")
class ImageController {
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    // Endpoint to fetch the image for a book
    @GetMapping("/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
        try {
            byte[] imageBytes = imageService.getImage("images", imageName);
            if(imageName.endsWith(".jpg") || imageName.endsWith(".jpeg")){
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(imageBytes);
            }else if(imageName.endsWith(".png")){
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(imageBytes);
            }else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
            }
        } catch (IOException e) {
            return getImage("notfound.jpg");
        }
    }
}
