package democracia2.javafx;

import democracia2.javafx.models.DataModel;
import democracia2.javafx.models.Project;
import democracia2.javafx.services.ProjectService;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ProjectListController {

  @FXML private Button viewBtn;

  @FXML private ListView<Project> projectList;

  private DataModel model;

  @FXML
  void viewProject(ActionEvent event) throws IOException {

    Project current = model.getCurrentProject();

    if (current != null) {

      App.showProjectDetail();

    } else {

      App.launchAlert("Please select an open project to view!");
    }
  }

  public void initModel(DataModel model) {

    if (this.model != null) {
      throw new IllegalStateException("Model can only be initialized once");
    }

    this.model = model;

    // Get projects from service
    ProjectService ps = new ProjectService();

    ObservableList<Project> projects = ps.getOpenProjects();

    // Set projects as items in ListView
    this.projectList.setItems(projects);

    projectList
        .getSelectionModel()
        .selectedItemProperty()
        .addListener((obs, oldSelection, newSelection) -> model.setCurrentProject(newSelection));
  }

  @FXML
  void goBack(ActionEvent event) throws IOException {
    App.showMain();
  }
}
