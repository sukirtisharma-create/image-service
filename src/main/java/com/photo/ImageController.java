package com.photo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    @PostMapping(value = "/grayscale", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> grayscale(@RequestParam("file") MultipartFile file) throws IOException {

        BufferedImage input = ImageIO.read(file.getInputStream());

        BufferedImage gray = new BufferedImage(
                input.getWidth(),
                input.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY
        );
        gray.getGraphics().drawImage(input, 0, 0, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(gray, "png", baos);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=output.png")
                .contentType(MediaType.IMAGE_PNG)
                .body(baos.toByteArray());
    }
}