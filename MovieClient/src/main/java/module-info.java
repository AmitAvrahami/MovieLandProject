module org.example.movieclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;


    opens org.example.movieclient to javafx.fxml;
    opens org.example.movieclient.model.movie to com.google.gson;
    opens org.example.movieclient.model.user to com.google.gson;
    opens org.example.movieclient.client to com.google.gson;
    opens org.example.movieclient.model.actor to com.google.gson;
    exports org.example.movieclient;
    exports org.example.movieclient.UI;
    exports org.example.movieclient.model.movie;
    exports org.example.movieclient.model.user;
    exports org.example.movieclient.UI.Login;
    exports org.example.movieclient.UI.Register;
    exports org.example.movieclient.UI.GenreSelection;
    exports org.example.movieclient.UI.MainMovie;
    exports org.example.movieclient.UI.MovieDetails;
    exports org.example.movieclient.UI.MovieUploader;
    exports org.example.movieclient.UI.SearchForMovie;
    exports org.example.movieclient.UI.WatchList;

}