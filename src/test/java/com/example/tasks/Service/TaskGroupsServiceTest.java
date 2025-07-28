package com.example.tasks.Service;

import com.example.tasks.Model.Board;
import com.example.tasks.Model.TaskGroups;
import com.example.tasks.Repository.BoardRepository;
import com.example.tasks.Repository.TaskGroupsRepository;
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
@DisplayName("Unit Test TaskGroupsService")
class TaskGroupsServiceTest {
    @Mock
    private TaskGroupsRepository taskGroupsRepository;

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private TaskGroupsService taskGroupsService;

    private TaskGroups taskGroup1;
    private TaskGroups taskGroup2;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        board.setId(1L);
        board.setNome("Projeto Principal");

        taskGroup1 = new TaskGroups();
        taskGroup1.setId(1L);
        taskGroup1.setNome("Tarefas Urgentes");
        taskGroup1.setBoard(board);

        taskGroup2 = new TaskGroups();
        taskGroup2.setId(2L);
        taskGroup2.setNome("Tarefas Normais");
        taskGroup2.setBoard(board);
    }

    //GETs
    @Test
    @DisplayName("GETs: buscarTodos_DeveRetornarTodosOsTaskGroups")
    void buscarTodos_DeveRetornarTodosOsTaskGroups() {
        System.out.println("[Test] buscarTodos_DeveRetornarTodosOsTaskGroups");
        when(taskGroupsRepository.findAll()).thenReturn(Arrays.asList(taskGroup1, taskGroup2));

        List<TaskGroups> result = taskGroupsService.buscarTodos();

        assertEquals(2, result.size());
        System.out.println("[Asserted] assertEquals(2, result.size())");
        assertTrue(result.containsAll(Arrays.asList(taskGroup1, taskGroup2)));
        System.out.println("[Asserted] assertTrue(result.containsAll(Arrays.asList(taskGroup1, taskGroup2)))");
        verify(taskGroupsRepository, times(1)).findAll();
        System.out.println("[Verified] verify(taskGroupsRepository, times(1)).findAll()");
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorId_QuandoExistir_DeveRetornarTaskGroup")
    void buscarPorId_QuandoExistir_DeveRetornarTaskGroup() {
        System.out.println("[Test] buscarPorId_QuandoExistir_DeveRetornarTaskGroup");
        when(taskGroupsRepository.findById(1L)).thenReturn(Optional.of(taskGroup1));

        Optional<TaskGroups> result = taskGroupsService.buscarPorId(1L);

        assertTrue(result.isPresent());
        System.out.println("[Asserted] assertTrue(result.isPresent())");
        assertEquals(taskGroup1, result.get());
        System.out.println("[Asserted] assertEquals(taskGroup1, result.get())");
        verify(taskGroupsRepository, times(1)).findById(1L);
        System.out.println("[Verified] verify(taskGroupsRepository, times(1)).findById(1L)");
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorId_QuandoNaoExistir_DeveRetornarVazio")
    void buscarPorId_QuandoNaoExistir_DeveRetornarVazio() {
        System.out.println("[Test] buscarPorId_QuandoNaoExistir_DeveRetornarVazio");
        when(taskGroupsRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<TaskGroups> result = taskGroupsService.buscarPorId(99L);

        assertTrue(result.isEmpty());
        System.out.println("[Asserted] assertTrue(result.isEmpty())");
        verify(taskGroupsRepository, times(1)).findById(99L);
        System.out.println("[Verified] verify(taskGroupsRepository, times(1)).findById(99L)");
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorNome_DeveRetornarTaskGroupsCorrespondentes")
    void buscarPorNome_DeveRetornarTaskGroupsCorrespondentes() {
        System.out.println("[Test] buscarPorNome_DeveRetornarTaskGroupsCorrespondentes");
        String nome = "urgentes";
        when(taskGroupsRepository.findByNomeContainingIgnoreCase(nome))
                .thenReturn(Arrays.asList(taskGroup1));

        List<TaskGroups> result = taskGroupsService.buscarPorNome(nome);

        assertEquals(1, result.size());
        System.out.println("[Asserted] assertEquals(1, result.size())");
        assertTrue(result.contains(taskGroup1));
        System.out.println("[Asserted] assertTrue(result.contains(taskGroup1))");
        verify(taskGroupsRepository, times(1)).findByNomeContainingIgnoreCase(nome);
        System.out.println("[Verified] verify(taskGroupsRepository, times(1)).findByNomeContainingIgnoreCase(nome)");
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorBoardId_DeveRetornarTaskGroupsDoBoard")
    void buscarPorBoardId_DeveRetornarTaskGroupsDoBoard() {
        System.out.println("[Test] buscarPorBoardId_DeveRetornarTaskGroupsDoBoard");
        when(taskGroupsRepository.findByBoardId(1L)).thenReturn(Arrays.asList(taskGroup1, taskGroup2));

        List<TaskGroups> result = taskGroupsService.buscarPorBoardId(1L);

        assertEquals(2, result.size());
        System.out.println("[Asserted] assertEquals(2, result.size())");
        assertTrue(result.containsAll(Arrays.asList(taskGroup1, taskGroup2)));
        System.out.println("[Asserted] assertTrue(result.containsAll(Arrays.asList(taskGroup1, taskGroup2)))");
        verify(taskGroupsRepository, times(1)).findByBoardId(1L);
        System.out.println("[Verified] verify(taskGroupsRepository, times(1)).findByBoardId(1L)");
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorBoardNome_DeveRetornarTaskGroupsDoBoard")
    void buscarPorBoardNome_DeveRetornarTaskGroupsDoBoard() {
        System.out.println("[Test] buscarPorBoardNome_DeveRetornarTaskGroupsDoBoard");
        String nomeBoard = "principal";
        when(taskGroupsRepository.findByBoardNomeContainingIgnoreCase(nomeBoard))
                .thenReturn(Arrays.asList(taskGroup1, taskGroup2));

        List<TaskGroups> result = taskGroupsService.buscarPorBoardNome(nomeBoard);

        assertEquals(2, result.size());
        System.out.println("[Asserted] assertEquals(2, result.size())");
        assertTrue(result.containsAll(Arrays.asList(taskGroup1, taskGroup2)));
        System.out.println("[Asserted] assertTrue(result.containsAll(Arrays.asList(taskGroup1, taskGroup2)))");
        verify(taskGroupsRepository, times(1)).findByBoardNomeContainingIgnoreCase(nomeBoard);
        System.out.println("[Verified] verify(taskGroupsRepository, times(1)).findByBoardNomeContainingIgnoreCase(nomeBoard)");
        System.out.println();
    }

    //POST
    @Test
    @DisplayName("POST: salvar_ComDadosValidos_DeveSalvarTaskGroup")
    void salvar_ComDadosValidos_DeveSalvarTaskGroup() {
        System.out.println("[Test] salvar_ComDadosValidos_DeveSalvarTaskGroup");
        TaskGroups newTaskGroup = new TaskGroups();
        newTaskGroup.setNome("Novo Grupo");
        newTaskGroup.setBoard(board);

        when(boardRepository.findById(1L)).thenReturn(Optional.of(board));
        when(taskGroupsRepository.save(newTaskGroup)).thenReturn(newTaskGroup);

        TaskGroups result = taskGroupsService.salvar(newTaskGroup);

        assertNotNull(result);
        System.out.println("[Asserted] assertNotNull(result)");
        assertEquals("Novo Grupo", result.getNome());
        System.out.println("[Asserted] assertEquals(\"Novo Grupo\", result.getNome())");
        assertNotNull(result.getBoard());
        System.out.println("[Asserted] assertNotNull(result.getBoard())");
        verify(taskGroupsRepository, times(1)).save(newTaskGroup);
        System.out.println("[Verified] verify(taskGroupsRepository, times(1)).save(newTaskGroup)");
        System.out.println();
    }

    @Test
    @DisplayName("POST: salvar_ComNomeNulo_DeveLancarExcecao")
    void salvar_ComNomeNulo_DeveLancarExcecao() {
        System.out.println("[Test] salvar_ComNomeNulo_DeveLancarExcecao");
        TaskGroups invalidTaskGroup = new TaskGroups();
        invalidTaskGroup.setNome(null);
        invalidTaskGroup.setBoard(board);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            taskGroupsService.salvar(invalidTaskGroup);
        });

        assertEquals("Nome deve conter no minimo 3 caracteres.", exception.getMessage());
        System.out.println("[Asserted] assertEquals(\"Nome deve conter no minimo 3 caracteres.\", exception.getMessage())");
        verify(taskGroupsRepository, never()).save(any());
        System.out.println("[Verified] verify(taskGroupsRepository, never()).save(any())");
        System.out.println();
    }

    @Test
    @DisplayName("POST: salvar_ComNomeVazio_DeveLancarExcecao")
    void salvar_ComNomeVazio_DeveLancarExcecao() {
        System.out.println("[Test] salvar_ComNomeVazio_DeveLancarExcecao");
        TaskGroups invalidTaskGroup = new TaskGroups();
        invalidTaskGroup.setNome("   ");
        invalidTaskGroup.setBoard(board);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            taskGroupsService.salvar(invalidTaskGroup);
        });

        assertEquals("Nome deve conter no minimo 3 caracteres.", exception.getMessage());
        System.out.println("[Asserted] assertEquals(\"Nome deve conter no minimo 3 caracteres.\", exception.getMessage())");
        verify(taskGroupsRepository, never()).save(any());
        System.out.println("[Verified] verify(taskGroupsRepository, never()).save(any())");
        System.out.println();
    }

    @Test
    @DisplayName("POST: salvar_ComNomeCurto_DeveLancarExcecao")
    void salvar_ComNomeCurto_DeveLancarExcecao() {
        System.out.println("[Test] salvar_ComNomeCurto_DeveLancarExcecao");
        TaskGroups invalidTaskGroup = new TaskGroups();
        invalidTaskGroup.setNome("ab");
        invalidTaskGroup.setBoard(board);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            taskGroupsService.salvar(invalidTaskGroup);
        });

        assertEquals("Nome deve conter no minimo 3 caracteres.", exception.getMessage());
        System.out.println("[Asserted] assertEquals(\"Nome deve conter no minimo 3 caracteres.\", exception.getMessage())");
        verify(taskGroupsRepository, never()).save(any());
        System.out.println("[Verified] verify(taskGroupsRepository, never()).save(any())");
        System.out.println();
    }

    @Test
    @DisplayName("POST: salvar_SemBoard_DeveLancarExcecao")
    void salvar_SemBoard_DeveLancarExcecao() {
        System.out.println("[Test] salvar_SemBoard_DeveLancarExcecao");
        TaskGroups invalidTaskGroup = new TaskGroups();
        invalidTaskGroup.setNome("Grupo Válido");
        invalidTaskGroup.setBoard(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            taskGroupsService.salvar(invalidTaskGroup);
        });

        assertEquals("TaskGroup deve ter um Board", exception.getMessage());
        System.out.println("[Asserted] assertEquals(\"TaskGroup deve ter um Board\", exception.getMessage())");
        verify(taskGroupsRepository, never()).save(any());
        System.out.println("[Verified] verify(taskGroupsRepository, never()).save(any())");
        System.out.println();
    }

    //DELETE
    @Test
    @DisplayName("DELETE: deletar_DeveChamarRepositorio")
    void deletar_DeveChamarRepositorio() {
        System.out.println("[Test] deletar_DeveChamarRepositorio");
        taskGroupsService.deletar(1L);

        verify(taskGroupsRepository, times(1)).deleteById(1L);
        System.out.println("[Verified] verify(taskGroupsRepository, times(1)).deleteById(1L)");
        System.out.println();
    }
}