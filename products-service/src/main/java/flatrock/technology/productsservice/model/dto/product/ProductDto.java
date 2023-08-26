package flatrock.technology.productsservice.model.dto.product;

import java.math.BigDecimal;

public record ProductDto(
        String name,
        BigDecimal price,
        Integer quantity,
        String category,
        String sellerEmail
) { }
