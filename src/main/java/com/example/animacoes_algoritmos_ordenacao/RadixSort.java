package com.example.animacoes_algoritmos_ordenacao;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.util.Random;

public class RadixSort {
    AnchorPane pane;

    private static final int COORD_Y_COUNTING_ARR = 200;
    private static final int COORD_Y_VETOR_PRINCIPAL = 70;
    private static final int COORD_X_INICIAL_VETOR_P = 50;
    private static final int ESPACAMENTO_VETOR = 35;
    private static final int TAMANHO_BOTAO = 25;
    private static final int LABEL_FONT_SIZE = 15;
    private static final int BUTTON_FONT_SIZE = 12;
    private static final int COORD_Y_INPUT_TAMANHO = 10;
    private static final int COORD_X_INPUT_TAMANHO = 10;
    private static final int COORD_Y_BOTAO_GERA_VETOR = 10;
    private static final int COORD_X_BOTAO_GERA_VETOR = 200;
    private static final int COORD_Y_BOTAO_RADIX = 10;
    private static final int COORD_X_BOTAO_RADIX = 300;
    private static final int COORD_Y_TITULO_COUNTING = 120;
    private static final int COORD_X_TITULO_COUNTING = 70;
    private static final int COORD_Y_TITULO_COPIA = 270;
    private static final int COORD_X_TITULO_COPIA = 70;
    private static final int COORD_Y_LABEL_RADIX = 170;

    private static int maior;
    private static int tl;
    private static Button[] vet;
    private static int tamanho_vetor;
    private static Label[] radix_lb_i;
    private static Label[] radix_lb;
    private static Button[][] radix;
    private static int calculaCoordenadaX(int i) {
        return 20 + (80 * i);
    }
    public static void geraVetor(int tamanho, AnchorPane pane) {
        int i;
        int pos_x = COORD_X_INICIAL_VETOR_P;
        Random rand = new Random();

        maior = tl = 0;
        vet = new Button[tamanho];

        for (i = 0; i < tamanho; i++) {
            int num = (i < tamanho / 2) ? rand.nextInt(1000) : rand.nextInt(100);
            if (num > maior) {
                maior = num;
            }

            Button botao = new Button();
            botao.setPrefSize(40, TAMANHO_BOTAO);
            botao.setMaxSize(40, TAMANHO_BOTAO);
            botao.setFont(new Font(BUTTON_FONT_SIZE));
            botao.setLayoutX(pos_x);
            botao.setLayoutY(COORD_Y_VETOR_PRINCIPAL);

            TextFlow textFlow = new TextFlow();
            textFlow.setTextAlignment(TextAlignment.CENTER);

            String numStr = Integer.toString(num);
            for (char c : numStr.toCharArray()) {
                Text digitText = new Text(String.valueOf(c));
                digitText.setStyle("-fx-fill: black; -fx-font-size: " + BUTTON_FONT_SIZE + "px;");
                textFlow.getChildren().add(digitText);
            }

            textFlow.setPrefWidth(40);
            textFlow.setPrefHeight(TAMANHO_BOTAO);
            textFlow.setMaxWidth(40);
            textFlow.setMaxHeight(TAMANHO_BOTAO);

            botao.setGraphic(textFlow);
            vet[tl++] = botao;
            pane.getChildren().add(botao);

            pos_x += ESPACAMENTO_VETOR;
        }
    }

    public static void gerarLabels(int tlLabels, AnchorPane pane) {
        radix_lb_i = new Label[tlLabels];
        radix_lb = new Label[tlLabels];
        Label titulo_cARR = new Label("Vetor Contagem");
        titulo_cARR.setLayoutY(COORD_Y_TITULO_COUNTING);
        titulo_cARR.setLayoutX(COORD_X_TITULO_COUNTING);
        titulo_cARR.setStyle("-fx-font-weight: bold;");
        pane.getChildren().add(titulo_cARR);

        Label titulo_copia = new Label("Vetor Resultante");
        titulo_copia.setLayoutX(COORD_X_TITULO_COPIA);
        titulo_copia.setLayoutY(COORD_Y_TITULO_COPIA);
        titulo_copia.setStyle("-fx-font-weight: bold;");
        pane.getChildren().add(titulo_copia);

        for (int i = 0; i < tlLabels; i++) {
            radix_lb_i[i] = new Label(" " + i);
            radix_lb[i] = new Label("0");
            radix_lb_i[i].setFont(new Font(LABEL_FONT_SIZE));
            radix_lb[i].setFont(new Font(LABEL_FONT_SIZE));
            radix_lb_i[i].setLayoutX(calculaCoordenadaX(i));
            radix_lb[i].setLayoutX(calculaCoordenadaX(i) + 5);
            radix_lb_i[i].setLayoutY(COORD_Y_COUNTING_ARR);
            radix_lb[i].setLayoutY(COORD_Y_LABEL_RADIX);
            radix_lb_i[i].setStyle("-fx-font-weight: bold;");
            pane.getChildren().add(radix_lb_i[i]);
            pane.getChildren().add(radix_lb[i]);
        }
    }

    public static void Radix(AnchorPane pane) {
        pane.getChildren().clear();
        pane.getStylesheets().add(RadixSort.class.getResource("/com/example/animacoes_algoritmos_ordenacao/style.css").toExternalForm());

        TextField input_tamanho = new TextField();
        input_tamanho.setPromptText("Tamanho do vetor");
        input_tamanho.setLayoutX(COORD_X_INPUT_TAMANHO);
        input_tamanho.setLayoutY(COORD_Y_INPUT_TAMANHO);

        Button botao_gera_vetor = new Button("Gerar Vetor");
        botao_gera_vetor.setLayoutX(COORD_X_BOTAO_GERA_VETOR);
        botao_gera_vetor.setLayoutY(COORD_Y_BOTAO_GERA_VETOR);

        botao_gera_vetor.setOnAction(e -> {
            String texto_tamanho_vetor = input_tamanho.getText();
            try {
                tamanho_vetor = Integer.parseInt(texto_tamanho_vetor);
                for (int i = 0; i < tl; i++) {
                    pane.getChildren().remove(vet[i]);
                }
                tl = 0;
                geraVetor(tamanho_vetor, pane);
            } catch (NumberFormatException ex) {
                System.out.println("Erro: Insira um número válido!");
            }
        });

        pane.getChildren().addAll(input_tamanho, botao_gera_vetor);

        Button botao_radix = new Button("Radix Sort");
        botao_radix.setLayoutX(COORD_X_BOTAO_RADIX);
        botao_radix.setLayoutY(COORD_Y_BOTAO_RADIX);

        pane.getChildren().add(botao_radix);
        gerarLabels(10, pane);
        pane.requestLayout();
    }

//    public static void move_bota_para_resultante() {
//
//    }
}
