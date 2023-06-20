package democracia2.javafx.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import democracia2.javafx.models.Project;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectService {

  private HttpClient client = null;

  private static final String API = "http://localhost:8080/api";

  public ProjectService() {

    this.client = HttpClient.newHttpClient();
  }

  public ObservableList<Project> getOpenProjects() {

    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API + "/projetosLei")).build();

    List<Project> projectList =
        this.client
            .sendAsync(request, BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenApply(this::parseResponseBody)
            .join();

    ObservableList<Project> observableProjectList =
        FXCollections.observableArrayList(
            project -> new Observable[] {project.idProperty(), project.titleProperty()});

    observableProjectList.setAll(projectList);

    return observableProjectList;
  }

  public boolean support(Long projectID, String numCC) {

    System.out.println("Supporting project " + projectID + " as " + numCC);

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(API + "/projetosLei/" + projectID + "/apoiar/" + numCC))
            .POST(HttpRequest.BodyPublishers.ofString(""))
            .header("Content-Type", "application/json")
            .build();
    int result =
        this.client
            .sendAsync(request, BodyHandlers.ofString())
            .thenApply(HttpResponse::statusCode)
            .join();

    return result == 200;
  }

  private List<Project> parseResponseBody(String body) {

    List<Project> result = new ArrayList<>();

    Gson gson = new Gson();

    Type t = new TypeToken<ArrayList<ProjectDTO>>() {}.getType();

    List<ProjectDTO> projects = gson.fromJson(body, t);

    for (ProjectDTO p : projects) {

      result.add(
          new Project(
              p.id,
              p.titulo,
              p.descricao,
              p.tema,
              p.dataValidade,
              p.delegadoProponente,
              createPDF(p.pdf)));
    }

    return result;
  }

  private File createPDF(FileDTO pdf) {

    byte[] decoded = Base64.getDecoder().decode(pdf.fileData);

    File localPDF = new File("data/" + pdf.filename);
    File parent;

    if ((parent = localPDF.getParentFile()) != null) {
      parent.mkdirs();
    }

    try {

      FileOutputStream fout = new FileOutputStream(localPDF);
      fout.write(decoded);
      fout.close();

    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return localPDF;
  }
}
