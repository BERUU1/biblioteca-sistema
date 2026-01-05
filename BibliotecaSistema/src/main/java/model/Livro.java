package model;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private boolean disponivel;
    
    public Livro(int id, String titulo, String autor, String isbn) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.disponivel = true;
    }
    
    // Getters e Setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }
    
    public String getTitulo() { 
        return titulo; 
    }
    
    public void setTitulo(String titulo) { 
        this.titulo = titulo; 
    }
    
    public String getAutor() { 
        return autor; 
    }
    
    public void setAutor(String autor) { 
        this.autor = autor; 
    }
    
    public String getIsbn() { 
        return isbn; 
    }
    
    public void setIsbn(String isbn) { 
        this.isbn = isbn; 
    }
    
    public boolean isDisponivel() { 
        return disponivel; 
    }
    
    public void setDisponivel(boolean disponivel) { 
        this.disponivel = disponivel; 
    }
    
    @Override
    public String toString() {
        return String.format("ID: %d | Título: %s | Autor: %s | ISBN: %s | Disponível: %s",
            id, titulo, autor, isbn, disponivel ? "Sim" : "Não");
    }
}