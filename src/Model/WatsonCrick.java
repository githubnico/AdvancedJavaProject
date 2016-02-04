package Model;

import java.util.ArrayList;

/**
 * Created by Deviltech on 04.02.2016.
 */
public class WatsonCrick {

    public final ArrayList<AResidue> myResidues;


    private  ArrayList<Adenine> myAdenines;
    private ArrayList<Cytosine> myCytosines;
    private ArrayList<Guanine> myGuanines;
    private ArrayList<Uracil> myUracils;

    /**
     * Constructor
     * @param myResidues
     */
    public  WatsonCrick(ArrayList<AResidue> myResidues){
        this.myResidues = myResidues;
        this. myAdenines = new ArrayList<Adenine>();
        this.myCytosines = new ArrayList<Cytosine>();
        this.myGuanines = new ArrayList<Guanine>();
        this.myUracils = new ArrayList<Uracil>();
    }

    /**
     * Generates Watson-Crick Pairs
     * @return
     */
    public int[] generatePairs(){
        int[] myPairs = new int[myResidues.size()];

        // Sort by base to lower runtime
        for(int i = 0; i < myResidues.size(); i++){
            AResidue currentResidue = myResidues.get(i);
            // Set index in general ArrayList to find it later
            currentResidue.setIndexInArrayList(i);

            // Sort by base into different ArrayLists
            currentResidue.sortIntoArrayList(myAdenines, myCytosines, myGuanines, myUracils);
        }
        // Check for pairs
        for(int i = 0; i < myResidues.size(); i++){
            AResidue currentResidue = myResidues.get(i);
            int myPairedIndex = currentResidue.checkForPairing(this);
            // if myPairedIndex is -1, then no Pair, otherwise returns index of pair
            if (myPairedIndex >= 0){
                // Set Pairs
                myPairs[i] = myPairedIndex;
                myPairs[myPairedIndex] = i;
            }
        }

        return myPairs;
    }


    // Getters
    public ArrayList<Adenine> getMyAdenines() {
        return myAdenines;
    }

    public ArrayList<Cytosine> getMyCytosines() {
        return myCytosines;
    }

    public ArrayList<Guanine> getMyGuanines() {
        return myGuanines;
    }

    public ArrayList<Uracil> getMyUracils() {
        return myUracils;
    }
}
