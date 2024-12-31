import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Gui extends Application {
    private Employee employee;

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    BinaryTree<String> binaryTree = new BinaryTree<String>();
    SetArr<Employee> employeesArray = new SetArr<Employee>();

    public void setBinaryTree(BinaryTree<String> binaryTree) {
        this.binaryTree = binaryTree;
    }

    public void setEmployeesArray(SetArr<Employee> employeesArray) {
        this.employeesArray = employeesArray;
    }

    public BinaryTree<String> getBinaryTreeG() {
        return binaryTree;
    }

    public SetArr<Employee> getEmployeesArrayG() {
        return employeesArray;
    }

    public void start(Stage primaryStage) throws Exception {

        // Font
        Font font = Font.font("yugothic", FontWeight.BOLD, FontPosture.ITALIC, 10);
        Font font2 = Font.font("Monospaced", FontWeight.BOLD, FontPosture.REGULAR, 30);
        Font font3 = Font.font("Monospaced", FontWeight.BOLD, FontPosture.REGULAR, 60);
        Font font5 = Font.font("yugothic", FontWeight.BOLD, FontPosture.ITALIC, 15);
        Font font6 = Font.font("yugothic", FontWeight.BOLD, FontPosture.ITALIC, 23);
        Color rgb = Color.rgb(65, 126, 192);
        BackgroundFill background = new BackgroundFill(rgb, null, null);

        // Boxes
        HBox hb = new HBox();
        VBox vbTop = new VBox();
        VBox vbBottom = new VBox();
        VBox vb = new VBox();
        vb.setBackground(new Background(background));

        // menuBlocker on start so you can't use menu before loading file
        StackPane stackP = new StackPane();
        Pane menuBlocker = new Pane();
        menuBlocker.setMinSize(140, 530);
        menuBlocker.setMaxSize(140, 530);
        menuBlocker.setTranslateX(0);
        menuBlocker.setTranslateY(-20);
        menuBlocker.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        ComboBox<String> employDataBox = new ComboBox<String>();

        // Scrolling Pane
        Pane scrollBarPane = new Pane();
        ScrollPane scrollBar = new ScrollPane(scrollBarPane);
        scrollBar.setMaxSize(1100, 600);
        scrollBar.setMinSize(1100, 600);
        scrollBar.setFitToWidth(true);

        // This is the help on hover for some guidence
        Button help = new Button("?");
        help.setTextFill(Color.FIREBRICK);
        help.setMinSize(30, 30);
        help.setMaxSize(30, 30);
        help.setLayoutX(1050);
        help.setLayoutY(10);

        Pane helpPane = new Pane();
        ScrollPane helpScrollBar = new ScrollPane(helpPane);
        helpScrollBar.setMaxSize(300, 200);
        helpScrollBar.setMinSize(300, 200);
        helpScrollBar.setLayoutX(800);
        helpScrollBar.setLayoutY(0);

        // This is the menu border
        Pane blackBorder = new Pane();
        blackBorder.setMaxSize(303, 203);
        blackBorder.setMinSize(303, 203);
        blackBorder.setStyle("-fx-background-color: black;");
        blackBorder.setLayoutX(797);
        blackBorder.setLayoutY(0);

        // This is the scroll pane for the help menu
        helpScrollBar.setFitToWidth(true);
        Label commands = new Label();
        commands.setText(
                "Welcome to the Employee Database! \n\n"
                        +
                        "To Insert An Employee Press Insert Button\n" +
                        "To Delete An Employee Press Delete Button\n" +
                        "To Search For an Employee Press Search Button\n" +
                        "To Display All Employees Press Display All Button\n" +
                        "To Save And Exit Press Save And Exit Button\n" +
                        "To Exit Without Saving Press Quit Button\n\n" +
                        "To Add Another Data File Press Merge Button\n" +
                        "While In Seach use The Top Right Drop Box For Data\n" +
                        "Make Sure Searches Are Formated Correctly\n\n" +
                        "Thank You For Using The Employee Database!\n");
        helpPane.getChildren().add(commands);
        BackgroundFill background2 = new BackgroundFill(Color.LIGHTBLUE, null, null);
        helpPane.setBackground(new Background(background2));

        // When entering the help menu it appears
        helpScrollBar.setVisible(false);
        help.setOnMouseEntered(e -> {
            scrollBarPane.getChildren().addAll(blackBorder, helpScrollBar);
            helpScrollBar.setVisible(true);
            helpPane.toFront();

        });

        // on mouse exit for the helpscrollbar remove the helpPane
        helpScrollBar.setOnMouseExited(e -> {
            helpScrollBar.setVisible(false);
            scrollBarPane.getChildren().removeAll(blackBorder, helpScrollBar);
        });

        // Blue Background
        Image image1 = new Image("blue2.jpg", 2000, 2000, false, false);
        BackgroundImage bg1 = new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background firstScreen = new Background(bg1);

        // Upload Png
        Image image2 = new Image("fileUpload.png", 200, 200, false, false);
        BackgroundImage bg2 = new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background uploadBg = new Background(bg2);

        // Arrow Png
        Image image3 = new Image("arrow1.png", 120, 100, false, false);
        BackgroundImage bg3 = new BackgroundImage(image3, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background arrowBg = new Background(bg3);

        // Image Panes
        Pane blueStart = new Pane();
        blueStart.setMinSize(2000, 2000);
        blueStart.setMaxSize(2000, 2000);
        blueStart.setBackground(firstScreen);

        Pane upload = new Pane();
        upload.setMinSize(200, 200);
        upload.setMaxSize(200, 200);
        upload.translateXProperty().set(450);
        upload.translateYProperty().set(200);
        upload.setBackground(uploadBg);

        Pane arrow = new Pane();
        arrow.setMinSize(150, 100);
        arrow.setMaxSize(150, 100);
        arrow.translateXProperty().set(920);
        arrow.translateYProperty().set(210);
        arrow.setBackground(arrowBg);

        // On Start
        employDataBox.setDisable(true);
        scrollBarPane.getChildren().addAll(blueStart, help, upload);
        Label dataBoxTitle = new Label("Employee Data");
        Label st = new Label("UPLOAD YOUR FILE HERE");
        st.setFont(font2);
        st.setTextFill(Color.WHITE);
        st.translateXProperty().set(350);
        st.translateYProperty().set(100);
        scrollBarPane.getChildren().add(st);
        Text sl = new Text("");
        sl.setFont(font);
        sl.setFill(rgb);
        scrollBarPane.getChildren().addAll(sl);

        //////////////////// Read Button ////////////////////
        Button readEmployDataBt = new Button("Read Input Data");
        readEmployDataBt.setStyle("-fx-text-fill: green");
        readEmployDataBt.setFont(font);
        readEmployDataBt.setMinSize(200, 200);
        readEmployDataBt.setMaxSize(200, 200);
        readEmployDataBt.translateXProperty().set(450);
        readEmployDataBt.translateYProperty().set(200);
        readEmployDataBt.setOpacity(0);

        EventHandler<ActionEvent> readInputData = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                scrollBarPane.getChildren().clear();
                scrollBarPane.getChildren().addAll(sl, blueStart, help);
                DataBase dataBase = new DataBase();
                setEmployeesArray(dataBase.getEmployeesArray());
                setBinaryTree(dataBase.getBinaryTree());
                System.out.println(employeesArray.toString());

                // timeline and animation for arrow to move a little left then right
                Timeline timeline = new Timeline();
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.setAutoReverse(true);
                KeyValue kv = new KeyValue(arrow.translateXProperty(), 850);
                KeyFrame kf = new KeyFrame(Duration.millis(800), kv);
                timeline.getKeyFrames().add(kf);
                timeline.play();

                // success label
                Label success = new Label("FILE UPLOADED SUCCESSFULLY");
                success.setFont(font3);
                success.setTextFill(Color.LIGHTGREEN);
                success.translateXProperty().set(300);
                success.translateYProperty().set(200);
                success.setMinSize(600, 200);
                success.setMaxSize(600, 200);
                scrollBarPane.getChildren().add(success);

                // timeline and animation for success label to fade out
                Timeline timeline3 = new Timeline();
                timeline3.setCycleCount(1);
                timeline3.setAutoReverse(true);
                KeyValue kv7 = new KeyValue(success.opacityProperty(), 0);
                KeyFrame kf8 = new KeyFrame(Duration.millis(3000), kv7);
                timeline3.getKeyFrames().add(kf8);
                timeline3.play();

                // Search Label
                Label search = new Label("CLICK HERE TO SEARCH");
                search.setFont(font2);
                search.setTextFill(Color.WHITE);
                search.translateXProperty().set(700);
                search.translateYProperty().set(190);

                // when success label reaches 0 opacity, add the search label and arrow pointer
                // and removes menu blocker for menu access
                timeline3.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        scrollBarPane.getChildren().addAll(search, arrow);
                        stackP.getChildren().removeAll(menuBlocker);
                    }
                });
            }
        };

        readEmployDataBt.setOnAction(readInputData);
        scrollBarPane.getChildren().add(readEmployDataBt);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        KeyValue kv = new KeyValue(upload.scaleXProperty(), 1.1);
        KeyValue kv2 = new KeyValue(upload.scaleYProperty(), 1.1);
        KeyValue kv3 = new KeyValue(readEmployDataBt.scaleXProperty(), 1.1);
        KeyValue kv4 = new KeyValue(readEmployDataBt.scaleYProperty(), 1.1);
        KeyFrame kf = new KeyFrame(Duration.millis(1000), kv, kv2, kv3, kv4);
        timeline.getKeyFrames().add(kf);

        Timeline timeline2 = new Timeline();
        timeline2.setCycleCount(Timeline.INDEFINITE);
        timeline2.setAutoReverse(true);
        KeyValue kv11 = new KeyValue(upload.translateYProperty(), 210);
        KeyFrame kf12 = new KeyFrame(Duration.millis(1000), kv11);
        KeyValue kv13 = new KeyValue(readEmployDataBt.translateYProperty(), 210);
        KeyFrame kf14 = new KeyFrame(Duration.millis(1000), kv13);
        timeline2.getKeyFrames().add(kf12);
        timeline2.getKeyFrames().add(kf14);
        if (readEmployDataBt.isHover() == false) {
            timeline2.play();
        }
        readEmployDataBt.setOnMouseEntered(e -> {
            upload.setScaleX(1.1);
            upload.setScaleY(1.1);
            readEmployDataBt.setScaleX(1.1);
            readEmployDataBt.setScaleY(1.1);
            st.setText("YEAH RIGHT THERE!");
            st.translateXProperty().set(400);
            st.translateYProperty().set(100);
            timeline.stop();
            timeline2.stop();
        });

        readEmployDataBt.setOnMouseExited(e -> {
            upload.setScaleX(1);
            upload.setScaleY(1);
            readEmployDataBt.setScaleX(1);
            readEmployDataBt.setScaleY(1);
            st.setText("NO NO, GO BACK! RIGHT HERE!");
            st.translateXProperty().set(340);
            st.translateYProperty().set(100);
            timeline.play();
        });

        stackP.setOnMouseClicked(e -> {
            Timeline shake = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(upload.translateXProperty(), 450)),
                    new KeyFrame(Duration.seconds(0.1), new KeyValue(upload.translateXProperty(), 460)),
                    new KeyFrame(Duration.seconds(0.2), new KeyValue(upload.translateXProperty(), 450)),
                    new KeyFrame(Duration.seconds(0.3), new KeyValue(upload.translateXProperty(), 460)),
                    new KeyFrame(Duration.seconds(0.4), new KeyValue(upload.translateXProperty(), 450)),
                    new KeyFrame(Duration.seconds(0.5), new KeyValue(upload.translateXProperty(), 460)),
                    new KeyFrame(Duration.seconds(0.6), new KeyValue(upload.translateXProperty(), 450)),
                    new KeyFrame(Duration.seconds(0.7), new KeyValue(upload.translateXProperty(), 460)),
                    new KeyFrame(Duration.seconds(0.8), new KeyValue(upload.translateXProperty(), 450)),
                    new KeyFrame(Duration.seconds(0.9), new KeyValue(upload.translateXProperty(), 460)),
                    new KeyFrame(Duration.seconds(1), new KeyValue(upload.translateXProperty(), 450)));
            st.setText("Don't be silly upload first");
            shake.play();
        });

        Timeline pulse = new Timeline();
        pulse.setCycleCount(Timeline.INDEFINITE);
        pulse.setAutoReverse(true);
        KeyValue kv005 = new KeyValue(dataBoxTitle.textFillProperty(), Color.GREEN);
        KeyFrame kf006 = new KeyFrame(Duration.millis(1000), kv005);
        KeyValue kv007 = new KeyValue(dataBoxTitle.textFillProperty(), Color.YELLOW);
        KeyFrame kf008 = new KeyFrame(Duration.millis(2000), kv007);
        KeyValue kv009 = new KeyValue(dataBoxTitle.textFillProperty(), Color.RED);
        KeyFrame kf010 = new KeyFrame(Duration.millis(3000), kv009);
        KeyValue kv011 = new KeyValue(dataBoxTitle.textFillProperty(), Color.BLUE);
        KeyFrame kf012 = new KeyFrame(Duration.millis(4000), kv011);
        pulse.getKeyFrames().add(kf006);
        pulse.getKeyFrames().add(kf008);
        pulse.getKeyFrames().add(kf010);
        pulse.getKeyFrames().add(kf012);

        //////////////////// SEARCH ////////////////////
        Button searchBt = new Button("Search");
        searchBt.setFont(font);
        searchBt.setMaxSize(120, 50);
        searchBt.setMinSize(120, 50);
        Label employeeFound = new Label("");
        Label employeeFound2 = new Label("Employee: ");
        EventHandler<ActionEvent> searchEmployees = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                employDataBox.setDisable(true);
                scrollBarPane.getChildren().clear();
                scrollBarPane.getChildren().addAll(sl, blueStart, help);
                Label searchLabel = new Label("Search:");
                searchLabel.setFont(font2);
                searchLabel.setTranslateX(200);
                searchLabel.setTranslateY(240);
                searchLabel.setTextFill(Color.WHITE);
                scrollBarPane.getChildren().add(searchLabel);

                TextField searchField2 = new TextField();
                searchField2.setPromptText("Enter Position Ex: Sales");
                searchField2.setTranslateX(200);
                searchField2.setTranslateY(300);
                searchField2.setMaxSize(200, 50);
                searchField2.setMinSize(200, 50);
                scrollBarPane.getChildren().add(searchField2);
                searchField2.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.ENTER) {
                            if (!searchField2.getText().isEmpty()) {
                                String idSearch2 = "";
                                String firstNameSearch2 = "";
                                String lastNameSearch2 = "";
                                String positionSearch2 = "";
                                String siteSearch2 = "";
                                for (int i = 0; i < employeesArray.size(); i++) {
                                    if (employeesArray.retreiveAtIndex(i).getPosition().equals(searchField2.getText())
                                            && employeesArray.retreiveAtIndex(i).getFired() == false) {
                                        idSearch2 += employeesArray.retreiveAtIndex(i).getEmployeeID() + "\n";
                                        firstNameSearch2 += employeesArray.retreiveAtIndex(i).getFirstName() + "\n";
                                        lastNameSearch2 += employeesArray.retreiveAtIndex(i).getLastName() + "\n";
                                        positionSearch2 += employeesArray.retreiveAtIndex(i).getPosition() + "\n";
                                        siteSearch2 += employeesArray.retreiveAtIndex(i).getSite() + "\n";
                                    }
                                }
                                Label idLabel2 = new Label("ID:");
                                idLabel2.setFont(font5);
                                idLabel2.setTranslateX(10);
                                idLabel2.setTranslateY(100);
                                idLabel2.setTextFill(Color.YELLOW);

                                Label firstNameLabel2 = new Label("First Name:");
                                firstNameLabel2.setFont(font5);
                                firstNameLabel2.setTranslateX(150);
                                firstNameLabel2.setTranslateY(100);
                                firstNameLabel2.setTextFill(Color.YELLOW);

                                Label lastNameLabel2 = new Label("Last Name:");
                                lastNameLabel2.setFont(font5);
                                lastNameLabel2.setTranslateX(300);
                                lastNameLabel2.setTranslateY(100);
                                lastNameLabel2.setTextFill(Color.YELLOW);

                                Label positionLabel2 = new Label("Position:");
                                positionLabel2.setFont(font5);
                                positionLabel2.setTranslateX(450);
                                positionLabel2.setTranslateY(100);
                                positionLabel2.setTextFill(Color.YELLOW);

                                Label siteLabel2 = new Label("Site:");
                                siteLabel2.setFont(font5);
                                siteLabel2.setTranslateX(600);
                                siteLabel2.setTranslateY(100);
                                siteLabel2.setTextFill(Color.YELLOW);

                                Label idLabelData2 = new Label(idSearch2);
                                idLabelData2.setFont(font5);
                                idLabelData2.setTranslateX(10);
                                idLabelData2.setTranslateY(150);
                                idLabelData2.setTextFill(Color.WHITE);

                                Label firstNameLabelData2 = new Label(firstNameSearch2);
                                firstNameLabelData2.setFont(font5);
                                firstNameLabelData2.setTranslateX(150);
                                firstNameLabelData2.setTranslateY(150);
                                firstNameLabelData2.setTextFill(Color.WHITE);

                                Label lastNameLabelData2 = new Label(lastNameSearch2);
                                lastNameLabelData2.setFont(font5);
                                lastNameLabelData2.setTranslateX(300);
                                lastNameLabelData2.setTranslateY(150);
                                lastNameLabelData2.setTextFill(Color.WHITE);

                                Label positionLabelData2 = new Label(positionSearch2);
                                positionLabelData2.setFont(font5);
                                positionLabelData2.setTranslateX(450);
                                positionLabelData2.setTranslateY(150);
                                positionLabelData2.setTextFill(Color.WHITE);

                                Label siteLabelData2 = new Label(siteSearch2);
                                siteLabelData2.setFont(font5);
                                siteLabelData2.setTranslateX(600);
                                siteLabelData2.setTranslateY(150);
                                siteLabelData2.setTextFill(Color.WHITE);
                                employeeFound.setText(searchField2.getText() + " Personnel");
                                employDataBox.setDisable(true);
                                scrollBarPane.getChildren().clear();
                                scrollBarPane.getChildren().addAll(sl, blueStart, help, idLabel2, firstNameLabel2,
                                        lastNameLabel2, positionLabel2, siteLabel2, idLabelData2, firstNameLabelData2,
                                        lastNameLabelData2, positionLabelData2, siteLabelData2);
                                if (idSearch2 == "") {
                                    searchBt.fire();
                                    Alert a = new Alert(Alert.AlertType.ERROR,
                                            "Invalid Position or Search Format", ButtonType.OK);
                                    a.showAndWait();

                                } else {
                                    employeeFound.setFont(font3);
                                    employeeFound.setTextFill(Color.LIGHTGREEN);
                                    employeeFound.setTranslateX(340);
                                    employeeFound.setTranslateY(10);
                                    employeeFound2.setFont(font3);
                                    employeeFound2.setTextFill(Color.WHITE);
                                    employeeFound2.setTranslateX(10);
                                    employeeFound2.setTranslateY(10);
                                    scrollBarPane.getChildren().addAll(employeeFound, employeeFound2);
                                }

                            }
                        }
                    }
                });

                TextField searchField3 = new TextField();
                searchField3.setPromptText("Enter Site Ex: Chicago");
                searchField3.setTranslateX(700);
                searchField3.setTranslateY(300);
                searchField3.setMaxSize(200, 50);
                searchField3.setMinSize(200, 50);
                scrollBarPane.getChildren().add(searchField3);
                searchField3.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.ENTER) {
                            if (!searchField3.getText().isEmpty()) {
                                String idSearch3 = "";
                                String firstNameSearch3 = "";
                                String lastNameSearch3 = "";
                                String positionSearch3 = "";
                                String siteSearch3 = "";
                                for (int i = 0; i < employeesArray.size(); i++) {
                                    if (employeesArray.retreiveAtIndex(i).getSite().equals(searchField3.getText())
                                            && employeesArray.retreiveAtIndex(i).getFired() == false) {

                                        idSearch3 += employeesArray.retreiveAtIndex(i).getEmployeeID() + "\n";
                                        firstNameSearch3 += employeesArray.retreiveAtIndex(i).getFirstName() + "\n";
                                        lastNameSearch3 += employeesArray.retreiveAtIndex(i).getLastName() + "\n";
                                        positionSearch3 += employeesArray.retreiveAtIndex(i).getPosition() + "\n";
                                        siteSearch3 += employeesArray.retreiveAtIndex(i).getSite() + "\n";
                                    }
                                }
                                Label idLabel3 = new Label("ID:");
                                idLabel3.setFont(font5);
                                idLabel3.setTranslateX(10);
                                idLabel3.setTranslateY(100);
                                idLabel3.setTextFill(Color.YELLOW);

                                Label firstNameLabel3 = new Label("First Name:");
                                firstNameLabel3.setFont(font5);
                                firstNameLabel3.setTranslateX(150);
                                firstNameLabel3.setTranslateY(100);
                                firstNameLabel3.setTextFill(Color.YELLOW);

                                Label lastNameLabel3 = new Label("Last Name:");
                                lastNameLabel3.setFont(font5);
                                lastNameLabel3.setTranslateX(300);
                                lastNameLabel3.setTranslateY(100);
                                lastNameLabel3.setTextFill(Color.YELLOW);

                                Label positionLabel3 = new Label("Position:");
                                positionLabel3.setFont(font5);
                                positionLabel3.setTranslateX(450);
                                positionLabel3.setTranslateY(100);
                                positionLabel3.setTextFill(Color.YELLOW);

                                Label siteLabel3 = new Label("Site:");
                                siteLabel3.setFont(font5);
                                siteLabel3.setTranslateX(600);
                                siteLabel3.setTranslateY(100);
                                siteLabel3.setTextFill(Color.YELLOW);

                                Label idLabelData3 = new Label(idSearch3);
                                idLabelData3.setFont(font5);
                                idLabelData3.setTranslateX(10);
                                idLabelData3.setTranslateY(150);
                                idLabelData3.setTextFill(Color.WHITE);

                                Label firstNameLabelData2 = new Label(firstNameSearch3);
                                firstNameLabelData2.setFont(font5);
                                firstNameLabelData2.setTranslateX(150);
                                firstNameLabelData2.setTranslateY(150);
                                firstNameLabelData2.setTextFill(Color.WHITE);

                                Label lastNameLabelData2 = new Label(lastNameSearch3);
                                lastNameLabelData2.setFont(font5);
                                lastNameLabelData2.setTranslateX(300);
                                lastNameLabelData2.setTranslateY(150);
                                lastNameLabelData2.setTextFill(Color.WHITE);

                                Label positionLabelData3 = new Label(positionSearch3);
                                positionLabelData3.setFont(font5);
                                positionLabelData3.setTranslateX(450);
                                positionLabelData3.setTranslateY(150);
                                positionLabelData3.setTextFill(Color.WHITE);

                                Label siteLabelData3 = new Label(siteSearch3);
                                siteLabelData3.setFont(font5);
                                siteLabelData3.setTranslateX(600);
                                siteLabelData3.setTranslateY(150);
                                siteLabelData3.setTextFill(Color.WHITE);
                                employeeFound.setText(searchField3.getText() + " Personnel");
                                employDataBox.setDisable(true);
                                scrollBarPane.getChildren().clear();
                                scrollBarPane.getChildren().addAll(sl, blueStart, help, idLabel3, firstNameLabel3,
                                        lastNameLabel3, positionLabel3, siteLabel3, idLabelData3, firstNameLabelData2,
                                        lastNameLabelData2, positionLabelData3, siteLabelData3);

                                if (idSearch3 == "") {
                                    searchBt.fire();
                                    Alert a = new Alert(Alert.AlertType.ERROR,
                                            "Invalid Position or Search Format", ButtonType.OK);
                                    a.showAndWait();

                                } else {
                                    employeeFound.setFont(font3);
                                    employeeFound.setTextFill(Color.LIGHTGREEN);
                                    employeeFound.setTranslateX(340);
                                    employeeFound.setTranslateY(10);
                                    employeeFound2.setFont(font3);
                                    employeeFound2.setTextFill(Color.WHITE);
                                    employeeFound2.setTranslateX(10);
                                    employeeFound2.setTranslateY(10);
                                    scrollBarPane.getChildren().addAll(employeeFound, employeeFound2);
                                }

                            }
                        }
                    }
                });

                TextField searchField = new TextField();
                searchField.setPromptText("Enter Employee ID Ex: A-ABCD-01");
                searchField.setTranslateX(450);
                searchField.setTranslateY(300);
                searchField.setMaxSize(200, 50);
                searchField.setMinSize(200, 50);
                scrollBarPane.getChildren().add(searchField);
                searchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        employDataBox.setDisable(false);
                        if (event.getCode() == KeyCode.ENTER) {
                            if (!searchField.getText().isEmpty()
                                    && searchField.getText().matches("[A-Z]{1}-[A-Z]{4}-[0-9]{2}")) {
                                if (binaryTree.contains(searchField.getText()) == true) {
                                    Employee emp = employeesArray
                                            .retreiveAtIndex(binaryTree.getRecordNum(searchField.getText()));
                                    System.out.println(emp.getFired());
                                    if (emp.getFired() == false) {
                                        pulse.play();
                                        setEmployee(emp);
                                        scrollBarPane.getChildren().clear();
                                        scrollBarPane.getChildren().addAll(sl, blueStart, help);
                                        employeeFound.setText(emp.getFirstName() + " " + emp.getLastName());
                                        employeeFound.setFont(font3);
                                        employeeFound.setTextFill(Color.LIGHTGREEN);
                                        employeeFound.setTranslateX(340);
                                        employeeFound.setTranslateY(10);
                                        employeeFound2.setFont(font3);
                                        employeeFound2.setTextFill(Color.WHITE);
                                        employeeFound2.setTranslateX(10);
                                        employeeFound2.setTranslateY(10);
                                        scrollBarPane.getChildren().addAll(employeeFound, employeeFound2);
                                    }
                                }

                                else {
                                    Alert a = new Alert(Alert.AlertType.ERROR,
                                            "404 Not Found",
                                            ButtonType.OK);
                                    a.showAndWait();
                                    searchField.clear();
                                }
                            } else {
                                Alert a = new Alert(Alert.AlertType.ERROR,
                                        "Employee Search Format Error",
                                        ButtonType.OK);
                                a.showAndWait();
                                // clear the text field
                                searchField.clear();
                            }
                        }

                    }
                });

                searchField.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        searchField.setOpacity(1);
                        searchField2.setOpacity(0.5);
                        searchField3.setOpacity(0.5);
                    }
                });
                searchField2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        searchField2.setOpacity(1);
                        searchField.setOpacity(0.5);
                        searchField3.setOpacity(0.5);
                    }
                });
                searchField3.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        searchField3.setOpacity(1);
                        searchField.setOpacity(0.5);
                        searchField2.setOpacity(0.5);
                    }
                });
            }
        };
        searchBt.setOnAction(searchEmployees);

        /////////////////////////// Quit Button ///////////////////////////
        Button quitBt = new Button("Quit");
        quitBt.setFont(font);
        quitBt.setMaxSize(120, 50);
        quitBt.setMinSize(120, 50);
        quitBt.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Do you really want to close this application?", ButtonType.YES,
                    ButtonType.NO);
            ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
            if (ButtonType.NO.equals(result)) {
                e.consume();
            } else {
                primaryStage.close();
            }
        });

        //////////////////// ComboBox ////////////////////
        Font font4 = Font.font("yugothic", FontWeight.BOLD, FontPosture.ITALIC, 17);
        dataBoxTitle.setFont(font4);
        dataBoxTitle.setTextFill(Color.LIGHTGREEN);

        Text item = new Text();
        item.setFont(font);
        employDataBox.setMaxSize(120, 50);
        employDataBox.setMinSize(120, 50);
        employDataBox.getItems().addAll("ID", "First Name", "Last Name", "Position", "Site");
        employDataBox.setOnAction(e -> {

            Label empData = new Label();
            empData.setFont(font2);
            empData.setTextFill(Color.WHITE);
            empData.setTranslateX(400);
            empData.setTranslateY(300);

            int itemSeleted = employDataBox.getSelectionModel().getSelectedIndex();
            switch (itemSeleted) {
                case 0:
                    item.setText("ID");
                    empData.setText("ID: " + getEmployee().getEmployeeID());
                    break;
                case 1:
                    item.setText("First Name");
                    empData.setText("First Name: " + getEmployee().getFirstName());
                    break;
                case 2:
                    item.setText("Last Name");
                    empData.setText("Last Name: " + getEmployee().getLastName());
                    break;
                case 3:
                    item.setText("Position");
                    empData.setText("Position: " + getEmployee().getPosition());
                    break;
                case 4:
                    item.setText("Site");
                    empData.setText("Site: " + getEmployee().getSite());
                    break;
            }
            pulse.stop();
            dataBoxTitle.setTextFill(Color.LIGHTGREEN);
            scrollBarPane.getChildren().clear();
            scrollBarPane.getChildren().addAll(sl, blueStart, help, employeeFound, empData, employeeFound2);
        });

        //////////////////// Display ALl ////////////////////
        Button DisplayAllBt = new Button(
                "Display All");
        DisplayAllBt.setFont(font);
        DisplayAllBt.setMaxSize(120, 50);
        DisplayAllBt.setMinSize(120, 50);
        EventHandler<ActionEvent> findPathAll = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                employDataBox.setDisable(true);
                Label employeeDataBase = new Label("Employee DataBase");
                employeeDataBase.setFont(font3);
                employeeDataBase.setTextFill(Color.LIGHTGREEN);
                employeeDataBase.setTranslateX(10);
                employeeDataBase.setTranslateY(10);

                Label chartLabel = new Label(
                        "Employee ID\t\tFirst Name\t\tLast Name\t\tPosition\t\tSite");
                chartLabel.setFont(font5);
                chartLabel.setTextFill(Color.YELLOW);
                chartLabel.setTranslateX(10);
                chartLabel.setTranslateY(80);

                Label idLabel = new Label("ID");
                String idStr = "";
                idLabel.setFont(font5);
                idLabel.setTextFill(Color.WHITE);
                idLabel.setTranslateX(10);
                idLabel.setTranslateY(150);

                Label lastNameLabel = new Label("Last Name");
                String lastNameStr = "";
                lastNameLabel.setFont(font5);
                lastNameLabel.setTextFill(Color.WHITE);
                lastNameLabel.setTranslateX(150);
                lastNameLabel.setTranslateY(150);

                Label firstNameLabel = new Label("First Name");
                String firstNameStr = "";
                firstNameLabel.setFont(font5);
                firstNameLabel.setTextFill(Color.WHITE);
                firstNameLabel.setTranslateX(300);
                firstNameLabel.setTranslateY(150);

                Label positionLabel = new Label("Position");
                String positionStr = "";
                positionLabel.setFont(font5);
                positionLabel.setTextFill(Color.WHITE);
                positionLabel.setTranslateX(450);
                positionLabel.setTranslateY(150);

                Label siteLabel = new Label("Site");
                String siteStr = "";
                siteLabel.setFont(font5);
                siteLabel.setTextFill(Color.WHITE);
                siteLabel.setTranslateX(600);
                siteLabel.setTranslateY(150);

                scrollBarPane.getChildren().clear();
                scrollBarPane.getChildren().addAll(sl, blueStart, help, employeeDataBase, chartLabel);

                for (int i = 0; i < employeesArray.size(); i++) {
                    // if the employee is not fired
                    if (employeesArray.retreiveAtIndex(i).getFired() == false) {
                        idStr += employeesArray.retreiveAtIndex(i).getEmployeeID() + "\n";
                        lastNameStr += employeesArray.retreiveAtIndex(i).getLastName() + "\n";
                        firstNameStr += employeesArray.retreiveAtIndex(i).getFirstName() + "\n";
                        positionStr += employeesArray.retreiveAtIndex(i).getPosition() + "\n";
                        siteStr += employeesArray.retreiveAtIndex(i).getSite() + "\n";
                    }
                }
                idLabel.setText(idStr);
                lastNameLabel.setText(lastNameStr);
                firstNameLabel.setText(firstNameStr);
                positionLabel.setText(positionStr);
                siteLabel.setText(siteStr);

                scrollBarPane.getChildren().addAll(idLabel, lastNameLabel, firstNameLabel, positionLabel, siteLabel);
            }
        };
        DisplayAllBt.setOnAction(findPathAll);

        //////////////////// Write ////////////////////
        Button writeDataBt = new Button(
                "Save and Exit");
        writeDataBt.setFont(font);
        writeDataBt.setMaxSize(120, 50);
        writeDataBt.setMinSize(120, 50);
        EventHandler<ActionEvent> write = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save File");
                File file = fileChooser.showSaveDialog(null);
                try (PrintWriter fOut = new PrintWriter(file)) {
                    for (int i = 0; i < employeesArray.size(); i++) {
                        if (employeesArray.retreiveAtIndex(i).getFired() == false) {
                            fOut.println(employeesArray.retreiveAtIndex(i).toPrinter());
                        }
                    }
                    fOut.close();
                } catch (FileNotFoundException ex) {
                    System.out.println("File not found");
                }
                primaryStage.close();
            }
        };
        writeDataBt.setOnAction(write);

        //////////////////// Delete////////////////////
        Button deleteBt = new Button(
                "Delete Employee");
        deleteBt.setFont(font);
        deleteBt.setMaxSize(120, 50);
        deleteBt.setMinSize(120, 50);
        EventHandler<ActionEvent> delete = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                System.out.println("delete called");
                TextInputDialog dialog = new TextInputDialog("Ex: A-ABCD-01");
                dialog.setTitle("Delete Employee");
                dialog.setHeaderText("Delete Employee");
                dialog.setContentText("Please enter the employee ID you wish to delete:");
                dialog.showAndWait();
                String result = dialog.getResult();
                if (binaryTree.contains(result)) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Are you sure you want to delete this employee?");
                    alert.setContentText("Are you sure you want to let them go?");
                    Optional<ButtonType> result2 = alert.showAndWait();
                    if (result2.get() == ButtonType.OK
                            && employeesArray.retreiveAtIndex(binaryTree.getRecordNum(result)).getFired() == false) {

                        employeesArray.retreiveAtIndex(binaryTree.getRecordNum(result)).setFired(true);
                        binaryTree.delete(result);

                        Label pipe = new Label("|");
                        pipe.setFont(font2);
                        pipe.setTextFill(Color.LIGHTGREEN);
                        pipe.setTranslateX(800);
                        pipe.setTranslateY(100);

                        Label pipe2 = new Label("|");
                        pipe2.setFont(font2);
                        pipe2.setTextFill(Color.LIGHTGREEN);
                        pipe2.setTranslateX(800);
                        pipe2.setTranslateY(200);

                        Label pipe3 = new Label("|");
                        pipe3.setFont(font2);
                        pipe3.setTextFill(Color.LIGHTGREEN);
                        pipe3.setTranslateX(800);
                        pipe3.setTranslateY(300);

                        Label cM1 = new Label("\u2705");
                        cM1.setFont(font2);
                        cM1.setTextFill(Color.LIGHTGREEN);
                        cM1.setTranslateX(800);
                        cM1.setTranslateY(100);

                        Label cM2 = new Label("\u2705");
                        cM2.setFont(font2);
                        cM2.setTextFill(Color.LIGHTGREEN);
                        cM2.setTranslateX(800);
                        cM2.setTranslateY(200);

                        Label cM3 = new Label("\u2705");
                        cM3.setFont(font2);
                        cM3.setTextFill(Color.LIGHTGREEN);
                        cM3.setTranslateX(800);
                        cM3.setTranslateY(300);

                        Timeline spin = new Timeline();
                        spin.getKeyFrames()
                                .add(new KeyFrame(Duration.seconds(2), new KeyValue(pipe.rotateProperty(), 360)));

                        Timeline spin2 = new Timeline();
                        spin2.getKeyFrames()
                                .add(new KeyFrame(Duration.seconds(2), new KeyValue(pipe2.rotateProperty(), 360)));

                        Timeline spin3 = new Timeline();
                        spin3.getKeyFrames()
                                .add(new KeyFrame(Duration.seconds(2), new KeyValue(pipe3.rotateProperty(), 360)));

                        Label employeeFired = new Label("Calling Security To Escort Employee Out Of The Building");
                        employeeFired.setFont(font6);
                        employeeFired.setTextFill(Color.RED);
                        employeeFired.setTranslateX(10);
                        employeeFired.setTranslateY(100);

                        Label securityAlerted = new Label("Security Alerted And Are On Their Way");
                        securityAlerted.setFont(font6);
                        securityAlerted.setTextFill(Color.YELLOW);
                        securityAlerted.setTranslateX(10);
                        securityAlerted.setTranslateY(200);

                        Label employeeThrownOut = new Label("Employee Thrown Out Of The Building, And Access Revoked");
                        employeeThrownOut.setFont(font6);
                        employeeThrownOut.setTextFill(Color.LIGHTGREEN);
                        employeeThrownOut.setTranslateX(10);
                        employeeThrownOut.setTranslateY(300);

                        spin.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                scrollBarPane.getChildren().clear();
                                scrollBarPane.getChildren().addAll(sl, blueStart, help, securityAlerted, employeeFired,
                                        cM1,
                                        pipe2);
                                spin2.play();
                            }
                        });

                        spin2.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                scrollBarPane.getChildren().clear();
                                scrollBarPane.getChildren().addAll(sl, blueStart, help, securityAlerted,
                                        employeeThrownOut,
                                        employeeFired, cM1, cM2, pipe3);
                                spin3.play();
                            }
                        });

                        Timeline dragDown = new Timeline();
                        dragDown.getKeyFrames()
                                .add(new KeyFrame(Duration.seconds(2), new KeyValue(cM1.translateYProperty(),
                                        700, Interpolator.EASE_BOTH)));
                        dragDown.getKeyFrames()
                                .add(new KeyFrame(Duration.seconds(2), new KeyValue(cM2.translateYProperty(),
                                        700, Interpolator.EASE_BOTH)));
                        dragDown.getKeyFrames()
                                .add(new KeyFrame(Duration.seconds(2), new KeyValue(cM3.translateYProperty(),
                                        700, Interpolator.EASE_BOTH)));
                        dragDown.getKeyFrames()
                                .add(new KeyFrame(Duration.seconds(2), new KeyValue(employeeFired.translateYProperty(),
                                        700, Interpolator.EASE_BOTH)));
                        dragDown.getKeyFrames()
                                .add(new KeyFrame(Duration.seconds(2),
                                        new KeyValue(securityAlerted.translateYProperty(),
                                                700, Interpolator.EASE_BOTH)));
                        dragDown.getKeyFrames()
                                .add(new KeyFrame(Duration.seconds(2),
                                        new KeyValue(employeeThrownOut.translateYProperty(),
                                                700, Interpolator.EASE_BOTH)));

                        Label decoy = new Label(" ");
                        Timeline decoyDisappear = new Timeline();
                        decoyDisappear.getKeyFrames()
                                .add(new KeyFrame(Duration.seconds(1), new KeyValue(decoy.opacityProperty(), 0)));

                        spin3.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                scrollBarPane.getChildren().clear();
                                scrollBarPane.getChildren().addAll(sl, blueStart, help, employeeFired, securityAlerted,
                                        employeeThrownOut, cM1, cM2, cM3);
                                decoyDisappear.play();
                            }
                        });

                        dragDown.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                searchBt.fire();
                            }
                        });

                        decoyDisappear.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                dragDown.play();
                            }
                        });
                        Label employeeDeleted = new Label(
                                "Would Not Want \nTo Be This Ex-Employee \nRight Now");
                        employeeDeleted.setFont(font3);
                        employeeDeleted.setTextFill(Color.LIGHTGREEN);
                        employeeDeleted.setTranslateX(100);
                        employeeDeleted.setTranslateY(200);
                        scrollBarPane.getChildren().clear();
                        scrollBarPane.getChildren().addAll(sl, blueStart, help, employeeDeleted);
                        FadeTransition fadeTransition = new FadeTransition(Duration.millis(4000), employeeDeleted);
                        fadeTransition.setFromValue(1.0);
                        fadeTransition.setToValue(0.0);
                        fadeTransition.play();
                        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                spin.play();
                                scrollBarPane.getChildren().clear();
                                scrollBarPane.getChildren().addAll(sl, blueStart, help, pipe, employeeFired);
                            }
                        });
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Error Employee Not Found",
                                ButtonType.OK);
                        a.showAndWait();
                    }

                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Error Employee Not Found",
                            ButtonType.OK);
                    a.showAndWait();
                }

            }

        };

        deleteBt.setOnAction(delete);

        //////////////////// Insert ////////////////////
        Button insertBt = new Button(
                "Insert New Employee");
        insertBt.setFont(font);
        insertBt.setMaxSize(120, 50);
        insertBt.setMinSize(120, 50);
        EventHandler<ActionEvent> insert = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                TextInputDialog newFirstName = new TextInputDialog("Ex: John");
                newFirstName.setTitle("Insert New Employee");
                newFirstName.setHeaderText("Insert New Employee");
                newFirstName.setContentText("Please enter the employee's first name:");
                newFirstName.showAndWait();
                String firstName = newFirstName.getResult();
                TextInputDialog newLastName = new TextInputDialog("Ex: Doe");
                newLastName.setTitle("Insert New Employee");
                newLastName.setHeaderText("Insert New Employee");
                newLastName.setContentText("Please enter the employee's last name:");
                newLastName.showAndWait();
                String lastName = newLastName.getResult();
                TextInputDialog newPosition = new TextInputDialog("Ex: Sales");
                newPosition.setTitle("Insert New Employee");
                newPosition.setHeaderText("Insert New Employee");
                newPosition.setContentText("Please enter the employee's position:");
                newPosition.showAndWait();
                String position = newPosition.getResult();
                TextInputDialog newSite = new TextInputDialog("Ex: Chicago");
                newSite.setTitle("Insert New Employee");
                newSite.setHeaderText("Insert New Employee");
                newSite.setContentText("Please enter the employee's site:");
                newSite.showAndWait();
                String site = newSite.getResult();
                String newID = site.substring(0, 1).toUpperCase() + "-" + lastName.substring(0, 3).toUpperCase()
                        + firstName.substring(0, 1).toUpperCase() + "-01";
                Employee newEmployee = new Employee(newID, firstName, lastName, position, site, false);
                employeesArray.add(newEmployee);
                newEmployee.setFirstName(firstName);
                newEmployee.setLastName(lastName);
                binaryTree.insert(newID);
                scrollBarPane.getChildren().clear();
                scrollBarPane.getChildren().addAll(sl, blueStart, help);

                Label r = new Label("R");
                r.setFont(font3);
                r.setTextFill(Color.RED);
                r.setTranslateX(10);
                r.setTranslateY(200);
                Label u = new Label("U");
                u.setFont(font3);
                u.setTextFill(Color.RED);
                u.setTranslateX(40);
                u.setTranslateY(200);
                Label n = new Label("N");
                n.setFont(font3);
                n.setTextFill(Color.RED);
                n.setTranslateX(80);
                n.setTranslateY(200);
                Label n2 = new Label("N");
                n2.setFont(font3);
                n2.setTextFill(Color.RED);
                n2.setTranslateX(120);
                n2.setTranslateY(200);
                Label i = new Label("I");
                i.setFont(font3);
                i.setTextFill(Color.RED);
                i.setTranslateX(160);
                i.setTranslateY(200);
                Label n3 = new Label("N");
                n3.setFont(font3);
                n3.setTextFill(Color.RED);
                n3.setTranslateX(200);
                n3.setTranslateY(200);
                Label g = new Label("G");
                g.setFont(font3);
                g.setTextFill(Color.RED);
                g.setTranslateX(240);
                g.setTranslateY(200);
                Label b = new Label("B");
                b.setFont(font3);
                b.setTextFill(Color.RED);
                b.setTranslateX(310);
                b.setTranslateY(200);
                Label a = new Label("A");
                a.setFont(font3);
                a.setTextFill(Color.RED);
                a.setTranslateX(350);
                a.setTranslateY(200);
                Label c = new Label("C");
                c.setFont(font3);
                c.setTextFill(Color.RED);
                c.setTranslateX(390);
                c.setTranslateY(200);
                Label k = new Label("K");
                k.setFont(font3);
                k.setTextFill(Color.RED);
                k.setTranslateX(430);
                k.setTranslateY(200);
                Label g2 = new Label("G");
                g2.setFont(font3);
                g2.setTextFill(Color.RED);
                g2.setTranslateX(470);
                g2.setTranslateY(200);
                Label r2 = new Label("R");
                r2.setFont(font3);
                r2.setTextFill(Color.RED);
                r2.setTranslateX(510);
                r2.setTranslateY(200);
                Label o = new Label("O");
                o.setFont(font3);
                o.setTextFill(Color.RED);
                o.setTranslateX(550);
                o.setTranslateY(200);
                Label u2 = new Label("U");
                u2.setFont(font3);
                u2.setTextFill(Color.RED);
                u2.setTranslateX(590);
                u2.setTranslateY(200);
                Label n4 = new Label("N");
                n4.setFont(font3);
                n4.setTextFill(Color.RED);
                n4.setTranslateX(630);
                n4.setTranslateY(200);
                Label d = new Label("D");
                d.setFont(font3);
                d.setTextFill(Color.RED);
                d.setTranslateX(670);
                d.setTranslateY(200);
                Label c2 = new Label("C");
                c2.setFont(font3);
                c2.setTextFill(Color.RED);
                c2.setTranslateX(740);
                c2.setTranslateY(200);
                Label h = new Label("H");
                h.setFont(font3);
                h.setTextFill(Color.RED);
                h.setTranslateX(780);
                h.setTranslateY(200);
                Label e1 = new Label("E");
                e1.setFont(font3);
                e1.setTextFill(Color.RED);
                e1.setTranslateX(820);
                e1.setTranslateY(200);
                Label c3 = new Label("C");
                c3.setFont(font3);
                c3.setTextFill(Color.RED);
                c3.setTranslateX(860);
                c3.setTranslateY(200);
                Label k2 = new Label("K");
                k2.setFont(font3);
                k2.setTextFill(Color.RED);
                k2.setTranslateX(900);
                k2.setTranslateY(200);
                Label s = new Label("S");
                s.setFont(font3);
                s.setTextFill(Color.RED);
                s.setTranslateX(940);
                s.setTranslateY(200);
                scrollBarPane.getChildren().addAll(r, u, n, n2, i, n3, g, b, a, c, k, g2, r2, o, u2, n4, d, c2, h, e1,
                        c3, k2, s);
                Timeline charColor = new Timeline();
                charColor.getKeyFrames().addAll(
                        new KeyFrame(Duration.seconds(0.3), new KeyValue(r.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(0.6), new KeyValue(u.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(0.9), new KeyValue(n.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(1.2), new KeyValue(n2.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(1.5), new KeyValue(i.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(1.8), new KeyValue(n3.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(2.1), new KeyValue(g.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(2.4), new KeyValue(b.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(2.7), new KeyValue(a.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(3.0), new KeyValue(c.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(3.3), new KeyValue(k.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(3.6), new KeyValue(g2.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(3.9), new KeyValue(r2.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(4.2), new KeyValue(o.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(4.5), new KeyValue(u2.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(4.8), new KeyValue(n4.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(5.1), new KeyValue(d.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(5.4), new KeyValue(c2.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(5.7), new KeyValue(h.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(6.0), new KeyValue(e1.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(6.3), new KeyValue(c3.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(6.6), new KeyValue(k2.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(6.9), new KeyValue(s.textFillProperty(), Color.LIGHTGREEN)));
                charColor.play();
                Label success = new Label("Successfully Employed!\n\n" + newEmployee.toStringFL());
                success.setFont(font2);
                success.setTextFill(Color.YELLOW);
                success.setTranslateX(10);
                success.setTranslateY(300);
                Timeline checkMark = new Timeline();
                checkMark.getKeyFrames().addAll(
                        new KeyFrame(Duration.seconds(6.9), new KeyValue(s.textFillProperty(), Color.LIGHTGREEN)),
                        new KeyFrame(Duration.seconds(6.9), new KeyValue(s.textProperty(), "\uD83D\uDC4C")));
                checkMark.play();
                checkMark.setOnFinished(ev -> scrollBarPane.getChildren().add(success));
            }
        };
        insertBt.setOnAction(insert);
        Button mergeFilesBt = new Button(
                "Merge Files");
        mergeFilesBt.setFont(font);
        mergeFilesBt.setMaxSize(120, 50);
        mergeFilesBt.setMinSize(120, 50);
        EventHandler<ActionEvent> merge = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File file = fileChooser.showOpenDialog(primaryStage);
                Scanner scanner = null;
                try {
                    scanner = new Scanner(file);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] employeeInfo = line.split(" ");
                    String FirstName = employeeInfo[0];
                    String LastName = employeeInfo[1];
                    String Position = employeeInfo[2];
                    String Site = employeeInfo[3];
                    String employeeID = Site.substring(0, 1) + "-" + FirstName.substring(0, 3).toUpperCase()
                            + LastName.substring(0, 1).toUpperCase() + "-" + "01";
                    Employee employee = new Employee(employeeID, FirstName, LastName, Position, Site, false);
                    employeesArray.add(employee);
                    // inset employee into the binary search tree
                    binaryTree.insert(employeeID);
                }
                scanner.close();
                DisplayAllBt.fire();
            }

        };
        mergeFilesBt.setOnAction(merge);

        // Stage Configuration
        vbTop.getChildren().addAll(dataBoxTitle, employDataBox);
        vbTop.setPadding(new Insets(0, 10, 250, 10));
        vbBottom.getChildren().addAll(searchBt, DisplayAllBt, deleteBt, insertBt, mergeFilesBt, writeDataBt, quitBt);
        vbBottom.translateXProperty().set(3);
        vbBottom.translateYProperty().set(-10);
        vbTop.translateXProperty().set(3);
        vbBottom.setPadding(new Insets(-70, 10, 0, 10));
        vb.getChildren().addAll(vbTop, vbBottom);

        stackP.getChildren().addAll(vb, menuBlocker);

        hb.getChildren().addAll(scrollBar, stackP);
        Scene sc = new Scene(
                hb);
        primaryStage.setScene(sc);
        primaryStage.setMaxHeight(640);
        primaryStage.setMaxWidth(1250);
        primaryStage.setMinHeight(640);
        primaryStage.setMinWidth(1250);
        primaryStage.show();

    }
}