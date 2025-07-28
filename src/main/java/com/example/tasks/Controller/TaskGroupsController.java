package com.example.tasks.Controller;

import com.example.tasks.Model.TaskGroups;
import com.example.tasks.Service.TaskGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/taskGroups")
public class TaskGroupsController {
    @Autowired
    private final TaskGroupsService taskGroupsService;

    //Constructor
    public TaskGroupsController(TaskGroupsService taskGroupsService) {
        this.taskGroupsService = taskGroupsService;
    }

    //Gets
    @GetMapping("/buscar")
    public List<TaskGroups> buscarTodos() {
        return taskGroupsService.buscarTodos();
    }
    @GetMapping("/buscar/id")
    @ResponseBody
    public TaskGroups buscarPorId(@RequestParam("id") Long id) {
        return taskGroupsService.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TaskGroup not found"));
    }
    @GetMapping("/buscar/nome")
    public List<TaskGroups> buscarPorNome(@RequestParam("nome") String nome){
        return taskGroupsService.buscarPorNome(nome);
    }
    @GetMapping("buscar/board/nome")
    public List<TaskGroups> buscarPorBoardNome(@RequestParam("nome") String nome){
        return taskGroupsService.buscarPorBoardNome(nome);
    }
    @GetMapping("buscar/board/id")
    public List<TaskGroups> buscarPorBoardId(@RequestParam("id") Long id){
        return taskGroupsService.buscarPorBoardId(id);
    }

    //Post
    @PostMapping("/salvar")
    public TaskGroups salvar(@RequestBody TaskGroups taskGroups){
        return taskGroupsService.salvar(taskGroups);
    }

    //Delete
    @DeleteMapping("deletar/id")
    public void deletar(@RequestParam("id")Long id) {
        taskGroupsService.deletar(id);
    }
}
