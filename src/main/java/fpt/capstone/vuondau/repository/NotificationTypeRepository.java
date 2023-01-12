package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.BaseEntity;
import fpt.capstone.vuondau.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;


@Repository
public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {
}
