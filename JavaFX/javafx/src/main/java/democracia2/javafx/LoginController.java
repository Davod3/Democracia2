package democracia2.javafx;

import democracia2.javafx.models.DataModel;
import democracia2.javafx.models.Person;
import democracia2.javafx.services.PersonService;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {

  private DataModel model;

  @FXML private Button submitBtn;

  @FXML private TextField numCC;

  @FXML
  void submit(ActionEvent event) {

    try {

      String content = numCC.getText();

      if (!content.equals("")) {

        PersonService ps = new PersonService();
        Person p = ps.getPerson(content);

        if (p != null) {

          this.model.setCurrentUser(p);

          System.out.println("Logging in CC " + this.model.getCurrentUser().getNumCC());

          App.showMain();

        } else {

          App.launchAlert("Utilizador não existente!");
        }

      } else {

        App.launchAlert("Por favor insira um número CC válido!");
      }

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
  }
}
