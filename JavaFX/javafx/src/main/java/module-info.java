module democracia2.javafx {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.net.http;
  requires com.google.gson;

  opens democracia2.javafx to
      javafx.fxml;

  exports democracia2.javafx;
  exports democracia2.javafx.services;
}
