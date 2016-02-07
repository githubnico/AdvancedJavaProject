package View;

import Model.*;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Deviltech on 20.01.2016.
 */
public class UI extends Application{

    // contains shapes
    Group draw3DRoot;

    TextArea sequenceArea;

    IndexRange myIndexRange;

    //Contains all Residues to be drawn
    Structure myStructure;

    // for rotation
    double originX;
    double originY;

    PerspectiveCamera camera3D;

    RotateTransition myAnimation;

    // indicates if shift key is pressed
    private boolean isShiftPressed;

    // for coloring
    private boolean isGreenPhosphorus;


    @Override
    public void start(Stage primaryStage) throws Exception {

        // Scene
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox, 800, 800);

        // Menu
        MenuBar menuBar = new MenuBar();

        Menu menuFile = new Menu(myValues.MENU_FILE);
        Menu menuSequence = new Menu(myValues.MENU_SEQUENCE);
        Menu menu2DStructure = new Menu(myValues.MENU_2D_STRUCTURE);
        Menu menu3DStructure = new Menu(myValues.MENU_3D_STRUCTURE);

        MenuItem fileOpenItem = new MenuItem(myValues.MENU_OPEN);
        MenuItem seqPairingItem = new MenuItem(myValues.MENU_PAIRING);
        MenuItem struct2DResetItem = new MenuItem(myValues.MENU_RESET_VIEW);
        MenuItem struct3DResetItem = new MenuItem(myValues.MENU_RESET_VIEW);
        MenuItem struct3DAnimateItem = new MenuItem(myValues.MENU_ANIMATION);
        Menu struct3DColoringItem = new Menu(myValues.MENU_COLORING);


        // Submenuitems
        MenuItem coloringAGCUItem = new MenuItem(myValues.MENU_AGCU);
        MenuItem coloringPurinePyrimidineItem = new MenuItem(myValues.MENU_PURINE_PYRIMIDINE);
        MenuItem coloringPairedItem = new MenuItem(myValues.MENU_PAIRED);

        // Checkmenuitem
        CheckMenuItem greenPhosphorus = new CheckMenuItem(myValues.MENU_GREEN_PHOSPHORUS);


        menuFile.getItems().addAll(fileOpenItem);
        menuSequence.getItems().addAll(seqPairingItem);
        menu2DStructure.getItems().addAll(struct2DResetItem);
        menu3DStructure.getItems().addAll(struct3DResetItem, struct3DColoringItem, struct3DAnimateItem);
        menuBar.getMenus().addAll(menuFile, menuSequence, menu2DStructure, menu3DStructure);

        struct3DColoringItem.getItems().addAll(greenPhosphorus, new SeparatorMenuItem(), coloringAGCUItem, coloringPurinePyrimidineItem, coloringPairedItem);


        // Sequence Pane
        sequenceArea = new TextArea();
        sequenceArea.setWrapText(true);
        sequenceArea.setStyle("-fx-font-family: monospace; -fx-font-size: 20;");
        sequenceArea.setEditable(false);

        myIndexRange = new IndexRange(0,0);

        // 2D Pane
        Pane drawPane = new Pane();


        // 3D Pane
        Text text3D = new Text(myValues.NO_FILE_SELECTED);
        StackPane stackPane = new StackPane();
        draw3DRoot = new Group();
        SubScene drawSubScene = new SubScene(draw3DRoot, 800, 700, true, SceneAntialiasing.BALANCED);
        stackPane.getChildren().addAll(drawSubScene, text3D);

        myStructure = new Structure();

        isGreenPhosphorus = true;

        // for 3D structure Rotation animation
        myAnimation = generateAnimation(Rotate.X_AXIS);


        // Panes and accordion
        TitledPane sequencePane = new TitledPane(myValues.PANE_SEQUENCE, sequenceArea);
        TitledPane structure2DPane = new TitledPane(myValues.PANE_2D_STRUCTURE, drawPane);
        TitledPane structure3DPane = new TitledPane(myValues.PANE_3D_STRUCTURE, stackPane);

        sequencePane.setExpanded(false);
        structure2DPane.setExpanded(false);
        structure3DPane.setExpanded(false);

        drawSubScene.heightProperty().bind(scene.heightProperty());
        drawSubScene.widthProperty().bind(scene.widthProperty());


        final VBox accordion = new VBox();
        accordion.getChildren().setAll(sequencePane, structure2DPane, structure3DPane
        );

        // Sequence area selection
        sequenceArea.setOnMouseClicked((value) -> {
            myIndexRange = sequenceArea.getSelection();
            colorSelectedCircles();
            System.out.println(sequenceArea.getSelection());
            System.out.println(myStructure.getMyCircles().size());
        });

        // Set open Menu handler
        fileOpenItem.setOnAction((value) -> {
                    FileChooser fileChooser = new FileChooser();

                    // Set extension filter
                    ArrayList<String> filters = new ArrayList<String>();
                    filters.add("*.pdb");
                    filters.add("*.ent");
                    FileChooser.ExtensionFilter extFilter =
                            new FileChooser.ExtensionFilter("PDB files (*.pdb), ENT files (*.ent)", filters);
                    fileChooser.getExtensionFilters().add(extFilter);

                    // Show open file dialog
                    File file = fileChooser.showOpenDialog(primaryStage);
                    if (file != null) {
                        try {
                            myStructure = new Structure();
                            draw3DRoot.getChildren().clear();
                            drawPane.getChildren().clear();
                            text3D.setText(file.getName());
                            ArrayList<Atom> myAtoms = new PDB_Reader().readInFile(file);
                            myStructure.generateResiduesbyAtoms(myAtoms);
                            myStructure.generateSequence();
                            sequenceArea.setText(myStructure.getMySequence());
                            generateAndDrawWCPairing(drawPane);
                            setAtomCoordinates(myAtoms, draw3DRoot);
                            text3D.setText(file.getName());
                            // open accordion
                            ((TitledPane) accordion.getChildren().get(2)).setExpanded(true);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                }
        );

        // Watson-Crick Pairing
        seqPairingItem.setOnAction((value)->{

        });

        // Reset View Menuitem
        struct3DResetItem.setOnAction((value)->{
            resetCamera3D(camera3D);
        });

        struct3DAnimateItem.setOnAction((value)->{
            myAnimation.playFromStart();
        });

        // Set color Handlers
        coloringAGCUItem.setOnAction((value) ->{
            colorAllShape3D(myStructure.filterShape3DByType("A"), myValues.MATERIAL_BLUE);
            colorAllShape3D(myStructure.filterShape3DByType("G"), myValues.MATERIAL_GREEN);
            colorAllShape3D(myStructure.filterShape3DByType("C"), myValues.MATERIAL_RED);
            colorAllShape3D(myStructure.filterShape3DByType("U"), myValues.MATERIAL_YELLOW);
        });

        coloringPurinePyrimidineItem.setOnAction((value) ->{
            colorAllShape3D(myStructure.filterShape3DByType("A"), myValues.MATERIAL_BLUE);
            colorAllShape3D(myStructure.filterShape3DByType("G"), myValues.MATERIAL_DARK_BLUE);
            colorAllShape3D(myStructure.filterShape3DByType("C"), myValues.MATERIAL_RED);
            colorAllShape3D(myStructure.filterShape3DByType("U"), myValues.MATERIAL_DARK_RED);
        });

        coloringPairedItem.setOnAction((value) ->{
            if(myStructure.getMyPaired() == null){
                myStructure.generatePairedWatsonCrick();
            }
            colorAllShape3D(myStructure.filterShape3DByPaired(true), myValues.MATERIAL_BLUE);
            colorAllShape3D(myStructure.filterShape3DByPaired(false), myValues.MATERIAL_RED);
        });

        // Handle Checkmenuitem
        greenPhosphorus.setSelected(isGreenPhosphorus);
        greenPhosphorus.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue ov,
                                Boolean old_val, Boolean new_val) {
                if(new_val){
                    //TODO get phosphorus
                //    colorAllShape3D(myMolecleMesh.getMyPhosporus(), myValues.MATERIAL_SEA_GREEN);
                //    colorAllShape3D(myMolecleMesh.getMyPhosphorusLines(), myValues.MATERIAL_SEA_GREEN);
                } else {
                //    colorAllShape3D(myMolecleMesh.getMyPhosporus(), myValues.MATERIAL_GRAY);
                //    colorAllShape3D(myMolecleMesh.getMyPhosphorusLines(), myValues.MATERIAL_GRAY);
                }
                isGreenPhosphorus = new_val;
            }
        });


        // for camera3D and rotation
        stackPane.setOnMousePressed(sceneOnMousePressedEventHandler);
        stackPane.setOnMouseDragged(sceneOnMouseDraggedEventHandler);

        // scene key pressed
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SHIFT) {
                isShiftPressed = true;
            }
        });

        // scene key released
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SHIFT) {
                isShiftPressed = false;
            }
        });

        // set camera3D properties
        camera3D = new PerspectiveCamera(true);

        // set the camera
        resetCamera3D(camera3D);

        drawSubScene.setCamera(camera3D);

        stackPane.setAlignment(text3D, Pos.TOP_LEFT);
        stackPane.setMargin(text3D, new Insets(30));


        vBox.getChildren().addAll(menuBar, accordion);

        primaryStage.setScene(scene);
        primaryStage.setTitle("3D Viewer");

        // show scene
        primaryStage.show();

    }


    /**
     * Sets the Residues.Atom Coordinates according ro bdp file Atoms
     *
     * @param myAtoms
     * @return
     */
    private void setAtomCoordinates(ArrayList<Atom> myAtoms, Group myGroup) {



        // used for lines between phosphorus
        int residueIndex = -1;
        Atom oldAtom = new Atom();

        // iterate over residues and draw them
        for (AResidue myResidue : myStructure.getMyResidues()) {

            // draw lines between phosphorus
            if (residueIndex == -1 && (myResidue.getAtom("P") != null)) {
                // initialize, if first residue of file
                oldAtom = myResidue.getAtom("P");
                residueIndex = oldAtom.getAtomResidueIndex();
            } else {
                // compare for sequencial index
                Atom newAtom = myResidue.getAtom("P");
                if ((newAtom != null) && residueIndex == newAtom.getAtomResidueIndex() - 1) {
                    // draw line, if sequencial
                    Shape3D line = myResidue.generateLine(oldAtom, newAtom, myValues.LINE_WIDTH_MEDIUM);
                    if(isGreenPhosphorus){
                        line.setMaterial(myValues.MATERIAL_SEA_GREEN);
                    } else {
                        line.setMaterial(myValues.MATERIAL_GRAY);
                    }
                    myGroup.getChildren().add(line);
                    myStructure.add3DotherShape(line);
                    //myMolecleMesh.addPhosphorusLine(line);
                    oldAtom = newAtom;
                    residueIndex = newAtom.getAtomResidueIndex();
                } else {
                    // if not sequencial, reset
                    if (myResidue.getAtom("P") != null) {
                        oldAtom = myResidue.getAtom("P");
                        residueIndex = oldAtom.getAtomResidueIndex();
                    }
                }
            }
            // generate base
            MeshView currentBaseView = myResidue.generateBaseMesh();
            currentBaseView.setMaterial(myValues.MATERIAL_DARK_RED);
            currentBaseView.setDrawMode(DrawMode.FILL);
            myStructure.add3DResidueShape(currentBaseView);
            //generate sugar
            MeshView currentSugarView = myResidue.generateSugarMesh();
            currentSugarView.setMaterial(myValues.MATERIAL_ORANGE);
            currentSugarView.setDrawMode(DrawMode.FILL);
            myStructure.add3DotherShape(currentSugarView);
            // handle phosphorus
            Shape3D currentPhosphorus = new Sphere(myValues.PHOSPHORUS_WIDTH);
            if (myResidue.generatePhosphorusMesh(currentPhosphorus)) {
                if(isGreenPhosphorus){
                    currentPhosphorus.setMaterial(myValues.MATERIAL_SEA_GREEN);
                } else {
                    currentPhosphorus.setMaterial(myValues.MATERIAL_GRAY);
                }
                myGroup.getChildren().add(currentPhosphorus);
                myStructure.add3DotherShape(currentPhosphorus);
            }
            // generate line between sugar and base
            Shape3D baseSugarLine = myResidue.generateLine();
            baseSugarLine.setMaterial(myValues.MATERIAL_BLACK);
            myStructure.add3DotherShape(baseSugarLine);

            // generate line to phosphorus
            // P, O5', C5', C4'
            try {
                Shape3D line1 = myResidue.generateLine(myResidue.getAtom("P"), myResidue.getAtom("O5'"), myValues.LINE_WIDTH_SMALL);
                Shape3D line2 = myResidue.generateLine(myResidue.getAtom("O5'"), myResidue.getAtom("C5'"), myValues.LINE_WIDTH_SMALL);
                Shape3D line3 = myResidue.generateLine(myResidue.getAtom("C5'"), myResidue.getAtom("C4'"), myValues.LINE_WIDTH_SMALL);
                line1.setMaterial(myValues.MATERIAL_BLACK);
                line2.setMaterial(myValues.MATERIAL_BLACK);
                line3.setMaterial(myValues.MATERIAL_BLACK);
                myGroup.getChildren().addAll(line1, line2, line3);
                myStructure.add3DotherShape(line1);
                myStructure.add3DotherShape(line2);
                myStructure.add3DotherShape(line3);
            } catch(Exception e){

            };

            myGroup.getChildren().addAll(currentBaseView, currentSugarView, baseSugarLine);

        }
        centerGroup(myGroup);


    }

    /**
     * generates the 2D structure and draws it
     * @param drawPane
     */
    private void generateAndDrawWCPairing(Pane drawPane){
        final double[][][] coordsRepresentation = {new double[1][2]};
        Graph myGraph = new Graph();
        try {
            myStructure.generatePairedWatsonCrick();
            //myGraph.parseNotation(new Nussinov(myStructure.getMySequence()).getBracketNotation());
            myGraph.parseNotation(myStructure.getMyPaired());
        } catch (IOException e) {

        }
        coordsRepresentation[0] = SpringEmbedder.computeSpringEmbedding(myValues.PAIRING_ITERATIONS, myGraph.getNumberOfNodes(), myGraph.getEdges(), null);
        SpringEmbedder.centerCoordinates(coordsRepresentation[0], 50, 550, 50, 550);
        drawShapes(drawPane, coordsRepresentation[0], myGraph.getEdges(), myGraph.getNumberOfNodes());
        colorSelectedCircles();
    }



    /**
     * Center Group elements
     *
     * @param g
     */
    private void centerGroup(Group g) {
        ObservableList<Node> nodes = g.getChildren();
        double meanX = 0.0;
        double meanY = 0.0;
        double meanZ = 0.0;

        for (Node currentNode : nodes) {
            meanX += currentNode.getTranslateX();
            meanY += currentNode.getTranslateY();
            meanZ += currentNode.getTranslateZ();
        }
        double nodeSize = nodes.size()/myValues.NODE_VALUE;

        meanX = meanX / nodeSize;
        meanY = meanY / nodeSize;
        meanZ = meanZ / nodeSize;


        for (Node currentNode : nodes) {
            currentNode.setTranslateX(currentNode.getTranslateX() - meanX);
            currentNode.setTranslateY(currentNode.getTranslateY() - meanY);
            currentNode.setTranslateZ(currentNode.getTranslateZ() - meanZ);
        }

    }

    /**
     * Color all Shapes with given Phongmaterial
     * @param myShape3D
     * @param myMaterial
     */
    private void colorAllShape3D(ArrayList<Shape3D> myShape3D, PhongMaterial myMaterial){
        for(Shape3D currentShape3D : myShape3D){
            currentShape3D.setMaterial(myMaterial);
        }
    }

    /**
     * Generates a Sequence String from Atoms
     * @param myAtoms
     * @return
     */
    private String generateSequence(ArrayList<Atom> myAtoms){
        String sequence = "";
        for(Atom currentAtom: myAtoms){
            sequence += currentAtom.getAtomResidue();
        }
        return sequence;
    }


    /**
     * Eventhandler for mouse pressed for circle drag
     */
    EventHandler<MouseEvent> sceneOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    // set origin coordinates
                    originX = t.getSceneX();
                    originY = t.getSceneY();

                    // Stop rotate animation, when user intervention
                    myAnimation.stop();
                }

            };

    /**
     * Eventhandler for mouse follow on drag
     */
    EventHandler<MouseEvent> sceneOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    // calculate offset
                    double offsetX = t.getSceneX() - originX;
                    double offsetY = t.getSceneY() - originY;
                    if (isShiftPressed) {
                        // zoom while shift pressed
                        camera3D.setTranslateZ(camera3D.getTranslateZ() + (offsetX + offsetY) / 2);
                    } else {
                        // follow mouse
                        draw3DRoot.getTransforms().add(new Rotate(offsetX, 0, 0, 0, Rotate.Y_AXIS));
                        draw3DRoot.getTransforms().add(new Rotate( - offsetY, 0, 0, 0, Rotate.Z_AXIS));
                    }

                    originX += offsetX;
                    originY += offsetY;

                    // Stop rotate animation, when user intervention
                    myAnimation.stop();

                }

            };

    /**
     * Resets the camera for the 3D view
     * @param myCamera
     */
    private void resetCamera3D(PerspectiveCamera myCamera){
        myCamera.setNearClip(0.1);
        myCamera.setFarClip(10000);
        myCamera.setTranslateX(0);
        myCamera.setTranslateY(0);
        myCamera.setTranslateZ(-100);
    }

    /**
     * Dialog for work in progress
     */
    private void dialogInProgress(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Work in Progress");
        alert.setHeaderText(null);
        alert.setContentText("This feature is in progress");
        alert.showAndWait();
    }


    /**
     * Drawing function for Circles and Lines
     *
     * @param drawPane
     * @param coords     Circle Coordinates
     * @param edges      array of edges
     * @param startIndex NumberOfNodes
     */
    private void drawShapes(Pane drawPane, double[][] coords, int[][] edges, int startIndex) {

        // clear previous pane
        drawPane.getChildren().clear();
        ArrayList<Circle> circleList = new ArrayList<>();

        // generate Circles
        for (int i = 0; i < coords.length; i++) {
            Circle currentCircle = new Circle(coords[i][0], coords[i][1], myValues.NODESIZE, Color.BLACK);
            String toolTipText = "Node " + Integer.toString(i + 1);
            // expand toolTip text with nucleotide and Circle color, if possible
            if (myStructure.getMySequence().length() > i) {
                toolTipText += ": " + myStructure.getMySequence().charAt(i);
                currentCircle.setFill(getNodeColor(myStructure.getMySequence().charAt(i)));
                currentCircle.setStroke(Color.BLACK);
                currentCircle.setStrokeWidth(2);

            }
            Tooltip.install(
                    currentCircle,
                    new Tooltip(toolTipText)
            );

            circleList.add(currentCircle);
        }


        // generate  basic Lines
        ArrayList<Line> lineList = new ArrayList<>();
        for (int i = 0; i < circleList.size() - 1; i++) {
            Line line = new Line();
            line.setStroke(Color.BLACK);
            line.setFill(Color.BLACK);
            Circle circle1 = circleList.get(i);
            Circle circle2 = circleList.get(i + 1);


            // bind ends of line:
            line.startXProperty().bind(circle1.centerXProperty().add(circle1.translateXProperty()));
            line.startYProperty().bind(circle1.centerYProperty().add(circle1.translateYProperty()));
            line.endXProperty().bind(circle2.centerXProperty().add(circle2.translateXProperty()));
            line.endYProperty().bind(circle2.centerYProperty().add(circle2.translateYProperty()));

            lineList.add(line);
        }

        // generate edges
        for (int i = Math.max(startIndex - 1, 0); i < edges.length; i++) {
            Line line = new Line();
            line.setStroke(Color.ORANGE);
            Circle circle1 = circleList.get(edges[i][0]);
            Circle circle2 = circleList.get(edges[i][1]);

            line.startXProperty().bind(circle1.centerXProperty().add(circle1.translateXProperty()));
            line.startYProperty().bind(circle1.centerYProperty().add(circle1.translateYProperty()));
            line.endXProperty().bind(circle2.centerXProperty().add(circle2.translateXProperty()));
            line.endYProperty().bind(circle2.centerYProperty().add(circle2.translateYProperty()));

            lineList.add(line);
        }

        myStructure.setMyCircles(circleList);

        drawPane.getChildren().addAll(lineList);
        drawPane.getChildren().addAll(circleList);

    }

    /**
     * color circles based on selection
     */
    private void colorSelectedCircles(){

        // color all circles unselected
        for(Circle currentCircle: myStructure.getMyCircles()){
            currentCircle.setStroke(Color.BLACK);
        }

        // draw the selected in selected stroke color
        for(int i = myIndexRange.getStart(); i<= Math.min(myIndexRange.getEnd(), myStructure.getMyCircles().size()-1); i++){
            myStructure.getMyCircles().get(i).setStroke(Color.ORANGERED);
        }

    }

    /**
     * Generate Color according to nucleotide
     *
     * @param c nucleotide
     * @return nucleotide color
     */
    private Color getNodeColor(char c) {
        switch (Character.toLowerCase(c)) {
            case 'a':
                return Color.LIGHTSEAGREEN;
            case 'u':
                return Color.DARKBLUE;
            case 'c':
                return Color.LAWNGREEN;
            case 'g':
                return Color.DARKGREEN;
            default:
                return Color.LIGHTGRAY;
        }
    }


    private RotateTransition generateAnimation(Point3D myAxis){
        RotateTransition myAnimation = new RotateTransition(Duration.seconds(3), draw3DRoot);
        myAnimation.setByAngle(360);
        myAnimation.setCycleCount(1);
        myAnimation.setDelay(Duration.ZERO);
        myAnimation.setAxis(myAxis);
        return  myAnimation;
    }


}
