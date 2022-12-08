package fpt.capstone.vuondau.repository;


import fpt.capstone.vuondau.entity.Archetype;
import fpt.capstone.vuondau.entity.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileAttachmentRepository extends JpaRepository<FileAttachment, Long> {


}
