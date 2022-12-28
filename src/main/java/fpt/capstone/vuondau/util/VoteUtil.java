package fpt.capstone.vuondau.util;

import fpt.capstone.vuondau.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoteUtil {
    private final SecurityUtil securityUtil;

    public VoteUtil(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }

    public static VoteNumberReponse getVoteResponse(Question question) {
        List<Vote> votes = question.getVotes();
        Integer upvote = 0;
        Integer downvote = 0;
        for (Vote vote : votes) {
            if (vote.getVote()) {
                upvote++;
            } else {
                downvote++;
            }
        }
        return new VoteNumberReponse(upvote, downvote);
    }

    public static VoteNumberReponse getVoteResponse(Comment comment) {
        List<Vote> votes = comment.getVotes();
        Integer upvote = 0;
        Integer downvote = 0;
        for (Vote vote : votes) {
            if (vote.getVote()) {
                upvote++;
            } else {
                downvote++;
            }
        }
        return new VoteNumberReponse(upvote, downvote);
    }

}
