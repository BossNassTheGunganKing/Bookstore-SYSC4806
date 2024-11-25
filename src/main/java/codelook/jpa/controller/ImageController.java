package codelook.jpa.controller;

import codelook.jpa.service.ImageService;
import codelook.jpa.utils.ErrorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {
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
