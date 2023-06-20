package democracia2.javafx.models;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Vote {

  private final LongProperty id = new SimpleLongProperty();

  public final LongProperty idProperty() {
    return this.id;
  }

  public final Long getId() {
    return this.idProperty().get();
  }

  public final void setId(Long id) {
    this.idProperty().set(id);
  }

  private final StringProperty name = new SimpleStringProperty();

  public final StringProperty nameProperty() {
    return this.name;
  }

  public final String getName() {
    return this.nameProperty().get();
  }

  public final void setName(String name) {
    this.nameProperty().set(name);
  }

  @Override
  public String toString() {
    return getName();
  }

  public Vote(Long id) {

    setId(id);
    setName("Votação " + id);
  }
}
