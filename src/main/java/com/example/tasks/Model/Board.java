package com.example.tasks.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "board")
@Data
@AllArgsConstructor
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_board", unique = true)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "dt_criacao")
    private LocalDate dtCriacao;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<TaskGroups> taskGroupsList;
    //Constructors
    public Board() {
        this.dtCriacao = LocalDate.now();
    }
    public Board (Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dtCriacao = LocalDate.now();
    }

    //toString
    @Override
    public String toString(){
        return STR."id_board, nome, descricao, dt_criacao = [\{getId()}, \{getNome()}, \{getDescricao()}, \{getDtCriacao()}]";
    }
}



