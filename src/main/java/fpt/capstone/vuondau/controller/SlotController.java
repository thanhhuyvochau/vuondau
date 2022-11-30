package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.dto.SlotDto;
import fpt.capstone.vuondau.service.ISlotService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/slot")
public class SlotController {

    private final ISlotService iSlotService ;

    public SlotController(ISlotService iSlotService) {
        this.iSlotService = iSlotService;
    }

    @Operation(summary = "Lấy tất cảs lot")
    @GetMapping
    public ResponseEntity<ApiResponse<List<SlotDto>>> getAllSlot() {
        return ResponseEntity.ok(ApiResponse.success(iSlotService.getAllSlot()));
    }



}
