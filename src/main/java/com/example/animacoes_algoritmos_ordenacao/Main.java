package com.example.animacoes_algoritmos_ordenacao;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.util.Random;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;



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
    final int COORD_X_INICIAL_VETOR = 10;
    final int ESPACAMENTO_VETOR = 35;
    final int TAMANHO_BOTAO = 25;
    final int LABEL_FONT_SIZE = 12;
    final int BUTTON_FONT_SIZE = 12;
    final int NUMERO_DE_BUCKETS = 10;

    public void geraVetor(int tamanho) {
        int i, pos_x = COORD_X_INICIAL_VETOR;
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
            pos_x += ESPACAMENTO_VETOR;
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
        int i, bucket_pos;

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
    }

    private void ordenaBucket(Button[] bucket, int tl) {
        Task<Void> ordena = new Task<Void>() {
            int i;

            @Override
            protected Void call() {

                moveBotaoParaFora(buckets[9][0]);

                return null;
            }
        };

        Thread thread = new Thread(ordena);
        thread.start();
    }

    private void ordenaBuckets(int numeroDeBuckets) {
        Task<Void> loop = new Task<Void>() {
            int i;

            @Override
            protected Void call() {
                for (i = 0; i < numeroDeBuckets; i ++) {
                    ordenaBucket(buckets[i], bucket_tls[i]);
                }
                return null;
            }
        };

        Thread thread = new Thread(loop);
        thread.start();
    }

    private void removeBotao(Button botao) {
        // essa função existe para que possamos manipular a ui fora da task principal do javafx
        // não dá pra usar platform.runLater diretamente dentro da task nesse caso, pois há variáveis
        // não finais em uso, portanto modularizei

        Platform.runLater(() -> {
            pane.getChildren().remove(botao);
        });
    }

    private void adicionaBotao(Button botao) {
        // função existe pelo mesmo motivo da removeBotao

        Platform.runLater(() -> {
            pane.getChildren().add(botao);
        });
    }

    private void bucketSort(int numeroDeBuckets) {

        geraBuckets(numeroDeBuckets);

        Task<Void> bucketSortTask = new Task<Void>() {
            @Override
            protected Void call() {
                int maior = max(), intervalo = maior / numeroDeBuckets, i, bucket_pos, j, k, nPos, pos = 0;
                Button botao_j, botao_aux;

                // percorrer o vetor vendo onde cada elemento se encaixa e então colocando ele no bucket
                for (i = 0; i < tl; i ++) {
                    bucket_pos = calculaBucket(Integer.parseInt(vet[i].getText()), intervalo, numeroDeBuckets);
                    move_botao_para_o_bucket(bucket_pos, i);
                }

                // percorre cada bucket
                for (i = 0; i < numeroDeBuckets; i ++) {

                    // isso aqui é um insertionSort
                    for (j = 1; j < bucket_tls[i]; j ++) {

                        // tem que criar um "clone" do botao
                        // assim como se faz em um insertionSort
                        botao_j = new Button(buckets[i][j].getText());
                        botao_j.setLayoutX(buckets[i][j].getLayoutX());
                        botao_j.setLayoutY(buckets[i][j].getLayoutY());
                        botao_j.setMinHeight(buckets[i][j].getMinHeight());
                        botao_j.setMinWidth(buckets[i][j].getMinWidth());
                        botao_j.setFont(buckets[i][j].getFont());

                        removeBotao(buckets[i][j]);
                        adicionaBotao(botao_j);
                        botao_j.getStyleClass().add("botao-destaque-azul");

                        moveBotaoParaFora(botao_j);

                        k = j - 1;

                        nPos = 0;
                        while (k >= 0 && Integer.parseInt(buckets[i][k].getText()) > Integer.parseInt(botao_j.getText())) {
                            buckets[i][k].getStyleClass().add("botao-destaque-vermelho");
                            moveBotaoParaCima(buckets[i][k]);
                            buckets[i][k].getStyleClass().clear();
                            buckets[i][k].getStyleClass().add("button");
                            buckets[i][k + 1] = buckets[i][k];
                            k --;
                            nPos ++;
                        }

                        moveBotaoParaBaixo(botao_j, nPos);

                        moveBotaoParaDentro(botao_j);
                        buckets[i][k + 1] = botao_j;
                        botao_j.getStyleClass().clear();
                        botao_j.getStyleClass().add("button");
                    }
                }

                pos = 0;
                for (i = 0; i < numeroDeBuckets; i ++) {
                    for (j = 0; j < bucket_tls[i]; j ++) {
                        moveBotaoParaOVetor(buckets[i][j], pos);
                        pos ++;
                    }
                }

                return null;
            }
        };

        Thread thread = new Thread(bucketSortTask);
        thread.start();
    }


    public void bucket() {
        bucket_tls = new int[NUMERO_DE_BUCKETS];
        //apaga tudo q tava na tela antes
        pane.getChildren().clear();
        //pane = new AnchorPane();
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
            tl = 0;

            for (int i = 0; i < NUMERO_DE_BUCKETS; i ++) {
                for (int j = 0; j < bucket_tls[i]; j ++) {
                    pane.getChildren().remove(buckets[i][j]);
                }
                bucket_tls[i] = 0;
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
            bucketSort(NUMERO_DE_BUCKETS);
        });

        pane.getChildren().add(botao_gera_vetor);
        pane.getChildren().add(input_tamanho);
        pane.getChildren().add(botao_bucket_sort);
    }
    @Override
    public void start(Stage stage) throws Exception {

        // Criando a barra de menus
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Opções");
        MenuItem menuItemBucket = new MenuItem("Bucket Sort");
        MenuItem menuItemRadix = new MenuItem("Radix Sort");

        // Ação para chamar o método bucket() quando o item de menu for selecionado

        menuItemBucket.setOnAction(e -> bucket());
        menuItemRadix.setOnAction(e -> RadixSort.Radix(pane));
        //menuItemBucket.setOnAction(e -> radix());

        //menu.getItems().add(menuItemBucket);
        menu.getItems().add(menuItemRadix);
        menuBar.getMenus().add(menu);

        // Pane principal
        pane = new AnchorPane();
        pane.getChildren().add(menuBar);

        // Definindo a cena
        Scene scene = new Scene(pane, 800, 600);
        stage.setTitle("Pesquisa e Ordenacao");
        stage.setScene(scene);
        stage.show();
        // Adicionar o GIF abaixo do menu
        Image gifImage = new Image(getClass().getResource("/com/example/animacoes_algoritmos_ordenacao/gif-main.gif").toExternalForm());
        ImageView gifView = new ImageView(gifImage);
        gifView.setLayoutX(10); // Posição X do GIF
        gifView.setLayoutY(60); // Posição Y abaixo do menu
        gifView.setFitWidth(800); // Largura ajustada do GIF
        gifView.setFitHeight(600); // Altura ajustada do GIF

        // Adicionar o GIF ao pane yay
        pane.getChildren().add(gifView);
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

    public void move_botao_para_o_bucket(int indiceBucket, int indiceElemento)
    {
        int coord_x_bucket = calculaCoordenadaXBucket(indiceBucket), coord_y_no_bucket = COORD_Y_INICIAL_BUCKET - ((bucket_tls[indiceBucket] + 1) * 35);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                // mudando a cor do elemento para destacá-lo
                vet[indiceElemento].getStyleClass().add("botao-destaque-azul");

                // descendo o elemento
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[indiceElemento].setLayoutY(vet[indiceElemento].getLayoutY() + 10));
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // movendo horizontalmente até a coordenada x do bucket
                // só vai executar um desses dois whiles
                while (vet[indiceElemento].getLayoutX() < coord_x_bucket) {
                    Platform.runLater(() -> vet[indiceElemento].setLayoutX(vet[indiceElemento].getLayoutX() + 10));
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                while (vet[indiceElemento].getLayoutX() > coord_x_bucket) {
                    Platform.runLater(() -> vet[indiceElemento].setLayoutX(vet[indiceElemento].getLayoutX() - 10));
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // descendo o elemento até a posição correta dentro do bucket
                while (vet[indiceElemento].getLayoutY() < coord_y_no_bucket) {
                    Platform.runLater(() -> vet[indiceElemento].setLayoutY(vet[indiceElemento].getLayoutY() + 10));
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // os incrementos não são feitos de 1 em 1 pixel, ent podem sair da posição desejada
                vet[indiceElemento].setLayoutY(coord_y_no_bucket);
                vet[indiceElemento].setLayoutX(coord_x_bucket);

                // retorna o botão pro estilo padrão do javafx
                vet[indiceElemento].getStyleClass().clear();
                vet[indiceElemento].getStyleClass().add("button");

                // aqui vamos colocar o botão no vetor do bucket de fato
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
                    move_botao_para_o_bucket(bucket_pos, i);
                    moveBotaoParaFora(buckets[9][0]);
                }

                return null;
            }
        };

        Thread thread = new Thread(loop);
        thread.start();
    }

    private void moveBotaoParaFora(Button botao) {
        Task<Void> moveParaFora = new Task<Void>() {
            int i;

            @Override
            protected Void call(){
                botao.getStyleClass().add("botao-destaque-azul");

                // movendo o elemento i para a lateral
                for (i = 0; i < 5; i ++) {
                    Platform.runLater(() -> {
                        botao.setLayoutX(botao.getLayoutX() + 5);
                    });

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }
        };

        Thread thread = new Thread(moveParaFora);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void moveBotaoParaDentro(Button botao) {
        Task<Void> moveParaDentro = new Task<Void>() {
            int j;

            @Override
            protected Void call() {
                // movendo o elemento i lateralmente de volta
                for (j = 0; j < 5; j ++) {
                    Platform.runLater(() -> {
                        botao.setLayoutX(botao.getLayoutX() - 5);
                    });

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                botao.getStyleClass().clear();
                botao.getStyleClass().add("button");
                return null;
            }
        };

        Thread thread = new Thread(moveParaDentro);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void moveBotaoParaCima(Button botao) {
        Task<Void> moveParaCima = new Task<Void>() {
            int i;

            @Override
            protected Void call() {
                for (i = 0; i < 35; i ++) {
                    Platform.runLater(() -> botao.setLayoutY(botao.getLayoutY() - 1));

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }
        };

        Thread thread = new Thread(moveParaCima);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void moveBotaoParaBaixo(Button botao, int nPosicoes) {
        Task<Void> moveParaCima = new Task<Void>() {
            int i, posFinal = (int)botao.getLayoutY() + (35 * nPosicoes);

            @Override
            protected Void call() {
                while (botao.getLayoutY() < posFinal) {
                    Platform.runLater(() -> botao.setLayoutY(botao.getLayoutY() + 5));

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                botao.setLayoutY(posFinal);

                return null;
            }
        };

        Thread thread = new Thread(moveParaCima);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void moveBotaoParaOVetor(Button botao, int pos) {
        Task<Void> moveParaOVetor = new Task<Void>() {
            @Override
            protected Void call() {

                // sobe o botão até a linha do vetor
                while (botao.getLayoutY() > COORD_Y_VETOR_PRINCIPAL) {
                    Platform.runLater(() -> botao.setLayoutY(botao.getLayoutY() - 5));

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // esses posicionam lateralmente, só executa um dos dois loops
                while (botao.getLayoutX() > COORD_X_INICIAL_VETOR + (pos * ESPACAMENTO_VETOR)) {
                    Platform.runLater(() -> botao.setLayoutX(botao.getLayoutX() - 5));

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                while (botao.getLayoutX() < COORD_X_INICIAL_VETOR + (pos * ESPACAMENTO_VETOR)) {
                    Platform.runLater(() -> botao.setLayoutX(botao.getLayoutX() + 5));

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                vet[pos] = botao;

                return null;
            }
        };

        Thread thread = new Thread(moveParaOVetor);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void moveTodosOsBotoesParaOVetor(int numeroDeBuckets) {
        Task<Void> moveTodosBotoes = new Task<Void>() {
            @Override
            protected Void call(){
                int pos = 0;

                for (int i = 0; i < numeroDeBuckets; i ++) {
                    for (int j = 0; j < bucket_tls[i]; j ++) {
                        moveBotaoParaOVetor(buckets[i][j], pos);
                        pos ++;
                    }
                }
                return null;
            }
        };

        Thread thread = new Thread(moveTodosBotoes);
        thread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}