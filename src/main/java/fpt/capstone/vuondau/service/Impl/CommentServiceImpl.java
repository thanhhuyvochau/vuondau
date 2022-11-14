package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Comment;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.dto.CommentDto;
import fpt.capstone.vuondau.entity.request.CreateCommentRequest;
import fpt.capstone.vuondau.repository.CommentRepository;
import fpt.capstone.vuondau.service.ICommentService;
import fpt.capstone.vuondau.util.ConvertUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto getComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Comment not found by id:" + id));
        return ConvertUtil.doConvertEntityToResponse(comment);
    }

    @Override
    public List<CommentDto> getCommentByQuestion(Long questionId) {
        return null;
    }

    @Override
    public CommentDto createComment(CreateCommentRequest createCommentRequest) {
        return null;
    }

    @Override
    public CommentDto updateComment(Long commentId, CreateCommentRequest createCommentRequest) {
        return null;
    }

    @Override
    public Boolean deleteComment(Long commentId) {
        return null;
    }
}
