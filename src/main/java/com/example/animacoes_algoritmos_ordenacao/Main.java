package com.example.animacoes_algoritmos_ordenacao;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.util.Random;

import java.io.IOException;

public class Main extends Application {
    AnchorPane pane;
    Button botao_inicio;
    private Button[] vet;
    private int tl;

    public void geraVetor(int tamanho) {
        int i, pos_x = 50;
        Random rand = new Random();

        tl = 0;

        vet = new Button[tamanho];

        for (i = 0; i < tamanho; i ++) {
            vet[tl] = new Button(Integer.toString(rand.nextInt(100)));
            vet[tl].setLayoutX(pos_x);
            vet[tl].setLayoutY(100);
            vet[tl].setMinHeight(40); vet[tl].setMinWidth(40);
            vet[tl].setFont(new Font(14));
            pane.getChildren().add(vet[tl]);

            tl ++;
            pos_x += 80;
        }
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Pesquisa e Ordenacao");
        pane = new AnchorPane();

        geraVetor(10);

        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void move_botoes()
    {
        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() {
                //permutação na tela
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[0].setLayoutY(vet[0].getLayoutY() + 5));
                    Platform.runLater(() -> vet[1].setLayoutY(vet[1].getLayoutY() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 16; i++) {
                    Platform.runLater(() -> vet[0].setLayoutX(vet[0].getLayoutX() + 5));
                    Platform.runLater(() -> vet[1].setLayoutX(vet[1].getLayoutX() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[0].setLayoutY(vet[0].getLayoutY() - 5));
                    Platform.runLater(() -> vet[1].setLayoutY(vet[1].getLayoutY() + 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //permutação na memória
                Button aux = vet[0];
                vet[0] = vet[1];
                vet[1] = aux;
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}