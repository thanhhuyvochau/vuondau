package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Notification;
import fpt.capstone.vuondau.entity.Notifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotifierRepository extends JpaRepository<Notifier, Long> {
}
