package service;

import model.Usuario;
import repository.UsuarioRepository;
import exception.ValidacaoException;
import exception.RecursoNaoEncontradoException;

import java.util.List;

public class UsuarioService {
    private UsuarioRepository repository;
    
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }
    
    public void cadastrarUsuario(String nome, String email, String cpf) throws ValidacaoException {
        // Validações
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidacaoException("Nome não pode ser vazio");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new ValidacaoException("Email não pode ser vazio");
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new ValidacaoException("CPF não pode ser vazio");
        }
        
        // CPF duplicado
        if (repository.buscarPorCpf(cpf).isPresent()) {
            throw new ValidacaoException("CPF já cadastrado no sistema");
        }
        
        Usuario usuario = new Usuario(0, nome, email, cpf);
        repository.adicionar(usuario);
    }
    
    public List<Usuario> listarTodos() {
        return repository.listarTodos();
    }
    
    public Usuario buscarPorId(int id) throws RecursoNaoEncontradoException {
        return repository.buscarPorId(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
    }
}