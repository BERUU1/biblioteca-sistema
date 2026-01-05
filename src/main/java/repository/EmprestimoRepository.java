package repository;

import model.Emprestimo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmprestimoRepository {
    private List<Emprestimo> emprestimos;
    private int proximoId;
    
    public EmprestimoRepository() {
        this.emprestimos = new ArrayList<>();
        this.proximoId = 1;
    }
    
    public void adicionar(Emprestimo emprestimo) {
        emprestimo.setId(proximoId++);
        emprestimos.add(emprestimo);
    }
    
    public List<Emprestimo> listarTodos() {
        return new ArrayList<>(emprestimos);
    }
    
    public Optional<Emprestimo> buscarPorId(int id) {
        return emprestimos.stream()
            .filter(e -> e.getId() == id)
            .findFirst();
    }
    
    public List<Emprestimo> buscarPorUsuario(int idUsuario) {
        return emprestimos.stream()
            .filter(e -> e.getIdUsuario() == idUsuario)
            .collect(Collectors.toList());
    }
    
    public List<Emprestimo> buscarAtivos() {
        return emprestimos.stream()
            .filter(e -> !e.isDevolvido())
            .collect(Collectors.toList());
    }
}
