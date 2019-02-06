package software.jevera.service.product;

import static software.jevera.service.product.ProductStateEnum.DELETED;

public class Deleted extends ProductState {

    @Override
    public ProductStateEnum getStatusName() {
        return DELETED;
    }
}
