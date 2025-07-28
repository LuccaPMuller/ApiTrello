package com.example.tasks.Controller;

import com.example.tasks.Model.Board;
import com.example.tasks.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {
    @Autowired
    private final BoardService boardService;

    //Constructor
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //Gets
    @GetMapping("/buscar")
    public List<Board> buscarTodos() {
        return boardService.buscarTodos();
    }
    @GetMapping("/buscar/id")
    @ResponseBody
    public Board buscarPorId(@RequestParam("id") Long id) {
        return boardService.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));
    }
    @GetMapping("/buscar/nome")
    public List<Board> buscarPorNome(@RequestParam("nome") String nome){
        return boardService.buscarPorNome(nome);
    }
    //Post
    @PostMapping("/salvar")
    public Board salvar(@RequestBody Board board){
        return boardService.salvar(board);
    }

    // Delete
    @DeleteMapping("deletar/id")
    public void deletar(@RequestParam("id")Long id) {
        boardService.deletar(id);
    }
}
