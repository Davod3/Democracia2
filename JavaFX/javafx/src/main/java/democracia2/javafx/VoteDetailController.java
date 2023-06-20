package democracia2.javafx;

import democracia2.javafx.models.DataModel;
import democracia2.javafx.models.Vote;
import democracia2.javafx.models.VoteTypes;
import democracia2.javafx.services.VoteService;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class VoteDetailController {

  @FXML private RadioButton radioBtnTrue;

  @FXML private RadioButton radioBtnFalse;

  @FXML private Label title;

  @FXML private Label defaultVote;

  private DataModel model;

  @FXML
  void vote(ActionEvent event) throws IOException {

    int voteType;

    if (radioBtnTrue.isSelected()) {
      // Positive
      voteType = 1;
    } else if (radioBtnFalse.isSelected()) {
      // Negative
      voteType = 0;
    } else {

      if (this.defaultVote.getText().equals(VoteTypes.NENHUM.toString())) {

        App.launchAlert(
            "Nenhuma opção de voto selecionada. \n Nenhum voto delegado detectado. \n "
                + "Por favor selecione uma opção ou escolha um delegado para o seu voto!");

        voteType = -1;

      } else if (this.defaultVote.getText().equals(VoteTypes.A_FAVOR.toString())) {

        voteType =
            App.launchConfirmation(
                    "Nenhuma opção de voto selecionada! A usar voto delegado: "
                        + VoteTypes.A_FAVOR.toString()
                        + "\n Continuar?")
                ? 1
                : -1;

      } else {

        voteType =
            App.launchConfirmation(
                    "Nenhuma opção de voto selecionada! A usar voto delegado: "
                        + VoteTypes.CONTRA.toString()
                        + "\n Continuar?")
                ? 0
                : -1;
      }
    }

    if (voteType != -1) {

      VoteService vs = new VoteService();
      int result =
          vs.vote(
              this.model.getCurrentUser().getNumCC(),
              this.model.getCurrentVote().getId().toString(),
              voteType);

      if (result == 200) {
        App.launchInformation("Voto registado com sucesso! Não é possível votar novamente.");
        App.showMain();
      } else {
        App.launchAlert("Registo de voto falhou. Não é possível votar duas vezes!");
      }
    }
  }

  @FXML
  void uncheckFalse(ActionEvent event) {

    if (this.radioBtnFalse.isSelected()) {
      this.radioBtnFalse.setSelected(false);
    }
  }

  @FXML
  void uncheckTrue(ActionEvent event) {

    if (this.radioBtnTrue.isSelected()) {
      this.radioBtnTrue.setSelected(false);
    }
  }

  @FXML
  void goBack(ActionEvent event) throws IOException {
    App.showVoteList();
  }

  public void initModel(DataModel model) {

    if (this.model != null) {
      throw new IllegalStateException("Model can only be initialized once");
    }

    this.model = model;

    VoteService vs = new VoteService();

    // Get current Vote
    Vote v = this.model.getCurrentVote();

    // Set label to Vote name
    this.title.setText(v.getName());

    this.defaultVote.setText(
        vs.getDefaultVote(this.model.getCurrentUser().getNumCC(), v.getId().toString()));

    // Just for testing
    // this.defaultVote.setText(VoteTypes.A_FAVOR.toString());
  }
}
