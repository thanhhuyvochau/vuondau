package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.PanoRequest;
import fpt.capstone.vuondau.entity.response.GetPanoResponse;
import fpt.capstone.vuondau.service.PanoService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/pano")
public class PanoController {

    private final PanoService panoService;


    public PanoController(PanoService panoService) {
        this.panoService = panoService;
    }

    @Operation(summary = "Tạo mới Pano")
    @PostMapping
    public ResponseEntity<ApiResponse<GetPanoResponse>> createNewPano(@Valid @RequestBody PanoRequest panoRequest) {
        return ResponseEntity.ok(ApiResponse.success(panoService.createNewPano(panoRequest), "Add Successful Pano"));
    }



}
