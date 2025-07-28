package com.example.tasks.Service;

import com.example.tasks.Model.Task;
import com.example.tasks.Model.Task.Situacao;
import com.example.tasks.Model.TaskGroups;
import com.example.tasks.Repository.TaskGroupsRepository;
import com.example.tasks.Repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import static com.example.tasks.Model.Task.Situacao.TODO;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskGroupsRepository taskGroupsRepository;

    //Constructor
    public TaskService(TaskRepository taskRepository, TaskGroupsRepository taskGroupsRepository) {
        this.taskRepository = taskRepository;
        this.taskGroupsRepository = taskGroupsRepository;
    }

    //Gets
    public List<Task> buscarTodos(){
        return taskRepository.findAll();
    }
    public Optional<Task> buscarPorId(Long id){
        return taskRepository.findById(id);
    }
    public List<Task>buscarPorTaskTitulo(String titulo){
        return taskRepository.findByTituloContainingIgnoreCase(titulo);
    }
    public List<Task> buscarPorSituacao(Situacao situacao){
        return taskRepository.findBySituacao(situacao);
    }
    public List<Task> buscarPorTaskGroupsId(Long id){
        return taskRepository.findByTaskGroupsId(id);
    }
    public List<Task> buscarPorTaskGroupsNome(String nome){
        return taskRepository.findByTaskGroupsNomeContainingIgnoreCase(nome);
    }

    // Post
    public Task salvar(Task task) {
        if(task.getTitulo() == null || task.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("Título é obrigatório.");
        }
        if(task.getSituacao() == null ||
                !(task.getSituacao() == TODO ||
                        task.getSituacao() == Situacao.IN_PROGRESS ||
                        task.getSituacao() == Situacao.DONE)) {
            throw new IllegalArgumentException("Situação deve ser TODO, IN_PROGRESS ou DONE");
        }
        if(task.getTaskGroups() == null) {
            throw new IllegalArgumentException("Task deve ter um TaskGroup");
        }
        Long taskGroupsId = task.getTaskGroups().getId();
        Optional<TaskGroups> taskGroups = taskGroupsRepository.findById(taskGroupsId);
        if(taskGroups.isEmpty()) {
            throw new IllegalArgumentException("TaskGroup associado não encontrado");
        }
        task.setTaskGroups(taskGroups.get());
        return taskRepository.save(task);
    }

    //Deletar
    public void deletar(Long id) {
        taskRepository.deleteById(id);
    }
}
