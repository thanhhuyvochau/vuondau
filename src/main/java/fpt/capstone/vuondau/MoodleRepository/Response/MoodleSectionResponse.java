package fpt.capstone.vuondau.MoodleRepository.Response;

import java.util.ArrayList;

public class MoodleSectionResponse {
    private int id;
    private String name;
    private int visible;
    private String summary;
    private int summaryformat;
    private int section;
    private int hiddenbynumsections;
    private boolean uservisible;
    private ArrayList<MoodleModule> modules;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getSummaryformat() {
        return summaryformat;
    }

    public void setSummaryformat(int summaryformat) {
        this.summaryformat = summaryformat;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getHiddenbynumsections() {
        return hiddenbynumsections;
    }

    public void setHiddenbynumsections(int hiddenbynumsections) {
        this.hiddenbynumsections = hiddenbynumsections;
    }

    public boolean isUservisible() {
        return uservisible;
    }

    public void setUservisible(boolean uservisible) {
        this.uservisible = uservisible;
    }

    public ArrayList<MoodleModule> getModules() {
        return modules;
    }

    public void setModules(ArrayList<MoodleModule> modules) {
        this.modules = modules;
    }
}
