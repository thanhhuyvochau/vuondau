package fpt.capstone.vuondau.MoodleRepository.Response;

import java.util.ArrayList;
import java.util.List;

public class S1BaseSingleResponse<T> {
    private String $id;
    private int status;
    private String message;
    private int code;

    private List<T> data = new ArrayList<>();

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
