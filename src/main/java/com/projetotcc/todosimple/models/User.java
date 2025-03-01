package com.projetotcc.todosimple.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import java.util.Objects;

@Entity
@Table(name = User.TABLE_NAME)
public class User {
    public interface CreateUser {
    } /*
       * Serve pra fazer GARANTIR os requisitos das colunas, não deixar os campos ser
       * nulos por exemplo.
       */

    public interface UpdateUser {
    }

    public final static String TABLE_NAME = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) /*
                                                         * É como se fosse um auto_increment, ele vai adicionando os id
                                                         * por sequencia, por exemplo: criou o primeiro user, ele vai
                                                         * ser o id 1; criou o user 2, ele vai ser o id 2 e por aí vai
                                                         * indo...
                                                         */
    @Column(name = "id", unique = true) /*
                                         * A gente ta criando o nome da coluna, ou seja, este vai ser o ID (Primary
                                         * Key), ele é UNIQUE, ou seja, não é possível ter dois ID's iguais.
                                         */
    private Long id;

    @Column(name = "username", length = 50, nullable = false, unique = true) /*
                                                                              * Length = tamanho de caracteres da minha
                                                                              * variável; nullable = false = o campo não
                                                                              * pode ser vazio; unique = dois usuarios
                                                                              * não podem ter o mesmo nome
                                                                              */
    @NotNull(groups = CreateUser.class) /* Não pode ser nulo */
    @NotEmpty(groups = CreateUser.class) /* Não pode passar uma cadeia de caracteres (String) vazia */
    @Size(groups = CreateUser.class, min = 2, max = 50) /*
                                                         * Aqui a gente define que o mínimo de caracteres pro campo
                                                         * usuário é 2, e o máximo 100 (que nem ta ali em length dps
                                                         * do @Column username)
                                                         */
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    /*
     * A gente usar o Update só aqui na senha, por que a senha pode ser atualizada,
     * já o username não. Quando atualizar a senha, esses parâmetros entram, pra
     * garantir as obrigações (notnull, notempty, size)
     */

     @JsonProperty(access = Access.WRITE_ONLY) /* Criei um pdf explicando: https://drive.google.com/file/d/1cE-cWGQl7euGnCKjFF7sWCyhHaFcdWdU/view?usp=drive_link */
    @NotNull(groups = { CreateUser.class, UpdateUser.class }) /* Não pode ser nulo */
    @NotEmpty(groups = { CreateUser.class, UpdateUser.class }) /*
                                                                * Não pode passar uma cadeia de caracteres (String)
                                                                * vazia
                                                                */
    @Size(groups = { CreateUser.class, UpdateUser.class }, min = 8, max = 100) /*
                                                                                * Aqui a gente define que o mínimo de
                                                                                * caracteres pro campo password é 8, e o
                                                                                * máximo 100 (que nem ta ali em length
                                                                                * dps do @Column password)
                                                                                */
    private String password;


    @OneToMany(mappedBy = "user")
    private List <Task> tasks = new ArrayList<Task>();

    public User() {
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }


    @Override
    public boolean equals(Object obj) { /*
                                         * Aqui criamos um método que recebe um parâmetro (obj), que pode ser qualquer
                                         * coisa (Object obj) (quando digo qqr coisa é q a gnt pode trocar esse (obj)
                                         * por qualquer outro nome; o método retorna um boolean, ou seja apenas true ou
                                         * false.)
                                         */
        if (obj == this) /*
                          * Nas linhas 118 e 123 se o objeto que estamos comparando (obj) for o mesmo que
                          * o
                          * objeto atual (this), então os dois são iguais e retorna verdadeiro e segue o
                          * fluxo
                          */
            return true;
        if (obj == null) /*
                          * Nas linhas 124 e 128 se o objeto passado (obj) for null (vazio), significa
                          * que
                          * não existe nada pra comparar, sendo assim, é false
                          */
            return false;
        if (!(obj instanceof User)) /*
                                     * Nas linhas 129 e 133 verificamos se o objeto (obj) é um objeto da classe
                                     * User;
                                     * se o objeto (obj) for de outro tipo (String, int, produto...) aí é false
                                     */
            return false;
        User other = (User) obj; /*
                                  * Aqui confirmamos que o obj é um User, então podemos converter (cast) ele para
                                  * um objeto User e guardar ele na variável other, agora conseguimos instanciar
                                  * os atributos (id, username, password) do outro objeto
                                  */
        if (this.id == null) /*
                              * Nas linhas 139, 150, 151, 152 e 153. Primeiro verificamos se o id do objeto
                              * atual(this.id) é null; se o id do
                              * outro objeto (other.id) não for null, então eles são diferentes, ou seja,
                              * retorna false; se ambos forem null, continuamos; se this. id não for null,
                              * verificamos se this.id.equals(other.id), se for diferente, retorna false
                              * Exemplo: User user1 = new User(null, "Alice", "1234");
                              * User user2 = new User(1, "Alice", "1234");
                              * 
                              * System.out.println(user1.equals(user2)); // false (porque os IDs são
                              * diferentes)
                              */
            if (other.id != null)
                return false;
            else if (!this.id.equals(other.id))
                return false;

        return Objects.equals(this.id, other.id) && Objects.equals(this.username, other.username) /*
                                                                                                   * Nas linhas 159 e
                                                                                                   * 175 usamos a classe
                                                                                                   * Object pra
                                                                                                   * verificar se os
                                                                                                   * atributos id,
                                                                                                   * username e password
                                                                                                   * são iguais; se
                                                                                                   * todos forem iguais,
                                                                                                   * retornamos true. Se
                                                                                                   * não, false... O
                                                                                                   * método
                                                                                                   * Objects.equals(a,
                                                                                                   * b) já trata null,
                                                                                                   * então evita erros
                                                                                                   */
                && Objects.equals(this.password, other.password);

        /*
         * Resumindo o código; se obj for o prório objeto > true; se obj for null >
         * false; se obj não for um User > retorna false; Converte obj para User >
         * Compara os IDs (se um for null e o outro não, retorna false) > Compara id,
         * username e password. Se todos forem iguais, retorna true, resumindo isso é
         * uma validação de dados
         */

    }

    /*
     * Aprender o que é um hashcode:
     * https://www.alura.com.br/artigos/ensinando-que-e-o-hashcode
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }

}
