package fpt.capstone.vuondau.MoodleRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class S1BaseRepository {
    @Value("${moodle.baseUri}")
    protected String masterUri;


    @Value("${moodle.categoryUri}")
    protected String categoryUri;

    @Value("${moodle.resourceUri}")
    protected String resourceUri;

    @Value("${moodle.createCategoryUri}")
    protected String createCategoryUri;

    @Value("${moodle.createCourseUri}")
    protected String createCourseUri;
    @Value("${moodle.courseUri}")
    protected String courseUri;
    @Value("${moodle.courseContentUri}")
    protected String courseContentUri;
    protected final Caller caller;

    public S1BaseRepository(Caller caller) {
        this.caller = caller;
    }

    public String getMasterUri() {
        return masterUri;
    }

    public void setMasterUri(String masterUri) {
        this.masterUri = masterUri;
    }

    public Caller getCaller() {
        return caller;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

    public String getCreateCourseUri() {
        return createCourseUri;
    }

    public void setCreateCourseUri(String createCourseUri) {
        this.createCourseUri = createCourseUri;
    }

    public String getCategoryUri() {
        return categoryUri;
    }

    public void setCategoryUri(String categoryUri) {
        this.categoryUri = categoryUri;
    }

    public String getCreateCategoryUri() {
        return createCategoryUri;
    }

    public void setCreateCategoryUri(String createCategoryUri) {
        this.createCategoryUri = createCategoryUri;
    }

    public String getCourseUri() {
        return courseUri;
    }

    public void setCourseUri(String courseUri) {
        this.courseUri = courseUri;
    }

    public String getCourseContentUri() {
        return courseContentUri;
    }

    public void setCourseContentUri(String courseContentUri) {
        this.courseContentUri = courseContentUri;
    }
}
