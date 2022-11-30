package fpt.capstone.vuondau.entity.request;


public class CreateQuestionRequest {
    private String content;

    private Long forumId;
    private Long forumLessonId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getForumId() {
        return forumId;
    }

    public void setForumId(Long forumId) {
        this.forumId = forumId;
    }

    public Long getForumLessonId() {
        return forumLessonId;
    }

    public void setForumLessonId(Long forumLessonId) {
        this.forumLessonId = forumLessonId;
    }
}
