package fpt.capstone.vuondau.entity;

import javax.persistence.*;

@Entity
@Table(name = "moodle_module")
public class MoodleModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "moodle_section_id")
    private MoodleSection moodleSection;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MoodleSection getMoodleSection() {
        return moodleSection;
    }

    public void setMoodleSection(MoodleSection moodleSection) {
        this.moodleSection = moodleSection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
