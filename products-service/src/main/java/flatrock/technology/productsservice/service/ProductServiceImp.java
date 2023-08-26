package flatrock.technology.productsservice.service;

import flatrock.technology.productsservice.model.dto.SimpleResponseDto;
import flatrock.technology.productsservice.model.dto.product.CreateProductDto;
import flatrock.technology.productsservice.model.dto.product.IsInStockDto;
import flatrock.technology.productsservice.model.dto.product.ProductDto;
import flatrock.technology.productsservice.model.dto.product.ProductListDto;
import flatrock.technology.productsservice.model.entities.Product;
import flatrock.technology.productsservice.model.exceptions.ProductCreationException;
import flatrock.technology.productsservice.model.exceptions.ProductNotFoundException;
import flatrock.technology.productsservice.repo.ProductRepository;
import flatrock.technology.productsservice.service.interfaces.CategoryService;
import flatrock.technology.productsservice.service.interfaces.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private static final String SCOPE = "scope";
    private static final String ADMIN = "ADMIN";
    private final ProductRepository repository;
    private final CategoryService categoryService;

    @Override
    @Transactional
    public ProductListDto getAllProducts() {
        var productList = repository
                .findAll()
                .stream()
                .map(product -> new ProductDto(
                        product.getName(),
                        product.getPrice(),
                        product.getQuantity(),
                        product.getCategory().getName(),
                        product.getSellersEmail())).toList();
        log.debug("retrieved all products [ quantity: {} ]", productList.size());
        return new ProductListDto(productList);
    }

    @Override
    public ProductDto getByName(String name) {
        return null;
    }

    @Override
    public IsInStockDto isProductInStock(String name) {
        var product = repository
                .findProductByName(name)
                .orElseThrow(() -> new ProductNotFoundException(name));
        log.debug("checking if product is in stock with name: {}", name);
        return product.getQuantity() > 0 ?
                new IsInStockDto(true) : new IsInStockDto(false);
    }

    @Override
    public SimpleResponseDto addProduct(Jwt jwt, CreateProductDto product) {
        var result = repository
                .findProductByName(product.name())
                .map(entity -> {
                    var email = entity.getSellersEmail();
                    checkPermission(jwt, email);
                    entity.setQuantity(entity.getQuantity() + 1);
                    return entity;
                })
                .or(() -> Optional.of(generateNewProduct(product, jwt.getSubject())))
                .orElseThrow(() -> new ProductCreationException("unknown problem during product creation"));
        repository.save(result);
        return new SimpleResponseDto("created");
    }

    private Product generateNewProduct(CreateProductDto product, String email) {
        var categoryName = product.categoryName();
        if (categoryName == null) throw new ProductCreationException(
                String.format("name is null when product with name: %s does not exists", product.name())
        );
        var category = categoryService.getByName(categoryName)
                .orElseThrow(() -> new ProductCreationException(
                        String.format("category with name: %s does not exists. create name first", product.categoryName())
                ));
        var newProduct = new Product();
        newProduct.setName(product.name());
        newProduct.setCategory(category);
        newProduct.setQuantity(1);
        newProduct.setSellersEmail(email);
        if (product.price() == null) {
            newProduct.setPrice(new BigDecimal(0));
        } else {
            newProduct.setPrice(product.price());
        }
        log.trace("new product created with name: {}", newProduct.getName());
        return newProduct;
    }

    private void checkPermission(Jwt jwt, String email) {
        if(!jwt.getSubject().equals(email)){
            if(!((List<String>)jwt.getClaims().get(SCOPE)).contains(ADMIN)) throw new RuntimeException();
        };
    }
}
