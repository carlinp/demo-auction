package software.jevera.service.product;

import static software.jevera.service.product.ProductStateEnum.ARCHIVED;
import static software.jevera.service.product.ProductStateEnum.FINISHED;
import static software.jevera.service.product.ProductStateEnum.PUBLISHED;

import software.jevera.domain.Bid;
import software.jevera.domain.Product;
import software.jevera.exceptions.AmountOfBidUncorrect;

public class Published extends ProductState {

    @Override
    public void finish(Product product) {
        product.getMaxBid().map(Bid::getUser).ifPresent(product::setBuyer);
        product.setStatus(FINISHED);
    }

    @Override
    public void delete(Product product) {
        product.setStatus(ARCHIVED);
    }

    @Override
    public void addBid(Product product, Bid bid) {
        if (product.getMaxBid().filter(b -> b.getAmount() >= bid.getAmount()).isPresent()) {
            throw new AmountOfBidUncorrect();
        }
        product.addBid(bid);
    }

    @Override
    public ProductStateEnum getStatusName() {
        return PUBLISHED;
    }
}
