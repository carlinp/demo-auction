package software.jevera.service.product;

import static software.jevera.service.product.ProductStateEnum.DELETED;
import static software.jevera.service.product.ProductStateEnum.NEW;
import static software.jevera.service.product.ProductStateEnum.PUBLISHED;

import software.jevera.domain.Product;

public class New extends ProductState {

    @Override
    public ProductStateEnum getStatusName() {
        return NEW;
    }

    @Override
    public void publish(Product product) {
        product.setStatus(PUBLISHED);
    }

    @Override
    public void delete(Product product) {
        product.setStatus(DELETED);
    }
}
