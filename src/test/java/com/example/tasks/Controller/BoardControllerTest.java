package com.example.tasks.Controller;

import com.example.tasks.Model.Board;
import com.example.tasks.Service.BoardService;
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
@DisplayName("@SpringBootTest: BoardController")
public class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    private Board board1;
    private Board board2;

    @BeforeEach
    public void setup() {
        board1 = new Board();
        board1.setId(1L);
        board1.setNome("Board 1");

        board2 = new Board();
        board2.setId(2L);
        board2.setNome("Board 2");
    }

    //GETs
    @Test
    @DisplayName("GETs: buscarTodos_deveRetornarListaDeBoards")
    public void buscarTodos_deveRetornarListaDeBoards() throws Exception {
        System.out.println("[Test] buscarTodos_deveRetornarListaDeBoards");
        List<Board> allBoards = Arrays.asList(board1, board2);

        Mockito.when(boardService.buscarTodos()).thenReturn(allBoards);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/board/buscar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is(board1.getNome())))
                .andExpect(jsonPath("$[1].nome", is(board2.getNome())));
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorId_deveRetornarBoard")
    public void buscarPorId_deveRetornarBoard() throws Exception {
        System.out.println("[Test] buscarPorId_deveRetornarBoard");
        Mockito.when(boardService.buscarPorId(1L)).thenReturn(Optional.of(board1));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/board/buscar/id")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(board1.getNome())));
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorId_naoEncontrado_deveRetornarNotFound")
    public void buscarPorId_naoEncontrado_deveRetornarNotFound() throws Exception {
        System.out.println("[Test] buscarPorId_naoEncontrado_deveRetornarNotFound");
        Mockito.when(boardService.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/board/buscar/id")
                        .param("id", "99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorNome_deveRetornarBoards")
    public void buscarPorNome_deveRetornarBoards() throws Exception {
        System.out.println("[Test] buscarPorNome_deveRetornarBoards");
        List<Board> boards = Collections.singletonList(board1);
        Mockito.when(boardService.buscarPorNome("Board 1")).thenReturn(boards);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/board/buscar/nome")
                        .param("nome", "Board 1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Board 1")));
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorNome_naoEncontrado_deveRetornarListaVazia")
    public void buscarPorNome_naoEncontrado_deveRetornarListaVazia() throws Exception {
        System.out.println("[Test] buscarPorNome_naoEncontrado_deveRetornarListaVazia");
        Mockito.when(boardService.buscarPorNome("Inexistente")).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/board/buscar/nome")
                        .param("nome", "Inexistente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", empty()));
        System.out.println();
    }

    //POST
    @Test
    @DisplayName("POST: salvar_deveRetornarBoardSalvo")
    public void salvar_deveRetornarBoardSalvo() throws Exception {
        System.out.println("[Test] salvar_deveRetornarBoardSalvo");
        Mockito.when(boardService.salvar(Mockito.any(Board.class))).thenReturn(board1);

        String boardJson = "{\"nome\":\"Board 1\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/board/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(boardJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(board1.getNome())));
        System.out.println();
    }

    //DELETE
    @Test
    @DisplayName("DELETE: deletar_deveRetornarOk")
    public void deletar_deveRetornarOk() throws Exception {
        System.out.println("[Test] deletar_deveRetornarOk");
        Mockito.doNothing().when(boardService).deletar(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/board/deletar/id")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(boardService, Mockito.times(1)).deletar(1L);
        System.out.println();
    }
}