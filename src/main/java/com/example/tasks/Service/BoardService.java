package com.example.tasks.Service;

import com.example.tasks.Model.Board;
import com.example.tasks.Repository.BoardRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    //Constructor
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    //Gets
    public List<Board> buscarTodos(){
        return boardRepository.findAll();
    }
    public Optional<Board> buscarPorId(Long id){
        return boardRepository.findById(id);
    }
    public List<Board>buscarPorNome(String nome){
        return boardRepository.findByNomeContainingIgnoreCase(nome);
    }

    //Post
    public Board salvar(Board board) {
        try {
            if (board.getNome() == null || board.getNome().trim().isEmpty() || board.getNome().length() < 3) {
                throw new IllegalArgumentException("Nome deve conter no minimo 3 caracteres.");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Nome não pode ser nulo.");
        }
        return boardRepository.save(board);
    }

    //Delete
    public void deletar(Long id) {
        boardRepository.deleteById(id);
    }
}
