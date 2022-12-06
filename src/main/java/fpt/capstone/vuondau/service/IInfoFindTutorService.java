package fpt.capstone.vuondau.service;


import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.dto.InfoFindTutorDto;
import fpt.capstone.vuondau.entity.request.PanoRequest;
import fpt.capstone.vuondau.entity.response.InfoFindTutorResponse;
import org.springframework.data.domain.Pageable;

public interface IInfoFindTutorService {


    Boolean registerFindTutor(InfoFindTutorDto infoFindTutorDto);

    ApiPage<InfoFindTutorResponse> getAllRegisterFindTutor(Pageable pageable);
}
