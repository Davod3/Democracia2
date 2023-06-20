package democracia2.javafx.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import democracia2.javafx.models.Vote;
import democracia2.javafx.models.VoteTypes;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VoteService {

  private HttpClient client = null;

  private static final String API = "http://localhost:8080/api";

  public VoteService() {

    this.client = HttpClient.newHttpClient();
  }

  public ObservableList<Vote> getActiveVotes() {

    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API + "/votacoes")).build();

    List<Vote> voteList =
        this.client
            .sendAsync(request, BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenApply(this::parseResponseBody)
            .join();

    ObservableList<Vote> observableVoteList =
        FXCollections.observableArrayList(
            vote -> new Observable[] {vote.idProperty(), vote.nameProperty()});

    observableVoteList.setAll(voteList);

    return observableVoteList;
  }

  private List<Vote> parseResponseBody(String body) {

    List<Vote> result = new ArrayList<>();

    Gson gson = new Gson();

    Type t = new TypeToken<ArrayList<VoteDTO>>() {}.getType();

    List<VoteDTO> voteDTOS = gson.fromJson(body, t);

    for (VoteDTO v : voteDTOS) {

      result.add(new Vote(v.id));
    }

    return result;
  }

  public String getDefaultVote(String currentNumCC, String currentVoteID) {

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(API + "/votacao/" + currentVoteID + "/defaultVote/" + currentNumCC))
            .build();

    int result =
        this.client
            .sendAsync(request, BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenApply(
                body -> {
                  return Integer.parseInt(body);
                })
            .join();

    if (result == 1) {
      return VoteTypes.A_FAVOR.toString();
    } else if (result == 0) {
      return VoteTypes.CONTRA.toString();
    } else {
      return VoteTypes.NENHUM.toString();
    }
  }

  public int vote(String currentNumCC, String currentVoteID, int voteType) {

    System.out.println(
        "Voting on vote " + currentVoteID + " as " + currentNumCC + " with value " + voteType);

    HashMap<String, String> values = new HashMap<String, String>();

    values.put("voto", Integer.toString(voteType));

    Gson gson = new Gson();

    String requestBody = gson.toJson(values);

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(API + "/votacao/" + currentVoteID + "/vote/" + currentNumCC))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .header("Content-Type", "application/json")
            .build();

    int result =
        this.client
            .sendAsync(request, BodyHandlers.ofString())
            .thenApply(HttpResponse::statusCode)
            .join();

    return result;
  }
}
