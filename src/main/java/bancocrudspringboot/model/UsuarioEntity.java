package bancocrudspringboot.model;


import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Setter
@Entity
@Table(name = "Usuario", uniqueConstraints={@UniqueConstraint(columnNames={"EMAIL"})})
public class UsuarioEntity {

    private long id;

    @NotNull
    @Size(min=2, max=100)
    @Column(name = "NOME",nullable = false)
    private String nome;

    @NotNull
    @Email
    @Pattern(regexp=".+@.+\\..+", message = "Informe um e-mail válido")
    private String email;

    @NotNull
    @Pattern(regexp="\\d{6}", message = "Informe no máximo 6 digitos.")
    private String senha;

    public UsuarioEntity() {

    }

    public UsuarioEntity(long id, String senha, String email, String nome) {
        this.id = id;
        this.senha = senha;
        this.email = email;
        this.nome = nome;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    @Column(name = "NOME",nullable = false)
    public String getNome() {
        return nome;
    }

    @Column(name = "EMAIL", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", Nome=" + nome + ", e-mail=" + email + "]";
    }

    @Column(name = "senha", nullable = false)
    public String getSenha() {
        return senha;
    }
}