package bancocrudspringboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import bancocrudspringboot.model.Produto;
import bancocrudspringboot.repository.ProdutoRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    // Listar todos os produtos
    @GetMapping("/produto")
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> getAll(){
        return this.produtoRepository.findAll();
    }
}
