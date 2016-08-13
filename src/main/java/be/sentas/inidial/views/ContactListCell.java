package be.sentas.inidial.views;

import be.sentas.inidial.model.Contact;
import com.gluonhq.charm.glisten.control.CharmListCell;
import com.gluonhq.charm.glisten.control.ListTile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by yannick on 11/08/16.
 */
public class ContactListCell extends CharmListCell<Contact> {

    private final ListTile tile;
    private final ImageView imageView;

    public ContactListCell() {
        this.tile = new ListTile();
        imageView = new ImageView();
        tile.setPrimaryGraphic(imageView);
        setText(null);
    }

    @Override
    public void updateItem(Contact item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            tile.textProperty().setAll(item.getFullName(), item.getInitials());
            /*final Image image = new Image("picture.jpg");
            if (image != null) {
                imageView.setImage(image);
            }*/
            setGraphic(tile);
        } else {
            setGraphic(null);
        }
    }

}
