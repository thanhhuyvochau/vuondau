package fpt.capstone.vuondau.repository;


import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
}
