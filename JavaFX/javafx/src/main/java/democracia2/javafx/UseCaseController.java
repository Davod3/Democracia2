package democracia2.javafx;

import democracia2.javafx.models.DataModel;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UseCaseController {

  private DataModel model;

  @FXML private Button EscolherDelegado;

  @FXML private Button ApresentarProjLei;

  @FXML private Button ConsultarVotacoes;

  @FXML private Button ConsultarProjsLei;

  @FXML
  void consultarVotacoes(ActionEvent event) throws IOException {

    System.out.println("Consulting votes as user " + model.getCurrentUser().getNumCC());

    App.showVoteList();
  }

  @FXML
  void consultarProjsLei(ActionEvent event) throws IOException {

    System.out.println("Consulting Law Projects as user " + model.getCurrentUser().getNumCC());

    App.showProjectList();
  }

  @FXML
  void escolherDelegado(ActionEvent event) throws IOException {

    System.out.println("Choosing delegate as user " + model.getCurrentUser().getNumCC());

    App.chooseDelegate();
  }

  @FXML
  void apresentarProjLei(ActionEvent event) throws IOException {

    System.out.println("Presenting law project as user " + model.getCurrentUser().getNumCC());

    App.showCreateProject();
  }

  public void initModel(DataModel model) {

    if (this.model != null) {
      throw new IllegalStateException("Model can only be initialized once");
    }

    this.model = model;

    if (this.model.getCurrentUser().isCidadao()) {

      this.ApresentarProjLei.disableProperty().set(true);
    }
  }
}
