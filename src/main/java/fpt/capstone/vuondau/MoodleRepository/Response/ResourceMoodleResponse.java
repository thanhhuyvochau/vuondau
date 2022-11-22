package fpt.capstone.vuondau.MoodleRepository.Response;




public class ResourceMoodleResponse {
    private Long id;
    private String  url;
    private String  name;
    private Long instance;
    private Long contextid;


    private Long  visible;
    private boolean uservisible;
    private Long visibleoncoursepage;

    private String  modicon;
    private String modname;
    private String modplural;

    private String  availability;
    private Long indent;
    private String onclick;
    private String  afterlink;

    private String  customdata;
    private boolean noviewlink;
    private Long completion;
    private Object  dates;

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

    public Long getInstance() {
        return instance;
    }

    public void setInstance(Long instance) {
        this.instance = instance;
    }

    public Long getContextid() {
        return contextid;
    }

    public void setContextid(Long contextid) {
        this.contextid = contextid;
    }

    public Long getVisible() {
        return visible;
    }

    public void setVisible(Long visible) {
        this.visible = visible;
    }

    public boolean isUservisible() {
        return uservisible;
    }

    public void setUservisible(boolean uservisible) {
        this.uservisible = uservisible;
    }

    public Long getVisibleoncoursepage() {
        return visibleoncoursepage;
    }

    public void setVisibleoncoursepage(Long visibleoncoursepage) {
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

    public String getModplural() {
        return modplural;
    }

    public void setModplural(String modplural) {
        this.modplural = modplural;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Long getIndent() {
        return indent;
    }

    public void setIndent(Long indent) {
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

    public boolean isNoviewlink() {
        return noviewlink;
    }

    public void setNoviewlink(boolean noviewlink) {
        this.noviewlink = noviewlink;
    }

    public Long getCompletion() {
        return completion;
    }

    public void setCompletion(Long completion) {
        this.completion = completion;
    }

    public Object getDates() {
        return dates;
    }

    public void setDates(Object dates) {
        this.dates = dates;
    }
}
