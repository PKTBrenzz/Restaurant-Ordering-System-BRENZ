package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class AddItemController {
    @FXML
    TextField field1;
    @FXML
    TextField field2;

    public void clear() {
        field1.setText(null);
        field2.setText(null);
    }

    public void save() throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure this item want to add to the food list?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            FileWriter fw = null;
            try
            {
                fw = new FileWriter("foodlist.txt",true);
                fw.write(field1.getText());
                fw.write(",");
                fw.write(field2.getText());
                fw.write("\r\n");
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                if (fw!=null)
                {
                    fw.close();
                }
            }
        }
        else {

        }
    }
}
