package flatrock.technology.productsservice.service;

import flatrock.technology.productsservice.model.dto.SimpleResponseDto;
import flatrock.technology.productsservice.model.dto.category.CategoryDto;
import flatrock.technology.productsservice.model.dto.category.CategoryListDto;
import flatrock.technology.productsservice.model.entities.ProductCategory;
import flatrock.technology.productsservice.model.exceptions.EntityAlreadyExists;
import flatrock.technology.productsservice.repo.CategoryRepository;
import flatrock.technology.productsservice.service.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository repository;
    @Override
    public CategoryListDto getCategories() {
        var categoryList = getAll()
                .map(category -> new CategoryDto(category.getName()))
                .toList();
        log.info("retrieved all categories [ quantity: {} ]", categoryList.size());
        return new CategoryListDto(categoryList);
    }

    @Override
    public Optional<ProductCategory> getByName(String name) {
        return getAll().filter(category -> category.getName().equals(name))
                .findFirst();
    }

    @Override
    public SimpleResponseDto addCategory(CategoryDto category) {
        repository.findProductCategoriesByName(category.name())
                .ifPresent((a) -> {
                    throw new EntityAlreadyExists(a.getName());
                });
        var newCategory = new ProductCategory();
        newCategory.setName(category.name());
        repository.save(newCategory);
        return new SimpleResponseDto("created");
    }

    private Stream<ProductCategory> getAll() {
        return repository
                .findAll()
                .stream();
    }
}
