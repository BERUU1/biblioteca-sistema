package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe Emprestimo")
public class EmprestimoTest {
    
    @Test
    @DisplayName("Deve calcular data de devolução corretamente (14 dias)")
    public void deveCalcularDataDevolucaoCorretamente() {
        // Arrange
        LocalDate dataEmprestimo = LocalDate.of(2026, 1, 1);
        
        // Act
        Emprestimo emprestimo = new Emprestimo(1, 1, 1, dataEmprestimo);
        
        // Assert
        LocalDate dataEsperada = LocalDate.of(2026, 1, 15);
        assertEquals(dataEsperada, emprestimo.getDataDevolucao());
    }
    
    @Test
    @DisplayName("Deve criar empréstimo com status não devolvido")
    public void deveCriarEmprestimoComStatusNaoDevolvido() {
        // Arrange & Act
        Emprestimo emprestimo = new Emprestimo(1, 1, 1, LocalDate.now());
        
        // Assert
        assertFalse(emprestimo.isDevolvido());
    }
    
    @Test
    @DisplayName("Deve alterar status de devolução")
    public void deveAlterarStatusDeDevolucao() {
        // Arrange
        Emprestimo emprestimo = new Emprestimo(1, 1, 1, LocalDate.now());
        
        // Act
        emprestimo.setDevolvido(true);
        
        // Assert
        assertTrue(emprestimo.isDevolvido());
    }
}