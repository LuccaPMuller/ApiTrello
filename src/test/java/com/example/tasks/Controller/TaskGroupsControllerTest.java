package com.example.tasks.Controller;

import com.example.tasks.Model.TaskGroups;
import com.example.tasks.Service.TaskGroupsService;
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
@DisplayName("@SpringBootTest: TaskGroupsController")
public class TaskGroupsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskGroupsService taskGroupsService;

    private TaskGroups taskGroup1;
    private TaskGroups taskGroup2;

    @BeforeEach
    public void setup() {
        taskGroup1 = new TaskGroups();
        taskGroup1.setId(1L);
        taskGroup1.setNome("Grupo 1");

        taskGroup2 = new TaskGroups();
        taskGroup2.setId(2L);
        taskGroup2.setNome("Grupo 2");
    }

    //GETs
    @Test
    @DisplayName("GETs: buscarTodos_deveRetornarListaDeTaskGroups")
    public void buscarTodos_deveRetornarListaDeTaskGroups() throws Exception {
        System.out.println("[Test] buscarTodos_deveRetornarListaDeTaskGroups");
        List<TaskGroups> allTaskGroups = Arrays.asList(taskGroup1, taskGroup2);

        Mockito.when(taskGroupsService.buscarTodos()).thenReturn(allTaskGroups);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/taskGroups/buscar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is(taskGroup1.getNome())))
                .andExpect(jsonPath("$[1].nome", is(taskGroup2.getNome())));
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorId_deveRetornarTaskGroup")
    public void buscarPorId_deveRetornarTaskGroup() throws Exception {
        System.out.println("[Test] buscarPorId_deveRetornarTaskGroup");
        Mockito.when(taskGroupsService.buscarPorId(1L)).thenReturn(Optional.of(taskGroup1));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/taskGroups/buscar/id")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(taskGroup1.getNome())));
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorId_naoEncontrado_deveRetornarNotFound")
    public void buscarPorId_naoEncontrado_deveRetornarNotFound() throws Exception {
        System.out.println("[Test] buscarPorId_naoEncontrado_deveRetornarNotFound");
        Mockito.when(taskGroupsService.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/taskGroups/buscar/id")
                        .param("id", "99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorNome_deveRetornarTaskGroups")
    public void buscarPorNome_deveRetornarTaskGroups() throws Exception {
        System.out.println("[Test] buscarPorNome_deveRetornarTaskGroups");
        List<TaskGroups> taskGroups = Collections.singletonList(taskGroup1);
        Mockito.when(taskGroupsService.buscarPorNome("Grupo 1")).thenReturn(taskGroups);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/taskGroups/buscar/nome")
                        .param("nome", "Grupo 1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Grupo 1")));
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorBoardNome_deveRetornarTaskGroups")
    public void buscarPorBoardNome_deveRetornarTaskGroups() throws Exception {
        System.out.println("[Test] buscarPorBoardNome_deveRetornarTaskGroups");
        List<TaskGroups> taskGroups = Collections.singletonList(taskGroup1);
        Mockito.when(taskGroupsService.buscarPorBoardNome("Board 1")).thenReturn(taskGroups);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/taskGroups/buscar/board/nome")
                        .param("nome", "Board 1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorBoardId_deveRetornarTaskGroups")
    public void buscarPorBoardId_deveRetornarTaskGroups() throws Exception {
        System.out.println("[Test] buscarPorBoardId_deveRetornarTaskGroups");
        List<TaskGroups> taskGroups = Collections.singletonList(taskGroup2);
        Mockito.when(taskGroupsService.buscarPorBoardId(2L)).thenReturn(taskGroups);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/taskGroups/buscar/board/id")
                        .param("id", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        System.out.println();
    }

    //POST
    @Test
    @DisplayName("POST: salvar_deveRetornarTaskGroupSalvo")
    public void salvar_deveRetornarTaskGroupSalvo() throws Exception {
        System.out.println("[Test] salvar_deveRetornarTaskGroupSalvo");
        Mockito.when(taskGroupsService.salvar(Mockito.any(TaskGroups.class))).thenReturn(taskGroup1);

        String taskGroupJson = "{\"nome\":\"Grupo 1\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/taskGroups/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskGroupJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(taskGroup1.getNome())));
        System.out.println();
    }

    //DELETE
    @Test
    @DisplayName("DELETE: deletar_deveRetornarOk")
    public void deletar_deveRetornarOk() throws Exception {
        System.out.println("[Test] deletar_deveRetornarOk");
        Mockito.doNothing().when(taskGroupsService).deletar(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/taskGroups/deletar/id")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(taskGroupsService, Mockito.times(1)).deletar(1L);
        System.out.println();
    }
}