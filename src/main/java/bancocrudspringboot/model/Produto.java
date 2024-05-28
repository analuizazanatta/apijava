package bancocrudspringboot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "Produto")
public class Produto {

    private long id;
    private String descricao;
    private double preco;
    private int estoque;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }
}