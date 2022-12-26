package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Comment;
import fpt.capstone.vuondau.entity.Question;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.dto.CommentDto;
import fpt.capstone.vuondau.entity.request.CreateCommentRequest;
import fpt.capstone.vuondau.repository.CommentRepository;
import fpt.capstone.vuondau.repository.QuestionRepository;
import fpt.capstone.vuondau.service.ICommentService;
import fpt.capstone.vuondau.util.ConvertUtil;
import fpt.capstone.vuondau.util.ObjectUtil;
import fpt.capstone.vuondau.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements ICommentService {
    private final CommentRepository commentRepository;
    private final SecurityUtil securityUtil;
    private final QuestionRepository questionRepository;

    public CommentServiceImpl(CommentRepository commentRepository, SecurityUtil securityUtil, QuestionRepository questionRepository) {
        this.commentRepository = commentRepository;
        this.securityUtil = securityUtil;
        this.questionRepository = questionRepository;
    }

    @Override
    public CommentDto getComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Comment not found by id:" + id));
        return ConvertUtil.doConvertEntityToResponse(comment);
    }

    @Override
    public List<CommentDto> getCommentByQuestion(Long questionId) {
        List<Comment> comments = commentRepository.findAllByQuestion_IdAndParentCommentIsNull(questionId);
        if (!comments.isEmpty()) {
            return comments.stream().map(ConvertUtil::doConvertEntityToResponse)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public CommentDto createComment(CreateCommentRequest createCommentRequest) {
        Account account = securityUtil.getCurrentUser();
        Comment comment = ObjectUtil.copyProperties(createCommentRequest, new Comment(), Comment.class, true);
        Question question = questionRepository.findById(createCommentRequest.getQuestionId())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND)
                        .withMessage("Question not found with id:" + createCommentRequest.getQuestionId()));
        if (question.getClosed()) {
            throw ApiException.create(HttpStatus.CONFLICT)
                    .withMessage("Question has closed!!");
        }
        comment.setQuestion(question);
        comment.setAccount(account);
        if (createCommentRequest.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(createCommentRequest.getParentCommentId())
                    .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND)
                            .withMessage("Parent comment not found with id:" + createCommentRequest.getParentCommentId()));
            comment.setParentComment(parentComment);
        }
        comment = commentRepository.save(comment);
        return ConvertUtil.doConvertEntityToResponse(comment);
    }

    @Override
    public CommentDto updateComment(Long commentId, CreateCommentRequest createCommentRequest) {
        Account account = securityUtil.getCurrentUser();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND)
                        .withMessage("Comment not found with id:" + commentId));
        if (!Objects.equals(account.getId(), comment.getAccount().getId())) {
            throw ApiException.create(HttpStatus.CONFLICT)
                    .withMessage("Comment is edited by another user with username:" + account.getUsername());
        }
        comment.setContent(createCommentRequest.getContent());
        comment = commentRepository.save(comment);
        return ConvertUtil.doConvertEntityToResponse(comment);
    }

    @Override
    public Boolean deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND)
                        .withMessage("Comment not found with id:" + commentId));
        commentRepository.delete(comment);
        return true;
    }
}
