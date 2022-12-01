package fpt.capstone.vuondau.entity;

import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_id_generator")
    @SequenceGenerator(name = "question_id_generator", sequenceName = "question_id_generator")
    private Long id;
    @Column(name = "content")
    private String content;
    @Column(name = "is_closed")
    private Boolean isClosed = false;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Account student;

    @ManyToOne
    @JoinColumn(name = "forum_id")
    private Forum forum;
    @Column(name = "upvote_number")
    private Integer upvoteNumber = 0;
    @Column(name = "downVote_number")
    private Integer downVoteNumber = 0;
    @OneToMany(mappedBy = "question")
    private List<Comment> comments = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "forum_lesson_id")
    private ForumLesson forumLesson;

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

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public Account getStudent() {
        return student;
    }

    public void setStudent(Account student) {
        this.student = student;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public ForumLesson getForumLesson() {
        return forumLesson;
    }

    public void setForumLesson(ForumLesson forumLesson) {
        this.forumLesson = forumLesson;
    }
}
