package com.example.animacoes_algoritmos_ordenacao;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.cert.PolicyNode;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import static java.lang.Thread.sleep;

public class RadixSort {
    private static VBox codigoBox;
    AnchorPane pane;

    // vetor de contagem
    private static final int COORD_Y_TITULO_COUNTING = 120;
    private static final int COORD_X_TITULO_COUNTING = 70;
    private static final int COORD_Y_COUNTING_ARR = 200;
    private static Label[] radix_lb_i;
    private static Label[] radix_lb;

    //vetor gerado
    private static final int COORD_Y_VETOR_PRINCIPAL = 70;
    private static final int COORD_X_INICIAL_VETOR_P = 50;
    private static final int COORD_Y_LABEL_RADIX = 170;
    private static int maior;
    private static int tl;
    private static Button[] vet;
    private static int tamanho_vetor;

    //especs gerais
    private static final int ESPACAMENTO_VETOR = 35;
    private static final int TAMANHO_BOTAO = 25;
    private static final int LABEL_FONT_SIZE = 15;
    private static final int BUTTON_FONT_SIZE = 12;

    //input n vetor
    private static final int COORD_Y_INPUT_TAMANHO = 10;
    private static final int COORD_X_INPUT_TAMANHO = 10;

    //botoes de cima
    private static final int COORD_Y_BOTAO_GERA_VETOR = 10;
    private static final int COORD_X_BOTAO_GERA_VETOR = 200;
    private static final int COORD_Y_BOTAO_RADIX = 10;
    private static final int COORD_X_BOTAO_RADIX = 300;

    //vetor resultante
    private static final int COORD_Y_TITULO_COPIA = 270;
    private static final int COORD_X_TITULO_COPIA = 70;
    private static final int COORD_Y_COPIA = 330;
    private static final int COORD_X_COPIA = 20;
    private static Button vetRes[];
    private static int tlRes;


    private static int calculaCoordenadaX(int i, int base) {
        return base + (80 * i);
    }

    public static void geraVetor(int tamanho, AnchorPane pane) {
        vet = new Button[tamanho];
        Random rand = new Random();
        tl = 0;
        maior = 0;
        int posX = COORD_X_INICIAL_VETOR_P;

        for (int i = 0; i < tamanho; i++) {
            int num = (i < tamanho / 2) ? rand.nextInt(1000) : rand.nextInt(100);
            if (num > maior) maior = num;

            Button btn = new Button();
            btn.setPrefSize(40, TAMANHO_BOTAO);
            btn.setFont(new Font(BUTTON_FONT_SIZE));
            btn.setLayoutX(posX);
            btn.setLayoutY(COORD_Y_VETOR_PRINCIPAL);

            TextFlow tf = new TextFlow();
            for (char c : String.valueOf(num).toCharArray()) {
                Text t = new Text(String.valueOf(c));
                t.setStyle("-fx-fill: black; -fx-font-size: " + BUTTON_FONT_SIZE + "px;");
                tf.getChildren().add(t);
            }
            btn.setGraphic(tf);
            vet[tl++] = btn;
            pane.getChildren().add(btn);
            posX += ESPACAMENTO_VETOR;
        }
    }

    public static void gerarLabels(int tamanho, AnchorPane pane) {
        radix_lb_i = new Label[tamanho];
        radix_lb = new Label[tamanho];

        Label lblContagem = new Label("Vetor Contagem");
        lblContagem.setLayoutX(COORD_X_TITULO_COUNTING);
        lblContagem.setLayoutY(COORD_Y_TITULO_COUNTING);
        lblContagem.setStyle("-fx-font-weight: bold;");
        pane.getChildren().add(lblContagem);

        Label lblResult = new Label("Vetor Resultante");
        lblResult.setLayoutX(COORD_X_TITULO_COPIA);
        lblResult.setLayoutY(COORD_Y_TITULO_COPIA);
        lblResult.setStyle("-fx-font-weight: bold;");
        pane.getChildren().add(lblResult);

        for (int i = 0; i < tamanho; i++) {
            radix_lb_i[i] = new Label(" " + i);
            radix_lb[i] = new Label("0");

            radix_lb_i[i].setFont(new Font(LABEL_FONT_SIZE));
            radix_lb[i].setFont(new Font(LABEL_FONT_SIZE));

            int posX = calculaCoordenadaX(i, 80);
            radix_lb_i[i].setLayoutX(posX);
            radix_lb[i].setLayoutX(posX + 5);

            radix_lb_i[i].setLayoutY(COORD_Y_COUNTING_ARR);
            radix_lb[i].setLayoutY(COORD_Y_LABEL_RADIX);

            radix_lb_i[i].setStyle("-fx-font-weight: bold;");
            pane.getChildren().addAll(radix_lb_i[i], radix_lb[i]);
        }
    }

    public static void Radix(AnchorPane pane) {
        pane.getChildren().clear();
        pane.getStylesheets().add(RadixSort.class.getResource("/com/example/animacoes_algoritmos_ordenacao/style.css").toExternalForm());
        lbmaior = new Label();
        lbi = new Label();
        jlb = new Label();
        klb = new Label();
        mlb = new Label();
        lbdigito = new Label();
        lbnum = new Label();

        adicionaLabel(lbmaior, 900, 50, "Maior: ", pane);
        adicionaLabel(lbi, 900, 75, "i: ", pane);
        adicionaLabel(jlb, 900, 100, "j: ", pane);
        adicionaLabel(klb, 900, 125, "k: ", pane);
        adicionaLabel(mlb, 900, 150, "l: ", pane);
        adicionaLabel(lbdigito, 900, 175, "Dígito: ", pane);
        adicionaLabel(lbnum, 900, 200, "Num: ", pane);
        codigoBox = new VBox(5);
        adicionaVBox(codigoBox, 1200, 50, pane);
        carregaCodigo("src/main/resources/com/example/animacoes_algoritmos_ordenacao/cod_radix.txt");
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

                if (vet != null && tl > 0) {
                    for (int i = 0; i < tl; i++) {
                        pane.getChildren().remove(vet[i]);
                    }
                }

                tl = 0; // Só reseta aqui, depois de remover os botões antigos
                geraVetor(tamanho_vetor, pane);
                vetRes = new Button[tamanho_vetor];
                tlRes = tl;

            } catch (NumberFormatException ex) {
                System.out.println("Erro: Insira um número válido!");
            }
        });


        Button botao_radix = new Button("Radix Sort");
        botao_radix.setLayoutX(COORD_X_BOTAO_RADIX);
        botao_radix.setLayoutY(COORD_Y_BOTAO_RADIX);
        botao_radix.setOnAction(e -> {
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    int vzs = String.valueOf(maior).length();
                    Platform.runLater(() -> lbmaior.setText("Maior: " + maior));

                    for (int i = 0; i < vzs; i++) {
                        int finalI_label = i;
                        Platform.runLater(() -> lbi.setText("i: " + finalI_label));

                        // Etapa 1: Contagem dos dígitos
                        for (int j = 0; j < tl; j++) {
                            int finalI = i;
                            int finalJ = j;
                            Platform.runLater(() -> jlb.setText("j: " + finalJ));
                            destacaLinha(2);
                            Platform.runLater(() -> destacaLinha(2));
                            PauseTransition reset = new PauseTransition(Duration.seconds(0.5));
                            reset.play();
                            TextFlow textFlow = (TextFlow) vet[finalJ].getGraphic();
                            ObservableList<Node> textos = textFlow.getChildren();

                            int index = textos.size() - 1 - finalI;

                            if (!textos.isEmpty() && index >= 0 && index < textos.size() && textos.get(index) instanceof Text t) {
                                destacaLinha(2);
                                Platform.runLater(() -> {
                                    t.setStyle("-fx-fill: red; -fx-font-size: 12px;");
                                });

                                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                                pause.setOnFinished(ev -> {
                                    int digito = Integer.parseInt(t.getText());
                                    Platform.runLater(() -> {
                                        lbdigito.setText("Dígito: " + digito);
                                    });

                                    Label label = radix_lb[digito];
                                    label.setStyle("-fx-font-weight: bold;");
                                    int valorAtual = Integer.parseInt(label.getText());
                                    destacaLinha(3);
                                    label.setText(String.valueOf(valorAtual + 1));

                                    PauseTransition reset1 = new PauseTransition(Duration.seconds(0.5));
                                    reset1.setOnFinished(e2 -> label.setStyle("-fx-font-weight: normal;"));
                                    reset1.play();
                                });
                                pause.play();
                            } else if (index < 0) {
                                PauseTransition reset1 = new PauseTransition(Duration.seconds(0.5));
                                reset.play();
                                destacaLinha(2);
                                Label label = radix_lb[0];
                                Platform.runLater(() -> {
                                    lbdigito.setText("Dígito: 0");
                                });

                                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                                pause.setOnFinished(ev -> {
                                    int valorAtual = Integer.parseInt(label.getText());
                                    label.setText(String.valueOf(valorAtual + 1));
                                    destacaLinha(3);
                                    PauseTransition reset2 = new PauseTransition(Duration.seconds(0.5));
                                    reset2.play();

                                    label.setStyle("-fx-font-weight: bold;");
                                    PauseTransition reset11 = new PauseTransition(Duration.seconds(0.5));
                                    reset11.setOnFinished(e2 -> label.setStyle("-fx-font-weight: normal;"));
                                    reset11.play();
                                });
                                pause.play();
                            }

                            try {
                                sleep(1500);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }

                        // Etapa 2: Soma acumulada
                        for (int k = 1; k < 10; k++) {
                            destacaLinha(6);
                            int finalK = k;
                            Platform.runLater(() -> klb.setText("k: " + finalK));
                            Platform.runLater(() -> destacaLinha(6));

                            Label origem = radix_lb[finalK - 1];
                            Label destino = radix_lb[finalK];

                            double x1 = origem.getLayoutX() + origem.getWidth() / 2;
                            double y1 = origem.getLayoutY() + origem.getHeight() / 2;
                            double x2 = destino.getLayoutX() + destino.getWidth() / 2;
                            double y2 = destino.getLayoutY() + destino.getHeight() / 2;

                            double offset = 10;
                            double dx = x2 - x1;
                            double dy = y2 - y1;
                            double distance = Math.sqrt(dx * dx + dy * dy);
                            double reduceX = (dx * offset / distance) - 5;
                            double reduceY = dy * offset / distance;

                            double xLineEnd = x2 - reduceX;
                            double yLineEnd = y2 - reduceY;

                            Line linha = new Line(x1, y1, xLineEnd, yLineEnd);
                            linha.setStroke(Color.RED);
                            linha.setStrokeWidth(2);

                            Polygon seta = new Polygon();
                            seta.setFill(Color.RED);

                            double arrowLength = 5;
                            double arrowWidth = 6;
                            double angle = Math.atan2(dy, dx);
                            double sin = Math.sin(angle);
                            double cos = Math.cos(angle);

                            seta.getPoints().addAll(
                                    x2, y2,
                                    x2 - arrowLength * cos + arrowWidth * sin, y2 - arrowLength * sin - arrowWidth * cos,
                                    x2 - arrowLength * cos - arrowWidth * sin, y2 - arrowLength * sin + arrowWidth * cos
                            );

                            Platform.runLater(() -> pane.getChildren().addAll(linha, seta));

                            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                            pause.setOnFinished(e2 -> {
                                int valorAtual1 = Integer.parseInt(destino.getText());
                                int valorAtual0 = Integer.parseInt(origem.getText());
                                destino.setText(String.valueOf(valorAtual1 + valorAtual0));
                                pane.getChildren().removeAll(linha, seta);
                            });
                            pause.play();

                            try {
                                sleep(1800);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }

                        // Etapa 3: Construir vetor resultante com pausa e destaque
                        CountDownLatch latchMoveBaixo = new CountDownLatch(tl);
                        for (int l = tl - 1; l >= 0; l--) {
                            int finalL = l;
                            int finalI1 = i;
                            destacaLinha(9);
                            Platform.runLater(() -> mlb.setText("l: " + finalL));

                            try {
                                Button botao = vet[finalL];
                                TextFlow tf = (TextFlow) botao.getGraphic();
                                StringBuilder sb = new StringBuilder();
                                for (Node node : tf.getChildren()) {
                                    if (node instanceof Text t) sb.append(t.getText());
                                }

                                String numeroTexto = sb.toString().trim();
                                if (numeroTexto.isEmpty()) {
                                    latchMoveBaixo.countDown();
                                    continue;
                                }

                                int aux = Integer.parseInt(numeroTexto);
                                Platform.runLater(() -> lbnum.setText("Num: " + aux));
                                destacaLinha(10);
                                int index = (aux / (int) Math.pow(10, finalI1)) % 10;
                                Platform.runLater(() -> lbdigito.setText("Dígito: " + index));

                                if (index < 0 || index > 9) {
                                    latchMoveBaixo.countDown();
                                    continue;
                                }

                                Label labelDestino = radix_lb[index];

                                Platform.runLater(() -> {
                                    labelDestino.setStyle("-fx-font-weight: bold;");
                                    PauseTransition pause1 = new PauseTransition(Duration.seconds(1));
                                    pause1.setOnFinished(ev1 -> {
                                        int destino = Integer.parseInt(labelDestino.getText()) - 1;
                                        labelDestino.setText(String.valueOf(destino));
                                        labelDestino.setStyle("-fx-font-weight: normal;");

                                        if (destino >= 0 && destino < tl) {
                                            destacaLinha(10);
                                            vetRes[destino] = botao;
                                            move_botao_para_resultante_com_latch(botao, destino, latchMoveBaixo);
                                        } else {
                                            latchMoveBaixo.countDown();
                                        }
                                    });
                                    pause1.play();
                                });

                                destacaLinha(12);
                                sleep(2000);

                            } catch (Exception ex) {
                                ex.printStackTrace();
                                latchMoveBaixo.countDown();
                            }
                        }

                        try {
                            latchMoveBaixo.await();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                        // Etapa 4: Subir e resetar
                        CountDownLatch latchMoveCima = new CountDownLatch(tl + 1);
                        for (int k = 0; k < tl; k++) {
                            int finalK = k;
                            Platform.runLater(() -> klb.setText("k: " + finalK));
                            vet[k] = vetRes[k];
                            move_botao_para_cima_com_latch(vet[finalK], finalK, latchMoveCima);
                            destacaLinha(15);
                        }

                        Platform.runLater(() -> {
                            for (int l = 0; l < 10; l++) {
                                radix_lb[l].setText("0");
                            }
                            latchMoveCima.countDown();
                        });

                        try {
                            latchMoveCima.await();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                        destacaLinha(18);
                    }

                    CountDownLatch latchFinalizacao = new CountDownLatch(tl);
                    for (int j = 0; j < tl; j++) {
                        int finalJ = j;
                        Platform.runLater(() -> jlb.setText("j: " + finalJ));
                        Platform.runLater(() -> {
                            Button botao = vetRes[finalJ];
                            if (botao != null) {
                                move_botao_para_resultante_com_latch(botao, finalJ, latchFinalizacao);
                            } else {
                                latchFinalizacao.countDown();
                            }
                        });
                    }

                    try {
                        latchFinalizacao.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return null;
                }
            };

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        });




        pane.getChildren().addAll(input_tamanho, botao_gera_vetor, botao_radix);
        gerarLabels(10, pane);
        pane.requestLayout();


        Platform.runLater(() -> {
            Scene scene = pane.getScene();
            if (scene != null) {
                pane.prefWidthProperty().bind(scene.widthProperty());
                pane.prefHeightProperty().bind(scene.heightProperty());

                Window window = scene.getWindow();
                if (window instanceof Stage stage) {
                    stage.setMaximized(true);
                }
            }
        });
    }

    public static void move_botao_para_resultante_com_latch(Button button, int pos, CountDownLatch latch) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                double destinoY = COORD_Y_COPIA;
                double destinoX = calculaCoordenadaX(pos, COORD_X_COPIA);

                try {
                    // Movimento vertical
                    while (button.getLayoutY() < destinoY) {
                        double novoY = Math.min(button.getLayoutY() + 5, destinoY);
                        Platform.runLater(() -> button.setLayoutY(novoY));
                        sleep(20);
                    }

                    // Movimento horizontal
                    while (button.getLayoutX() != destinoX) {
                        double atualX = button.getLayoutX();
                        double passo = (destinoX > atualX) ? 5 : -5;
                        double novoX = Math.abs(destinoX - atualX) < 5 ? destinoX : atualX + passo;

                        Platform.runLater(() -> button.setLayoutX(novoX));
                        sleep(20);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown(); // Garante que mesmo se algo falhar o latch não trava
                }

                return null;
            }
        };

        new Thread(task).start();
    }

    public static void move_botao_para_resultante(Button button, int pos) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                double destinoY = COORD_Y_COPIA;
                double destinoX = calculaCoordenadaX(pos, COORD_X_COPIA);

                double atualX = button.getLayoutX();
                double atualY = button.getLayoutY();

                // Direções de movimento
                double deltaY = destinoY - atualY;
                double deltaX = destinoX - atualX;

                // Movimento vertical (pra baixo ou cima)
                int passosY = (int) Math.abs(deltaY);
                int direcaoY = (int) Math.signum(deltaY);

                for (int i = 0; i < passosY; i += 5) {
                    double newY = button.getLayoutY() + (5 * direcaoY);
                    Platform.runLater(() -> button.setLayoutY(newY));
                    try {
                        sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Movimento horizontal (pra direita ou esquerda)
                int passosX = (int) Math.abs(deltaX);
                int direcaoX = (int) Math.signum(deltaX);

                for (int i = 0; i < passosX; i += 5) {
                    double newX = button.getLayoutX() + (5 * direcaoX);
                    Platform.runLater(() -> button.setLayoutX(newX));
                    try {
                        sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Garantir posição final exata
                Platform.runLater(() -> {
                    button.setLayoutX(destinoX);
                    button.setLayoutY(destinoY);
                });

                return null;
            }
        };
        new Thread(task).start();
    }

    public static void move_botao_para_cima(Button button, int pos) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws InterruptedException {
                double destinoY = COORD_Y_VETOR_PRINCIPAL;
                double destinoX = calculaCoordenadaX(pos, COORD_X_INICIAL_VETOR_P);

                double yAtual = button.getLayoutY();
                double xAtual = button.getLayoutX();

                // Movimentação vertical
                while (Math.abs(yAtual - destinoY) > 1) {
                    double finalY = yAtual + Math.signum(destinoY - yAtual) * 5;
                    yAtual = finalY;

                    double finalYAtual = yAtual;
                    Platform.runLater(() -> button.setLayoutY(finalYAtual));
                    sleep(20);
                }

                // Movimentação horizontal
                while (Math.abs(xAtual - destinoX) > 1) {
                    double finalX = xAtual + Math.signum(destinoX - xAtual) * 5;
                    xAtual = finalX;

                    double finalXAtual = xAtual;
                    Platform.runLater(() -> button.setLayoutX(finalXAtual));
                    sleep(20);
                }

                return null;
            }
        };
        new Thread(task).start();
    }

    public static void move_botao_para_cima_com_latch(Button button, int pos, CountDownLatch latch) {
        double destinoY = COORD_Y_VETOR_PRINCIPAL;
        double destinoX = calculaCoordenadaX(pos, COORD_X_INICIAL_VETOR_P);

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                // Subir verticalmente
                while (button.getLayoutY() > destinoY) {
                    double y = button.getLayoutY();
                    Platform.runLater(() -> button.setLayoutY(y - 5));
                    try {
                        sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Mover horizontalmente
                double deltaX = destinoX - button.getLayoutX();
                int direcao = deltaX > 0 ? 1 : -1;
                while (Math.abs(button.getLayoutX() - destinoX) > 2) {
                    Platform.runLater(() -> button.setLayoutX(button.getLayoutX() + (5 * direcao)));
                    try {
                        sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Remover estilização do TextFlow (deixar todos os Texts com estilo padrão)
                Platform.runLater(() -> {
                    if (button.getGraphic() instanceof TextFlow tf) {
                        for (Node node : tf.getChildren()) {
                            if (node instanceof Text text) {
                                text.setStyle(""); // Remove qualquer estilo
                            }
                        }
                    }
                });

                latch.countDown(); // Marca finalização
                return null;
            }
        };

        new Thread(task).start();
    }


    private static Label linhasCodigo[];
    private static int nLinhasCodigo;
    private static Label lbmaior, lbi, jlb, klb, mlb, lbdigito, lbnum;
    private static void adicionaLabel(Label label, int posx, int posy, String texto, AnchorPane pane) {
        Platform.runLater(() -> {
            label.setFont(new Font(24));
            label.setLayoutX(posx);
            label.setLayoutY(posy);
            label.setText(texto);
            pane.getChildren().add(label);
        });
    }
    private static void removeLabel(Label label, AnchorPane pane) {
        Platform.runLater(() -> {
            pane.getChildren().remove(label);
        });
    }

    private static void adicionaVBox(VBox box, int posx, int posy, AnchorPane pane) {
        Platform.runLater(() -> {
            box.setLayoutX(posx);
            box.setLayoutY(posy);
            pane.getChildren().add(box);
        });
    }

    private static void carregaCodigo(String nome_do_txt) {
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

    private static void destacaLinha(int indice) {
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

}