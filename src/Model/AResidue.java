package Model;

import javafx.geometry.Point3D;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Deviltech on 21.12.2015.
 */
public abstract class AResidue {


    //contains all atoms of residue
    HashMap<String, Atom> myAtoms;

    private int indexInArrayList;


    // START abstract

    public abstract int checkForPairing(WatsonCrick myWatsonCrick);

    /**
     * sorts into the right given Arraylist according to type
     * @param myAdenines
     * @param myCytosines
     * @param myGuanines
     * @param myUracils
     */
    public abstract void sortIntoArrayList(ArrayList<Adenine> myAdenines, ArrayList<Cytosine> myCytosines, ArrayList<Guanine> myGuanines, ArrayList<Uracil> myUracils);


    /**
     * Generate the sugar TriangleMesh according to Residue Type
     *
     * @return mesh
     */
    abstract public MeshView generateSugarMesh();


    /**
     * Generate the base TriangleMesh according to Residue Type
     *
     * @return mesh
     */
    abstract public MeshView generateBaseMesh();


    /**
     * Generate Line between sugar and base
     *
     * @return
     */
    abstract public Shape3D generateLine();

    /**
     * Adds the current Residue to its Structure
     * @param myStructure
     */
    abstract public void addToStructure(Structure myStructure);


    // END abstract

    /**
     * Add a single Residues.Atom to the Residues.AResidue
     *
     * @param a
     */
    public void addAtom(Atom a) {
        myAtoms.put(a.getAtomName(), a);
    }

    /**
     * Get the Atom according to it's name
     * @param atomName
     * @return
     */
    public Atom getAtom(String atomName) {
        return myAtoms.get(atomName);
    }


    /**
     * Generate phosoporus shape
     *
     * @return shape
     */
    public boolean generatePhosphorusMesh(Shape3D sphere) {
        Atom phosphorus = myAtoms.get("P");
        if (phosphorus != null) {
            sphere.setTranslateX(phosphorus.getCoordX());
            sphere.setTranslateY(phosphorus.getCoordY());
            sphere.setTranslateZ(phosphorus.getCoordZ());
            return true;
        } else {
            return false;
        }
    }


    /**
     * general TriangleMesh generation
     *
     * @param atomNames
     * @param faces
     * @return
     */
    public MeshView generateGeneralMesh(String[] atomNames, int faces[]) {
        TriangleMesh mesh = new TriangleMesh();

        for (String currentName : atomNames) {
            Atom currentAtom = myAtoms.get(currentName);
            float points[] = {currentAtom.getCoordX(), currentAtom.getCoordY(), currentAtom.getCoordZ()};
            mesh.getPoints().addAll(points);
        }
        mesh.getTexCoords().addAll(0.0f, 1.0f, 0.5f, 0.5f, 0.5f, 1.0f);
        mesh.getFaces().addAll(faces);

        MeshView myMeshView = new MeshView(mesh);

        Atom firstAtom = myAtoms.get(atomNames[0]);
        Tooltip myToolTip = new Tooltip(firstAtom.getAtomResidue()+Integer.toString(firstAtom.getAtomResidueIndex()));
        Tooltip.install(myMeshView, myToolTip);

        return  myMeshView;
    }


    /**
     * generate a Line (cylinder) between two Atoms
     *
     * @param firstAtom
     * @param secondAtom
     * @param width
     * @return
     */
    public Shape3D generateLine(Atom firstAtom, Atom secondAtom, double width) {
        Point3D origin = new Point3D(firstAtom.getCoordX(), firstAtom.getCoordY(), firstAtom.getCoordZ());
        Point3D target = new Point3D(secondAtom.getCoordX(), secondAtom.getCoordY(), secondAtom.getCoordZ());
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder line = new Cylinder(width, height);

        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        return line;
    }

    /**
     * Returns the Residue Type of the Residue
     * @return
     */
    public char getResidueType(){
        return myAtoms.values().iterator().next().getAtomResidue();
    }

    public int getIndexInArrayList(){
        return indexInArrayList;
    }

    public void setIndexInArrayList(int i){
        indexInArrayList = i;
    }

    /**
     * checks if the angles and lengths of the atoms are in range for pairing
     * @param HBond1
     * @param HBondMid
     * @param NoHBond
     * @return
     */
    public boolean checkForLenghAndAngles(Point3D HBond1, Point3D HBondMid, Point3D NoHBond){
        double length = HBond1.distance(HBondMid);
        System.out.println(length);
        double angle = HBondMid.angle(HBond1, NoHBond);
        System.out.println(angle);
        //return (length >= myValues.HBOND_MIN_DISTANCE && length <= myValues.HBOND_MAX_DISTANCE && angle >= myValues.HBOND_MIN_ANGLE && angle <= myValues.HBOND_MAX_ANGLE);
        return (length <= myValues.HBOND_MAX_DISTANCE && angle >= myValues.HBOND_MIN_ANGLE);
    }
}




