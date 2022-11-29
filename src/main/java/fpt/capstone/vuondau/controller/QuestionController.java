package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.dto.QuestionDto;
import fpt.capstone.vuondau.entity.request.CreateQuestionRequest;
import fpt.capstone.vuondau.service.IQuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    private final IQuestionService iQuestionService;

    public QuestionController(IQuestionService iQuestionService) {
        this.iQuestionService = iQuestionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuestionDto>> getQuestion(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(iQuestionService.getQuestion(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionDto>>> getQuestions() {
        return ResponseEntity.ok(ApiResponse.success(iQuestionService.getQuestions()));
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<ApiResponse<List<QuestionDto>>> getQuestionsBySubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(ApiResponse.success(iQuestionService.getQuestionsBySubject(subjectId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionDto>> createQuestion(@RequestBody CreateQuestionRequest createQuestionRequest) {
        return ResponseEntity.ok(ApiResponse.success(iQuestionService.createQuestion(createQuestionRequest)));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<QuestionDto>> updateQuestion(@PathVariable Long id, @RequestBody CreateQuestionRequest createQuestionRequest) {
        return ResponseEntity.ok(ApiResponse.success(iQuestionService.updateQuestion(id, createQuestionRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> closeQuestion(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(iQuestionService.closeQuestion(id)));
    }
}
