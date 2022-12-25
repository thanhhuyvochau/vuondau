package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.common.EForumType;
import fpt.capstone.vuondau.entity.dto.ForumDto;
import fpt.capstone.vuondau.entity.dto.SimpleForumDto;
import fpt.capstone.vuondau.service.IForumService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/forums")
public class ForumControllerV2 {
    private final IForumService forumService;

    public ForumControllerV2(IForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ApiPage<SimpleForumDto>>> getAllForum(Pageable pageable, @RequestParam EForumType forumType) {
        return ResponseEntity.ok(ApiResponse.success(forumService.getAllClassForums(pageable, forumType)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ForumDto>> updateForum(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(forumService.updateForum(id)));
    }

}
