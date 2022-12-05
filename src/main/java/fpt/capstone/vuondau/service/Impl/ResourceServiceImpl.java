package fpt.capstone.vuondau.service.Impl;


import fpt.capstone.vuondau.entity.Resource;
import fpt.capstone.vuondau.entity.common.EResourceType;
import fpt.capstone.vuondau.entity.dto.ResourceDto;
import fpt.capstone.vuondau.entity.request.UploadAvatarRequest;
import fpt.capstone.vuondau.repository.ResourceRepository;
import fpt.capstone.vuondau.service.IResourceService;

import fpt.capstone.vuondau.util.ObjectUtil;
import fpt.capstone.vuondau.util.RequestUrlUtil;
import fpt.capstone.vuondau.util.adapter.MinioAdapter;
import io.minio.ObjectWriteResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;

@Service
public class ResourceServiceImpl implements IResourceService {
    private final MinioAdapter minioAdapter;
    private final ResourceRepository resourceRepository;
    @Value("${minio.url}")
    String minioUrl;

    public ResourceServiceImpl(MinioAdapter minioAdapter, ResourceRepository resourceRepository) {
        this.minioAdapter = minioAdapter;
        this.resourceRepository = resourceRepository;
    }


    @Override
    public ResourceDto uploadFile(UploadAvatarRequest uploadFile) {


        Resource save;
        try {
            String name = uploadFile.getFile().getOriginalFilename() + "-" + Instant.now().toString();
            ObjectWriteResponse objectWriteResponse = minioAdapter.uploadFile(name, uploadFile.getFile().getContentType(),
                    uploadFile.getFile().getInputStream(), uploadFile.getFile().getSize());

            Resource resource = new Resource();
            resource.setName(name);
            resource.setUrl(RequestUrlUtil.buildUrl(minioUrl, objectWriteResponse));
            resource.setResourceType(EResourceType.CONTRACT);
            save = resourceRepository.save(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ObjectUtil.copyProperties(save, new ResourceDto(), ResourceDto.class);
    }
}
