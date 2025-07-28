package com.example.tasks.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "task")
@Data
@AllArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_task", unique = true)
    private Long id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "situacao")
    private Situacao situacao;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_taskgroup")
    private TaskGroups taskGroups;
    @Column(name = "dt_criacao")
    private LocalDate dtCriacao;

    //Constructors
    public Task() {
        this.dtCriacao = LocalDate.now();
    }
    public Task(Long id, String titulo, String descricao, Situacao situacao, TaskGroups taskGroups) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.situacao = situacao;
        this.taskGroups = taskGroups;
        this.dtCriacao = LocalDate.now();
    }
    //toString
    @Override
    public String toString(){
        return STR."id_task, titulo, descricao, situacao, id_taskgroup, dt_criacao = [\{getId()}, \{getTitulo()}, \{getDescricao()}, \{getSituacao()}, \{getTaskGroups().getId()}, \{getDtCriacao()}]";
    }

    //Enum status
    public enum Situacao {
        TODO,
        IN_PROGRESS,
        DONE
    }
}

