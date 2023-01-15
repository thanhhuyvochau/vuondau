package fpt.capstone.vuondau.controller;


import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.dto.RequestFormDto;
import fpt.capstone.vuondau.entity.dto.RequestFormReplyDto;
import fpt.capstone.vuondau.entity.dto.RequestTypeDto;
import fpt.capstone.vuondau.entity.request.RequestFormSearchRequest;
import fpt.capstone.vuondau.entity.response.RequestFormReplyResponse;
import fpt.capstone.vuondau.entity.response.RequestFormResponse;
import fpt.capstone.vuondau.service.IRequestFormService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/requests")
public class RequestFormController {
    private final IRequestFormService iRequestFormService;

    public RequestFormController(IRequestFormService iRequestFormService) {
        this.iRequestFormService = iRequestFormService;
    }


    @Operation(summary = "Học sinh nộp gưi form request cho hệ thống ")
    @PostMapping("/student")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<ApiResponse<RequestFormResponse>> uploadRequestForm(@ModelAttribute RequestFormDto requestFormDto) {
        return ResponseEntity.ok(ApiResponse.success(iRequestFormService.uploadRequestForm(requestFormDto)));
    }

    @Operation(summary = "Admin search request")
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<ApiResponse<ApiPage<RequestFormResponse>>> searchRequestForm(RequestFormSearchRequest searchRequestForm, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iRequestFormService.searchRequestForm(searchRequestForm ,pageable)));
    }


    @Operation(summary = "Admin xem tất cả request")
    @GetMapping
    public ResponseEntity<ApiResponse<ApiPage<RequestFormResponse>>> getAllRequestForm(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iRequestFormService.getAllRequestForm(pageable)));
    }

    @Operation(summary = "Admin xem chi tiết request của học sinh")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RequestFormResponse>> getRequestDetail(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(iRequestFormService.getRequestDetail(id)));
    }

    @Operation(summary = "lấy tất cả request Type ")
    @GetMapping("/request-type")
    public ResponseEntity<ApiResponse<List<RequestTypeDto>>> getRequestType() {
        return ResponseEntity.ok(ApiResponse.success(iRequestFormService.getRequestType()));
    }


    @Operation(summary = "Admin trả lời request của hs ")
    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<ApiResponse<RequestFormReplyResponse>> replyRequest(@PathVariable Long id , @ModelAttribute RequestFormReplyDto requestFormReplyDto ) {
        return ResponseEntity.ok(ApiResponse.success(iRequestFormService.replyRequest(id, requestFormReplyDto)));
    }



}
