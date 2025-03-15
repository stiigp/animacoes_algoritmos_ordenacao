module com.example.animacoes_algoritmos_ordenacao {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.animacoes_algoritmos_ordenacao to javafx.fxml;
    exports com.example.animacoes_algoritmos_ordenacao;
}