package repository;

import model.Livro;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LivroRepository {
    private List<Livro> livros;
    private int proximoId;
    
    public LivroRepository() {
        this.livros = new ArrayList<>();
        this.proximoId = 1;
    }
    
    public void adicionar(Livro livro) {
        livro.setId(proximoId++);
        livros.add(livro);
    }
    
    public List<Livro> listarTodos() {
        return new ArrayList<>(livros);
    }
    
    public Optional<Livro> buscarPorId(int id) {
        return livros.stream()
            .filter(l -> l.getId() == id)
            .findFirst();
    }
    
    public Optional<Livro> buscarPorIsbn(String isbn) {
        return livros.stream()
            .filter(l -> l.getIsbn().equals(isbn))
            .findFirst();
    }
    
    public boolean remover(int id) {
        return livros.removeIf(l -> l.getId() == id);
    }
    
    public void atualizar(Livro livro) {
        for (int i = 0; i < livros.size(); i++) {
            if (livros.get(i).getId() == livro.getId()) {
                livros.set(i, livro);
                break;
            }
        }
    }
}