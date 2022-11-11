package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.request.RequestFormDto;
import fpt.capstone.vuondau.entity.response.RequestFormResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IStudentService {

    RequestFormResponse uploadRequestForm( RequestFormDto requestFormDto);
}
