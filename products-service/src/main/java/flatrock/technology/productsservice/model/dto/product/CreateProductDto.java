package flatrock.technology.productsservice.model.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Create product request")
public record CreateProductDto(
        @Schema(description = "name of the product, if this product already exists quantity will be increased, if now new record will be created in DB", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "name can not be null")
        @Size(min = 2, max = 20, message = "name should be between 2 to 20 characters long")
        String name,
        @Schema(description = "only required when product with this name does not exists",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        BigDecimal price,
        @Schema(description = "only required when product with this name does not exists", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String categoryName
        ) {
}
