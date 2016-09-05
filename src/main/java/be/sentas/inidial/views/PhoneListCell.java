package be.sentas.inidial.views;

import be.sentas.inidial.model.Phone;
import be.sentas.inidial.model.PhoneType;
import com.gluonhq.charm.glisten.control.CharmListCell;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PhoneListCell extends CharmListCell<Phone> {

    private HBox hContainer;
    private final VBox vContainer;
    private final Label number;
    private final Label type;
    private Button textButton;

    private OnInteractionListener listener;

    public PhoneListCell(OnInteractionListener listener) {
        vContainer = new VBox();
        number = new Label();
        type = new Label();
        vContainer.getChildren().addAll(type, number);
        this.listener = listener;
    }


    @Override
    public void updateItem(Phone item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            vContainer.setAlignment(Pos.CENTER_LEFT);
            type.setText(item.getType().name());
            type.getStyleClass().add("phoneListType");
            type.setPadding(new Insets(0,16,0,16));
            number.setText(item.getNumber());
            number.setPadding(new Insets(0,16,0,16));
            number.getStyleClass().add("phoneListNumber");
            if (item.getType() == PhoneType.MOBILE) {
                textButton = MaterialDesignIcon.TEXTSMS.button(e -> listener.onTextNumber(item));
                textButton.setStyle("-fx-background-color: #2284c5");
                hContainer = new HBox();
                vContainer.setMaxWidth(Double.MAX_VALUE);
                HBox.setHgrow(vContainer, Priority.ALWAYS);
                HBox.setMargin(textButton, new Insets(0, 8, 0, 8));
                hContainer.getChildren().addAll(vContainer, textButton);
                setGraphic(hContainer);
            } else {
                setGraphic(vContainer);
            }
            if (this.listener != null) {
                vContainer.setOnMouseClicked(event -> listener.onCallNumber(item));
            }
        } else {
            setGraphic(null);
        }
    }

    public interface OnInteractionListener {
        void onCallNumber(Phone item);
        void onTextNumber(Phone item);
    }

}
