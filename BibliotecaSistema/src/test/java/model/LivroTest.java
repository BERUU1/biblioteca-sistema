package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe Livro")
public class LivroTest {
    
    @Test
    @DisplayName("Deve criar livro com disponibilidade inicial true")
    public void deveCriarLivroComDisponibilidadeInicialTrue() {
        // Arrange & Act
        Livro livro = new Livro(1, "Clean Code", "Robert Martin", "978-0132350884");
        
        // Assert
        assertTrue(livro.isDisponivel());
    }
    
    @Test
    @DisplayName("Deve alterar disponibilidade do livro")
    public void deveAlterarDisponibilidadeDoLivro() {
        // Arrange
        Livro livro = new Livro(1, "Clean Code", "Robert Martin", "978-0132350884");
        
        // Act
        livro.setDisponivel(false);
        
        // Assert
        assertFalse(livro.isDisponivel());
    }
    
    @Test
    @DisplayName("Deve retornar string formatada corretamente")
    public void deveRetornarStringFormatadaCorretamente() {
        // Arrange
        Livro livro = new Livro(1, "Clean Code", "Robert Martin", "978-0132350884");
        
        // Act
        String resultado = livro.toString();
        
        // Assert
        assertTrue(resultado.contains("Clean Code"));
        assertTrue(resultado.contains("Robert Martin"));
        assertTrue(resultado.contains("978-0132350884"));
    }
}
