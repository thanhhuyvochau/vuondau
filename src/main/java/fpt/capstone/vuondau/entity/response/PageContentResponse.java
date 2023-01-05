package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.common.EPageContent;

public class PageContentResponse {


    private Long id;
    private EPageContent type;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EPageContent getType() {
        return type;
    }

    public void setType(EPageContent type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
