package flatrock.technology.productsservice.controllers;

import flatrock.technology.productsservice.model.dto.SimpleResponseDto;
import flatrock.technology.productsservice.model.dto.category.CategoryDto;
import flatrock.technology.productsservice.model.dto.category.CategoryListDto;
import flatrock.technology.productsservice.service.interfaces.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Category controller", description = "handles controller management")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/category", produces = APPLICATION_JSON_VALUE)
public class CategoryController {

    private final CategoryService service;

    @Operation(summary = "get all categories")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "categories are retrieved",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryListDto.class))}),
            @ApiResponse(
                    responseCode = "500",
                    description = "internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))})
    })
    @GetMapping
    public CategoryListDto getCategories() {
        return service.getCategories();
    }

    @PostMapping
    public SimpleResponseDto addCategory(CategoryDto category) {
        return service.addCategory(category);
    }
}
