package democracia2.javafx;

import democracia2.javafx.models.DataModel;
import democracia2.javafx.models.Project;
import democracia2.javafx.services.ProjectService;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ProjectDetailController {

  @FXML private Label proponent;

  @FXML private Label closeDate;

  @FXML private Label documentName;

  @FXML private Button supportBtn;

  @FXML private Label description;

  @FXML private Label theme;

  @FXML private Label title;

  private DataModel model;

  @FXML
  void supportProject(ActionEvent event) throws IOException {

    ProjectService ps = new ProjectService();

    if (App.launchConfirmation("Apoiar este projeto?")) {

      if (ps.support(model.getCurrentProject().getId(), model.getCurrentUser().getNumCC())) {

        App.launchInformation("Apoio registado! Apenas pode apoiar cada projeto uma vez.");
        App.showMain();

      } else {

        App.launchAlert(
            "Não foi possível completar a operação. Apenas pode apoiar cada projeto uma vez.");
      }
    }
  }

  @FXML
  void viewDocument(ActionEvent event) {

    App.getHS().showDocument(this.model.getCurrentProject().getPdf().getAbsolutePath());
  }

  public void initModel(DataModel model) {

    if (this.model != null) {
      throw new IllegalStateException("Model can only be initialized once");
    }

    this.model = model;

    // Get current Project
    Project p = this.model.getCurrentProject();

    // Set title
    this.title.setText(p.getTitle());

    // Set theme
    this.theme.setText(p.getTheme());

    // Set description
    this.description.setText(p.getDescription());

    // Set end date
    this.closeDate.setText(p.getEndDate());

    // Set creator
    this.proponent.setText(p.getCreatorName());

    // Set document
    this.documentName.setText(p.getPdf().getName());
  }

  @FXML
  void goBack(ActionEvent event) throws IOException {

    App.showProjectList();
  }
}
