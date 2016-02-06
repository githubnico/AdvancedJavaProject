package Model;

import javafx.scene.shape.Shape3D;

import java.util.ArrayList;

/**
 * Created by Deviltech on 10.01.2016.
 */
public class Structure {

    private String mySequence;

    private ArrayList<AResidue> myResidues;

    private String myPaired;

    private ArrayList<Shape3D> my3DResidueShapes;

    private ArrayList<Shape3D> my3DotherShapes;


    // Constructor
    public Structure() {
        this.myResidues = new ArrayList<AResidue>();
        this.my3DResidueShapes = new ArrayList<Shape3D>();
        this.my3DotherShapes = new ArrayList<Shape3D>();
    }

    // Getters and Setters


    public String getMySequence(){
        return mySequence;
    }

    public void addResidue(AResidue myResidue){
        myResidues.add(myResidue);
    }

    public ArrayList<AResidue> getMyResidues() {
        return myResidues;
    }

    public void add3DResidueShape(Shape3D shape){
        my3DResidueShapes.add(shape);
    }

    public ArrayList<Shape3D> getMy3DResidueShapes(){
        return my3DResidueShapes;
    }

    public void add3DotherShape(Shape3D shape){
        my3DotherShapes.add(shape);
    }

    public ArrayList<Shape3D> getMy3DotherShapes(){
        return my3DotherShapes;
    }

    public String getMyPaired() {
        return myPaired;
    }

    /**
     * generate Residues by Atoms
     */
    public void generateResiduesbyAtoms(ArrayList<Atom> myAtoms){

        // Current Residue Index for while loop
        int currentResidueIndex = myAtoms.get(0).getAtomResidueIndex();
        AResidue currentResidue = constructResidueWithType(myAtoms.get(0));

        for (int i = 0; i < myAtoms.size(); i++) {

            // As long as the atom belongs to the same residue, add it to current residue
            while (i < myAtoms.size() - 1 && currentResidueIndex == myAtoms.get(i).getAtomResidueIndex()) {
                currentResidue.addAtom(myAtoms.get(i));
                i++;
            }

            // save completed residue
            if(isCorrectResidue(myAtoms.get(i-1))){
                //currentResidue.addToStructure(myStructure);
                addResidue(currentResidue);
            }


            if (i < myAtoms.size()) {
                // generate new Residue based on the Residue type
                currentResidue = constructResidueWithType(myAtoms.get(i));
                currentResidue.addAtom(myAtoms.get(i));
                currentResidueIndex = myAtoms.get(i).getAtomResidueIndex();
            }
        }
    }



    /**
     * filter Residues by Residue type
     * @param myResidue
     * @return
     */
    public ArrayList<AResidue> filterResiduesByType(AResidue myResidue){
        String myType = myResidue.getResidueType();
        ArrayList<AResidue> filteredList = new ArrayList<AResidue>();
        for (AResidue currentResidue: myResidues){
            if(myType == currentResidue.getResidueType()){
                filteredList.add(currentResidue);
            }
        }
        return filteredList;
    }

    /**
     * filter Shape3D by Residue type
     * @param myType
     * @return
     */
    public ArrayList<Shape3D> filterShape3DByType(String myType){
        ArrayList<Shape3D> filteredList = new ArrayList<Shape3D>();
        for (int i = 0; i < myResidues.size(); i++){
            if(myType.equals(myResidues.get(i).getResidueType())){
                filteredList.add(my3DResidueShapes.get(i));
            }
        }
        return filteredList;
    }

    /**
     * get Shapes3D based on boolean[] and keepTrue
     * @param myBooleans
     * @param keepTrue
     * @return
     */
    public ArrayList<Shape3D> filterShape3DByBoolean(boolean[] myBooleans, boolean keepTrue){
        int index = Math.min(myBooleans.length, myResidues.size());
        ArrayList<Shape3D> filteredList = new ArrayList<Shape3D>();
        for(int i = 0; i < index; i++){
            // if keeptrue, add at true, else add at false
            if(myBooleans[i] ^ !keepTrue){
                filteredList.add(my3DResidueShapes.get(i));
            }
        }
        return filteredList;
    }

    /**
     * get Shapes3D that paired or that didn't pair based on keepPaired
     * @param keepPaired
     * @return
     */
    public ArrayList<Shape3D> filterShape3DByPaired(boolean keepPaired){
        boolean[] myBooleans = new boolean[myPaired.length()];
        for(int i = 0; i < myPaired.length(); i++){
            myBooleans[i] = myPaired.charAt(i) =='(' || myPaired.charAt(i) == ')';
        }
        return filterShape3DByBoolean(myBooleans, keepPaired);
    }


    /**
     generates a String Sequence based on myResidues
     */
    public void generateSequence(){
        mySequence = "";
        for (AResidue currentResidue: myResidues){
            mySequence += currentResidue.getResidueType();
        }
    }

    /**
     * outdated pairing generation
     */
    public void generatePairedNussinov(){
        myPaired = new Nussinov(mySequence).getBracketNotation();
    }

    /**
     * generate pairing based on Watson Crick
     */
    public void generatePairedWatsonCrick(){
        myPaired = new WatsonCrick(myResidues).getMyDotBracketNotation();
    }



    public void generateMoleculeMesh(){

    }

    /**
     * generate Residue according to char
     *
     * @param a
     * @return
     */
    private AResidue constructResidueWithType(Atom a) {

        switch ((a.getAtomResidue().toUpperCase().charAt(0))) {
            case 'A':
                return new Adenine();
            case 'U':
                return new Uracil();
            case 'G':
                return new Guanine();
            case 'C':
                return new Cytosine();
            default:
                return null;
        }
    }

    /**
     * checks for correct Residue Type
     * @param currentAtom
     * @return
     */
    private boolean isCorrectResidue(Atom currentAtom){
        String name = currentAtom.getAtomResidue();
        return (name.equals("A") || name.equals("U") || name.equals("G") || name.equals("C"));
    }


}
