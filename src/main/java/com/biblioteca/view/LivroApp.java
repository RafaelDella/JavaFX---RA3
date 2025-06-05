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
    private Button adicionarBtn;

    private TextField tituloField = new TextField();
    private TextField autorField = new TextField();
    private TextField generoField = new TextField();
    private TextField quantidadeField = new TextField();
    private CheckBox livroFisicoCheckbox = new CheckBox("É livro físico?");
    private int indexEdicao = -1;

    public LivroApp(ArrayList<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Cadastro de Livros");

        tituloField.setPromptText("Título");
        autorField.setPromptText("Autor");
        generoField.setPromptText("Gênero");

        quantidadeField.setPromptText("Quantidade disponível");
        quantidadeField.setVisible(false);

        livroFisicoCheckbox.setOnAction(e -> {
            quantidadeField.setVisible(livroFisicoCheckbox.isSelected());
        });

        adicionarBtn = new Button("Adicionar Livro");
        adicionarBtn.setOnAction(this::handleAdicionar);

        Button editarBtn = new Button("Editar Selecionado");
        editarBtn.setOnAction(e -> {
            int index = listView.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                Livro livro = livros.get(index);
                tituloField.setText(livro.getTitulo());
                autorField.setText(livro.getAutor());
                generoField.setText(livro.getGenero());

                if (livro instanceof LivroFisico lf) {
                    livroFisicoCheckbox.setSelected(true);
                    quantidadeField.setVisible(true);
                    quantidadeField.setText(String.valueOf(lf.getQuantidadeDisponivel()));
                } else {
                    livroFisicoCheckbox.setSelected(false);
                    quantidadeField.setVisible(false);
                    quantidadeField.clear();
                }

                adicionarBtn.setText("Salvar Alterações");
                indexEdicao = index;
            } else {
                mostrarAlerta("Selecione um livro para editar.");
            }
        });

        Button removerBtn = new Button("Remover Selecionado");
        removerBtn.setOnAction(e -> {
            int index = listView.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                livros.remove(index);
                atualizarLista();
            } else {
                mostrarAlerta("Selecione um livro para remover.");
            }
        });

        VBox form = new VBox(10,
                tituloField,
                autorField,
                generoField,
                livroFisicoCheckbox,
                quantidadeField,
                adicionarBtn,
                editarBtn,
                removerBtn
        );
        form.setPadding(new Insets(20));

        HBox layout = new HBox(20, form, listView);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 700, 400));
        stage.show();

        atualizarLista();

        stage.setOnCloseRequest(event -> {
            Persistencia.salvar("livros.dat", livros);
        });
    }

    private void handleAdicionar(javafx.event.ActionEvent e) {
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
                LivroFisico livro = new LivroFisico(titulo, autor, genero, true, qtd);
                if (indexEdicao >= 0) {
                    livros.set(indexEdicao, livro);
                } else {
                    livros.add(livro);
                }
            } catch (NumberFormatException ex) {
                mostrarAlerta("Quantidade deve ser um número inteiro.");
                return;
            }
        } else {
            LivroDigital livro = new LivroDigital(titulo, autor, genero, false);
            if (indexEdicao >= 0) {
                livros.set(indexEdicao, livro);
            } else {
                livros.add(livro);
            }
        }

        atualizarLista();
        limparFormulario();
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

    private void limparFormulario() {
        tituloField.clear();
        autorField.clear();
        generoField.clear();
        quantidadeField.clear();
        livroFisicoCheckbox.setSelected(false);
        quantidadeField.setVisible(false);
        adicionarBtn.setText("Adicionar Livro");
        adicionarBtn.setOnAction(this::handleAdicionar);
        indexEdicao = -1;
    }
}
