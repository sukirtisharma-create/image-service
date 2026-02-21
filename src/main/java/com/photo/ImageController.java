package com.photo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.awt.image.ConvolveOp;
import java.awt.Graphics2D;
import java.awt.image.Kernel;

@RestController
@RequestMapping("/image")
public class ImageController {

   @PostMapping(value = "/grayscale", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<byte[]> grayscale(@RequestParam("file") MultipartFile file) throws IOException {

    if (file.isEmpty()) {
        throw new RuntimeException("File is empty");
    }

    BufferedImage input = ImageIO.read(file.getInputStream());

    if (input == null) {
        throw new RuntimeException("Uploaded file is not a valid image");
    }

    BufferedImage gray = new BufferedImage(
            input.getWidth(),
            input.getHeight(),
            BufferedImage.TYPE_BYTE_GRAY
    );
    gray.getGraphics().drawImage(input, 0, 0, null);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(gray, "png", baos);

    return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(baos.toByteArray());
}

@PostMapping(value = "/resize", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<byte[]> resize(
        @RequestParam("file") MultipartFile file,
        @RequestParam int width,
        @RequestParam int height) throws IOException {

    BufferedImage original = ImageIO.read(file.getInputStream());

    BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    resized.getGraphics().drawImage(original, 0, 0, width, height, null);

    return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(toPngBytes(resized));
}

@PostMapping(value = "/blur", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<byte[]> blur(@RequestParam("file") MultipartFile file)
        throws IOException {

    BufferedImage image = ImageIO.read(file.getInputStream());

    float[] kernel = {
        1f/9f, 1f/9f, 1f/9f,
        1f/9f, 1f/9f, 1f/9f,
        1f/9f, 1f/9f, 1f/9f
    };

    ConvolveOp op = new ConvolveOp(new Kernel(3, 3, kernel));
    BufferedImage blurred = op.filter(image, null);

    return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(toPngBytes(blurred));
}

@PostMapping(value = "/rotate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<byte[]> rotate(
        @RequestParam("file") MultipartFile file,
        @RequestParam double angle) throws IOException {

    BufferedImage image = ImageIO.read(file.getInputStream());

    double radians = Math.toRadians(angle);
    int w = image.getWidth();
    int h = image.getHeight();

    BufferedImage rotated = new BufferedImage(w, h, image.getType());
    Graphics2D g2d = rotated.createGraphics();

    g2d.rotate(radians, w / 2.0, h / 2.0);
    g2d.drawImage(image, 0, 0, null);
    g2d.dispose();

    return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(toPngBytes(rotated));
}
private byte[] toPngBytes(BufferedImage image) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(image, "png", baos);
    return baos.toByteArray();
}
}