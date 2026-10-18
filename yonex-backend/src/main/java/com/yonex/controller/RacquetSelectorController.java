package com.yonex.controller;

import com.yonex.dto.ApiResponse;
import com.yonex.dto.ProductDto;
import com.yonex.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/racquet-selector")
@RequiredArgsConstructor
@Tag(name = "Racquet Selector", description = "Công cụ thông minh tư vấn và đề xuất vợt Yonex theo phong cách chơi")
public class RacquetSelectorController {

    private final ProductService productService;

    @PostMapping("/recommendations")
    @Operation(summary = "Đề xuất vợt theo trình độ, phong cách chơi và ngân sách")
    public ResponseEntity<ApiResponse<ProductDto.RecommendationResult>> getRecommendations(
            @RequestBody ProductDto.RacquetSelectorRequest req
    ) {
        ProductDto.RecommendationResult result = productService.recommendRacquet(req);
        return ResponseEntity.ok(ApiResponse.ok("Đề xuất vợt thành công", result));
    }
}
