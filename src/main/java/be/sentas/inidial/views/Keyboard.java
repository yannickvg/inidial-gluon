package be.sentas.inidial.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;

/**
 * Created by yannick on 11/08/16.
 */
public class Keyboard extends VBox {

    @FXML
    private HBox firstRow;

    @FXML
    private HBox secondRow;

    @FXML
    private HBox thirdRow;

    @FXML
    private HBox dummyRow;

    public Keyboard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "keyboard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        init();
    }

    private void init() {

        addSmallButton(firstRow, "Q");
        addSmallButton(firstRow, "W");
        addSmallButton(firstRow, "E");
        addSmallButton(firstRow, "R");
        addSmallButton(firstRow, "T");
        addSmallButton(firstRow, "Y");
        addSmallButton(firstRow, "U");
        addSmallButton(firstRow, "I");
        addSmallButton(firstRow, "O");
        addSmallButton(firstRow, "P");

        addSmallButton(secondRow, "A");
        addSmallButton(secondRow, "S");
        addSmallButton(secondRow, "D");
        addSmallButton(secondRow, "F");
        addSmallButton(secondRow, "G");
        addSmallButton(secondRow, "H");
        addSmallButton(secondRow, "J");
        addSmallButton(secondRow, "K");
        addSmallButton(secondRow, "L");

        addSmallButton(thirdRow, "Z");
        addSmallButton(thirdRow, "X");
        addSmallButton(thirdRow, "C");
        addSmallButton(thirdRow, "V");
        addSmallButton(thirdRow, "B");
        addSmallButton(thirdRow, "N");
        addSmallButton(thirdRow, "M");
    }

    private void addSmallButton(HBox row, String letter) {
        Button button = new Button(letter);
        button.setStyle("-fx-min-width: 0; -fx-font-family: Monaco");
        HBox.setHgrow(button, Priority.ALWAYS);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setPrefHeight(40);
        row.getChildren().add(button);
        button.setDisable(true);
    }

}
