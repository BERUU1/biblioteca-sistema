package service;

import model.Emprestimo;
import model.Livro;
import repository.EmprestimoRepository;
import exception.ValidacaoException;
import exception.RecursoNaoEncontradoException;

import java.time.LocalDate;
import java.util.List;

public class EmprestimoService {
    private EmprestimoRepository emprestimoRepository;
    private LivroService livroService;
    private UsuarioService usuarioService;
    
    public EmprestimoService(EmprestimoRepository emprestimoRepository, 
                            LivroService livroService, 
                            UsuarioService usuarioService) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroService = livroService;
        this.usuarioService = usuarioService;
    }
    
    public void realizarEmprestimo(int idLivro, int idUsuario) 
            throws ValidacaoException, RecursoNaoEncontradoException {
        
        // Verifica se livro existe
        Livro livro = livroService.buscarPorId(idLivro);
        
        // Verifica se usuário existe
        usuarioService.buscarPorId(idUsuario);
        
        // Verifica disponibilidade
        if (!livro.isDisponivel()) {
            throw new ValidacaoException("Livro não está disponível para empréstimo");
        }
        
        // Realiza empréstimo
        Emprestimo emprestimo = new Emprestimo(0, idLivro, idUsuario, LocalDate.now());
        emprestimoRepository.adicionar(emprestimo);
        
        // Atualiza disponibilidade do livro
        livro.setDisponivel(false);
    }
    
    public void realizarDevolucao(int idEmprestimo) 
            throws RecursoNaoEncontradoException, ValidacaoException {
        
        Emprestimo emprestimo = emprestimoRepository.buscarPorId(idEmprestimo)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Empréstimo não encontrado"));
        
        if (emprestimo.isDevolvido()) {
            throw new ValidacaoException("Livro já foi devolvido");
        }
        
        // Marca como devolvido
        emprestimo.setDevolvido(true);
        
        // Libera o livro
        Livro livro = livroService.buscarPorId(emprestimo.getIdLivro());
        livro.setDisponivel(true);
    }
    
    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.listarTodos();
    }
    
    public List<Emprestimo> listarAtivos() {
        return emprestimoRepository.buscarAtivos();
    }
}
