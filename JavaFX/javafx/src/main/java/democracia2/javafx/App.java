package democracia2.javafx;

import democracia2.javafx.models.*;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/** JavaFX App */
public class App extends Application {

  private static Scene scene;

  private static DataModel model = new DataModel();

  private static BorderPane root;

  private static Stage currentStage;

  private static HostServices hs;

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("LoginTemplate.fxml"));

    hs = getHostServices();

    scene = new Scene(fxmlLoader.load(), 640, 480);

    LoginController lc = fxmlLoader.getController();
    lc.initModel(model);

    currentStage = stage;
    stage.setScene(scene);
    stage.show();
  }

  static void showMain() throws IOException {
    FXMLLoader mainLoader = new FXMLLoader(App.class.getResource("main.fxml"));

    root = mainLoader.load();
    scene.setRoot(root);

    MainController mc = mainLoader.getController();
    mc.initModel(model);

    FXMLLoader UseCaseLoader = new FXMLLoader(App.class.getResource("UseCasesTemplate.fxml"));

    TilePane useCaseBtnContainer = UseCaseLoader.load();
    root.setCenter(useCaseBtnContainer);

    UseCaseController ucc = UseCaseLoader.getController();
    ucc.initModel(model);
  }

  static void showLogin() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("LoginTemplate.fxml"));

    scene.setRoot(fxmlLoader.load());

    LoginController lc = fxmlLoader.getController();
    lc.initModel(model);
  }

  static void showVoteList() throws IOException {

    FXMLLoader voteListLoader = new FXMLLoader(App.class.getResource("VoteListTemplate.fxml"));

    root.setCenter(voteListLoader.load());

    VoteListController vlc = voteListLoader.getController();
    vlc.initModel(model);
  }

  static void chooseDelegate() throws IOException {

    FXMLLoader chooseDelegateLoader =
        new FXMLLoader(App.class.getResource("ChooseDelegateTemplate.fxml"));

    root.setCenter(chooseDelegateLoader.load());

    ChooseDelegateController cdc = chooseDelegateLoader.getController();
    cdc.initModel(model);
  }

  static void showCreateProject() throws IOException {

    FXMLLoader createProjectLoader =
        new FXMLLoader(App.class.getResource("CreateProjectTemplate.fxml"));

    root.setCenter(createProjectLoader.load());

    CreateProjectController cpc = createProjectLoader.getController();
    cpc.initModel(model);
  }

  static void showVoteDetail() throws IOException {

    FXMLLoader voteDetailLoader = new FXMLLoader(App.class.getResource("VoteDetailTemplate.fxml"));

    root.setCenter(voteDetailLoader.load());

    VoteDetailController vdc = voteDetailLoader.getController();
    vdc.initModel(model);
  }

  static void showProjectList() throws IOException {

    FXMLLoader projectListLoader =
        new FXMLLoader(App.class.getResource("ProjectListTemplate.fxml"));

    root.setCenter(projectListLoader.load());

    ProjectListController plc = projectListLoader.getController();
    plc.initModel(model);
  }

  static void showProjectDetail() throws IOException {

    FXMLLoader projectDetailLoader =
        new FXMLLoader(App.class.getResource("ProjectDetailTemplate.fxml"));

    root.setCenter(projectDetailLoader.load());

    ProjectDetailController pdc = projectDetailLoader.getController();
    pdc.initModel(model);
  }

  static void launchAlert(String content) {

    Alert a = new Alert(AlertType.ERROR);
    a.setContentText(content);
    a.show();
  }

  static boolean launchConfirmation(String content) {

    Alert a = new Alert(AlertType.CONFIRMATION);
    a.setContentText(content);

    Optional<ButtonType> result = a.showAndWait();

    return result.get() == ButtonType.OK;
  }

  static void launchInformation(String content) {
    Alert a = new Alert(AlertType.INFORMATION);
    a.setContentText(content);

    a.show();
  }

  static Stage getStage() {
    return currentStage;
  }

  static HostServices getHS() {
    return hs;
  }

  public static void main(String[] args) {
    launch();
  }
}
