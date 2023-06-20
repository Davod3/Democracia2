package democracia2.javafx.services;

import com.google.gson.Gson;
import democracia2.javafx.models.Person;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class PersonService {

  private HttpClient client = null;

  private static final String API = "http://localhost:8080/api";

  public PersonService() {

    this.client = HttpClient.newHttpClient();
  }

  public Person getPerson(String numCC) {

    HttpRequest request =
        HttpRequest.newBuilder().uri(URI.create(API + "/obterCidadao/" + numCC)).build();

    Person result =
        this.client
            .sendAsync(request, BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenApply(this::parseResponseBody)
            .join();

    return result;
  }

  private Person parseResponseBody(String body) {

    if (body.equals("")) {
      return null;
    }

    Gson gson = new Gson();

    PersonDTO p = gson.fromJson(body, PersonDTO.class);

    return new Person(p.name, p.numCC, p.cidadao);
  }
}
