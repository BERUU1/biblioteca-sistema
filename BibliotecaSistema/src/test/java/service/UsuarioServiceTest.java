package service;

import model.Usuario;
import repository.UsuarioRepository;
import exception.ValidacaoException;
import exception.RecursoNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do UsuarioService")
public class UsuarioServiceTest {
    
    private UsuarioService usuarioService;
    private UsuarioRepository usuarioRepository;
    
    @BeforeEach
    public void setUp() {
        usuarioRepository = new UsuarioRepository();
        usuarioService = new UsuarioService(usuarioRepository);
    }
    
    @Test
    @DisplayName("Deve cadastrar usuário com dados válidos")
    public void deveCadastrarUsuarioComDadosValidos() throws ValidacaoException {
        // Arrange
        String nome = "João Silva";
        String email = "joao@email.com";
        String cpf = "123.456.789-00";
        
        // Act
        usuarioService.cadastrarUsuario(nome, email, cpf);
        
        // Assert
        assertEquals(1, usuarioService.listarTodos().size());
        assertEquals(nome, usuarioService.listarTodos().get(0).getNome());
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao cadastrar usuário com nome vazio")
    public void deveLancarExcecaoAoCadastrarUsuarioComNomeVazio() {
        // Arrange
        String nome = "";
        String email = "joao@email.com";
        String cpf = "123.456.789-00";
        
        // Act & Assert
        ValidacaoException exception = assertThrows(
            ValidacaoException.class,
            () -> usuarioService.cadastrarUsuario(nome, email, cpf)
        );
        
        assertEquals("Nome não pode ser vazio", exception.getMessage());
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao cadastrar CPF duplicado")
    public void deveLancarExcecaoAoCadastrarCpfDuplicado() throws ValidacaoException {
        // Arrange
        String cpf = "123.456.789-00";
        usuarioService.cadastrarUsuario("João Silva", "joao@email.com", cpf);
        
        // Act & Assert
        ValidacaoException exception = assertThrows(
            ValidacaoException.class,
            () -> usuarioService.cadastrarUsuario("Maria Santos", "maria@email.com", cpf)
        );
        
        assertEquals("CPF já cadastrado no sistema", exception.getMessage());
    }
    
    @Test
    @DisplayName("Deve buscar usuário por ID existente")
    public void deveBuscarUsuarioPorIdExistente() throws ValidacaoException, RecursoNaoEncontradoException {
        // Arrange
        usuarioService.cadastrarUsuario("João Silva", "joao@email.com", "123.456.789-00");
        
        // Act
        Usuario usuario = usuarioService.buscarPorId(1);
        
        // Assert
        assertNotNull(usuario);
        assertEquals("João Silva", usuario.getNome());
    }
}