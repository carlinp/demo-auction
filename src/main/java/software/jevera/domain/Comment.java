package software.jevera.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@AllArgsConstructor
public class Comment {

    private Long id;
    private String text;
    private User author;
    private Product product;

    public Comment(String text, User author, Product product) {
        this.text = text;
        this.author = author;
        this.product = product;
    }
}
