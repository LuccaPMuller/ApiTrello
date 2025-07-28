package com.example.tasks.Service;

import com.example.tasks.Model.Board;
import com.example.tasks.Repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit Test BoardService")
class BoardServiceTest {
    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    private Board board1;
    private Board board2;

    @BeforeEach
    void setUp() {
        board1 = new Board();
        board1.setId(1L);
        board1.setNome("Projeto A");

        board2 = new Board();
        board2.setId(2L);
        board2.setNome("Projeto B");
    }

    //GETs
    @Test
    @DisplayName("GETs: buscarTodos_DeveRetornarTodosOsBoards")
    void buscarTodos_DeveRetornarTodosOsBoards() {
        System.out.println("[Test] buscarTodos_DeveRetornarTodosOsBoards");
        when(boardRepository.findAll()).thenReturn(Arrays.asList(board1, board2));

        List<Board> result = boardService.buscarTodos();

        assertEquals(2, result.size());
        System.out.println("[Asserted] assertEquals(2, result.size())");
        assertTrue(result.containsAll(Arrays.asList(board1, board2)));
        System.out.println("[Asserted] assertTrue(result.containsAll(Arrays.asList(board1, board2)))");
        verify(boardRepository, times(1)).findAll();
        System.out.println("[Verified] verify(boardRepository, times(1)).findAll()");
        System.out.println();

    }

    @Test
    @DisplayName("GETs: buscarPorId_QuandoExistir_DeveRetornarBoard")
    void buscarPorId_QuandoExistir_DeveRetornarBoard() {
        System.out.println("[Test] buscarPorId_QuandoExistir_DeveRetornarBoard");
        when(boardRepository.findById(1L)).thenReturn(Optional.of(board1));

        Optional<Board> result = boardService.buscarPorId(1L);

        assertTrue(result.isPresent());
        System.out.println("[Asserted] assertTrue(result.isPresent())");
        assertEquals(board1, result.get());
        System.out.println("[Asserted] assertEquals(board1, result.get())");
        verify(boardRepository, times(1)).findById(1L);
        System.out.println("[Verified] verify(boardRepository, times(1)).findById(1L)");
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorId_QuandoNaoExistir_DeveRetornarVazio")
    void buscarPorId_QuandoNaoExistir_DeveRetornarVazio() {
        System.out.println("[Test] buscarPorId_QuandoNaoExistir_DeveRetornarVazio");
        when(boardRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Board> result = boardService.buscarPorId(99L);

        assertTrue(result.isEmpty());
        System.out.println("[Asserted] assertTrue(result.isEmpty())");
        verify(boardRepository, times(1)).findById(99L);
        System.out.println("[Verified] verify(boardRepository, times(1)).findById(99L)");
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorNome_DeveRetornarBoardsCorrespondentes")
    void buscarPorNome_DeveRetornarBoardsCorrespondentes() {
        System.out.println("[Test] buscarPorNome_DeveRetornarBoardsCorrespondentes");
        String nome = "projeto";
        when(boardRepository.findByNomeContainingIgnoreCase(nome))
                .thenReturn(Arrays.asList(board1, board2));

        List<Board> result = boardService.buscarPorNome(nome);

        assertEquals(2, result.size());
        System.out.println("[Asserted] assertEquals(2, result.size())");
        assertTrue(result.containsAll(Arrays.asList(board1, board2)));
        System.out.println("[Asserted] assertTrue(result.containsAll(Arrays.asList(board1, board2)))");
        verify(boardRepository, times(1)).findByNomeContainingIgnoreCase(nome);
        System.out.println("[Verified] verify(boardRepository, times(1)).findByNomeContainingIgnoreCase(nome)");
        System.out.println();
    }

    //POST
    @Test
    @DisplayName("POST: salvar_ComDadosValidos_DeveSalvarBoard")
    void salvar_ComDadosValidos_DeveSalvarBoard() {
        System.out.println("[Test] salvar_ComDadosValidos_DeveSalvarBoard");
        Board newBoard = new Board();
        newBoard.setNome("Novo Projeto");

        when(boardRepository.save(newBoard)).thenReturn(newBoard);

        Board result = boardService.salvar(newBoard);

        assertNotNull(result);
        System.out.println("[Asserted] assertNotNull(result)");
        assertEquals("Novo Projeto", result.getNome());
        System.out.println("[Asserted] assertEquals(\"Novo Projeto\", result.getNome())");
        verify(boardRepository, times(1)).save(newBoard);
        System.out.println("[Verified] verify(boardRepository, times(1)).save(newBoard)");
        System.out.println();
    }

    @Test
    @DisplayName("POST: salvar_ComNomeNulo_DeveLancarExcecao")
    void salvar_ComNomeNulo_DeveLancarExcecao() {
        System.out.println("[Test] salvar_ComNomeNulo_DeveLancarExcecao");
        Board invalidBoard = new Board();
        invalidBoard.setNome(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            boardService.salvar(invalidBoard);
        });

        assertEquals("Nome deve conter no minimo 3 caracteres.", exception.getMessage());
        System.out.println("[Asserted] assertEquals(\"Nome deve conter no minimo 3 caracteres.\", exception.getMessage())");
        verify(boardRepository, never()).save(any());
        System.out.println("[Verified] verify(boardRepository, never()).save(any())");
        System.out.println();
    }

    @Test
    @DisplayName("POST: salvar_ComNomeVazio_DeveLancarExcecao")
    void salvar_ComNomeVazio_DeveLancarExcecao() {
        System.out.println("[Test] salvar_ComNomeVazio_DeveLancarExcecao");
        Board invalidBoard = new Board();
        invalidBoard.setNome("   ");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            boardService.salvar(invalidBoard);
        });

        assertEquals("Nome deve conter no minimo 3 caracteres.", exception.getMessage());
        System.out.println("[Asserted] assertEquals(\"Nome deve conter no minimo 3 caracteres.\", exception.getMessage())");
        verify(boardRepository, never()).save(any());
        System.out.println("[Verified] verify(boardRepository, never()).save(any())");
        System.out.println();
    }

    @Test
    @DisplayName("POST: salvar_ComNomeCurto_DeveLancarExcecao")
    void salvar_ComNomeCurto_DeveLancarExcecao() {
        System.out.println("[Test] salvar_ComNomeCurto_DeveLancarExcecao");
        Board invalidBoard = new Board();
        invalidBoard.setNome("ab");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            boardService.salvar(invalidBoard);
        });

        assertEquals("Nome deve conter no minimo 3 caracteres.", exception.getMessage());
        System.out.println("[Asserted] assertEquals(\"Nome deve conter no minimo 3 caracteres.\", exception.getMessage())");
        verify(boardRepository, never()).save(any());
        System.out.println("[Verified] verify(boardRepository, never()).save(any()");
        System.out.println();
    }

    //DELETE
    @Test
    @DisplayName("DELETE: deletar_DeveChamarRepositorio")
    void deletar_DeveChamarRepositorio() {
        System.out.println("[Test] deletar_DeveChamarRepositorio");
        boardService.deletar(1L);

        verify(boardRepository, times(1)).deleteById(1L);
        System.out.println("[Verified] verify(boardRepository, times(1)).deleteById(1L)");
        System.out.println();
    }
}