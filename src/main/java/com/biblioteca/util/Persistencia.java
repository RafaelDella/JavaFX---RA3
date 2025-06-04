package com.biblioteca.util;

import java.io.*;
import java.util.ArrayList;

public class Persistencia {

    public static <T> void salvar(String nomeArquivo, ArrayList<T> lista) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            out.writeObject(lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> ArrayList<T> carregar(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);

        if (!arquivo.exists()) {
            ArrayList<T> novaLista = new ArrayList<>();
            salvar(nomeArquivo, novaLista);
            return novaLista;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (ArrayList<T>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>(); // evita crash se der erro
        }
    }
}
