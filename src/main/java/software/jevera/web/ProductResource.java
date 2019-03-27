package software.jevera.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.jevera.domain.Product;
import software.jevera.domain.User;
import software.jevera.domain.dto.ProductDto;
import software.jevera.domain.dto.ProductMapper;
import software.jevera.service.ProductService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductResource {

    private final ProductService productService;
    private final HttpSession session;
    private final ProductMapper productMapper;

    @PostMapping("/products")
    public Product product(@RequestBody ProductDto product) {
        return productService.createProduct(productMapper.toProduct(product), getUser());
    }

    private User getUser() {
        return (User)session.getAttribute("user");
    }

    @PostMapping("/products/{id}/publish")
    public void publish(@PathVariable("id") Long id) {
        productService.publish(id, getUser());
    }

    @RequestMapping(value = "/products", method = GET)
    public List<Product> products(@RequestParam(value = "maxPrice", required = false) Optional<Integer> maxPrice) {
        return productService.getAllProducts(maxPrice);
    }

    @GetMapping("/products/own")
    public List<Product> getOwnProducts() {
        return productService.getUserProducts(getUser());
    }

}
