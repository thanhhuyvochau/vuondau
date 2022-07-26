package fpt.capstone.vuondau.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.dto.EmailDto;
import fpt.capstone.vuondau.entity.dto.ProvincesDto;
import fpt.capstone.vuondau.entity.dto.ResourceDto;
import fpt.capstone.vuondau.entity.request.AccountDetailRequest;
import fpt.capstone.vuondau.entity.request.UploadAvatarRequest;
import fpt.capstone.vuondau.entity.response.AccountDetailResponse;
import fpt.capstone.vuondau.entity.response.GenderResponse;
import fpt.capstone.vuondau.service.IAccountDetailService;
import fpt.capstone.vuondau.service.ISendMailService;
import fpt.capstone.vuondau.util.GenderUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/account-profile")
public class AccountProfileController {


    private final RestTemplate restTemplate;
    private final IAccountDetailService iAccountDetailService;

    private final ISendMailService iSendMailService ;

    @Value("${provinces}")
    private String province;

    public AccountProfileController(RestTemplate restTemplate, IAccountDetailService iAccountDetailService, ISendMailService iSendMailService) {
        this.restTemplate = restTemplate;
        this.iAccountDetailService = iAccountDetailService;
        this.iSendMailService = iSendMailService;
    }

    @Operation(summary = "Đăng ký làm gia sư cho vườn đậu")
    @PostMapping("/register-tutor")
    public ResponseEntity<ApiResponse<Long>> registerTutor(@RequestBody AccountDetailRequest accountDetailRequest) {
        return ResponseEntity.ok(ApiResponse.success(iAccountDetailService.registerTutor(accountDetailRequest)));
    }

    @Operation(summary = "upload dại diện - bằng cấp - CMMD.CDCC  để đk giảng dạy")
    @PostMapping("/{id}/image-register-profile")
    public ResponseEntity<List<ResourceDto>> uploadImageRegisterProfile(@PathVariable Long id, @ModelAttribute UploadAvatarRequest uploadImageRequest ) throws IOException {
        return ResponseEntity.ok(iAccountDetailService.uploadImageRegisterProfile(id, uploadImageRequest));
    }

    @Operation(summary = "Admin phê duyệt request đăng ký giang dạy của giao vien")
    @PostMapping("/{id}/approve-request-register-profile")
    public ResponseEntity<List<EmailDto>> approveRegisterAccount(@RequestBody List<Long> id) {
        return ResponseEntity.ok(iAccountDetailService.approveRegisterAccount(id));
    }

    @GetMapping
    @Operation(summary = "Lấy tất cả request tạo tk để đang ký giang dạy theo trạng thái (đã duyệt/chờ)")
    public ResponseEntity<ApiResponse<ApiPage<AccountDetailResponse>>> getRequestToActiveAccount(  Boolean isActive   , Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iAccountDetailService.getRequestToActiveAccount(isActive , pageable)));
    }

    @GetMapping("/genders")
    @Operation(summary = "Lấy tất cả giới tính")
    public ResponseEntity<ApiResponse<List<GenderResponse>>> getGenderAsList() {
        return ResponseEntity.ok(ApiResponse.success(GenderUtil.getGendersAsList()));
    }

    @GetMapping("/provinces")
    @Operation(summary = "Lấy tât cả tỉnh thành ở viêt nam")
    public List<ProvincesDto> getProvinces() throws JsonProcessingException {

        ResponseEntity<List<ProvincesDto>> response = restTemplate.exchange("https://provinces.open-api.vn/api/?depth=1", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ProvincesDto>>() {});

        return response.getBody();
    }


    @PostMapping("/sendMail")
    public ResponseEntity<ApiResponse<Boolean>> sendMail(List<EmailDto> emailDto) {
        return ResponseEntity.ok(ApiResponse.success(iSendMailService.sendMail( emailDto )));

    }

    @GetMapping("/{accountId}/account-detail")
    @Operation(summary = "Lấy tất cả thông tin của giáo viên")
    public ResponseEntity<ApiResponse<AccountDetailResponse>> getAccountDetail(@PathVariable Long accountId) {
        return ResponseEntity.ok(ApiResponse.success(iAccountDetailService.getAccountDetail(accountId)));
    }


}