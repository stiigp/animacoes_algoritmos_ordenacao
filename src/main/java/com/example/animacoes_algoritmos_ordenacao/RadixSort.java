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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class RadixSort {
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
    private static final int COORD_Y_COPIA = 350;
    private static final int COORD_X_COPIA = 30;
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

                    for (int i = 0; i < vzs; i++) {

                        // Etapa 1: Contagem dos dígitos
                        for (int j = 0; j < tl; j++) {
                            int finalI = i;
                            int finalJ = j;

                            Platform.runLater(() -> {
                                Button botao = vet[finalJ];
                                TextFlow textFlow = (TextFlow) botao.getGraphic();
                                ObservableList<Node> textos = textFlow.getChildren();

                                int index = textos.size() - 1 - finalI;

                                if (!textos.isEmpty() && index >= 0 && index < textos.size() && textos.get(index) instanceof Text t) {
                                    t.setStyle("-fx-fill: red; -fx-font-size: 12px;");
                                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                                    pause.setOnFinished(ev -> {
                                        int digito = Integer.parseInt(t.getText());
                                        Label label = radix_lb[digito];
                                        int valorAtual = Integer.parseInt(label.getText());
                                        label.setText(String.valueOf(valorAtual + 1));
                                    });
                                    pause.play();
                                } else if (index < 0) {
                                    Label label = radix_lb[0];
                                    int valorAtual = Integer.parseInt(label.getText());
                                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                                    pause.setOnFinished(ev -> {
                                        label.setText(String.valueOf(valorAtual + 1));
                                    });
                                    pause.play();
                                }
                            });

                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }

                        // Etapa 2: Soma acumulada
                        for (int k = 1; k < 10; k++) {
                            int finalK = k;
                            Platform.runLater(() -> {
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

                                pane.getChildren().addAll(linha, seta);

                                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                                pause.setOnFinished(e2 -> {
                                    int valorAtual1 = Integer.parseInt(destino.getText());
                                    int valorAtual0 = Integer.parseInt(origem.getText());
                                    destino.setText(String.valueOf(valorAtual1 + valorAtual0));
                                    pane.getChildren().removeAll(linha, seta);
                                });
                                pause.play();
                            });

                            try {
                                Thread.sleep(1800);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }

                        // Etapa 3: Construir vetor resultante com pausa e destaque
                        CountDownLatch latchMoveBaixo = new CountDownLatch(tl);

                        for (int l = tl - 1; l >= 0; l--) {
                            int finalL = l;
                            int finalI1 = i;

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
                                int index = (aux / (int) Math.pow(10, finalI1)) % 10;

                                if (index < 0 || index > 9) {
                                    latchMoveBaixo.countDown();
                                    continue;
                                }

                                Label labelDestino = radix_lb[index];

                                Platform.runLater(() -> {
                                    // Deixar label em negrito
                                    labelDestino.setStyle("-fx-font-weight: bold;");

                                    PauseTransition pause1 = new PauseTransition(Duration.seconds(1));
                                    pause1.setOnFinished(ev1 -> {
                                        int destino = Integer.parseInt(labelDestino.getText()) - 1;
                                        labelDestino.setText(String.valueOf(destino));

                                        // Voltar ao normal
                                        labelDestino.setStyle("-fx-font-weight: normal;");

                                        if (destino >= 0 && destino < tl) {
                                            vetRes[destino] = botao;
                                            move_botao_para_resultante_com_latch(botao, destino, latchMoveBaixo);
                                        } else {
                                            latchMoveBaixo.countDown();
                                        }
                                    });

                                    pause1.play();
                                });

                                Thread.sleep(1200); // Espera o botão ser processado antes de continuar com o próximo

                            } catch (Exception ex) {
                                ex.printStackTrace();
                                latchMoveBaixo.countDown();
                            }
                        }

                        try {
                            latchMoveBaixo.await(); // Espera todos descerem
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }


                        // Etapa 4: Subir e resetar
                        CountDownLatch latchMoveCima = new CountDownLatch(tl + 1); // +1 pro reset

                        for (int k = 0; k < tl; k++) {
                            int finalK = k;
                            vet[k] = vetRes[k];
                            move_botao_para_cima_com_latch(vet[finalK], finalK, latchMoveCima);
                        }

                        Platform.runLater(() -> {
                            for (int l = 0; l < 10; l++) {
                                radix_lb[l].setText("0");
                            }
                            latchMoveCima.countDown();
                        });

                        try {
                            latchMoveCima.await(); // Espera todos subirem
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
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

                // Movimento vertical
                double atualY = button.getLayoutY();
                while (atualY < destinoY) {
                    double finalAtualY = atualY;
                    Platform.runLater(() -> button.setLayoutY(finalAtualY + 5));
                    atualY += 5;
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Movimento horizontal
                double atualX = button.getLayoutX();
                if (destinoX > atualX) {
                    while (atualX < destinoX) {
                        double finalAtualX = atualX;
                        Platform.runLater(() -> button.setLayoutX(finalAtualX + 5));
                        atualX += 5;
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    while (atualX > destinoX) {
                        double finalAtualX = atualX;
                        Platform.runLater(() -> button.setLayoutX(finalAtualX - 5));
                        atualX -= 5;
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                latch.countDown(); // Marca que terminou
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
                        Thread.sleep(20);
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
                        Thread.sleep(20);
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
                    Thread.sleep(20);
                }

                // Movimentação horizontal
                while (Math.abs(xAtual - destinoX) > 1) {
                    double finalX = xAtual + Math.signum(destinoX - xAtual) * 5;
                    xAtual = finalX;

                    double finalXAtual = xAtual;
                    Platform.runLater(() -> button.setLayoutX(finalXAtual));
                    Thread.sleep(20);
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
                        Thread.sleep(20);
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
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                latch.countDown(); // Marca finalização
                return null;
            }
        };
        new Thread(task).start();
    }

}
