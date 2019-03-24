package software.jevera.web;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.jevera.domain.Product;
import software.jevera.domain.User;
import software.jevera.service.ProductService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductResource {

    private final ProductService productService;

    @PostMapping("/products")
    public Product product(@RequestBody Product product, HttpSession session) {
        return productService.createProduct(product, getUser(session));
    }

    private User getUser(HttpSession session) {
        return (User)session.getAttribute("user");
    }

    @PostMapping("/products/{id}/publish")
    public void publish(@PathVariable("id") Long id) {
        productService.publish(id);
    }

}
