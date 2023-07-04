package study.board.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import study.board.service.AwsS3Service;
import study.board.utils.CommonUtils;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UploadFileController {

    private final AwsS3Service awsS3Service;

    @PostMapping("/upload")
    public List<String> uploadFile(@RequestParam("category") String category, @RequestPart("file") List<MultipartFile> multipartFiles) {
        return awsS3Service.saveUploadFile(category, multipartFiles);
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("resourcePath") String resourcePath) {
        byte[] data = awsS3Service.downloadFile(resourcePath);
        ByteArrayResource resource = new ByteArrayResource(data);
        HttpHeaders headers = buildHeaders(resourcePath, data);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(resource);
    }

    private HttpHeaders buildHeaders(String resourcePath, byte[] data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(data.length);
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentDisposition(CommonUtils.createContentDisposition(resourcePath));
        return headers;
    }
}
