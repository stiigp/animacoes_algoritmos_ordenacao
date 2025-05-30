package com.example.animacoes_algoritmos_ordenacao;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class BucketSort {

    private Button[] vet;
    private Button[][] buckets; // os buckets serão uma matriz
    private Label[] bucket_labels, linhasCodigo;
    private Label intervalo_label, pos_label, i_label, j_label, k_label, elemento_j_label;
    private int[] bucket_tls;
    private VBox codigoBox;
    private int tl, nLinhasCodigo;
    private TextField input_tamanho;

    final int COORD_Y_LABELS_BUCKETS = 600;
    final int COORD_Y_VETOR_PRINCIPAL = 100;
    final int COORD_Y_INICIAL_BUCKET = 570;
    final int COORD_X_INICIAL_BUCKET = 10;
    final int COORD_X_INICIAL_VETOR = 10;
    final int ESPACAMENTO_VETOR = 35;
    final int TAMANHO_BOTAO = 25;
    final int LABEL_FONT_SIZE = 12;
    final int BUTTON_FONT_SIZE = 12;

    private int nBuckets;

    // tempo em milissegundos que vai parar na linha destacada, para aumentar compreensão da animação
    final int TEMPO_ENTRE_LINHAS = 1000;

    AnchorPane pane;

    public BucketSort(AnchorPane pane, int nBuckets, TextField input_tamanho) {
        this.pane = pane;
        this.nBuckets = nBuckets;
        this.input_tamanho = input_tamanho;

        this.bucket_tls = new int[nBuckets];
    }

    public void removeBotoes() {
        // as variáveis usadas aqui tem que ser declaradas aqui dentro ou tem que ser final

        String texto_tamanho_vetor = input_tamanho.getText();

        // removendo todos os botões antigos
        for (int i = 0; i < tl; i ++) {
            pane.getChildren().remove(vet[i]);
        }
        tl = 0;

        for (int i = 0; i < nBuckets; i ++) {
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
    }

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

    private int min() {
        int menor = max(), i;

        for (i = 0; i < tl; i ++){
            if (Integer.parseInt(vet[i].getText()) < menor)
                menor = Integer.parseInt(vet[i].getText());
        }

        return menor;
    }

    private int calculaBucket(int ele, double intervalo, int numeroBuckets, int menor) {
        /*
         * calcula em qual bucket o numero vai, com base no seu valor e no valor do intervalo
         * calculado previamente
         * */
        int res;

//        for (res = 0; res < numeroDeBuckets - 1 && ele > intervalo * (res + 1); res ++);

        res = (int)((ele - menor) / intervalo);


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

    private void adicionaLabel(Label label, int posx, int posy, String texto) {
        Platform.runLater(() -> {
            label.setFont(new Font(24));
            label.setLayoutX(posx);
            label.setLayoutY(posy);
            label.setText(texto);
            pane.getChildren().add(label);
        });
    }

    private void removeLabel(Label label) {
        Platform.runLater(() -> {
            pane.getChildren().remove(label);
        });
    }

    private void adicionaVBox(VBox box, int posx, int posy) {
        Platform.runLater(() -> {
            box.setLayoutX(posx);
            box.setLayoutY(posy);
            pane.getChildren().add(box);
        });
    }

    private void carregaCodigo(String nome_do_txt) {
        linhasCodigo = new Label[20]; // 20 é um TF arbitrário
        nLinhasCodigo = 0; // funciona como um TL, é atributo da classe
        Label linhaLabel;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(nome_do_txt));

            String linha = reader.readLine();
            while (linha != null) {
                linhaLabel = new Label(linha);
                linhaLabel.getStyleClass().add("linha-codigo");

                linhasCodigo[nLinhasCodigo++] = linhaLabel;

                linha = reader.readLine();
            }

            Platform.runLater(() -> {
                // removendo as linhas do código que estava anteriormente
                codigoBox.getChildren().clear();

                // adicionando as linhas do vetor linhas no vBox
                for (int i = 0; i < nLinhasCodigo; i ++) {
                    codigoBox.getChildren().add(linhasCodigo[i]);
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void destacaLinha(int indice) {
        Platform.runLater(() -> {
            // voltando todas as linhas ao normal
            for (int i = 0; i < nLinhasCodigo; i ++) {
                linhasCodigo[i].getStyleClass().clear();
                linhasCodigo[i].getStyleClass().add("linha-codigo");
            }

            // destacando a linha desejada
            linhasCodigo[indice].getStyleClass().add("linha-destaque");
        });
    }

    public void bucketSort() {

        bucket_tls = new int[nBuckets];

        geraBuckets(nBuckets);
        codigoBox = new VBox(5);
        adicionaVBox(codigoBox, 1200, 50);

        Task<Void> bucketSortTask = new Task<Void>() {
            @Override
            protected Void call() {
                int maior = max(), menor = min(), i, bucket_pos, j, k, nPos, pos = 0;
                double intervalo = (double) (maior - menor) / nBuckets;
                Button botao_j, botao_aux;

                pos_label = new Label();
                intervalo_label = new Label();
                i_label = new Label();

                adicionaLabel(intervalo_label, 900, 50, "Intervalo: " + Double.toString(intervalo));
                adicionaLabel(pos_label, 900, 75, "Bucket: ");
                adicionaLabel(i_label, 900, 100, "i: ");

                carregaCodigo("src/main/resources/com/example/animacoes_algoritmos_ordenacao/separa_buckets.txt");

                // percorrer o vetor vendo onde cada elemento se encaixa e então colocando ele no bucket
                for (i = 0; i < tl; i ++) {
                    vet[i].getStyleClass().add("botao-destaque-azul");

                    removeLabel(i_label);
                    adicionaLabel(i_label, 900, 100, "i: " + i);

                    // esses sleeps aqui vao ser executados meramente pra ilustrar a linha que está sendo executada

                    destacaLinha(0);
                    try {
                        Thread.sleep(TEMPO_ENTRE_LINHAS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    bucket_pos = calculaBucket(Integer.parseInt(vet[i].getText()), intervalo, nBuckets, menor);
                    removeLabel(pos_label);
                    adicionaLabel(pos_label, 900, 75, "Bucket: " + bucket_pos);

                    // destacar a linha do calcula bucket
                    destacaLinha(3);
                    try {
                        Thread.sleep(TEMPO_ENTRE_LINHAS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // destacar a linha do if
                    destacaLinha(5);
                    try {
                        Thread.sleep(TEMPO_ENTRE_LINHAS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (bucket_pos >= nBuckets) {
                        // destacar a linha de dentro do if
                        destacaLinha(6);

                        bucket_pos = nBuckets - 1;

                        removeLabel(pos_label);
                        adicionaLabel(pos_label, 900, 75, "Bucket: " + bucket_pos);

                        try {
                            Thread.sleep(TEMPO_ENTRE_LINHAS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                    // destacar a linha do move botão
                    destacaLinha(8);

                    vet[i].getStyleClass().clear();
                    vet[i].getStyleClass().add("button");

                    move_botao_para_o_bucket(bucket_pos, i);
                }

                carregaCodigo("src/main/resources/com/example/animacoes_algoritmos_ordenacao/insertions_sorts.txt");
                elemento_j_label = new Label();
                j_label = new Label();
                k_label = new Label();
                adicionaLabel(j_label, 900, 125, "j: ");
                adicionaLabel(k_label, 900, 150, "k: ");
                adicionaLabel(elemento_j_label, 900, 175, "elemento_j: ");

                // percorre cada bucket
                for (i = 0; i < nBuckets; i ++) {
                    // destacar o bucket sendo ordenado?
                    destacaLinha(0);
                    removeLabel(i_label);
                    adicionaLabel(i_label, 900, 100, "i: " + i);

                    removeLabel(pos_label);
                    adicionaLabel(pos_label, 900, 75, "Bucket: " + i);

                    try {
                        Thread.sleep(TEMPO_ENTRE_LINHAS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // isso aqui é um insertionSort
                    for (j = 1; j < bucket_tls[i]; j ++) {

                        destacaLinha(1);
                        removeLabel(j_label);
                        adicionaLabel(j_label, 900, 125, "j: " + j);
                        try {
                            Thread.sleep(TEMPO_ENTRE_LINHAS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // destacar a linha 3 (int elemento_j = buckets[i][j];) e colocar um label mostrando o número do elemento_j
                        destacaLinha(2);
                        removeLabel(elemento_j_label);
                        adicionaLabel(elemento_j_label, 900,175, "elemento_j: " + buckets[i][j].getText());

                        // tem que criar um "clone" do botao
                        // assim como se faz em um insertionSort
                        botao_j = new Button(buckets[i][j].getText());
                        botao_j.setLayoutX(buckets[i][j].getLayoutX());
                        botao_j.setLayoutY(buckets[i][j].getLayoutY());
                        botao_j.setMinHeight(buckets[i][j].getMinHeight());
                        botao_j.setMinWidth(buckets[i][j].getMinWidth());
                        botao_j.setFont(buckets[i][j].getFont());

                        botao_j.getStyleClass().add("botao-destaque-azul");

                        removeBotao(buckets[i][j]);
                        adicionaBotao(botao_j);

                        try {
                            Thread.sleep(TEMPO_ENTRE_LINHAS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        botao_j.getStyleClass().clear();
                        botao_j.getStyleClass().add("button");

                        moveBotaoParaFora(botao_j);

                        botao_j.getStyleClass().add("botao-destaque-azul");


                        destacaLinha(4);
                        k = j - 1;
                        removeLabel(k_label);
                        adicionaLabel(k_label, 900, 150, "k: " + k);
                        buckets[i][k].getStyleClass().add("botao-destaque-vermelho");
                        try {
                            Thread.sleep(TEMPO_ENTRE_LINHAS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        nPos = 0;
                        while (k >= 0 && Integer.parseInt(buckets[i][k].getText()) > Integer.parseInt(botao_j.getText())) {
                            // destacar linha do while + sleep
                            destacaLinha(5);
                            try {
                                Thread.sleep(TEMPO_ENTRE_LINHAS);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            // destacar linha 7 (buckets[i][k + 1] = buckets[i][k];)
                            destacaLinha(6);
                            buckets[i][k].getStyleClass().add("botao-destaque-vermelho");
                            moveBotaoParaCima(buckets[i][k]);
                            buckets[i][k].getStyleClass().clear();
                            buckets[i][k].getStyleClass().add("button");
                            buckets[i][k + 1] = buckets[i][k];

                            // destacar linha 8 (k --;)
                            destacaLinha(7);
                            k --;
                            removeLabel(k_label);
                            adicionaLabel(k_label, 900, 150, "k: " + k);
                            try {
                                Thread.sleep(TEMPO_ENTRE_LINHAS);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            nPos ++;
                        }

                        if (k == j - 1) {
                            buckets[i][k].getStyleClass().clear();
                            buckets[i][k].getStyleClass().add("button");
                        }

                        // destacar linha 11 (buckets[j][k + 1] = elemento_j;)
                        destacaLinha(10);
                        moveBotaoParaBaixo(botao_j, nPos);

                        moveBotaoParaDentro(botao_j);
                        buckets[i][k + 1] = botao_j;
                        botao_j.getStyleClass().clear();
                        botao_j.getStyleClass().add("button");
                    }
                }

                pos = 0;
                for (i = 0; i < nBuckets; i ++) {
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
                        Thread.sleep(50);
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

}
