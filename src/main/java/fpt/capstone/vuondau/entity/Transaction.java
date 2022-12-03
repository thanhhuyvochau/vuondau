package fpt.capstone.vuondau.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_info")
    private String orderInfo;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "vpn_command")
    private String vpnCommand;

    private Boolean isSuccess;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class paymentClass;
    @Column(name = "transaction_no")
    private String transactionNo;

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public Class getPaymentClass() {
        return paymentClass;
    }

    public void setPaymentClass(Class paymentClass) {
        this.paymentClass = paymentClass;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getVpnCommand() {
        return vpnCommand;
    }

    public void setVpnCommand(String vpnCommand) {
        this.vpnCommand = vpnCommand;
    }
}
