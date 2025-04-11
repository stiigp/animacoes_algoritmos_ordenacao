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
        private AnchorPane pane;
    
    @Override
    public void start(Stage stage) throws Exception {

        // Criando a barra de menus
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Opções");
        MenuItem menuItemRadix = new MenuItem("Radix Sort");

        // Ação para chamar o método bucket() quando o item de menu for selecionado


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

    

    public static void main(String[] args) {
        launch(args);
    }
}