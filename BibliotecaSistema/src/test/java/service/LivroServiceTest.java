package service;

import model.Livro;
import repository.LivroRepository;
import exception.ValidacaoException;
import exception.RecursoNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do LivroService")
public class LivroServiceTest {
    
    private LivroService livroService;
    private LivroRepository livroRepository;
    
    @BeforeEach
    public void setUp() {
        // Arrange: Preparar o ambiente antes de cada teste
        livroRepository = new LivroRepository();
        livroService = new LivroService(livroRepository);
    }
    
    @Test
    @DisplayName("Deve cadastrar livro com dados válidos")
    public void deveCadastrarLivroComDadosValidos() throws ValidacaoException {
        // Arrange
        String titulo = "Clean Code";
        String autor = "Robert Martin";
        String isbn = "978-0132350884";
        
        // Act
        livroService.cadastrarLivro(titulo, autor, isbn);
        
        // Assert
        assertEquals(1, livroService.listarTodos().size());
        assertEquals(titulo, livroService.listarTodos().get(0).getTitulo());
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao cadastrar livro com título vazio")
    public void deveLancarExcecaoAoCadastrarLivroComTituloVazio() {
        // Arrange
        String titulo = "";
        String autor = "Robert Martin";
        String isbn = "978-0132350884";
        
        // Act & Assert
        ValidacaoException exception = assertThrows(
            ValidacaoException.class,
            () -> livroService.cadastrarLivro(titulo, autor, isbn)
        );
        
        assertEquals("Título não pode ser vazio", exception.getMessage());
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao cadastrar livro com autor vazio")
    public void deveLancarExcecaoAoCadastrarLivroComAutorVazio() {
        // Arrange
        String titulo = "Clean Code";
        String autor = "";
        String isbn = "978-0132350884";
        
        // Act & Assert
        ValidacaoException exception = assertThrows(
            ValidacaoException.class,
            () -> livroService.cadastrarLivro(titulo, autor, isbn)
        );
        
        assertEquals("Autor não pode ser vazio", exception.getMessage());
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao cadastrar livro com ISBN vazio")
    public void deveLancarExcecaoAoCadastrarLivroComIsbnVazio() {
        // Arrange
        String titulo = "Clean Code";
        String autor = "Robert Martin";
        String isbn = "";
        
        // Act & Assert
        ValidacaoException exception = assertThrows(
            ValidacaoException.class,
            () -> livroService.cadastrarLivro(titulo, autor, isbn)
        );
        
        assertEquals("ISBN não pode ser vazio", exception.getMessage());
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao cadastrar ISBN duplicado")
    public void deveLancarExcecaoAoCadastrarIsbnDuplicado() throws ValidacaoException {
        // Arrange
        String titulo1 = "Clean Code";
        String titulo2 = "Outro Livro";
        String autor = "Robert Martin";
        String isbn = "978-0132350884";
        
        livroService.cadastrarLivro(titulo1, autor, isbn);
        
        // Act & Assert
        ValidacaoException exception = assertThrows(
            ValidacaoException.class,
            () -> livroService.cadastrarLivro(titulo2, autor, isbn)
        );
        
        assertEquals("ISBN já cadastrado no sistema", exception.getMessage());
    }
    
    @Test
    @DisplayName("Deve buscar livro por ID existente")
    public void deveBuscarLivroPorIdExistente() throws ValidacaoException, RecursoNaoEncontradoException {
        // Arrange
        livroService.cadastrarLivro("Clean Code", "Robert Martin", "978-0132350884");
        
        // Act
        Livro livro = livroService.buscarPorId(1);
        
        // Assert
        assertNotNull(livro);
        assertEquals("Clean Code", livro.getTitulo());
        assertEquals("Robert Martin", livro.getAutor());
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao buscar livro inexistente")
    public void deveLancarExcecaoAoBuscarLivroInexistente() {
        // Act & Assert
        RecursoNaoEncontradoException exception = assertThrows(
            RecursoNaoEncontradoException.class,
            () -> livroService.buscarPorId(999)
        );
        
        assertEquals("Livro não encontrado", exception.getMessage());
    }
    
    @Test
    @DisplayName("Deve listar todos os livros cadastrados")
    public void deveListarTodosOsLivrosCadastrados() throws ValidacaoException {
        // Arrange
        livroService.cadastrarLivro("Clean Code", "Robert Martin", "978-0132350884");
        livroService.cadastrarLivro("Design Patterns", "Gang of Four", "978-0201633612");
        livroService.cadastrarLivro("Refactoring", "Martin Fowler", "978-0134757599");
        
        // Act
        int totalLivros = livroService.listarTodos().size();
        
        // Assert
        assertEquals(3, totalLivros);
    }
    
    @Test
    @DisplayName("Deve retornar lista vazia quando não há livros")
    public void deveRetornarListaVaziaQuandoNaoHaLivros() {
        // Act
        int totalLivros = livroService.listarTodos().size();
        
        // Assert
        assertEquals(0, totalLivros);
        assertTrue(livroService.listarTodos().isEmpty());
    }
    
    @Test
    @DisplayName("Deve remover livro disponível")
    public void deveRemoverLivroDisponivel() throws ValidacaoException, RecursoNaoEncontradoException {
        // Arrange
        livroService.cadastrarLivro("Clean Code", "Robert Martin", "978-0132350884");
        
        // Act
        livroService.removerLivro(1);
        
        // Assert
        assertEquals(0, livroService.listarTodos().size());
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao remover livro emprestado")
    public void deveLancarExcecaoAoRemoverLivroEmprestado() throws ValidacaoException, RecursoNaoEncontradoException {
        // Arrange
        livroService.cadastrarLivro("Clean Code", "Robert Martin", "978-0132350884");
        Livro livro = livroService.buscarPorId(1);
        livro.setDisponivel(false); // Simula que está emprestado
        
        // Act & Assert
        ValidacaoException exception = assertThrows(
            ValidacaoException.class,
            () -> livroService.removerLivro(1)
        );
        
        assertEquals("Não é possível remover livro emprestado", exception.getMessage());
    }
}