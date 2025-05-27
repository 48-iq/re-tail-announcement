package dev.ilya_anna.announcement_service.services;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.ilya_anna.announcement_service.entities.Image;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;

@Service
public class S3ImageService implements ImageService {
  @Autowired
  private MinioClient minioClient;

  @Autowired
  private UuidService uuidService;

  @Value("${app.s3.bucket.name}")
  private String bucketName;

  @Override
  public Resource getImageById(String id) {
    try {
      
      GetObjectResponse response = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(id + ".jpg").build());
      return new InputStreamResource(response);
    } catch (ErrorResponseException | InsufficientDataException | InternalException | 
        InvalidKeyException | InvalidResponseException | IOException |NoSuchAlgorithmException | 
        ServerException | XmlParserException e) {
      throw new RuntimeException(e);  
    }

  }

  @Override
  public Image uploadImage(MultipartFile file) {
    try {
      String id = uuidService.generate();
      minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object( id + ".jpg").stream(file.getInputStream(), file.getSize(), -1).build());
      return Image.builder().id(id).path(file.getOriginalFilename()).build();
    } catch (ErrorResponseException | InsufficientDataException | InternalException | 
        InvalidKeyException | InvalidResponseException | IOException |NoSuchAlgorithmException | 
        ServerException | XmlParserException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteImage(String id) {
    try {
      minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(id + ".jpg").build());
    } catch (ErrorResponseException | InsufficientDataException | InternalException | 
        InvalidKeyException | InvalidResponseException | IOException |NoSuchAlgorithmException | 
        ServerException | XmlParserException e) {
      throw new RuntimeException(e);
    }
  }
  
}
