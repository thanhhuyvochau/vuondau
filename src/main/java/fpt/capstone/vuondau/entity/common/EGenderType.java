package fpt.capstone.vuondau.entity.common;

public enum EGenderType {
    MALE("Nam"), FEMALE("Nữ"), OTHER("Khác"), nam("nam");

    EGenderType(String label) {
        this.label = label;
    }

    private final String label;

    public String getLabel() {
        return label;
    }
}
