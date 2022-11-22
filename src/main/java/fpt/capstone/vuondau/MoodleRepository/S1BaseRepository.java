package fpt.capstone.vuondau.MoodleRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class S1BaseRepository {
    @Value("${moodle.baseUri}")
    protected String masterUri;

    @Value("${moodle.resourceUri}")
    protected String resourceUri;

    @Value("${moodle.createCourseUri}")
    protected String createCourseUri;

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
}
