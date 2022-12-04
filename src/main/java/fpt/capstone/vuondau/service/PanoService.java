package fpt.capstone.vuondau.service;


import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.dto.PanoDto;
import fpt.capstone.vuondau.entity.request.PanoRequest;
import fpt.capstone.vuondau.entity.response.GetPanoResponse;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface PanoService {


    GetPanoResponse createNewPano(PanoRequest panoRequest);


    ApiPage<GetPanoResponse> getAllPano(Pageable pageable);

    List<GetPanoResponse> getPanos();

    Long deletePano(Long id);

    GetPanoResponse updatePano(Long id, PanoDto panoDto);
}
