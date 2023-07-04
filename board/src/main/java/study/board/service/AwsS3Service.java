package study.board.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import study.board.utils.CommonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class AwsS3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public List<String> saveUploadFile(String category, List<MultipartFile> multipartFiles) {
        List<MultipartFile> validMultipartFiles = multipartFiles.stream()
                .filter(this::validateFileExists)
                .collect(Collectors.toList());

        validateFileSize(validMultipartFiles);

        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile validMultipartFile : validMultipartFiles) {
            String fileName = CommonUtils.buildFileName(category, validMultipartFile.getOriginalFilename());

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(validMultipartFile.getSize());
            objectMetadata.setContentType(validMultipartFile.getContentType());

            try (InputStream inputStream = validMultipartFile.getInputStream()) {
                amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                fileUrls.add(amazonS3Client.getUrl(bucketName, fileName).toString());
            } catch (IOException e) {
                //TODO custom exception
                throw new RuntimeException(e);
            }
        }

        return fileUrls;
    }

    private void validateFileSize(List<MultipartFile> validMultipartFiles) {
        if (validMultipartFiles.size() > 10) {
            throw new RuntimeException("파일은 10개까지만 업로드할 수 있습니다.");
        }
    }

    public byte[] downloadFile(String resourcePath) {
        validateFileExistsAtUrl(resourcePath);

        S3Object s3Object = amazonS3Client.getObject(bucketName, resourcePath);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("aws IOUtils.toByteArray() 예외", e);
        }
    }

    private boolean validateFileExists(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            //TODO custom exception 만들기
            throw new RuntimeException("파일 없음");
        }
        return true;
    }

    private void validateFileExistsAtUrl(String resourcePath) {
        if (!amazonS3Client.doesObjectExist(bucketName, resourcePath)) {
            //TODO custom exception
            throw new RuntimeException("잘못된 경로");
        }
    }
}
