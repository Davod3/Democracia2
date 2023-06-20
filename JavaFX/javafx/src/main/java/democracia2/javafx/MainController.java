package democracia2.javafx;

import democracia2.javafx.models.DataModel;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {

  private DataModel model;

  @FXML private Label userCC;

  @FXML
  void logout(ActionEvent event) {

    System.out.println("Logging out CC " + this.model.getCurrentUser().getNumCC());
    try {
      App.showLogin();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void initModel(DataModel model) {

    if (this.model != null) {
      throw new IllegalStateException("Model can only be initialized once");
    }

    this.model = model;
    this.userCC.setText(model.getCurrentUser().getName());
  }
}
