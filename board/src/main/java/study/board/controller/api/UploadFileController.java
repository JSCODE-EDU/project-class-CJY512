package study.board.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import study.board.service.AmazonS3Service;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UploadFileController {

    private final AmazonS3Service amazonS3Service;

    @PostMapping("/upload")
    public void uploadFile(@RequestParam("category") String category, @RequestPart("file") MultipartFile multipartFile) {
        amazonS3Service.saveUploadFile(category, multipartFile);
    }
}
