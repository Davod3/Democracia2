package democracia2.javafx.models;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataModel {

  private final ObjectProperty<Person> currentUser = new SimpleObjectProperty<>();

  public final Person getCurrentUser() {
    return this.currentUser.getValue();
  }

  public ObjectProperty<Person> getCurrentUserProperty() {
    return this.currentUser;
  }

  public final void setCurrentUser(Person p) {
    this.currentUser.setValue(p);
  }

  public ObservableList<Theme> getMockThemeList() {

    ObservableList<Theme> themeList =
        FXCollections.observableArrayList(
            theme -> new Observable[] {theme.idProperty(), theme.nameProperty()});

    // Poulate list

    // Mock
    themeList.setAll(
        new Theme((long) 1, "Saúde"),
        new Theme((long) 2, "Educação"),
        new Theme((long) 3, "Justiça"));

    return themeList;
  }

  public ObservableList<Person> getMockDelegateList() {

    ObservableList<Person> personList =
        FXCollections.observableArrayList(person -> new Observable[] {person.nameProperty()});

    // Poulate list

    // Mock
    personList.setAll(
        new Person("André", "12345", true),
        new Person("David", "123456", true),
        new Person("Miguel", "1234567", true));

    return personList;
  }

  private final ObjectProperty<Vote> currentVote = new SimpleObjectProperty<>();

  public final ObjectProperty<Vote> currentVoteProperty() {
    return this.currentVote;
  }

  public final Vote getCurrentVote() {
    return currentVoteProperty().getValue();
  }

  public final void setCurrentVote(Vote v) {
    currentVoteProperty().setValue(v);

    System.out.println("Current vote: " + v.getName());
  }

  private final ObjectProperty<Project> currentProject = new SimpleObjectProperty<>();

  public final ObjectProperty<Project> currentProjectProperty() {
    return this.currentProject;
  }

  public final Project getCurrentProject() {
    return currentProjectProperty().getValue();
  }

  public final void setCurrentProject(Project p) {
    currentProjectProperty().setValue(p);

    System.out.println("Current project: " + p.getTitle());
  }
}
