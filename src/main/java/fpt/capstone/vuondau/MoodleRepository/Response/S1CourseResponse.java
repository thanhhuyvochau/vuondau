package fpt.capstone.vuondau.MoodleRepository.Response;

import java.util.List;

public class S1CourseResponse {


    private Long id;
    private String name;
    private String visible;
    private String summary;
    private String summaryformat;
    private Long section;
    private String hiddenbynumsections;
    private String uservisible;
    private List<CourseMoodle> modules;
    public class CourseMoodle {
        Long id;
        String url;
        String name;
        String instance;
        String contextid;
        Long visible;
        String uservisible;
        String visibleoncoursepage;
        String modicon;
        String modname;
        Long modplural;
        String availability;
        String indent;
        String onclick;
        String afterlink;
        String customdata;
        String noviewlink;
        String completion;
        String dates;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInstance() {
            return instance;
        }

        public void setInstance(String instance) {
            this.instance = instance;
        }

        public String getContextid() {
            return contextid;
        }

        public void setContextid(String contextid) {
            this.contextid = contextid;
        }

        public Long getVisible() {
            return visible;
        }

        public void setVisible(Long visible) {
            this.visible = visible;
        }

        public String getUservisible() {
            return uservisible;
        }

        public void setUservisible(String uservisible) {
            this.uservisible = uservisible;
        }

        public String getVisibleoncoursepage() {
            return visibleoncoursepage;
        }

        public void setVisibleoncoursepage(String visibleoncoursepage) {
            this.visibleoncoursepage = visibleoncoursepage;
        }

        public String getModicon() {
            return modicon;
        }

        public void setModicon(String modicon) {
            this.modicon = modicon;
        }

        public String getModname() {
            return modname;
        }

        public void setModname(String modname) {
            this.modname = modname;
        }

        public Long getModplural() {
            return modplural;
        }

        public void setModplural(Long modplural) {
            this.modplural = modplural;
        }

        public String getAvailability() {
            return availability;
        }

        public void setAvailability(String availability) {
            this.availability = availability;
        }

        public String getIndent() {
            return indent;
        }

        public void setIndent(String indent) {
            this.indent = indent;
        }

        public String getOnclick() {
            return onclick;
        }

        public void setOnclick(String onclick) {
            this.onclick = onclick;
        }

        public String getAfterlink() {
            return afterlink;
        }

        public void setAfterlink(String afterlink) {
            this.afterlink = afterlink;
        }

        public String getCustomdata() {
            return customdata;
        }

        public void setCustomdata(String customdata) {
            this.customdata = customdata;
        }

        public String getNoviewlink() {
            return noviewlink;
        }

        public void setNoviewlink(String noviewlink) {
            this.noviewlink = noviewlink;
        }

        public String getCompletion() {
            return completion;
        }

        public void setCompletion(String completion) {
            this.completion = completion;
        }

        public String getDates() {
            return dates;
        }

        public void setDates(String dates) {
            this.dates = dates;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummaryformat() {
        return summaryformat;
    }

    public void setSummaryformat(String summaryformat) {
        this.summaryformat = summaryformat;
    }

    public Long getSection() {
        return section;
    }

    public void setSection(Long section) {
        this.section = section;
    }

    public String getHiddenbynumsections() {
        return hiddenbynumsections;
    }

    public void setHiddenbynumsections(String hiddenbynumsections) {
        this.hiddenbynumsections = hiddenbynumsections;
    }

    public String getUservisible() {
        return uservisible;
    }

    public void setUservisible(String uservisible) {
        this.uservisible = uservisible;
    }

    public List<CourseMoodle> getModules() {
        return modules;
    }

    public void setModules(List<CourseMoodle> modules) {
        this.modules = modules;
    }
}
