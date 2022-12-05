package fpt.capstone.vuondau.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.entity.dto.ResourceDto;
import fpt.capstone.vuondau.entity.request.UploadAvatarRequest;
import fpt.capstone.vuondau.entity.response.CartCourseResponse;
import fpt.capstone.vuondau.entity.response.CartResponse;

public interface IResourceService {


    ResourceDto uploadFile(UploadAvatarRequest uploadFile);
}
