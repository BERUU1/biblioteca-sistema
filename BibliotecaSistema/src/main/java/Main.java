import service.LivroService;
import service.UsuarioService;
import service.EmprestimoService;
import repository.LivroRepository;
import repository.UsuarioRepository;
import repository.EmprestimoRepository;
import exception.ValidacaoException;

public class Main {
    public static void main(String[] args) {
        // Inicialização dos repositórios
        LivroRepository livroRepo = new LivroRepository();
        UsuarioRepository usuarioRepo = new UsuarioRepository();
        EmprestimoRepository emprestimoRepo = new EmprestimoRepository();
        
        // Inicialização dos serviços
        LivroService livroService = new LivroService(livroRepo);
        UsuarioService usuarioService = new UsuarioService(usuarioRepo);
        EmprestimoService emprestimoService = new EmprestimoService(
            emprestimoRepo, livroService, usuarioService
        );
        
        System.out.println("=== TESTES DO SISTEMA DE BIBLIOTECA ===\n");
        
        // TESTE 1: Cadastrar Livros
        System.out.println("--- TESTE 1: Cadastro de Livros ---");
        try {
            livroService.cadastrarLivro("Clean Code", "Robert Martin", "978-0132350884");
            livroService.cadastrarLivro("Design Patterns", "Gang of Four", "978-0201633612");
            livroService.cadastrarLivro("Refactoring", "Martin Fowler", "978-0134757599");
            System.out.println("✓ Livros cadastrados com sucesso\n");
        } catch (ValidacaoException e) {
            System.out.println("✗ Erro: " + e.getMessage() + "\n");
        }
        
        // TESTE 2: Listar Livros
        System.out.println("--- TESTE 2: Listagem de Livros ---");
        livroService.listarTodos().forEach(System.out::println);
        System.out.println();
        
        // TESTE 3: Cadastrar Usuários
        System.out.println("--- TESTE 3: Cadastro de Usuários ---");
        try {
            usuarioService.cadastrarUsuario("João Silva", "joao@email.com", "123.456.789-00");
            usuarioService.cadastrarUsuario("Maria Santos", "maria@email.com", "987.654.321-00");
            System.out.println("✓ Usuários cadastrados com sucesso\n");
        } catch (ValidacaoException e) {
            System.out.println("✗ Erro: " + e.getMessage() + "\n");
        }
        
        // TESTE 4: Realizar Empréstimo
        System.out.println("--- TESTE 4: Realizar Empréstimo ---");
        try {
            emprestimoService.realizarEmprestimo(1, 1);
            System.out.println("✓ Empréstimo realizado com sucesso\n");
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage() + "\n");
        }
        
        // TESTE 5: Tentar emprestar livro indisponível
        System.out.println("--- TESTE 5: Validação de Livro Indisponível ---");
        try {
            emprestimoService.realizarEmprestimo(1, 2);
            System.out.println("✗ Não deveria permitir este empréstimo\n");
        } catch (ValidacaoException e) {
            System.out.println("✓ Validação funcionou: " + e.getMessage() + "\n");
        } catch (Exception e) {
            System.out.println("✗ Erro inesperado: " + e.getMessage() + "\n");
        }
        
        // TESTE 6: Listar Empréstimos Ativos
        System.out.println("--- TESTE 6: Empréstimos Ativos ---");
        emprestimoService.listarAtivos().forEach(System.out::println);
        System.out.println();
        
        // TESTE 7: Realizar Devolução
        System.out.println("--- TESTE 7: Realizar Devolução ---");
        try {
            emprestimoService.realizarDevolucao(1);
            System.out.println("✓ Devolução realizada com sucesso\n");
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage() + "\n");
        }
        
        // TESTE 8: Verificar disponibilidade após devolução
        System.out.println("--- TESTE 8: Verificar Disponibilidade ---");
        try {
            System.out.println(livroService.buscarPorId(1));
            System.out.println("✓ Livro disponível novamente\n");
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage() + "\n");
        }
        
        // TESTE 9: Validação de ISBN duplicado
        System.out.println("--- TESTE 9: Validação de ISBN Duplicado ---");
        try {
            livroService.cadastrarLivro("Outro Livro", "Outro Autor", "978-0132350884");
            System.out.println("✗ Não deveria permitir ISBN duplicado\n");
        } catch (ValidacaoException e) {
            System.out.println("✓ Validação funcionou: " + e.getMessage() + "\n");
        }
        
        System.out.println("=== FIM DOS TESTES ===");
    }
}