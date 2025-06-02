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

    public AluguelApp(ArrayList<Aluno> alunos, ArrayList<Livro> livros) {
        this.alunos = alunos;
        this.livros = livros;
        this.alugueis = Persistencia.carregar("alugueis.dat");
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Aluguel de Livros");

        ComboBox<Aluno> alunoCombo = new ComboBox<>();
        alunoCombo.getItems().addAll(alunos);

        ComboBox<Livro> livroCombo = new ComboBox<>();
        for (Livro l : livros) {
            if (l instanceof LivroFisico livroFisico) {
                if (livroFisico.getDisponibilidade()) {
                    livroCombo.getItems().add(livroFisico);
                }
            } else {
                livroCombo.getItems().add(l);
            }
        }

        Button alugarBtn = new Button("Realizar Aluguel");
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

            alugueisListView.getItems().add(aluno.getNome() + " → " + livro.getTitulo());

            livroCombo.getItems().clear();
            for (Livro l : livros) {
                if (l instanceof LivroFisico lf) {
                    if (lf.getDisponibilidade()) {
                        livroCombo.getItems().add(lf);
                    }
                } else {
                    livroCombo.getItems().add(l);
                }
            }
        });

        VBox layout = new VBox(10,
                new Label("Selecionar Aluno:"), alunoCombo,
                new Label("Selecionar Livro:"), livroCombo,
                alugarBtn,
                new Label("Aluguéis Realizados:"),
                alugueisListView
        );

        layout.setStyle("-fx-padding: 20;");
        stage.setScene(new Scene(layout, 400, 400));
        stage.show();

        atualizarListaAlugueis();
    }

    private void atualizarListaAlugueis() {
        alugueisListView.getItems().clear();
        for (Aluguel a : alugueis) {
            alugueisListView.getItems().add(a.getAluno().getNome() + " → " + a.getLivro().getTitulo());
        }
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
