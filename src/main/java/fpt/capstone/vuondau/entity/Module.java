package fpt.capstone.vuondau.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "module")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "moodle_section_id")
    private Section section;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EModuleType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
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

    public EModuleType getType() {
        return type;
    }

    public void setType(EModuleType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return Objects.equals(id, module.id) && Objects.equals(section, module.section) && Objects.equals(name, module.name) && Objects.equals(url, module.url) && type == module.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, section, name, url, type);
    }
}
