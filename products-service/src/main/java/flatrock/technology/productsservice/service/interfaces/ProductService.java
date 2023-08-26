package flatrock.technology.productsservice.service.interfaces;

import flatrock.technology.productsservice.model.dto.*;
import flatrock.technology.productsservice.model.dto.product.CreateProductDto;
import flatrock.technology.productsservice.model.dto.product.IsInStockDto;
import flatrock.technology.productsservice.model.dto.product.ProductDto;
import flatrock.technology.productsservice.model.dto.product.ProductListDto;
import org.springframework.security.oauth2.jwt.Jwt;

public interface ProductService {
    ProductListDto getAllProducts();

    ProductDto getByName(String name);

    IsInStockDto isProductInStock(String name);

    SimpleResponseDto addProduct(Jwt jwt, CreateProductDto product);
}
