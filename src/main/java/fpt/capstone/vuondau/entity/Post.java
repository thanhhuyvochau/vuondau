package fpt.capstone.vuondau.entity;

import fpt.capstone.vuondau.entity.common.EPageContent;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EPageContent type ;

    @Column(name = "content", columnDefinition = "LONGBLOB")
    private String content;



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

    public EPageContent getType() {
        return type;
    }

    public void setType(EPageContent type) {
        this.type = type;
    }


}
