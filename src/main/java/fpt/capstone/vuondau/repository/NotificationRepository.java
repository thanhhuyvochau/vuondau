package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Notification;
import fpt.capstone.vuondau.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
