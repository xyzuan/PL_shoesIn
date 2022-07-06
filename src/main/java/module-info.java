module id.xyzprjkt.modul6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens id.xyzprjkt.shoesin to javafx.fxml;
    exports id.xyzprjkt.shoesin;
}