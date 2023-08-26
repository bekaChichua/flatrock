package flatrock.technology.productsservice.controllers;

import flatrock.technology.productsservice.model.dto.*;
import flatrock.technology.productsservice.model.dto.product.CreateProductDto;
import flatrock.technology.productsservice.model.dto.product.IsInStockDto;
import flatrock.technology.productsservice.model.dto.product.ProductListDto;
import flatrock.technology.productsservice.service.interfaces.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Product controller", description = "handles product retrieval and creation")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/products", produces = APPLICATION_JSON_VALUE)
public class ProductController {
    private final ProductService service;

    @Operation(summary = "get all listed products")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "items are retrieved",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductListDto.class))}),
            @ApiResponse(
                    responseCode = "500",
                    description = "internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))})
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductListDto getProducts() {
        return service.getAllProducts();
    }

//    @GetMapping("/{name}")
//    public ProductDto getProduct(@PathVariable String name) {
//        return productService.getByName(name);
//    }
//
    @Operation(summary = "add product")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "new product added",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductListDto.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "incorrect user input",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(
                    responseCode = "500",
                    description = "internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))})
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public SimpleResponseDto addProduct(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody CreateProductDto product) {
        return service.addProduct(jwt, product);
    }
//
//    @GetMapping("/seller/{email}")
//    public void getProductsBySeller(@PathVariable String email) {
//
//    }
//
//    @GetMapping("/name/{name}")
//    public void getProductsByCategory(@PathVariable String name) {
//
//    }

    @Operation(summary = "checks if product is available")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "response sent",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = IsInStockDto.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "product not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(
                    responseCode = "500",
                    description = "internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))})
    })
    @GetMapping("/{name}/in-stock")
    public IsInStockDto isProductInStock(@PathVariable String name) {
        return service.isProductInStock(name);
    }
}
