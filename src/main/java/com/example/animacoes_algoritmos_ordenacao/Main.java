package com.example.animacoes_algoritmos_ordenacao;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Main extends Application {
    AnchorPane pane;
    Button botao_inicio;
    BucketSort bucketSort;
    private int tl;

    final int NUMERO_DE_BUCKETS = 5;


    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Pesquisa e Ordenacao");
        pane = new AnchorPane();
        pane.getStylesheets().add(getClass().getResource("/com/example/animacoes_algoritmos_ordenacao/style.css").toExternalForm());

        TextField input_tamanho = new TextField();
        input_tamanho.setPromptText("Tamanho do vetor");
        input_tamanho.setLayoutX(10);
        input_tamanho.setLayoutY(10);

        bucketSort = new BucketSort(pane, NUMERO_DE_BUCKETS, input_tamanho);

        Button botao_gera_vetor = new Button("Gerar Vetor");
        botao_gera_vetor.setLayoutX(200); botao_gera_vetor.setLayoutY(10);
        botao_gera_vetor.setOnAction(e -> {
            bucketSort.removeBotoes();
        });

        Button botao_bucket_sort = new Button("Bucket Sort");
        botao_bucket_sort.setLayoutX(300); botao_bucket_sort.setLayoutY(10);
        botao_bucket_sort.setOnAction(e -> {
            bucketSort.bucketSort();
        });

        pane.getChildren().add(botao_gera_vetor);
        pane.getChildren().add(input_tamanho);
        pane.getChildren().add(botao_bucket_sort);

        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}