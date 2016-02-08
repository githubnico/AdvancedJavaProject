package Model;

import javafx.geometry.Point3D;

/**
 * Created by Deviltech on 13.12.2015.
 */
public class Atom {

    private int atomID;
    private String atomName;
    private String atomResiduum;
    private char atomChain;
    private int atomResidueIndex;
    private float coordX;
    private float coordY;
    private float coordZ;
    // no marker field
    private double atomBeta;
    private char atomType;


    // empty Constructor
    public Atom(){

    }

    // constructor using String[] of values
    public Atom(String[] s) {
        this.atomID = Integer.parseInt(s[1].replaceAll("\\s+",""));
        this.atomName = s[2].replaceAll("\\s+","");
        this.atomResiduum = s[4].replaceAll("\\s+","");
        this.atomChain = s[5].replaceAll("\\s+","").charAt(0);
        this.atomResidueIndex = Integer.parseInt(s[6].replaceAll("\\s+",""));
        this.coordX = Float.parseFloat(s[8]);
        this.coordY = Float.parseFloat(s[9]);
        this.coordZ = Float.parseFloat(s[10]);
        this.atomBeta = Double.parseDouble(s[12]);
        this.atomType = s[14].replaceAll("\\s+","").charAt(0);
    }

    // Generates the Point3D of this atom
    public Point3D toPoint3D(){
        return new Point3D(coordX, coordY, coordZ);
    }

    public int getAtomID() {
        return atomID;
    }

    public void setAtomID(int atomID) {
        this.atomID = atomID;
    }

    public String getAtomName() {
        return atomName;
    }

    public void setAtomName(String atomName) {
        this.atomName = atomName;
    }

    public String  getAtomResidue() {
        return atomResiduum;
    }

    public void setAtomResidue(String atomResiduum) {
        this.atomResiduum = atomResiduum;
    }

    public char getAtomChain() {
        return atomChain;
    }

    public void setAtomChain(char atomChain) {
        this.atomChain = atomChain;
    }

    public int getAtomResidueIndex() {
        return atomResidueIndex;
    }

    public void setAtomResidueIndex(int atomResidueIndex) {
        this.atomResidueIndex = atomResidueIndex;
    }

    public float getCoordX() {
        return coordX;
    }

    public void setCoordX(float coordX) {
        this.coordX = coordX;
    }

    public float getCoordY() {
        return coordY;
    }

    public void setCoordY(float coordY) {
        this.coordY = coordY;
    }

    public float getCoordZ() {
        return coordZ;
    }

    public void setCoordZ(float coordZ) {
        this.coordZ = coordZ;
    }

    public double getAtomBeta() {
        return atomBeta;
    }

    public void setAtomBeta(double atomBeta) {
        this.atomBeta = atomBeta;
    }

    public char getAtomType() {
        return atomType;
    }

    public void setAtomType(char atomType) {
        this.atomType = atomType;
    }

}
