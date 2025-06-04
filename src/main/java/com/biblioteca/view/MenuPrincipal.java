package com.biblioteca.view;

import com.biblioteca.model.Aluguel;
import com.biblioteca.model.Aluno;
import com.biblioteca.model.Livro;
import com.biblioteca.util.Persistencia;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MenuPrincipal extends Application {
    private ArrayList<Aluno> alunos;
    private ArrayList<Livro> livros;
    private ArrayList<Aluguel> alugueis;

    @Override
    public void start(Stage primaryStage) {
        alunos = Persistencia.carregar("alunos.dat");
        livros = Persistencia.carregar("livros.dat");
        alugueis = Persistencia.carregar("alugueis.dat");

        Button alunoBtn = new Button("Gerenciar Alunos");
        Button livroBtn = new Button("Gerenciar Livros");
        Button aluguelBtn = new Button("Realizar Aluguel");

        alunoBtn.setOnAction(e -> {
            try {
                new AlunoApp(alunos).start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        livroBtn.setOnAction(e -> {
            try {
                new LivroApp(livros).start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        aluguelBtn.setOnAction(e -> {
            try {
                new AluguelApp(alunos, livros, alugueis).start(new Stage()); // ✅ passando os 3
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox layout = new VBox(15, alunoBtn, livroBtn, aluguelBtn);
        layout.setStyle("-fx-padding: 30; -fx-alignment: center;");

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
