package fpt.capstone.vuondau.entity.request;

import fpt.capstone.vuondau.entity.common.EResourceType;
import org.springframework.web.multipart.MultipartFile;

public class UploadAvatarRequest {


    private EResourceType resourceType;
    private MultipartFile file ;


    public EResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(EResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
