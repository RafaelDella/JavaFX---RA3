package com.biblioteca.view;

import com.biblioteca.model.Livro;
import com.biblioteca.model.LivroDigital;
import com.biblioteca.model.LivroFisico;
import com.biblioteca.util.Persistencia;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class LivroApp extends Application {

    private ArrayList<Livro> livros = new ArrayList<>();
    private ListView<String> listView = new ListView<>();

    public LivroApp(ArrayList<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Cadastro de Livros");

        // Campos básicos
        TextField tituloField = new TextField();
        tituloField.setPromptText("Título");

        TextField autorField = new TextField();
        autorField.setPromptText("Autor");

        TextField generoField = new TextField();
        generoField.setPromptText("Gênero");

        // Checkbox: É livro físico?
        CheckBox livroFisicoCheckbox = new CheckBox("É livro físico?");

        // Campo de quantidade (somente visível se for físico)
        TextField quantidadeField = new TextField();
        quantidadeField.setPromptText("Quantidade disponível");
        quantidadeField.setVisible(false);

        // Mostrar/ocultar campo de quantidade baseado no checkbox
        livroFisicoCheckbox.setOnAction(e -> {
            quantidadeField.setVisible(livroFisicoCheckbox.isSelected());
        });

        // Botão adicionar
        Button adicionarBtn = new Button("Adicionar Livro");
        adicionarBtn.setOnAction(e -> {
            String titulo = tituloField.getText();
            String autor = autorField.getText();
            String genero = generoField.getText();
            boolean isFisico = livroFisicoCheckbox.isSelected();

            if (titulo.isEmpty() || autor.isEmpty() || genero.isEmpty()) {
                mostrarAlerta("Preencha todos os campos obrigatórios.");
                return;
            }

            if (isFisico) {
                String qtdStr = quantidadeField.getText();
                if (qtdStr.isEmpty()) {
                    mostrarAlerta("Informe a quantidade para livro físico.");
                    return;
                }
                try {
                    int qtd = Integer.parseInt(qtdStr);
                    LivroFisico livro = new LivroFisico(titulo, autor, genero, qtd);
                    livros.add(livro);
                } catch (NumberFormatException ex) {
                    mostrarAlerta("Quantidade deve ser um número inteiro.");
                    return;
                }
            } else {
                LivroDigital livro = new LivroDigital(titulo, autor, genero);
                livros.add(livro);
            }

            atualizarLista();
            tituloField.clear();
            autorField.clear();
            generoField.clear();
            livroFisicoCheckbox.setSelected(false);
            quantidadeField.clear();
            quantidadeField.setVisible(false);
        });

        // Layout do formulário
        VBox form = new VBox(10,
                tituloField,
                autorField,
                generoField,
                livroFisicoCheckbox,
                quantidadeField,
                adicionarBtn
        );
        form.setPadding(new Insets(20));

        // Layout principal
        HBox layout = new HBox(20, form, listView);
        layout.setPadding(new Insets(20));

        // Cena e exibição
        Scene scene = new Scene(layout, 700, 300);
        stage.setScene(scene);
        stage.show();
        atualizarLista();

        stage.setOnCloseRequest(event -> {
            Persistencia.salvar("livros.dat", livros);
        });

    }

    private void atualizarLista() {
        listView.getItems().clear();
        for (Livro l : livros) {
            listView.getItems().add(l.toString());
        }
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}