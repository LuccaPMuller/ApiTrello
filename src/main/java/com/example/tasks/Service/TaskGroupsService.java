package com.example.tasks.Service;

import com.example.tasks.Model.Board;
import com.example.tasks.Model.TaskGroups;
import com.example.tasks.Repository.BoardRepository;
import com.example.tasks.Repository.TaskGroupsRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskGroupsService {
    private final TaskGroupsRepository taskGroupsRepository;
    private final BoardRepository boardRepository;

    //Constructor
    public TaskGroupsService(TaskGroupsRepository taskGroupsRepository, BoardRepository boardRepository) {
        this.taskGroupsRepository = taskGroupsRepository;
        this.boardRepository = boardRepository;
    }

    //Gets
    public List<TaskGroups> buscarTodos(){
        return taskGroupsRepository.findAll();
    }
    public Optional<TaskGroups> buscarPorId(Long id){
        return taskGroupsRepository.findById(id);
    }
    public List<TaskGroups> buscarPorNome(String nome){
        return taskGroupsRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<TaskGroups>buscarPorBoardId(Long id){
        return taskGroupsRepository.findByBoardId(id);
    }

    public List<TaskGroups>buscarPorBoardNome(String nome){
        return taskGroupsRepository.findByBoardNomeContainingIgnoreCase(nome);
    }

    // Post
    public TaskGroups salvar(TaskGroups taskGroups) {
        if(taskGroups.getNome() == null || taskGroups.getNome().trim().isEmpty() || taskGroups.getNome().length() < 3) {
            throw new IllegalArgumentException("Nome deve conter no minimo 3 caracteres.");
        }
        if(taskGroups.getBoard() == null) {
            throw new IllegalArgumentException("TaskGroup deve ter um Board");
        }
        Long boardId = taskGroups.getBoard().getId();
        Optional<Board> board = boardRepository.findById(boardId);
        if(board.isEmpty()) {
            throw new IllegalArgumentException("Board associado não encontrado");
        }

        taskGroups.setBoard(board.get());
        return taskGroupsRepository.save(taskGroups);
    }

    //Deletar
    public void deletar(Long id) {
        taskGroupsRepository.deleteById(id);
    }
}
