package democracia2.javafx;

import democracia2.javafx.models.DataModel;
import democracia2.javafx.models.Theme;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class CreateProjectController {

  @FXML private ComboBox<Theme> themeList;

  @FXML private DatePicker endDate;

  @FXML private TextArea description;

  @FXML private TextField title;

  @FXML private Label fileName;

  private DataModel model;

  @FXML
  void submit(ActionEvent event) throws IOException {

    if (this.description.getText().equals("")) {
      App.launchAlert("Insira uma descrição!");
      return;
    }

    if (this.title.getText().equals("")) {
      App.launchAlert("Insira um título!");
      return;
    }

    if (this.fileName.getText().equals("Nenhum ficheiro selecionado")) {
      App.launchAlert("Por favor selecione um ficheiro!");
      return;
    }

    if (this.themeList.getSelectionModel().isEmpty()) {
      App.launchAlert("Por favor selecione um tema!");
      return;
    }

    if (this.endDate.getValue() == null) {
      App.launchAlert("Por favor selecione uma data de fim!");
      return;
    }

    App.launchInformation("Projeto de Lei criado com sucesso!");

    App.showMain();
  }

  @FXML
  void goBack(ActionEvent event) throws IOException {

    App.showMain();
  }

  @FXML
  void chooseFile(ActionEvent event) {

    FileChooser fileChooser = new FileChooser();

    File selectedFile = fileChooser.showOpenDialog(App.getStage());

    this.fileName.setText(selectedFile.getName());
  }

  public void initModel(DataModel model) {

    if (this.model != null) {
      throw new IllegalStateException("Model can only be initialized once");
    }

    this.model = model;

    this.themeList.setItems(this.model.getMockThemeList());

    /*
     * Since date to be chosen must be future, disable all past dates
     * Snippet taken from:
     * https://stackoverflow.com/questions/48238855/how-to-disable-past-dates-in-datepicker-of-javafx-scene-builder
     *
     * The code below changes the way each day cell is created. Now the date is checked to see if it is before or
     * after current date. If it is before, cell shows as disabled.
     */

    this.endDate.setDayCellFactory(
        picker ->
            new DateCell() {

              public void updateItem(LocalDate date, boolean empty) {

                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
              }
            });
  }
}
