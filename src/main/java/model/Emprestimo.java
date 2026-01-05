package model;

import java.time.LocalDate;

public class Emprestimo {
    private int id;
    private int idLivro;
    private int idUsuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private boolean devolvido;
    
    public Emprestimo(int id, int idLivro, int idUsuario, LocalDate dataEmprestimo) {
        this.id = id;
        this.idLivro = idLivro;
        this.idUsuario = idUsuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataEmprestimo.plusDays(14); // 14 dias de prazo
        this.devolvido = false;
    }
    
    // Getters e Setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }
    
    public int getIdLivro() { 
        return idLivro; 
    }
    
    public void setIdLivro(int idLivro) { 
        this.idLivro = idLivro; 
    }
    
    public int getIdUsuario() { 
        return idUsuario; 
    }
    
    public void setIdUsuario(int idUsuario) { 
        this.idUsuario = idUsuario; 
    }
    
    public LocalDate getDataEmprestimo() { 
        return dataEmprestimo; 
    }
    
    public void setDataEmprestimo(LocalDate dataEmprestimo) { 
        this.dataEmprestimo = dataEmprestimo; 
    }
    
    public LocalDate getDataDevolucao() { 
        return dataDevolucao; 
    }
    
    public void setDataDevolucao(LocalDate dataDevolucao) { 
        this.dataDevolucao = dataDevolucao; 
    }
    
    public boolean isDevolvido() { 
        return devolvido; 
    }
    
    public void setDevolvido(boolean devolvido) { 
        this.devolvido = devolvido; 
    }
    
    @Override
    public String toString() {
        return String.format("ID: %d | Livro ID: %d | Usuário ID: %d | Empréstimo: %s | Devolução: %s | Devolvido: %s",
            id, idLivro, idUsuario, dataEmprestimo, dataDevolucao, devolvido ? "Sim" : "Não");
    }
}
