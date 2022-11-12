package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.dto.QuestionDto;
import fpt.capstone.vuondau.entity.request.CreateQuestionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuestionDto>> getQuestion(@PathVariable Long id) {
        return null;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionDto>>> getQuestions() {
        return null;
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<ApiResponse<List<QuestionDto>>> getQuestionsBySubject(@PathVariable Long subjectId) {
        return null;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionDto>> createQuestion(@RequestBody CreateQuestionRequest createQuestionRequest) {
        return null;
    }

    @PutMapping
    public ResponseEntity<ApiResponse<QuestionDto>> updateQuestion(@PathVariable Long id, @RequestBody CreateQuestionRequest createQuestionRequest) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> closeQuestion(@PathVariable Long id) {
        return null;
    }
}
