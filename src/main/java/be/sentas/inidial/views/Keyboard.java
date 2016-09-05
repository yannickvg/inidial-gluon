package be.sentas.inidial.views;

import be.sentas.inidial.model.KeyboardConfig;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Keyboard extends VBox {

    @FXML
    private HBox firstRow;

    @FXML
    private HBox secondRow;

    @FXML
    private HBox thirdRow;

    private OnInteractionListener listener;

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
    }

    public void load(KeyboardConfig config) {
        clear();
        for (KeyboardConfig.Key key : config.getFirstRow().getKeys()) {
            addSmallButton(firstRow, key);
        }
        for (KeyboardConfig.Key key : config.getSecondRow().getKeys()) {
            addSmallButton(secondRow, key);
        }
        for (KeyboardConfig.Key key : config.getThirdRow().getKeys()) {
            addSmallButton(thirdRow, key);
        }
    }

    private void clear() {
        firstRow.getChildren().clear();
        secondRow.getChildren().clear();
        thirdRow.getChildren().clear();
    }

    private void addSmallButton(HBox row, KeyboardConfig.Key key) {
        Button button = new Button(key.getSymbol());
        button.setStyle("-fx-min-width: 0; -fx-font-family: Monaco; -fx-font-size:11");
        HBox.setHgrow(button, Priority.ALWAYS);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setPrefHeight(40);
        button.setDisable(!key.isEnabled());
        button.setOnAction(event -> listener.onKeyPressed(key.getSymbol()));
        button.setPadding(Insets.EMPTY);
        row.getChildren().add(button);
    }

    public void setListener(OnInteractionListener listener) {
        this.listener = listener;
    }

    public interface OnInteractionListener {
        void onKeyPressed(String symbol);
    }
}
