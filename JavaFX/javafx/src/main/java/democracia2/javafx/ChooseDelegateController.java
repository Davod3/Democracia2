package democracia2.javafx;

import democracia2.javafx.models.DataModel;
import democracia2.javafx.models.Person;
import democracia2.javafx.models.Theme;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class ChooseDelegateController {

  @FXML private ComboBox<Person> delegateMenu;

  @FXML private Button backBtn;

  @FXML private ComboBox<Theme> themeMenu;

  private DataModel model;

  @FXML
  void choose(ActionEvent event) throws IOException {

    App.launchInformation("Delegado selecionado com sucesso!");
    App.showMain();
  }

  @FXML
  void goBack(ActionEvent event) throws IOException {

    App.showMain();
  }

  public void initModel(DataModel model) {

    if (this.model != null) {
      throw new IllegalStateException("Model can only be initialized once");
    }

    this.model = model;

    this.delegateMenu.setItems(this.model.getMockDelegateList());

    this.themeMenu.setItems(this.model.getMockThemeList());
  }
}
