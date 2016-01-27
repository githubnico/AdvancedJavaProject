package Model;

import java.util.ArrayList;

/**
 * Created by Deviltech on 10.01.2016.
 */
public class Structure {

    private ArrayList<AResidue>  myAdenines;
    private ArrayList<AResidue> myCytosines;
    private ArrayList<AResidue> myGuanines;
    private ArrayList<AResidue> myUracils;

    private ArrayList<AResidue> myResidues;


    // Constructor
    public Structure() {
        this.myAdenines = new ArrayList<AResidue>();
        this.myCytosines = new ArrayList<AResidue>();
        this.myGuanines = new ArrayList<AResidue>();
        this.myUracils = new ArrayList<AResidue>();

        this.myResidues = new ArrayList<AResidue>();

    }

    // Setters for every Residue
    public void addResidue(Adenine myResidue){
        myAdenines.add(myResidue);
        myResidues.add(myResidue);
    }

    public void addResidue(Guanine myResidue){
        myGuanines.add(myResidue);
        myResidues.add(myResidue);
    }

    public void addResidue(Cytosine myResidue){
        myCytosines.add(myResidue);
        myResidues.add(myResidue);
    }

    public void addResidue(Uracil myResidue){
        myUracils.add(myResidue);
        myResidues.add(myResidue);
    }

    // Getters for Resides
    public ArrayList<AResidue> getMyAdenines() {
        return myAdenines;
    }

    public ArrayList<AResidue> getMyCytosines() {
        return myCytosines;
    }

    public ArrayList<AResidue> getMyGuanines() {
        return myGuanines;
    }

    public ArrayList<AResidue> getMyUracils() {
        return myUracils;
    }

    public ArrayList<AResidue> getMyResidues() {
        return myResidues;
    }


}
