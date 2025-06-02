package com.biblioteca.model;

import java.io.Serializable;

public abstract class Livro implements Serializable {
    protected String titulo;
    protected String autor;
    protected String genero;

    public Livro(String titulo, String autor, String genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
    }

    public void alugarLivro(){

    }

    @Override
    public String toString() {
        return titulo + " - " + autor;
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

    public abstract void exibirDetalhes();

}
