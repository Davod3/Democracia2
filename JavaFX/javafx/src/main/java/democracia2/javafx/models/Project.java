package democracia2.javafx.models;

import java.io.File;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Project {

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

  private final StringProperty title = new SimpleStringProperty();

  public final StringProperty titleProperty() {
    return this.title;
  }

  public final String getTitle() {
    return this.titleProperty().get();
  }

  public final void setTitle(String title) {
    this.titleProperty().set(title);
  }

  private String description;

  public String getDescription() {
    return this.description;
  }

  private String theme;

  public String getTheme() {
    return this.theme;
  }

  private String endDate;

  public String getEndDate() {
    return this.endDate;
  }

  private String creatorName;

  public String getCreatorName() {
    return this.creatorName;
  }

  private File pdf;

  public File getPdf() {
    return this.pdf;
  }

  public Project(
      Long id,
      String title,
      String description,
      String theme,
      String endDate,
      String creatorName,
      File pdf) {

    setId(id);
    setTitle(title);
    this.description = description;
    this.theme = theme;
    this.endDate = endDate;
    this.creatorName = creatorName;
    this.pdf = pdf;
  }

  @Override
  public String toString() {
    return getTitle();
  }
}
