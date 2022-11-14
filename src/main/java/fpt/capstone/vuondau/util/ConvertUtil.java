package fpt.capstone.vuondau.util;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Comment;
import fpt.capstone.vuondau.entity.Question;
import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.entity.dto.CommentDto;
import fpt.capstone.vuondau.entity.dto.QuestionDto;
import fpt.capstone.vuondau.entity.dto.RoleDto;
import fpt.capstone.vuondau.entity.response.AccountResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertUtil {
    public static QuestionDto doConvertEntityToResponse(Question question) {
        QuestionDto questionDto = ObjectUtil.copyProperties(question, new QuestionDto(), QuestionDto.class, true);
        AccountResponse accountResponse = doConvertEntityToResponse(question.getStudent());
        // filter(comment -> comment.getParentComment() == null)
        // -> Để lấy tất cả các comment trực tiếp của câu hỏi (Không hỏi comment con) mục đích để get được ra dạng cây của các comment
        // -> Nếu không filter thì sẽ trả ra tất cả comment của câu hỏi và không handle ra dạng cây được.
        List<CommentDto> comments = question.getComments().stream().filter(comment -> comment.getParentComment() == null)
                .map(ConvertUtil::doConvertEntityToResponse)
                .collect(Collectors.toList());
        questionDto.setStudent(accountResponse);
        questionDto.setComments(comments);
        return questionDto;
    }

    public static CommentDto doConvertEntityToResponse(Comment comment) {
        return doConvertEntityToResponseAsTree(comment, comment.getParentComment());
    }

    private static CommentDto doConvertEntityToResponseAsTree(Comment comment, Comment parentComment) {
        CommentDto commentDto = ObjectUtil.copyProperties(comment, new CommentDto(), CommentDto.class, true);
        AccountResponse accountResponse = doConvertEntityToResponse(comment.getStudent());
        commentDto.setStudent(accountResponse);
        if (parentComment != null) {
            CommentDto parentCommentDto = ObjectUtil.copyProperties(parentComment, new CommentDto(), CommentDto.class, true);
            commentDto.setParentComment(parentCommentDto);
        }
        if (!comment.getSubComments().isEmpty()) {
            List<CommentDto> subCommentDtos = comment.getSubComments().stream()
                    .map(subComment -> doConvertEntityToResponseAsTree(subComment, comment))
                    .collect(Collectors.toList());
            commentDto.setSubComments(subCommentDtos);
        }
        return commentDto;
    }

    public static AccountResponse doConvertEntityToResponse(Account account) {
        AccountResponse accountResponse = ObjectUtil.copyProperties(account, new AccountResponse(), AccountResponse.class, true);
        RoleDto roleDto = doConvertEntityToResponse(account.getRole());
        accountResponse.setRole(roleDto);
        return accountResponse;
    }

    public static RoleDto doConvertEntityToResponse(Role role) {
        return ObjectUtil.copyProperties(role, new RoleDto(), RoleDto.class, true);
    }
}