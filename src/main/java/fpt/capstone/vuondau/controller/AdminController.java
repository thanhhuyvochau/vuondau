package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.AccountSearchRequest;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.service.IAdminService;
import fpt.capstone.vuondau.util.specification.AccountSpecificationBuilder;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    private final IAdminService iAdminService ;

    public AdminController(IAdminService iAdminService) {
        this.iAdminService = iAdminService;
    }

    // MANGER ACCOUNT
    @Operation(summary = "Tìm Kiếm account")
    @GetMapping("/search-account")
    public ResponseEntity<ApiResponse<ApiPage<AccountResponse>>> searchAccount(@Nullable AccountSearchRequest query,
                                                                             @PageableDefault(sort = "lastModified", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.searchAccount(query, pageable)));
    }

}
