package com.example.tasks.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "taskgroups")
@Data
@AllArgsConstructor
@Getter
@Setter
public class TaskGroups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_taskgroup", unique = true)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_board")
    private Board board;
    @Column(name = "dt_criacao")
    private LocalDate dtCriacao;
    @OneToMany(mappedBy = "taskGroups", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<Task> taskList;
    //Constructors
    public TaskGroups() {
        this.dtCriacao = LocalDate.now();
    }
    public TaskGroups (Long id, String nome, Board board) {
        this.id = id;
        this.nome = nome;
        this.board = board;
        this.dtCriacao = LocalDate.now();
    }

    //toString
    @Override
    public String toString(){
        return STR."id_taskgroup, nome, id_board, dt_criacao = [\{getId()}, \{getNome()}, \{getBoard().getId()}, \{getDtCriacao()}]";
    }
}


