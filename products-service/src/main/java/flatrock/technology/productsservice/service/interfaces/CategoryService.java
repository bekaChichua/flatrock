package flatrock.technology.productsservice.service.interfaces;

import flatrock.technology.productsservice.model.dto.SimpleResponseDto;
import flatrock.technology.productsservice.model.dto.category.CategoryDto;
import flatrock.technology.productsservice.model.dto.category.CategoryListDto;
import flatrock.technology.productsservice.model.entities.ProductCategory;

import java.util.Optional;

public interface CategoryService {
    CategoryListDto getCategories();

    Optional<ProductCategory> getByName(String name);

    SimpleResponseDto addCategory(CategoryDto category);
}
