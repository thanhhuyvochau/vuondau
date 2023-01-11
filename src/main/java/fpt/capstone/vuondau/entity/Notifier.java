package fpt.capstone.vuondau.entity;

import javax.persistence.*;

@Entity
@Table(name = "notifier")
public class Notifier extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "notifier_id")
    @ManyToOne
    private Account notifier;
    @JoinColumn(name = "notification_id")
    @ManyToOne
    private Notification notification;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getNotifier() {
        return notifier;
    }

    public void setNotifier(Account notifier) {
        this.notifier = notifier;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
