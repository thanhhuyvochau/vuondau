package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.dto.FeedBackDto;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.*;
import org.springframework.data.domain.Pageable;

public interface ICartService {


    CartResponse addCourseIntoCart(long courseId , Long studentId);

    CartCourseResponse getCourseIntoCart(long studentId);
}
