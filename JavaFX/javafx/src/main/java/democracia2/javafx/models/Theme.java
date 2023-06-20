package democracia2.javafx.models;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Theme {

  private final LongProperty id = new SimpleLongProperty();

  private final StringProperty name = new SimpleStringProperty();

  public final LongProperty idProperty() {
    return this.id;
  }

  public final Long getId() {
    return this.idProperty().get();
  }

  public final void setId(Long id) {
    this.idProperty().set(id);
  }

  public final StringProperty nameProperty() {
    return this.name;
  }

  public final String getName() {
    return nameProperty().getValue();
  }

  public final void setName(String name) {
    nameProperty().setValue(name);
  }

  public Theme(Long id, String name) {
    setId(id);
    setName(name);
  }

  @Override
  public String toString() {
    return nameProperty().getValue();
  }
}
