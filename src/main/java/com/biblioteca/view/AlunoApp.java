    package com.biblioteca.view;

    import com.biblioteca.model.Aluno;
    import com.biblioteca.util.Persistencia;
    import javafx.application.Application;
    import javafx.event.ActionEvent;
    import javafx.event.EventHandler;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.layout.*;
    import javafx.stage.Stage;

    import java.util.ArrayList;

    public class AlunoApp extends Application {
        private ArrayList<Aluno> alunos = new ArrayList<>();
        private ListView<String> listView = new ListView<>();
        private int indexEdicao = -1;

        public AlunoApp(ArrayList<Aluno> alunos) {
            this.alunos = alunos;
        }

        @Override
        public void start(Stage stage) {
            stage.setTitle("Cadastro de Alunos");

            TextField nomeField = new TextField();
            nomeField.setPromptText("Nome");

            TextField cpfField = new TextField();
            cpfField.setPromptText("CPF");

            TextField cursoField = new TextField();
            cursoField.setPromptText("Curso");

            Button adicionarBtn = new Button("Adicionar");
            Button editarBtn = new Button("Editar Selecionado");
            Button removerBtn = new Button("Remover Selecionado");

            // Handler original de adicionar
            EventHandler<ActionEvent> adicionarHandler = e -> {
                String nome = nomeField.getText();
                String cpf = cpfField.getText();
                String curso = cursoField.getText();

                if (!nome.isEmpty() && !cpf.isEmpty() && !curso.isEmpty()) {
                    if (cpf.length() != 11 || !cpf.matches("\\d{11}")) {
                        mostrarAlerta("O CPF deve conter exatamente 11 dígitos numéricos.");
                        return;
                    }

                    if (cpfJaExiste(cpf)) {
                        mostrarAlerta("Já existe um aluno cadastrado com esse CPF.");
                        return;
                    }

                    Aluno aluno = new Aluno(nome, cpf, curso);
                    alunos.add(aluno);
                    nomeField.clear();
                    cpfField.clear();
                    cursoField.clear();
                    atualizarLista();
                } else {
                    mostrarAlerta("Preencha todos os campos.");
                }
            };
            adicionarBtn.setOnAction(adicionarHandler);

            removerBtn.setOnAction(e -> {
                int index = listView.getSelectionModel().getSelectedIndex();
                if (index >= 0) {
                    alunos.remove(index);
                    atualizarLista();
                }
            });

            editarBtn.setOnAction(e -> {
                indexEdicao = listView.getSelectionModel().getSelectedIndex();
                if (indexEdicao >= 0) {
                    Aluno aluno = alunos.get(indexEdicao);
                    nomeField.setText(aluno.getNome());
                    cpfField.setText(aluno.getCpf());
                    cursoField.setText(aluno.getCurso());

                    adicionarBtn.setText("Salvar Alterações");

                    adicionarBtn.setOnAction(ev -> {
                        String nome = nomeField.getText();
                        String cpf = cpfField.getText();
                        String curso = cursoField.getText();

                        if (!nome.isEmpty() && !cpf.isEmpty() && !curso.isEmpty()) {
                            // Validação de CPF
                            if (cpf.length() != 11 || !cpf.matches("\\d{11}")) {
                                mostrarAlerta("O CPF deve conter exatamente 11 dígitos numéricos.");
                                return;
                            }

                            // Verifica se o CPF já existe em outro aluno
                            for (int i = 0; i < alunos.size(); i++) {
                                if (i != indexEdicao && alunos.get(i).getCpf().equals(cpf)) {
                                    mostrarAlerta("Já existe outro aluno com esse CPF.");
                                    return;
                                }
                            }

                            // Atualiza aluno
                            Aluno alunoEditado = new Aluno(nome, cpf, curso);
                            alunos.set(indexEdicao, alunoEditado);
                            atualizarLista();

                            nomeField.clear();
                            cpfField.clear();
                            cursoField.clear();
                            adicionarBtn.setText("Adicionar");
                            adicionarBtn.setOnAction(adicionarHandler);
                            indexEdicao = -1;
                        } else {
                            mostrarAlerta("Preencha todos os campos.");
                        }
                    });
                } else {
                    mostrarAlerta("Selecione um aluno para editar.");
                }
            });


            VBox form = new VBox(10, nomeField, cpfField, cursoField, adicionarBtn, editarBtn, removerBtn);
            HBox layout = new HBox(20, form, listView);
            layout.setStyle("-fx-padding: 20");

            stage.setScene(new Scene(layout, 600, 300));
            stage.show();
            atualizarLista();
            stage.setOnCloseRequest(event -> {
                Persistencia.salvar("alunos.dat", alunos);
            });

        }

        private void atualizarLista() {
            listView.getItems().clear();
            for (Aluno a : alunos) {
                listView.getItems().add(a.getNome() + " - " + a.getCurso());
            }
        }

        private void mostrarAlerta(String msg) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(msg);
            alert.showAndWait();
        }

        private boolean cpfJaExiste(String cpf) {
            for (Aluno aluno : alunos) {
                if (aluno.getCpf().equals(cpf)) {
                    return true;
                }
            }
            return false;
        }
    }
