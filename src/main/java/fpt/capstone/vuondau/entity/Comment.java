package fpt.capstone.vuondau.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "comment_id_generator")
    @SequenceGenerator(name = "comment_id_generator", sequenceName = "comment_id_generator")
    private Long id;
    @Column(name = "content")
    private String content;
    @Column(name = "upvote_number")
    private Integer upvoteNumber;
    @Column(name = "downVote_number")
    private Integer downVoteNumber;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "parentComment")
    private List<Comment> subComments = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @JoinColumn(name = "student_id")
    @ManyToOne
    private Account student;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUpvoteNumber() {
        return upvoteNumber;
    }

    public void setUpvoteNumber(Integer upvoteNumber) {
        this.upvoteNumber = upvoteNumber;
    }

    public Integer getDownVoteNumber() {
        return downVoteNumber;
    }

    public void setDownVoteNumber(Integer downVoteNumber) {
        this.downVoteNumber = downVoteNumber;
    }

    public List<Comment> getSubComments() {
        return subComments;
    }

    public void setSubComments(List<Comment> subComments) {
        this.subComments = subComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Account getStudent() {
        return student;
    }

    public void setStudent(Account student) {
        this.student = student;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
