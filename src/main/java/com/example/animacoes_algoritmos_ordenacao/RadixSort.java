package com.example.animacoes_algoritmos_ordenacao;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.util.Random;

public class RadixSort {
    AnchorPane pane;
    private static final int COORD_Y_LABELS_BUCKETS = 600;
    private static final int COORD_Y_VETOR_PRINCIPAL = 100;
    private static final int COORD_X_INICIAL_VETOR = 10;
    private static final int ESPACAMENTO_VETOR = 35;
    private static final int TAMANHO_BOTAO = 25;
    private static final int LABEL_FONT_SIZE = 12;
    private static final int BUTTON_FONT_SIZE = 12;

    private static int maior;

    private static int tl;
    private static Button[] vet;
    private static Label radix_lb[];

    public static void geraVetor(int tamanho, AnchorPane pane) {
        int i, pos_x = COORD_X_INICIAL_VETOR;
        Random rand = new Random();

        //define maior como zero
        maior  = tl = 0;
        int  num=0;
        vet = new Button[tamanho];

        for (i = 0; i < tamanho/2; i ++) {

            num = rand.nextInt(1000);

            vet[tl] = new Button(Integer.toString(num));

            if (num>maior) {
                maior=num;
            }



            vet[tl].setLayoutX(pos_x);
            vet[tl].setLayoutY(COORD_Y_VETOR_PRINCIPAL);
            vet[tl].setMinHeight(TAMANHO_BOTAO); vet[tl].setMinWidth(TAMANHO_BOTAO);
            vet[tl].setFont(new Font(BUTTON_FONT_SIZE));
            pane.getChildren().add(vet[tl]);

            tl ++;
            pos_x += ESPACAMENTO_VETOR;
        }

        for (i = tamanho/2; i < tamanho; i ++) {

            num = rand.nextInt(100);

            vet[tl] = new Button(Integer.toString(num));

            if (num>maior) {
                maior=num;
            }



            vet[tl].setLayoutX(pos_x);
            vet[tl].setLayoutY(COORD_Y_VETOR_PRINCIPAL);
            vet[tl].setMinHeight(TAMANHO_BOTAO); vet[tl].setMinWidth(TAMANHO_BOTAO);
            vet[tl].setFont(new Font(BUTTON_FONT_SIZE));
            pane.getChildren().add(vet[tl]);

            tl ++;
            pos_x += ESPACAMENTO_VETOR;
        }
    }


    private static int calculaCoordenadaX(int ini) {
        return 10 + (80 * ini);
    }

    public static void gerarLabels(int tlLabels, AnchorPane pane) {

        radix_lb= new Label[tlLabels];

        for (int i=0; i<tlLabels; i++) {
            radix_lb[i] = new Label(""+i);
            radix_lb[i].setFont(new Font(LABEL_FONT_SIZE));
            radix_lb[i].setLayoutX(RadixSort.calculaCoordenadaX(i));
            radix_lb[i].setLayoutY(COORD_Y_LABELS_BUCKETS);
            pane.getChildren().add(radix_lb[i]);
        }

    }

    public static void Radix(AnchorPane pane) {
        pane.getChildren().clear();
        pane.getStylesheets().add(RadixSort.class.getResource("/com/example/animacoes_algoritmos_ordenacao/style.css").toExternalForm());

        TextField input_tamanho = new TextField();
        input_tamanho.setPromptText("Tamanho do vetor");
        input_tamanho.setLayoutX(10);
        input_tamanho.setLayoutY(10);

        Button botao_gera_vetor = new Button("Gerar Vetor");
        botao_gera_vetor.setLayoutX(200);
        botao_gera_vetor.setLayoutY(10);

        botao_gera_vetor.setOnAction(e -> {
            String texto_tamanho_vetor = input_tamanho.getText();

            try {
                int tamanho = Integer.parseInt(texto_tamanho_vetor);

                // Removendo os botões antigos do vetor antes de gerar um novo
                for (int i = 0; i < tl; i++) {
                    pane.getChildren().remove(vet[i]);
                }
                tl = 0;

                // Gera um novo vetor
                geraVetor(tamanho, pane);
            } catch (NumberFormatException ex) {
                System.out.println("Erro: Insira um número válido!");
            }
        });

        // Adicionando os elementos ao pane
        pane.getChildren().addAll(input_tamanho, botao_gera_vetor);

        // Botão para executar Radix Sort e criar os labels dos buckets
        Button botao_radix = new Button("Radix Sort");
        botao_radix.setLayoutX(300);
        botao_radix.setLayoutY(10);

        botao_radix.setOnAction(e -> {
            // Criar os labels dos buckets (0 a 9) sem apagar o vetor
            RadixSort.gerarLabels(10, pane);
        });

        pane.getChildren().add(botao_radix);
    }



}
