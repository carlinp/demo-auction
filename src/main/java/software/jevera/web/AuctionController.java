package software.jevera.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import software.jevera.domain.Product;
import software.jevera.service.ProductService;

@Controller
@RequiredArgsConstructor
public class AuctionController {

    private final ProductService productService;

    @RequestMapping(value = "/helloworld", method = GET)
    public String helloworld() {
        return "helloworld";
    }

    @RequestMapping(value = "/hellome/{name}", method = GET)
    public String hellome(@PathVariable("name") String name) {
        return "hellome";
    }

    @RequestMapping(value = "/hellome", method = GET)
    public String helloWithQuery(@ModelAttribute("name") String name) {
        return "hellome";
    }

    @RequestMapping(value = "/products", method = GET)
    public String products(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

}
