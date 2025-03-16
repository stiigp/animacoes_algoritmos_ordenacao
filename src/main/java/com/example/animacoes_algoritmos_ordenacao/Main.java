package com.example.animacoes_algoritmos_ordenacao;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.util.Random;

public class Main extends Application {
    AnchorPane pane;
    Button botao_inicio;
    private Button[] vet;
    private Button[][] buckets; // os buckets serão uma matriz
    private Label[] bucket_labels;
    private int[] bucket_tls;
    private int tl;

    final int COORD_Y_LABELS_BUCKETS = 600;
    final int COORD_Y_VETOR_PRINCIPAL = 100;
    final int COORD_Y_INICIAL_BUCKET = 570;
    final int COORD_X_INICIAL_BUCKET = 10;
    final int TAMANHO_BOTAO = 25;
    final int LABEL_FONT_SIZE = 12;
    final int BUTTON_FONT_SIZE = 12;

    public void geraVetor(int tamanho) {
        int i, pos_x = 10;
        Random rand = new Random();

        tl = 0;

        vet = new Button[tamanho];

        for (i = 0; i < tamanho; i ++) {
            vet[tl] = new Button(Integer.toString(rand.nextInt(100)));
            vet[tl].setLayoutX(pos_x);
            vet[tl].setLayoutY(COORD_Y_VETOR_PRINCIPAL);
            vet[tl].setMinHeight(TAMANHO_BOTAO); vet[tl].setMinWidth(TAMANHO_BOTAO);
            vet[tl].setFont(new Font(BUTTON_FONT_SIZE));
            pane.getChildren().add(vet[tl]);

            tl ++;
            pos_x += 35;
        }
    }

    private int max() {
        int maior = 0, i;

        for (i = 0; i < tl; i ++) {
            if (Integer.parseInt(vet[i].getText()) > maior)
                maior = Integer.parseInt(vet[i].getText());
        }

        return maior;
    }

    private int calculaBucket(int ele, int intervalo, int numeroDeBuckets) {
        /*
        * calcula em qual bucket o numero vai, com base no seu valor e no valor do intervalo
        * calculado previamente
        * */
        int res;

        for (res = 0; res < numeroDeBuckets - 1 && ele > intervalo * (res + 1); res ++);

        return res;
    }

    private int calculaCoordenadaXBucket(int indiceBucket) {
        return COORD_X_INICIAL_BUCKET + (80 * indiceBucket);
    }

    private void geraBuckets(int numeroDeBuckets) {
        int maior = max(), intervalo = maior / numeroDeBuckets, i, bucket_pos;

        // o tamanho de um bucket vai ser no máximo o tamanho do próprio vetor, pensando no caso
        // de todos os elementos serem iguais, portanto:
        buckets = new Button[numeroDeBuckets][tl];
        bucket_labels = new Label[numeroDeBuckets];
        bucket_tls = new int[numeroDeBuckets];

        for (i = 0; i < numeroDeBuckets; i ++) {
            bucket_labels[i] = new Label("Bucket " + i);
            bucket_labels[i].setFont(new Font(LABEL_FONT_SIZE));
            bucket_labels[i].setLayoutX(calculaCoordenadaXBucket(i));
            bucket_labels[i].setLayoutY(COORD_Y_LABELS_BUCKETS);

            pane.getChildren().add(bucket_labels[i]);
        }

        // percorrer o vetor vendo onde cada elemento se encaixa e então colocando ele no bucket
        move_todos_os_botoes_para_buckets(intervalo, numeroDeBuckets);
    }

    private void bucketSort(int numeroDeBuckets) {
        geraBuckets(numeroDeBuckets);
    }

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

        Button botao_gera_vetor = new Button("Gerar Vetor");
        botao_gera_vetor.setLayoutX(200); botao_gera_vetor.setLayoutY(10);
        botao_gera_vetor.setOnAction(e -> {
            // as variáveis usadas aqui tem que ser declaradas aqui dentro ou tem que ser final

            String texto_tamanho_vetor = input_tamanho.getText();

            // removendo todos os botões antigos
            for (int i = 0; i < tl; i ++) {
                pane.getChildren().remove(vet[i]);
            }

            // feito para evitar tamanho negativo
            // posteriormente talvez adicionar uma mensagem de erro na tela
            if (!texto_tamanho_vetor.startsWith("-")) {
                geraVetor(Integer.parseInt(texto_tamanho_vetor));
            }
        });

        Button botao_bucket_sort = new Button("Bucket Sort");
        botao_bucket_sort.setLayoutX(300); botao_bucket_sort.setLayoutY(10);
        botao_bucket_sort.setOnAction(e -> {
            bucketSort(10);
        });

        pane.getChildren().add(botao_gera_vetor);
        pane.getChildren().add(input_tamanho);
        pane.getChildren().add(botao_bucket_sort);

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

    public void move_botoes_para_o_bucket(int indiceBucket, int indiceElemento)
    {
        int coord_x_bucket = calculaCoordenadaXBucket(indiceBucket), coord_y_no_bucket = COORD_Y_INICIAL_BUCKET - ((bucket_tls[indiceBucket] + 1) * 35);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                // mudando a cor do elemento para destacá-lo
                vet[indiceElemento].getStyleClass().add("botao-destaque-azul");

                // descendo o elemento
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[indiceElemento].setLayoutY(vet[indiceElemento].getLayoutY() + 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // movendo horizontalmente até a coordenada x do bucket
                // só vai executar um desses dois whiles
                while (vet[indiceElemento].getLayoutX() < coord_x_bucket) {
                    Platform.runLater(() -> vet[indiceElemento].setLayoutX(vet[indiceElemento].getLayoutX() + 1));
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                while (vet[indiceElemento].getLayoutX() > coord_x_bucket) {
                    Platform.runLater(() -> vet[indiceElemento].setLayoutX(vet[indiceElemento].getLayoutX() - 1));
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // descendo o elemento até a posição correta dentro do bucket
                while (vet[indiceElemento].getLayoutY() < coord_y_no_bucket) {
                    Platform.runLater(() -> vet[indiceElemento].setLayoutY(vet[indiceElemento].getLayoutY() + 1));
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // retorna o botão pro estilo padrão do javafx
                vet[indiceElemento].getStyleClass().clear();
                vet[indiceElemento].getStyleClass().add("button");

                // aqui vamos colocar o botão vetor do bucket de fato
                buckets[indiceBucket][bucket_tls[indiceBucket]] = vet[indiceElemento];
                bucket_tls[indiceBucket] ++;

                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();

        // tem que executar esse join ao final da animação para impedir que
        // sejam criadas todas as threads de uma vez dentro do loop
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void move_todos_os_botoes_para_buckets(int intervalo, int numeroDeBuckets) {
        Task<Void> loop = new Task<Void>() {
            @Override
            protected Void call() {
                int i, bucket_pos;

                for (i = 0; i < tl; i ++) {
                    bucket_pos = calculaBucket(Integer.parseInt(vet[i].getText()), intervalo, numeroDeBuckets);
                    move_botoes_para_o_bucket(bucket_pos, i);
                }

                return null;
            }
        };

        Thread thread = new Thread(loop);
        thread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}