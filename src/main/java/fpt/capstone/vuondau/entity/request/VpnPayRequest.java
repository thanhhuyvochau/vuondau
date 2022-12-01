package fpt.capstone.vuondau.entity.request;

public class VpnPayRequest {
    private String vnp_OrderInfo;
    private String ordertype;
    private String amount;
    private String txt_billing_mobile = "0934998386";
    private String txt_billing_email = "xonv@vnpay.vn";
    private String txt_billing_fullname = "NGUYEN VAN XO";
    private String txt_inv_addr1 = "22 Lang Ha Dong Da Ha Noi";
    private String txt_bill_city = "Ha Noi";
    private String txt_bill_country = "VN";
    private String txt_inv_mobile = "02437764668";
    private String txt_inv_email = "xonv@vnpay.vn";
    private String txt_inv_customer = "Nguyen Van A";
    private String txt_inv_taxcode = "0102182292";
    private String cbo_inv_type = "I";

    public String getVnp_OrderInfo() {
        return vnp_OrderInfo;
    }

    public void setVnp_OrderInfo(String vnp_OrderInfo) {
        this.vnp_OrderInfo = vnp_OrderInfo;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTxt_billing_mobile() {
        return txt_billing_mobile;
    }

    public void setTxt_billing_mobile(String txt_billing_mobile) {
        this.txt_billing_mobile = txt_billing_mobile;
    }

    public String getTxt_billing_email() {
        return txt_billing_email;
    }

    public void setTxt_billing_email(String txt_billing_email) {
        this.txt_billing_email = txt_billing_email;
    }

    public String getTxt_billing_fullname() {
        return txt_billing_fullname;
    }

    public void setTxt_billing_fullname(String txt_billing_fullname) {
        this.txt_billing_fullname = txt_billing_fullname;
    }

    public String getTxt_inv_addr1() {
        return txt_inv_addr1;
    }

    public void setTxt_inv_addr1(String txt_inv_addr1) {
        this.txt_inv_addr1 = txt_inv_addr1;
    }

    public String getTxt_bill_city() {
        return txt_bill_city;
    }

    public void setTxt_bill_city(String txt_bill_city) {
        this.txt_bill_city = txt_bill_city;
    }

    public String getTxt_bill_country() {
        return txt_bill_country;
    }

    public void setTxt_bill_country(String txt_bill_country) {
        this.txt_bill_country = txt_bill_country;
    }

    public String getTxt_inv_mobile() {
        return txt_inv_mobile;
    }

    public void setTxt_inv_mobile(String txt_inv_mobile) {
        this.txt_inv_mobile = txt_inv_mobile;
    }

    public String getTxt_inv_email() {
        return txt_inv_email;
    }

    public void setTxt_inv_email(String txt_inv_email) {
        this.txt_inv_email = txt_inv_email;
    }

    public String getTxt_inv_customer() {
        return txt_inv_customer;
    }

    public void setTxt_inv_customer(String txt_inv_customer) {
        this.txt_inv_customer = txt_inv_customer;
    }

    public String getTxt_inv_taxcode() {
        return txt_inv_taxcode;
    }

    public void setTxt_inv_taxcode(String txt_inv_taxcode) {
        this.txt_inv_taxcode = txt_inv_taxcode;
    }

    public String getCbo_inv_type() {
        return cbo_inv_type;
    }

    public void setCbo_inv_type(String cbo_inv_type) {
        this.cbo_inv_type = cbo_inv_type;
    }
}
