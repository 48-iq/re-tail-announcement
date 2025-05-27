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
import dev.ilya_anna.announcement_service.repositories.ImageRepository;
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
import jakarta.transaction.Transactional;

@Service
public class S3ImageService implements ImageService {
  @Autowired
  private MinioClient minioClient;

  @Autowired
  private UuidService uuidService;

  @Value("${app.s3.bucket.name}")
  private String bucketName;

  @Autowired
  private ImageRepository imageRepository;

  @Override
  public Resource getImageById(String id) {
    try {
      
      GetObjectResponse response = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(id).build());
      return new InputStreamResource(response);
    } catch (ErrorResponseException | InsufficientDataException | InternalException | 
        InvalidKeyException | InvalidResponseException | IOException |NoSuchAlgorithmException | 
        ServerException | XmlParserException e) {
      throw new RuntimeException(e);  
    }

  }

  @Override
  @Transactional
  public Image uploadImage(MultipartFile file) {
    try {
      String id = uuidService.generate();
      minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(id).stream(file.getInputStream(), file.getSize(), -1).build());
      return imageRepository.save(new Image(id));
    } catch (ErrorResponseException | InsufficientDataException | InternalException | 
        InvalidKeyException | InvalidResponseException | IOException |NoSuchAlgorithmException | 
        ServerException | XmlParserException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteImage(String id) {
    try {
      minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(id).build());
    } catch (ErrorResponseException | InsufficientDataException | InternalException | 
        InvalidKeyException | InvalidResponseException | IOException |NoSuchAlgorithmException | 
        ServerException | XmlParserException e) {
      throw new RuntimeException(e);
    }
  }
  
}
