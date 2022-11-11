package fpt.capstone.vuondau.repository;


import fpt.capstone.vuondau.entity.Request;
import fpt.capstone.vuondau.entity.RequestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestTypeRepository extends JpaRepository<RequestType, Long> {
}
