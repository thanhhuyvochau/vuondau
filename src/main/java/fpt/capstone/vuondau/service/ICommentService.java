package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.dto.CommentDto;
import fpt.capstone.vuondau.entity.dto.QuestionDto;
import fpt.capstone.vuondau.entity.request.CreateCommentRequest;
import fpt.capstone.vuondau.entity.request.CreateQuestionRequest;

import java.util.List;

public interface ICommentService {
    CommentDto getComment(Long id);

    List<CommentDto> getComments();

    List<CommentDto> getCommentByQuestion(Long questionId);

    CommentDto createComment(CreateCommentRequest createCommentRequest);

    CommentDto updateComment(Long commentId, CreateCommentRequest createCommentRequest);

    Boolean deleteComment(Long commentId);
}
