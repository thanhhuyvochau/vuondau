package fpt.capstone.vuondau.entity;

public enum EModuleType {
    QUIZ("quiz"), LESSON("lesson");

    EModuleType(String label) {
        this.label = label;
    }

    private final String label;

    public String getLabel() {
        return label;
    }
}
