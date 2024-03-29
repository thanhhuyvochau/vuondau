package fpt.capstone.vuondau.controller;


import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.common.EPageContent;
import fpt.capstone.vuondau.entity.request.PageContentRequest;
import fpt.capstone.vuondau.entity.response.PageContentResponse;
import fpt.capstone.vuondau.service.IPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private final IPostService iPostService;

    public PostController(IPostService iPostService) {
        this.iPostService = iPostService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('MANAGER','ROOT')")
    public ResponseEntity<ApiResponse<Long>> createContentIntroPage(@RequestBody PageContentRequest content) {
        return ResponseEntity.ok(ApiResponse.success(iPostService.createContentIntroPage(content)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER','ROOT')")
    public ResponseEntity<ApiResponse<PageContentResponse>> getContentIntroPage(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(iPostService.getContentIntroPage(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER','ROOT')")
    public ResponseEntity<ApiResponse<PageContentResponse>> updateContentIntroPage(@PathVariable Long id, @RequestBody PageContentRequest content) {
        return ResponseEntity.ok(ApiResponse.success(iPostService.updateContentIntroPage(id, content)));
    }

    @GetMapping

    public ResponseEntity<ApiResponse<PageContentResponse>> renderIntroPage(EPageContent type ) {
        return ResponseEntity.ok(ApiResponse.success(iPostService.renderIntroPage(type)));
    }

}
