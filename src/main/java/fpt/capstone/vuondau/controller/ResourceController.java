package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.dto.ResourceDto;
import fpt.capstone.vuondau.entity.request.AccountEditRequest;
import fpt.capstone.vuondau.entity.request.UploadAvatarRequest;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.service.IResourceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/resource")
public class ResourceController {
    private final IResourceService iResourceService ;

    public ResourceController(IResourceService iResourceService) {
        this.iResourceService = iResourceService;
    }

    @Operation(summary = "Cập nhật hồ sơ học sinh")
    @PostMapping ("/upload-file")
    public ResponseEntity<ResourceDto> uploadFile(@ModelAttribute UploadAvatarRequest uploadFile) {
        return ResponseEntity.ok(iResourceService.uploadFile( uploadFile));
    }
}
