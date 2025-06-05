package com.biblioteca.model;

import java.io.Serializable;

public abstract class Livro implements Serializable {
    protected String titulo;
    protected String autor;
    protected String genero;
    protected Boolean isFisico;

    public Livro(String titulo, String autor, String genero,  Boolean isFisico) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.isFisico = isFisico;
    }


    public abstract void alugarLivro();

    @Override
    public String toString() {
        return titulo + " - " + autor + " - " + genero;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }

    public Boolean getIsFisico() {return isFisico;}
}
