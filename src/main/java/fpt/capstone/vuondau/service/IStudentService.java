package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.dto.RequestFormDto;
import fpt.capstone.vuondau.entity.response.RequestFormResponse;

public interface IStudentService {

    RequestFormResponse uploadRequestForm( Long id ,RequestFormDto requestFormDto);
}
