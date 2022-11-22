package fpt.capstone.vuondau.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.response.CartCourseResponse;
import fpt.capstone.vuondau.entity.response.CartResponse;
import fpt.capstone.vuondau.service.ICartService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final ICartService iCartService;


    public CartController(ICartService iCartService) {
        this.iCartService = iCartService;
    }

    @Operation(summary = "Hoc Sinh thêm course vào cart ")
    @PostMapping("/{courseId}")
    public ResponseEntity<ApiResponse<CartResponse>> addCourseIntoCart(@PathVariable long courseId, Long studentId) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iCartService.addCourseIntoCart(courseId, studentId)));
    }

    @Operation(summary = "Học sinh xem cart chứa các source đã add ")
    @GetMapping("/{studentId}/courses")
    public ResponseEntity<ApiResponse<CartCourseResponse>> getCourseIntoCart(@PathVariable long studentId) {
        return ResponseEntity.ok(ApiResponse.success(iCartService.getCourseIntoCart(studentId)));
    }

}
