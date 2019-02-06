package software.jevera.service.product;

import static software.jevera.service.product.ProductStateEnum.ARCHIVED;

public class Archived extends ProductState {

    @Override
    public ProductStateEnum getStatusName() {
        return ARCHIVED;
    }
}
