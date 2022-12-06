package fpt.capstone.vuondau.controller;



import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.dto.InfoFindTutorDto;
import fpt.capstone.vuondau.entity.response.InfoFindTutorResponse;
import fpt.capstone.vuondau.service.IInfoFindTutorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.Nullable;

@RestController
@RequestMapping("/api/register-find-tutor")
public class InfoFindTutorController {

private final IInfoFindTutorService iInfoFindTutorService ;

    public InfoFindTutorController(IInfoFindTutorService iInfoFindTutorService) {
        this.iInfoFindTutorService = iInfoFindTutorService;
    }

    @Operation(summary = "dang-ky-tim-gia-su")
    @PostMapping
    public ResponseEntity<ApiResponse<Boolean>> registerFindTutor (@Nullable @RequestBody InfoFindTutorDto infoFindTutorDto) {
        return ResponseEntity.ok(ApiResponse.success(iInfoFindTutorService.registerFindTutor(infoFindTutorDto)));
    }

    @Operation(summary = "admin lấy form  dang-ky-tim-gia-su")
    @GetMapping
    public ResponseEntity<ApiResponse<ApiPage<InfoFindTutorResponse>>> getAllRegisterFindTutor (Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iInfoFindTutorService.getAllRegisterFindTutor(pageable)));
    }


}
