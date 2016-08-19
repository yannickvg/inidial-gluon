package be.sentas.inidial.views;

import be.sentas.inidial.model.Contact;
import be.sentas.inidial.model.NameDirection;
import be.sentas.inidial.model.Phone;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.CharmListCell;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by yannick on 11/08/16.
 */
public class PhoneListCell extends CharmListCell<Phone> {

    private final VBox container;
    private final Label number;
    private final Label type;

    private OnInteractionListener listener;

    public PhoneListCell(OnInteractionListener listener) {
        container = new VBox();
        number = new Label();
        type = new Label();
        container.getChildren().addAll(type, number);
        this.listener = listener;
    }


    @Override
    public void updateItem(Phone item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            container.setAlignment(Pos.CENTER_LEFT);
            type.setText(item.getType().name());
            type.getStyleClass().add("phoneListType");
            type.setPadding(new Insets(0,16,0,16));
            number.setText(item.getNumber());
            number.setPadding(new Insets(0,16,0,16));
            number.getStyleClass().add("phoneListNumber");
            setGraphic(container);
            if (this.listener != null) {
                container.setOnMouseClicked(event -> listener.onItemClicked(item));
            }
        } else {
            setGraphic(null);
        }
    }

    public interface OnInteractionListener {
        void onItemClicked(Phone item);
    }

}
