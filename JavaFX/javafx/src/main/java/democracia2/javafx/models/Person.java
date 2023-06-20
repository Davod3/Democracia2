package democracia2.javafx.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {

  private final StringProperty name = new SimpleStringProperty();

  private final StringProperty numCC = new SimpleStringProperty();

  private final BooleanProperty isCidadao = new SimpleBooleanProperty();

  public final StringProperty nameProperty() {
    return this.name;
  }

  public final String getName() {
    return nameProperty().getValue();
  }

  public final void setName(String name) {
    nameProperty().setValue(name);
  }

  public final StringProperty numCCProperty() {
    return this.numCC;
  }

  public final String getNumCC() {
    return numCCProperty().getValue();
  }

  public final void setNumCC(String numCC) {
    numCCProperty().setValue(numCC);
  }

  public final BooleanProperty isCidadaoProperty() {
    return this.isCidadao;
  }

  public final boolean isCidadao() {
    return isCidadaoProperty().getValue();
  }

  public final void setDelegateStatus(boolean isDelegate) {
    isCidadaoProperty().set(isDelegate);
  }

  public Person(String name, String numCC, boolean isDelegate) {

    setName(name);
    setNumCC(numCC);
    setDelegateStatus(isDelegate);
  }

  @Override
  public String toString() {
    return nameProperty().getValue();
  }
}
