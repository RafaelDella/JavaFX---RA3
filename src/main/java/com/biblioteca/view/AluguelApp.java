package com.biblioteca.view;

import com.biblioteca.model.*;
import com.biblioteca.util.Persistencia;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AluguelApp extends Application {

    private ArrayList<Aluno> alunos;
    private ArrayList<Livro> livros;
    private ArrayList<Aluguel> alugueis;
    private ListView<String> alugueisListView = new ListView<>();

    public AluguelApp(ArrayList<Aluno> alunos, ArrayList<Livro> livros, ArrayList<Aluguel> alugueis) {
        this.alunos = alunos;
        this.livros = livros;
        this.alugueis = alugueis != null ? alugueis : new ArrayList<>();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Aluguel de Livros");

        ComboBox<Aluno> alunoCombo = new ComboBox<>();
        alunoCombo.getItems().addAll(alunos);

        ComboBox<Livro> livroCombo = new ComboBox<>();
        atualizarComboLivros(livroCombo);

        Button alugarBtn = new Button("Realizar Aluguel");
        Button editarBtn = new Button("Editar Selecionado"); // ✅
        Button removerBtn = new Button("Remover Selecionado"); // ✅

        alugarBtn.setOnAction(e -> {
            Aluno aluno = alunoCombo.getValue();
            Livro livro = livroCombo.getValue();

            if (aluno == null || livro == null) {
                mostrarAlerta("Selecione um aluno e um livro.");
                return;
            }

            aluno.alugarLivro(livro);
            livro.alugarLivro();

            Aluguel novoAluguel = new Aluguel(aluno, livro);
            alugueis.add(novoAluguel);
            Persistencia.salvar("alugueis.dat", alugueis);

            atualizarListaAlugueis();
            atualizarComboLivros(livroCombo);
        });

        // ✅ Remover selecionado
        removerBtn.setOnAction(e -> {
            int index = alugueisListView.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                alugueis.remove(index);
                Persistencia.salvar("alugueis.dat", alugueis);
                atualizarListaAlugueis();
                atualizarComboLivros(livroCombo);
            } else {
                mostrarAlerta("Selecione um aluguel para remover.");
            }
        });

        // ✅ Editar selecionado
        editarBtn.setOnAction(e -> {
            int index = alugueisListView.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                Aluno aluno = alunoCombo.getValue();
                Livro livro = livroCombo.getValue();

                if (aluno == null || livro == null) {
                    mostrarAlerta("Selecione um novo aluno e livro.");
                    return;
                }

                Aluguel editado = new Aluguel(aluno, livro);
                alugueis.set(index, editado);
                Persistencia.salvar("alugueis.dat", alugueis);
                atualizarListaAlugueis();
                atualizarComboLivros(livroCombo);
            } else {
                mostrarAlerta("Selecione um aluguel para editar.");
            }
        });

        VBox layout = new VBox(10,
                new Label("Selecionar Aluno:"), alunoCombo,
                new Label("Selecionar Livro:"), livroCombo,
                alugarBtn,
                editarBtn,
                removerBtn,
                new Label("Aluguéis Realizados:"),
                alugueisListView
        );

        layout.setStyle("-fx-padding: 20;");
        stage.setScene(new Scene(layout, 450, 500));
        stage.show();

        atualizarListaAlugueis();

        // ✅ Salvar ao fechar
        stage.setOnCloseRequest(event -> {
            Persistencia.salvar("alugueis.dat", alugueis);
        });
    }

    private void atualizarListaAlugueis() {
        alugueisListView.getItems().clear();
        for (Aluguel a : alugueis) {
            alugueisListView.getItems().add(a.getAluno().getNome() + " → " + a.getLivro().getTitulo());
        }
    }

    private void atualizarComboLivros(ComboBox<Livro> combo) {
        combo.getItems().clear();
        for (Livro l : livros) {
            if (l instanceof LivroFisico lf) {
                if (lf.getDisponibilidade()) {
                    combo.getItems().add(lf);
                }
            } else {
                combo.getItems().add(l);
            }
        }
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
