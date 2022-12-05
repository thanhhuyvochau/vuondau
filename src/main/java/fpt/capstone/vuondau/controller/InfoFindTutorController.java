package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.dto.InfoFindTutorDto;
import fpt.capstone.vuondau.entity.request.PanoRequest;
import fpt.capstone.vuondau.entity.response.GetPanoResponse;
import fpt.capstone.vuondau.service.IInfoFindTutorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/register-find-tutor")
public class InfoFindTutorController {

private final IInfoFindTutorService iInfoFindTutorService ;

    public InfoFindTutorController(IInfoFindTutorService iInfoFindTutorService) {
        this.iInfoFindTutorService = iInfoFindTutorService;
    }

    @Operation(summary = "Tạo mới Pano")
    @PostMapping
    public ResponseEntity<ApiResponse<Boolean>>registerFindTutor (@Nullable InfoFindTutorDto infoFindTutorDto) {
        return ResponseEntity.ok(ApiResponse.success(iInfoFindTutorService.registerFindTutor(infoFindTutorDto)));
    }


}
