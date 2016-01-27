package View;

import javafx.scene.shape.Shape3D;

import java.util.ArrayList;

/**
 * Created by Deviltech on 10.01.2016.
 */
public class MoleculeMesh {

    private ArrayList<Shape3D> myAdenines;
    private ArrayList<Shape3D> myGuanines;
    private ArrayList<Shape3D> myCytosines;
    private ArrayList<Shape3D> myUracils;
    private ArrayList<Shape3D> mySugars;
    private ArrayList<Shape3D> myLines;
    private ArrayList<Shape3D> myPhosporus;
    private ArrayList<Shape3D> myPhosphorusLines;

    private ArrayList<Shape3D> allShapes;

    // Constructor
    public MoleculeMesh() {
        this.myAdenines = new ArrayList<Shape3D>();
        this.myGuanines = new ArrayList<Shape3D>();
        this.myCytosines = new ArrayList<Shape3D>();
        this.myUracils = new ArrayList<Shape3D>();
        this.mySugars = new ArrayList<Shape3D>();
        this.myLines = new ArrayList<Shape3D>();
        this.myPhosporus = new ArrayList<Shape3D>();
        this.myPhosphorusLines = new ArrayList<Shape3D>();
        this.allShapes = new ArrayList<Shape3D>();;
    }

    // Setters
    public void addAdenine(Shape3D myShape3D){
        myAdenines.add(myShape3D);
        allShapes.add(myShape3D);
    }

    public void addGuanine(Shape3D myShape3D){
        myGuanines.add(myShape3D);
        allShapes.add(myShape3D);
    }

    public void addCytosine(Shape3D myShape3D){
        myCytosines.add(myShape3D);
        allShapes.add(myShape3D);
    }

    public void addUracil(Shape3D myShape3D){
        myUracils.add(myShape3D);
        allShapes.add(myShape3D);
    }

    public void addSugar(Shape3D myShape3D){
        mySugars.add(myShape3D);
        allShapes.add(myShape3D);
    }

    public void addLine(Shape3D myShape3D){
        myLines.add(myShape3D);
        allShapes.add(myShape3D);
    }

    public void addPhosphorus(Shape3D myShape3D){
        myPhosporus.add(myShape3D);
        allShapes.add(myShape3D);
    }

    public void addPhosphorusLine(Shape3D myShape3D){
        myPhosphorusLines.add(myShape3D);
        allShapes.add(myShape3D);
    }

    public void addCustom(Shape3D myShape3D){
        allShapes.add(myShape3D);
    }


    // Getters
    public ArrayList<Shape3D> getMyAdenines() {
        return myAdenines;
    }

    public ArrayList<Shape3D> getMyGuanines() {
        return myGuanines;
    }

    public ArrayList<Shape3D> getMyCytosines() {
        return myCytosines;
    }

    public ArrayList<Shape3D> getMyUracils() {
        return myUracils;
    }

    public ArrayList<Shape3D> getMySugars() {
        return mySugars;
    }

    public ArrayList<Shape3D> getMyLines() {
        return myLines;
    }

    public ArrayList<Shape3D> getMyPhosporus() {
        return myPhosporus;
    }

    public ArrayList<Shape3D> getMyPhosphorusLines() {
        return myPhosphorusLines;
    }

    public ArrayList<Shape3D> getAllShapes() {
        return allShapes;
    }
}
