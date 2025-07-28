package com.example.tasks.Controller;

import com.example.tasks.Model.Task;
import com.example.tasks.Model.Task.Situacao;
import com.example.tasks.Service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("@SpringBootTest: TaskController")
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private Task todoTask;
    private Task inProgressTask;
    private Task doneTask;

    @BeforeEach
    public void setup() {
        todoTask = new Task();
        todoTask.setId(1L);
        todoTask.setTitulo("Fazer documentação");
        todoTask.setSituacao(Situacao.TODO);

        inProgressTask = new Task();
        inProgressTask.setId(2L);
        inProgressTask.setTitulo("Implementar feature X");
        inProgressTask.setSituacao(Situacao.IN_PROGRESS);

        doneTask = new Task();
        doneTask.setId(3L);
        doneTask.setTitulo("Corrigir bug Y");
        doneTask.setSituacao(Situacao.DONE);
    }

    //GETs
    @Test
    @DisplayName("GETs: buscarTodos_deveRetornarTodasTasks")
    public void buscarTodos_deveRetornarTodasTasks() throws Exception {
        System.out.println("[Test] buscarTodos_deveRetornarTodasTasks");
        List<Task> allTasks = Arrays.asList(todoTask, inProgressTask, doneTask);
        Mockito.when(taskService.buscarTodos()).thenReturn(allTasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/task/buscar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].situacao", is("TODO")))
                .andExpect(jsonPath("$[1].situacao", is("IN_PROGRESS")))
                .andExpect(jsonPath("$[2].situacao", is("DONE")));
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorId_deveRetornarTaskExistente")
    public void buscarPorId_deveRetornarTaskExistente() throws Exception {
        System.out.println("[Test] buscarPorId_deveRetornarTaskExistente");
        Mockito.when(taskService.buscarPorId(1L)).thenReturn(Optional.of(todoTask));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/task/buscar/id")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is(todoTask.getTitulo())))
                .andExpect(jsonPath("$.situacao", is("TODO")));
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorId_deveRetornarNotFoundParaTaskInexistente")
    public void buscarPorId_deveRetornarNotFoundParaTaskInexistente() throws Exception {
        System.out.println("[Test] buscarPorId_deveRetornarNotFoundParaTaskInexistente");
        Mockito.when(taskService.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/task/buscar/id")
                        .param("id", "99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorTitulo_deveRetornarTasksCorrespondentes")
    public void buscarPorTitulo_deveRetornarTasksCorrespondentes() throws Exception {
        System.out.println("[Test] buscarPorTitulo_deveRetornarTasksCorrespondentes");
        List<Task> tasks = Collections.singletonList(todoTask);
        Mockito.when(taskService.buscarPorTaskTitulo("Fazer")).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/task/buscar/titulo")
                        .param("titulo", "Fazer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].titulo", is(todoTask.getTitulo())));
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorSituacao_TODO_deveRetornarTasksCorrespondentes")
    public void buscarPorSituacao_TODO_deveRetornarTasksCorrespondentes() throws Exception {
        System.out.println("[Test] buscarPorSituacao_TODO_deveRetornarTasksCorrespondentes");
        List<Task> tasks = Collections.singletonList(todoTask);
        Mockito.when(taskService.buscarPorSituacao(Situacao.TODO)).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/task/buscar/situacao")
                        .param("situacao", "TODO")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].situacao", is("TODO")));
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorSituacao_IN_PROGRESS_deveRetornarTasksCorrespondentes")
    public void buscarPorSituacao_IN_PROGRESS_deveRetornarTasksCorrespondentes() throws Exception {
        System.out.println("[Test] buscarPorSituacao_IN_PROGRESS_deveRetornarTasksCorrespondentes");
        List<Task> tasks = Collections.singletonList(inProgressTask);
        Mockito.when(taskService.buscarPorSituacao(Situacao.IN_PROGRESS)).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/task/buscar/situacao")
                        .param("situacao", "IN_PROGRESS")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].situacao", is("IN_PROGRESS")));
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorSituacao_DONE_deveRetornarTasksCorrespondentes")
    public void buscarPorSituacao_DONE_deveRetornarTasksCorrespondentes() throws Exception {
        System.out.println("[Test] buscarPorSituacao_DONE_deveRetornarTasksCorrespondentes");
        List<Task> tasks = Collections.singletonList(doneTask);
        Mockito.when(taskService.buscarPorSituacao(Situacao.DONE)).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/task/buscar/situacao")
                        .param("situacao", "DONE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].situacao", is("DONE")));
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorTaskGroupsId_deveRetornarTasksDoGrupo")
    public void buscarPorTaskGroupsId_deveRetornarTasksDoGrupo() throws Exception {
        System.out.println("[Test] buscarPorTaskGroupsId_deveRetornarTasksDoGrupo");
        List<Task> tasks = Arrays.asList(todoTask, inProgressTask);
        Mockito.when(taskService.buscarPorTaskGroupsId(1L)).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/task/buscar/taskgroups/id")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorTaskGroupsNome_deveRetornarTasksDoGrupo")
    public void buscarPorTaskGroupsNome_deveRetornarTasksDoGrupo() throws Exception {
        System.out.println("[Test] buscarPorTaskGroupsNome_deveRetornarTasksDoGrupo");
        List<Task> tasks = Collections.singletonList(doneTask);
        Mockito.when(taskService.buscarPorTaskGroupsNome("Backlog")).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/task/buscar/taskgroups/nome")
                        .param("nome", "Backlog")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        System.out.println();
    }

    //POST
    @Test
    @DisplayName("POST: salvar_deveCriarNovaTask")
    public void salvar_deveCriarNovaTask() throws Exception {
        System.out.println("[Test] salvar_deveCriarNovaTask");
        Task novaTask = new Task();
        novaTask.setTitulo("Nova Task");
        novaTask.setSituacao(Situacao.TODO);

        Mockito.when(taskService.salvar(Mockito.any(Task.class))).thenReturn(novaTask);

        String taskJson = "{\"titulo\":\"Nova Task\",\"situacao\":\"TODO\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/task/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is("Nova Task")))
                .andExpect(jsonPath("$.situacao", is("TODO")));
        System.out.println();
    }

    //DELETE
    @Test
    @DisplayName("DELETE: deletar_deveRemoverTaskExistente")
    public void deletar_deveRemoverTaskExistente() throws Exception {
        System.out.println("[Test] deletar_deveRemoverTaskExistente");
        Mockito.doNothing().when(taskService).deletar(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/task/deletar/id")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(taskService, Mockito.times(1)).deletar(1L);
        System.out.println();
    }
}