package fpt.capstone.vuondau.entity.common;

public enum ENotificationType {
    /**
     * Quy tắc đặt tên notìfication type:
     * <p>
     * ENTITY-NAME + ACTION + STATUS
     */
    TRANSACTION_PAY_SUCCESS("TRANSACTION_PAY_SUCCESS", "Thanh toán thành công", "Bạn đã thanh toán thành công lớp học ");


    /**
     * -----------------------------------
     */
    private final String code;
    private final String title;
    private final String template;

    ENotificationType(String code, String title, String template) {
        this.code = code;
        this.title = title;
        this.template = template;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getTemplate() {
        return template;
    }
}
