package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.common.EForumType;
import fpt.capstone.vuondau.entity.dto.ForumDto;
import fpt.capstone.vuondau.entity.dto.SimpleForumDto;
import fpt.capstone.vuondau.entity.request.UpdateForumRequest;
import fpt.capstone.vuondau.repository.SubjectRepository;
import fpt.capstone.vuondau.service.IForumService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/forums")
public class ForumController {
    private final IForumService forumService;
    private final SubjectRepository subjectRepository;

    public ForumController(IForumService forumService, SubjectRepository subjectRepository) {
        this.forumService = forumService;
        this.subjectRepository = subjectRepository;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ApiPage<SimpleForumDto>>> getAllForum(Pageable pageable, @RequestParam EForumType forumType) {
        return ResponseEntity.ok(ApiResponse.success(forumService.getAllForumByTypes(pageable, forumType)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ForumDto>> updateForum(@PathVariable Long id, @RequestBody UpdateForumRequest request) {
        return ResponseEntity.ok(ApiResponse.success(forumService.updateForum(id, request)));
    }

//    @GetMapping("/auto")
//    public ResponseEntity<ApiResponse<Boolean>> createauto() {
//        List<Subject> all = subjectRepository.findAll();
//        for (Subject subject : all) {
//            forumService.createForumForSubject(subject.getId());
//        }
//        return ResponseEntity.ok(ApiResponse.success(true));
//    }

    /**Temporary API will be deleted in future*/
    @GetMapping("/synchronize-lesson")
    public ResponseEntity<ApiResponse<Boolean>> synchronizeLessonForum(@RequestParam Long classId) {
        return ResponseEntity.ok(ApiResponse.success(forumService.synchronizeLessonForum(classId)));
    }
}
