package com.ipwa.kp.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class FileService {
    public final Function<String, String> fileExtension = fileName -> Optional.of(fileName).filter(name -> name.contains("."))
            .map(name -> "." + name.substring(fileName.lastIndexOf(".") + 1)).orElse(".png");

    public final Function<MultipartFile, String> uploadCompanyLogo = image -> {
        try {
            Path absolutePath = Paths.get("").toAbsolutePath();
            Path fileStorageLocation = Paths.get(absolutePath+"/uploads").toAbsolutePath().normalize();
            String fileName = UUID.randomUUID() + fileExtension.apply(image.getOriginalFilename());
            if (!Files.exists(fileStorageLocation)) { Files.createDirectories(fileStorageLocation); }
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(fileName), REPLACE_EXISTING);
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/companies/logos/" + fileName).toUriString();
        }catch (Exception exception) {
            throw new RuntimeException("Unable to save image");
        }
    };

    public final Function<MultipartFile, String> uploadPostPdf = pdf -> {
        String pngFileName;
        try {
            Path absolutePath = Paths.get("").toAbsolutePath();
            Path fileStorageLocation = Paths.get(absolutePath+"/uploads/posts").toAbsolutePath().normalize();
            if (!Files.exists(fileStorageLocation)) { Files.createDirectories(fileStorageLocation); }
            if (".pdf".equals(fileExtension.apply(pdf.getOriginalFilename()))) {
                PDDocument document = PDDocument.load(convertMultipartFileToFile(pdf));

                PDFRenderer pdfRenderer = new PDFRenderer(document);

                BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300);
                String uniqueFileName = UUID.randomUUID() + ".png";
                String outputImagePath = fileStorageLocation + File.separator + uniqueFileName;

                ImageIO.write(bim, "png", new File(outputImagePath));

                pngFileName = uniqueFileName;

                document.close();
            } else {
                pngFileName = UUID.randomUUID() + fileExtension.apply(pdf.getOriginalFilename());
                Files.copy(pdf.getInputStream(), fileStorageLocation.resolve(pngFileName), REPLACE_EXISTING);
            }
        }catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Unable to save pdf file");
        }
        return pngFileName;
    };

    private static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        Path absolutePath = Paths.get("").toAbsolutePath();
        Path fileStorageLocation = Paths.get(absolutePath+"/uploads/posts").toAbsolutePath().normalize();

        String fileName = UUID.randomUUID().toString() + ".pdf";
        File file = new File(fileStorageLocation.toFile(), fileName);
        multipartFile.transferTo(file);
        return file;
    }

}
