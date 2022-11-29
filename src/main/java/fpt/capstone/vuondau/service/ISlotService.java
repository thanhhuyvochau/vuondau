package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.dto.FeedBackDto;
import fpt.capstone.vuondau.entity.dto.SlotDto;
import fpt.capstone.vuondau.entity.request.CourseSearchRequest;
import fpt.capstone.vuondau.entity.request.RequestSearchRequest;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.CourseDetailResponse;
import fpt.capstone.vuondau.entity.response.RequestFormResponese;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISlotService {


    List<SlotDto> getAllSlot();
}
