package id.xyzprjkt.shoesin;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Objects;

@SuppressWarnings({"rawtypes", "unchecked"})
public class MainActivity extends Application {

    final String appName = "shoesIn.";
    final String css = Objects.requireNonNull(this.getClass().getResource("css/main.css")).toExternalForm();
    private final ObservableList<shoesDB> data = FXCollections.observableArrayList();
    final HBox hBox = new HBox();

    public void start (Stage stage){
        stage.setTitle(appName);
        String name = "Jody Yuantoro";

        Label mainBtn = new Label("Login");
        mainBtn.setOnMouseClicked(e -> stage.setScene(mainActivity(name)));
        stage.setScene(landingPage(mainBtn));
        stage.setResizable(false);
        stage.show();
    }
    public Scene landingPage(Label backButton) {

        GridPane homeContainer = new GridPane();
        homeContainer.setId("home-container");
        Scene homePage = new Scene(homeContainer, 1280, 720);

        homePage.getStylesheets().add(css);

        GridPane introContainer = new GridPane();
        introContainer.setId("intro-container");
        Text introTitle = new Text("Welcome to\n" + appName);
        introTitle.setId("intro-title");
        GridPane btnContainer = new GridPane();

        backButton.setId("landing-button");
        btnContainer.add(backButton, 0, 0);

        introContainer.add(introTitle, 0, 0);
        introContainer.add(btnContainer, 0, 2);

        HBox imageContainer = new HBox();
        imageContainer.setId("img-container");
        homeContainer.add(introContainer ,0, 0);
        homeContainer.add(imageContainer, 1, 0);

        return homePage;
    }

    public Scene mainActivity(String name) {

        // Main View
        GridPane mainViewContainer = new GridPane();
        mainViewContainer.setId("main-container");
        Scene mainActivity = new Scene(mainViewContainer, 1280, 720);
        mainActivity.getStylesheets().add(css);

        // Header View
        GridPane headerViewContainer = new GridPane();
        headerViewContainer.setId("header-container");
        final Label titleHeader = new Label(appName);
        titleHeader.setId("header");
        headerViewContainer.add(titleHeader,0,0);

        // Table View
        GridPane tableViewContainer = new GridPane();
        tableViewContainer.setId("table-container");

        TableView table = new TableView();
        table.setId("table-view");
        table.prefWidthProperty().bind(tableViewContainer.widthProperty());
        table.prefHeightProperty().bind(tableViewContainer. heightProperty());

        table.setEditable(true);

        final TableColumn noColumn = new TableColumn("No.");
        final TableColumn productNameColumn = new TableColumn("Nama Produk");
        final TableColumn productCodeColumn = new TableColumn("No Produk");
        final TableColumn productPriceCol = new TableColumn("Harga");
        final TableColumn productQTYCol = new TableColumn("QTY");

        noColumn.setMinWidth(50); noColumn.setMaxWidth(50);
        productNameColumn.setMinWidth(310); productNameColumn.setMaxWidth(310);
        productCodeColumn.setMinWidth(100); productCodeColumn.setMaxWidth(100);
        productPriceCol.setMinWidth(100); productPriceCol.setMaxWidth(100);
        productQTYCol.setMinWidth(222); productQTYCol.setMaxWidth(222);

        noColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<shoesDB, String>, ObservableValue>) p -> p.getValue().noProperty());
        productNameColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<shoesDB, String>, ObservableValue>) p -> p.getValue().productNameProperty());
        productCodeColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<shoesDB, String>, ObservableValue>) p -> p.getValue().productCodeProperty());
        productPriceCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<shoesDB, String>, ObservableValue>) p -> p.getValue().productPriceProperty());
        productQTYCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<shoesDB, String>, ObservableValue>) p -> p.getValue().productQTYProperty());

        table.getColumns().addAll(noColumn, productNameColumn, productCodeColumn, productPriceCol, productQTYCol);
        table.setItems(data);

        // Input View
        GridPane inputViewContainer = new GridPane();
        inputViewContainer.setId("input-container");

        final TextField inputNo = new TextField();
        final TextField inputProductName = new TextField();
        final TextField inputProductCode = new TextField();
        final TextField inputProductPrice = new TextField();
        final TextField inputProductQTY = new TextField();

        // Dosen Field
        final Text inputNoTitle = new Text("No"); inputNoTitle.setId("input-field-text");
        inputNo.setPromptText("Masukkan No.");
        inputNo.setId("input-field");

        final Text inputProductNameTitle = new Text("Nama Produk"); inputProductNameTitle.setId("input-field-text");
        inputProductName.setPromptText("Masukkan Nama Produk");
        inputProductName.setId("input-field");

        final Text inputProductCodeTitle = new Text("No Produk"); inputProductCodeTitle.setId("input-field-text");
        inputProductCode.setPromptText("Masukkan No Produk");
        inputProductCode.setId("input-field");
        inputProductCode.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                inputProductCode.setText(newValue.replaceAll("\\D", ""));
            }
        });

        final Text inputProductPriceTitle = new Text("Harga"); inputProductPriceTitle.setId("input-field-text");
        inputProductPrice.setPromptText("Masukkan Harga");
        inputProductPrice.setId("input-field");
        inputProductPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                inputProductPrice.setText(newValue.replaceAll("\\D", ""));
            }
        });

        final Text inputProductQTYTitle = new Text("QTY"); inputProductQTYTitle.setId("input-field-text");
        inputProductQTY.setPromptText("Masukkan Jumlah Produk");
        inputProductQTY.setId("input-field");

        final Button buttonAdd = new Button("Add");
        buttonAdd.setId("button");
        buttonAdd.setOnAction((ActionEvent e) -> {
            if ( inputNo.getText().isEmpty() || inputProductName.getText().isEmpty() || inputProductCode.getText().isEmpty() || inputProductPrice.getText().isEmpty() || inputProductQTY.getText().isEmpty()){
                Alert errorDialog = new Alert(Alert.AlertType.ERROR, "Data Yang Di Input Harus Tidak Ada Yang Boleh Kosong");
                errorDialog.showAndWait();
            } else {
                data.add(new shoesDB(
                        inputNo.getText(),
                        inputProductName.getText(),
                        inputProductCode.getText(),
                        inputProductPrice.getText(),
                        inputProductQTY.getText()));
                clearTextField(inputNo, inputProductName, inputProductCode, inputProductPrice, inputProductQTY);
            }
        });

        Button buttonUpdate = new Button("Update");
        buttonUpdate.setId("button");
        buttonUpdate.setOnAction((ActionEvent e) -> {

            int selectedIndex = table.getSelectionModel().getSelectedIndex();

            if ( inputNo.getText().isEmpty() || inputProductName.getText().isEmpty() || inputProductCode.getText().isEmpty() || inputProductPrice.getText().isEmpty() || inputProductQTY.getText().isEmpty()){
                Alert errorDialog = new Alert(Alert.AlertType.ERROR, "Data Yang Di Input Harus Tidak Ada Yang Boleh Kosong");
                errorDialog.showAndWait();
            } else {
                data.set(selectedIndex, new shoesDB(
                        inputNo.getText(),
                        inputProductName.getText(),
                        inputProductCode.getText(),
                        inputProductPrice.getText(),
                        inputProductQTY.getText()));
                clearTextField(inputNo, inputProductName, inputProductCode, inputProductPrice, inputProductQTY);
            }
        });

        final Button buttonDelete = new Button("Delete");
        buttonDelete.setId("button");
        buttonDelete.setOnAction(actionEvent -> table.getItems().removeAll(table.getSelectionModel().getSelectedItem()));

        hBox.getChildren().addAll(
                inputNo,inputProductName,inputProductCode,inputProductPrice,inputProductQTY,
                buttonAdd,buttonUpdate,buttonDelete);
        hBox.setSpacing(10);

        final VBox vBox = new VBox();
        vBox.setSpacing(18);
        vBox.setPadding(new Insets(15,15,15,15));
        vBox.getChildren().addAll(titleHeader,table,hBox);

        // Container Controller
        headerViewContainer.add(titleHeader, 0 ,0);

        tableViewContainer.add(table, 0, 0);

        inputViewContainer.add(inputNoTitle,0,0);
        inputViewContainer.add(inputNo, 0,1);

        inputViewContainer.add(inputProductNameTitle,0,2);
        inputViewContainer.add(inputProductName,0,3);

        inputViewContainer.add(inputProductPriceTitle,0,4);
        inputViewContainer.add(inputProductPrice,0,5);

        inputViewContainer.add(inputProductCodeTitle,0,6);
        inputViewContainer.add(inputProductCode,0,7);

        inputViewContainer.add(inputProductQTYTitle,0,8);
        inputViewContainer.add(inputProductQTY,0,9);

        inputViewContainer.add(buttonAdd,0,10);
        inputViewContainer.add(buttonUpdate,0,11);
        inputViewContainer.add(buttonDelete,0,12);

        mainViewContainer.add(headerViewContainer,0,0);
        mainViewContainer.add(tableViewContainer, 0,1);
        mainViewContainer.add(inputViewContainer,1,1);

        return mainActivity;
    }

    private void clearTextField(TextField no, TextField productName, TextField productCode, TextField productPrice, TextField productQTY) {
        no.clear();
        productName.clear();
        productCode.clear();
        productPrice.clear();
        productQTY.clear();
    }

    public static class shoesDB {

        final private SimpleStringProperty no, productName, productCode, productPrice, productQTY;
        private shoesDB(String no, String productName, String productCode, String productPrice, String productQTY){
            this.no = new SimpleStringProperty(no);
            this.productName = new SimpleStringProperty(productName);
            this.productCode = new SimpleStringProperty(productCode);
            this.productPrice = new SimpleStringProperty(productPrice);
            this.productQTY = new SimpleStringProperty(productQTY);
        }
        public SimpleStringProperty noProperty() {
            return no;
        }
        public SimpleStringProperty productNameProperty() {
            return productName;
        }
        public SimpleStringProperty productCodeProperty() {
            return productCode;
        }
        public SimpleStringProperty productPriceProperty() {
            return productPrice;
        }
        public SimpleStringProperty productQTYProperty() {
            return productQTY;
        }
    }
}