package service;

import model.Livro;
import repository.LivroRepository;
import exception.ValidacaoException;
import exception.RecursoNaoEncontradoException;

import java.util.List;

public class LivroService {
    private LivroRepository repository;
    
    public LivroService(LivroRepository repository) {
        this.repository = repository;
    }
    
    public void cadastrarLivro(String titulo, String autor, String isbn) throws ValidacaoException {
        // Validação de campos vazios
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new ValidacaoException("Título não pode ser vazio");
        }
        if (autor == null || autor.trim().isEmpty()) {
            throw new ValidacaoException("Autor não pode ser vazio");
        }
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new ValidacaoException("ISBN não pode ser vazio");
        }
        
        // Validação de ISBN duplicado
        if (repository.buscarPorIsbn(isbn).isPresent()) {
            throw new ValidacaoException("ISBN já cadastrado no sistema");
        }
        
        Livro livro = new Livro(0, titulo, autor, isbn);
        repository.adicionar(livro);
    }
    
    public List<Livro> listarTodos() {
        return repository.listarTodos();
    }
    
    public Livro buscarPorId(int id) throws RecursoNaoEncontradoException {
        return repository.buscarPorId(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado"));
    }
    
    public void removerLivro(int id) throws RecursoNaoEncontradoException, ValidacaoException {
        Livro livro = buscarPorId(id);
        
        if (!livro.isDisponivel()) {
            throw new ValidacaoException("Não é possível remover livro emprestado");
        }
        
        repository.remover(id);
    }
}