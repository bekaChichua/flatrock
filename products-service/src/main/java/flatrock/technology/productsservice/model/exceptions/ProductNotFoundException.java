package flatrock.technology.productsservice.model.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(String.format("product with name %s was not found", message));
    }
}
