package be.sentas.inidial.views;

import be.sentas.inidial.model.Contact;
import be.sentas.inidial.model.NameDirection;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.CharmListCell;
import com.gluonhq.charm.glisten.control.ListTile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Created by yannick on 11/08/16.
 */
public class ContactListCell extends CharmListCell<Contact> {

    private final HBox container;
    private final Avatar avatar;
    private final Label name;

    private final NameDirection direction;

    private OnInteractionListener listener;

    public ContactListCell(NameDirection direction, OnInteractionListener listener) {
        this.direction = direction;
        container = new HBox();
        avatar = new Avatar();
        name = new Label();
        container.getChildren().addAll(avatar, name);
        this.listener = listener;
    }


    @Override
    public void updateItem(Contact item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            name.setText(item.getDisplayName(direction));
            final Image image = new Image("picture.jpg");
            if (image != null) {
                avatar.setImage(image);
            }
            container.setAlignment(Pos.CENTER_LEFT);
            name.setPadding(new Insets(0,16,0,16));
            setGraphic(container);
            if (this.listener != null) {
                container.setOnMouseClicked(event -> listener.onItemClicked(item));
            }
        } else {
            setGraphic(null);
        }
    }

    public interface OnInteractionListener {
        void onItemClicked(Contact item);
    }

}
