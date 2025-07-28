package com.example.tasks.Service;

import com.example.tasks.Model.Task;
import com.example.tasks.Model.TaskGroups;
import com.example.tasks.Repository.TaskGroupsRepository;
import com.example.tasks.Repository.TaskRepository;
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
import static com.example.tasks.Model.Task.Situacao.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit Test TaskService")
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskGroupsRepository taskGroupsRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task1;
    private Task task2;
    private TaskGroups taskGroup;

    @BeforeEach
    void setUp() {
        taskGroup = new TaskGroups();
        taskGroup.setId(1L);
        taskGroup.setNome("Grupo de Tarefas");

        task1 = new Task();
        task1.setId(1L);
        task1.setTitulo("Implementar testes");
        task1.setSituacao(TODO);
        task1.setTaskGroups(taskGroup);

        task2 = new Task();
        task2.setId(2L);
        task2.setTitulo("Refatorar código");
        task2.setSituacao(IN_PROGRESS);
        task2.setTaskGroups(taskGroup);
    }

    //GETs
    @Test
    @DisplayName("GETs: buscarTodos_DeveRetornarTodasAsTasks")
    void buscarTodos_DeveRetornarTodasAsTasks() {
        System.out.println("[Test] buscarTodos_DeveRetornarTodasAsTasks");
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> result = taskService.buscarTodos();

        assertEquals(2, result.size());
        System.out.println("[Asserted] assertEquals(2, result.size())");
        assertTrue(result.containsAll(Arrays.asList(task1, task2)));
        System.out.println("[Asserted] assertTrue(result.containsAll(Arrays.asList(task1, task2)))");
        verify(taskRepository, times(1)).findAll();
        System.out.println("[Verified] verify(taskRepository, times(1)).findAll()");
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorId_QuandoExistir_DeveRetornarTask")
    void buscarPorId_QuandoExistir_DeveRetornarTask() {
        System.out.println("[Test] buscarPorId_QuandoExistir_DeveRetornarTask");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));

        Optional<Task> result = taskService.buscarPorId(1L);

        assertTrue(result.isPresent());
        System.out.println("[Asserted] assertTrue(result.isPresent())");
        assertEquals(task1, result.get());
        System.out.println("[Asserted] assertEquals(task1, result.get())");
        verify(taskRepository, times(1)).findById(1L);
        System.out.println("[Verified] verify(taskRepository, times(1)).findById(1L)");
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorId_QuandoNaoExistir_DeveRetornarVazio")
    void buscarPorId_QuandoNaoExistir_DeveRetornarVazio() {
        System.out.println("[Test] buscarPorId_QuandoNaoExistir_DeveRetornarVazio");
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.buscarPorId(99L);

        assertTrue(result.isEmpty());
        System.out.println("[Asserted] assertTrue(result.isEmpty())");
        verify(taskRepository, times(1)).findById(99L);
        System.out.println("[Verified] verify(taskRepository, times(1)).findById(99L)");
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorTaskTitulo_DeveRetornarTasksCorrespondentes")
    void buscarPorTaskTitulo_DeveRetornarTasksCorrespondentes() {
        System.out.println("[Test] buscarPorTaskTitulo_DeveRetornarTasksCorrespondentes");
        String titulo = "implementar";
        when(taskRepository.findByTituloContainingIgnoreCase(titulo))
                .thenReturn(Arrays.asList(task1));

        List<Task> result = taskService.buscarPorTaskTitulo(titulo);

        assertEquals(1, result.size());
        System.out.println("[Asserted] assertEquals(1, result.size())");
        assertTrue(result.contains(task1));
        System.out.println("[Asserted] assertTrue(result.contains(task1))");
        verify(taskRepository, times(1)).findByTituloContainingIgnoreCase(titulo);
        System.out.println("[Verified] verify(taskRepository, times(1)).findByTituloContainingIgnoreCase(titulo)");
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorSituacao_DeveRetornarTasksCorrespondentes")
    void buscarPorSituacao_DeveRetornarTasksCorrespondentes() {
        System.out.println("[Test] buscarPorSituacao_DeveRetornarTasksCorrespondentes");
        when(taskRepository.findBySituacao(TODO)).thenReturn(Arrays.asList(task1));

        List<Task> result = taskService.buscarPorSituacao(TODO);

        assertEquals(1, result.size());
        System.out.println("[Asserted] assertEquals(1, result.size())");
        assertTrue(result.contains(task1));
        System.out.println("[Asserted] assertTrue(result.contains(task1))");
        verify(taskRepository, times(1)).findBySituacao(TODO);
        System.out.println("[Verified] verify(taskRepository, times(1)).findBySituacao(TODO)");
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorTaskGroupsId_DeveRetornarTasksDoGrupo")
    void buscarPorTaskGroupsId_DeveRetornarTasksDoGrupo() {
        System.out.println("[Test] buscarPorTaskGroupsId_DeveRetornarTasksDoGrupo");
        when(taskRepository.findByTaskGroupsId(1L)).thenReturn(Arrays.asList(task1, task2));

        List<Task> result = taskService.buscarPorTaskGroupsId(1L);

        assertEquals(2, result.size());
        System.out.println("[Asserted] assertEquals(2, result.size())");
        assertTrue(result.containsAll(Arrays.asList(task1, task2)));
        System.out.println("[Asserted] assertTrue(result.containsAll(Arrays.asList(task1, task2)))");
        verify(taskRepository, times(1)).findByTaskGroupsId(1L);
        System.out.println("[Verified] verify(taskRepository, times(1)).findByTaskGroupsId(1L)");
        System.out.println();
    }

    @Test
    @DisplayName("GETs: buscarPorTaskGroupsNome_DeveRetornarTasksDoGrupo")
    void buscarPorTaskGroupsNome_DeveRetornarTasksDoGrupo() {
        System.out.println("[Test] buscarPorTaskGroupsNome_DeveRetornarTasksDoGrupo");
        String nomeGrupo = "tarefas";
        when(taskRepository.findByTaskGroupsNomeContainingIgnoreCase(nomeGrupo))
                .thenReturn(Arrays.asList(task1, task2));

        List<Task> result = taskService.buscarPorTaskGroupsNome(nomeGrupo);

        assertEquals(2, result.size());
        System.out.println("[Asserted] assertEquals(2, result.size())");
        assertTrue(result.containsAll(Arrays.asList(task1, task2)));
        System.out.println("[Asserted] assertTrue(result.containsAll(Arrays.asList(task1, task2)))");
        verify(taskRepository, times(1)).findByTaskGroupsNomeContainingIgnoreCase(nomeGrupo);
        System.out.println("[Verified] verify(taskRepository, times(1)).findByTaskGroupsNomeContainingIgnoreCase(nomeGrupo)");
        System.out.println();
    }

    //POST
    @Test
    @DisplayName("POST: salvar_ComDadosValidos_DeveSalvarTask")
    void salvar_ComDadosValidos_DeveSalvarTask() {
        System.out.println("[Test] salvar_ComDadosValidos_DeveSalvarTask");
        Task newTask = new Task();
        newTask.setTitulo("Nova Tarefa");
        newTask.setSituacao(TODO);
        newTask.setTaskGroups(taskGroup);

        when(taskGroupsRepository.findById(1L)).thenReturn(Optional.of(taskGroup));
        when(taskRepository.save(newTask)).thenReturn(newTask);

        Task result = taskService.salvar(newTask);

        assertNotNull(result);
        System.out.println("[Asserted] assertNotNull(result)");
        assertEquals("Nova Tarefa", result.getTitulo());
        System.out.println("[Asserted] assertEquals(\"Nova Tarefa\", result.getTitulo())");
        assertEquals(TODO, result.getSituacao());
        System.out.println("[Asserted] assertEquals(TODO, result.getSituacao())");
        assertNotNull(result.getTaskGroups());
        System.out.println("[Asserted] assertNotNull(result.getTaskGroups())");
        verify(taskRepository, times(1)).save(newTask);
        System.out.println("[Verified] verify(taskRepository, times(1)).save(newTask)");
        System.out.println();
    }

    @Test
    @DisplayName("POST: salvar_ComTituloNulo_DeveLancarExcecao")
    void salvar_ComTituloNulo_DeveLancarExcecao() {
        System.out.println("[Test] salvar_ComTituloNulo_DeveLancarExcecao");
        Task invalidTask = new Task();
        invalidTask.setTitulo(null);
        invalidTask.setSituacao(TODO);
        invalidTask.setTaskGroups(taskGroup);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.salvar(invalidTask);
        });

        assertEquals("Título é obrigatório.", exception.getMessage());
        System.out.println("[Asserted] assertEquals(\"Título é obrigatório.\", exception.getMessage())");
        verify(taskRepository, never()).save(any());
        System.out.println("[Verified] verify(taskRepository, never()).save(any())");
        System.out.println();
    }

    @Test
    @DisplayName("POST: salvar_ComTituloVazio_DeveLancarExcecao")
    void salvar_ComTituloVazio_DeveLancarExcecao() {
        System.out.println("[Test] salvar_ComTituloVazio_DeveLancarExcecao");
        Task invalidTask = new Task();
        invalidTask.setTitulo("   ");
        invalidTask.setSituacao(TODO);
        invalidTask.setTaskGroups(taskGroup);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.salvar(invalidTask);
        });

        assertEquals("Título é obrigatório.", exception.getMessage());
        System.out.println("[Asserted] assertEquals(\"Título é obrigatório.\", exception.getMessage())");
        verify(taskRepository, never()).save(any());
        System.out.println("[Verified] verify(taskRepository, never()).save(any())");
        System.out.println();
    }

    @Test
    @DisplayName("POST: salvar_ComSituacaoInvalida_DeveLancarExcecao")
    void salvar_ComSituacaoInvalida_DeveLancarExcecao() {
        System.out.println("[Test] salvar_ComSituacaoInvalida_DeveLancarExcecao");
        Task invalidTask = new Task();
        invalidTask.setTitulo("Tarefa Válida");
        invalidTask.setSituacao(null);
        invalidTask.setTaskGroups(taskGroup);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.salvar(invalidTask);
        });

        assertEquals("Situação deve ser TODO, IN_PROGRESS ou DONE", exception.getMessage());
        System.out.println("[Asserted] assertEquals(\"Situação deve ser TODO, IN_PROGRESS ou DONE\", exception.getMessage())");
        verify(taskRepository, never()).save(any());
        System.out.println("[Verified] verify(taskRepository, never()).save(any())");
        System.out.println();
    }

    @Test
    @DisplayName("POST: salvar_SemTaskGroup_DeveLancarExcecao")
    void salvar_SemTaskGroup_DeveLancarExcecao() {
        System.out.println("[Test] salvar_SemTaskGroup_DeveLancarExcecao");
        Task invalidTask = new Task();
        invalidTask.setTitulo("Tarefa Válida");
        invalidTask.setSituacao(TODO);
        invalidTask.setTaskGroups(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.salvar(invalidTask);
        });

        assertEquals("Task deve ter um TaskGroup", exception.getMessage());
        System.out.println("[Asserted] assertEquals(\"Task deve ter um TaskGroup\", exception.getMessage())");
        verify(taskRepository, never()).save(any());
        System.out.println("[Verified] verify(taskRepository, never()).save(any())");
        System.out.println();
    }

    @Test
    @DisplayName("POST: salvar_ComTaskGroupInexistente_DeveLancarExcecao")
    void salvar_ComTaskGroupInexistente_DeveLancarExcecao() {
        System.out.println("[Test] salvar_ComTaskGroupInexistente_DeveLancarExcecao");
        Task invalidTask = new Task();
        invalidTask.setTitulo("Tarefa Válida");
        invalidTask.setSituacao(TODO);
        invalidTask.setTaskGroups(taskGroup);

        when(taskGroupsRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.salvar(invalidTask);
        });

        assertEquals("TaskGroup associado não encontrado", exception.getMessage());
        System.out.println("[Asserted] assertEquals(\"TaskGroup associado não encontrado\", exception.getMessage())");
        verify(taskRepository, never()).save(any());
        System.out.println("[Verified] verify(taskRepository, never()).save(any())");
        System.out.println();
    }

    //DELETE
    @Test
    @DisplayName("DELETE: deletar_DeveChamarRepositorio")
    void deletar_DeveChamarRepositorio() {
        System.out.println("[Test] deletar_DeveChamarRepositorio");
        taskService.deletar(1L);

        verify(taskRepository, times(1)).deleteById(1L);
        System.out.println("[Verified] verify(taskRepository, times(1)).deleteById(1L)");
        System.out.println();
    }
}