package fpt.capstone.vuondau.MoodleRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class S1BaseRepository {
    @Value("${moodle.baseUri}")
    protected String masterUri;





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


}
