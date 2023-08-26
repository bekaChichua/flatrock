package flatrock.technology.productsservice.model.exceptions;

public class EntityAlreadyExists extends RuntimeException{
    public EntityAlreadyExists(String message) {
        super(String.format("Entity with name %s already exists", message));
    }
}
