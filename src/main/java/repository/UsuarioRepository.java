package repository;

import model.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository {
    private List<Usuario> usuarios;
    private int proximoId;
    
    public UsuarioRepository() {
        this.usuarios = new ArrayList<>();
        this.proximoId = 1;
    }
    
    public void adicionar(Usuario usuario) {
        usuario.setId(proximoId++);
        usuarios.add(usuario);
    }
    
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }
    
    public Optional<Usuario> buscarPorId(int id) {
        return usuarios.stream()
            .filter(u -> u.getId() == id)
            .findFirst();
    }
    
    public Optional<Usuario> buscarPorCpf(String cpf) {
        return usuarios.stream()
            .filter(u -> u.getCpf().equals(cpf))
            .findFirst();
    }
    
    public boolean remover(int id) {
        return usuarios.removeIf(u -> u.getId() == id);
    }
}