package democracia2.javafx;

import democracia2.javafx.models.DataModel;
import democracia2.javafx.models.Vote;
import democracia2.javafx.services.VoteService;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class VoteListController {

  @FXML private ListView<Vote> voteList;

  @FXML private Button viewBtn;

  private DataModel model;

  @FXML
  void viewVote(ActionEvent event) throws IOException {

    Vote current = model.getCurrentVote();

    if (current != null) {

      App.showVoteDetail();

    } else {

      App.launchAlert("Please select an active vote to view!");
    }
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

    // Get votes from model
    VoteService vs = new VoteService();

    ObservableList<Vote> votes = vs.getActiveVotes();

    // Set votes as items in ListView
    this.voteList.setItems(votes);

    voteList
        .getSelectionModel()
        .selectedItemProperty()
        .addListener((obs, oldSelection, newSelection) -> model.setCurrentVote(newSelection));
  }
}
