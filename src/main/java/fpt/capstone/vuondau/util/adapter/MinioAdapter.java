package fpt.capstone.vuondau.util.adapter;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.messages.Bucket;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;

@Service
public class MinioAdapter {

    private final MinioClient minioClient;

    public MinioAdapter(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Value("${minio.bucket}")
    String defaultBucketName;

    public ObjectWriteResponse uploadFile(String name, String contentType, InputStream content, long size) {
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(defaultBucketName)
                    .stream(content, size, -1)
                    .contentType(contentType)
                    .object(name)
                    .build();
            return minioClient.putObject(objectArgs);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}