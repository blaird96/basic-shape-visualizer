module edu.umgc.cmsc315.project4 {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.umgc.cmsc315.project4 to javafx.fxml;
    exports edu.umgc.cmsc315.project4;
}
