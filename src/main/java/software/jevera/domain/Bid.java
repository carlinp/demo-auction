package software.jevera.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Bid {
    private Integer amount;
    private Product product;
    private User user;
}
