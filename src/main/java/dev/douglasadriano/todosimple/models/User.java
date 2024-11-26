package dev.douglasadriano.todosimple.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity //define que tem que ser tratado como uma tabela no banco de dados
@Table(name = User.TABLE_NAME) //nomeia a tabela no banco de dados
public class User {
    public interface CreatedUser {}
    public interface UpdateUser {}

    public static final String TABLE_NAME = "user"; //definição do nome na tabela direto na classe

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //padrão de autoincremento do mysql
    @Column(name = "id", unique = true) //define que o valor nunca será duplicado
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true) //nome da tabela, comprimento dos caracteres, garante que não pode ser nulo e define que nunca será duplicado
    @NotNull (groups = CreatedUser.class)
    @NotEmpty (groups = CreatedUser.class)
    @Size(groups = CreatedUser.class, min = 2, max = 100) // minimo e maximo de caracteres
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //garante que a senha seja só de escrita, que não seja lida, nunca retorna pro front end
    @Column(name = "password", length = 60, nullable = false)
    @NotNull(groups = {CreatedUser.class, UpdateUser.class})
    @NotEmpty(groups = {CreatedUser.class, UpdateUser.class})
    @Size(groups = {CreatedUser.class, UpdateUser.class}, min = 8, max = 60)
    private String password;

    //private List<Task> tasks = new ArrayList<Task>();


    public User() {
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(groups = CreatedUser.class) @NotEmpty(groups = CreatedUser.class) @Size(groups = CreatedUser.class, min = 2, max = 100) String getUsername() {
        return username;
    }

    public void setUsername(@NotNull(groups = CreatedUser.class) @NotEmpty(groups = CreatedUser.class) @Size(groups = CreatedUser.class, min = 2, max = 100) String username) {
        this.username = username;
    }

    public @NotNull(groups = {CreatedUser.class, UpdateUser.class}) @NotEmpty(groups = {CreatedUser.class, UpdateUser.class}) @Size(groups = {CreatedUser.class, UpdateUser.class}, min = 8, max = 60) String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(groups = {CreatedUser.class, UpdateUser.class}) @NotEmpty(groups = {CreatedUser.class, UpdateUser.class}) @Size(groups = {CreatedUser.class, UpdateUser.class}, min = 8, max = 60) String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (this.id == null)
            if (other.id != null)
                return false;
            else if (!this.id.equals(other.id))
                return false;
        return Objects.equals(this.id, other.id) && Objects.equals(this.username, other.username)
                && Objects.equals(this.password, other.password);
      }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
            return result;
        }
    }