package software.jevera.web;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final HttpSession httpSession;
    private final ProductMapper productMapper;

    @PostMapping
    public Product create(@RequestBody @Valid ProductDto product) {
        return productService.createProduct(productMapper.toProduct(product), getUser());
    }

    @GetMapping("/own")
    public List<Product> ownProducts() {
        return productService.getUserProducts(getUser());
    }

    @GetMapping()
    public List<Product> getAllProducts(@RequestParam(value = "maxPrice", required = false) Optional<Integer> maxPrice) {
        return productService.getAllProducts(maxPrice);
    }

    private User getUser() {
        return (User) httpSession.getAttribute("user");
    }

}
