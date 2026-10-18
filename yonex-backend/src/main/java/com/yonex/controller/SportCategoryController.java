package com.yonex.controller;

import com.yonex.dto.ApiResponse;
import com.yonex.entity.Category;
import com.yonex.entity.Sport;
import com.yonex.repository.CategoryRepository;
import com.yonex.repository.SportRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Sports & Categories", description = "Danh mục sản phẩm và môn thể thao")
public class SportCategoryController {

    private final SportRepository sportRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping("/sports")
    @Operation(summary = "Lấy danh sách tất cả các môn thể thao (Badminton, Tennis, Golf, Running...)")
    public ResponseEntity<ApiResponse<List<Sport>>> getAllSports() {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách môn thể thao thành công", sportRepository.findByActiveTrue()));
    }

    @GetMapping("/categories")
    @Operation(summary = "Lấy danh sách tất cả danh mục sản phẩm (Racquets, Shoes, Apparel...)")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách danh mục thành công", categoryRepository.findByActiveTrue()));
    }

    @GetMapping("/categories/sport/{sportId}")
    @Operation(summary = "Lấy danh mục theo ID môn thể thao")
    public ResponseEntity<ApiResponse<List<Category>>> getCategoriesBySport(@PathVariable Long sportId) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh mục theo môn thể thao thành công", categoryRepository.findBySportIdAndActiveTrue(sportId)));
    }
}
