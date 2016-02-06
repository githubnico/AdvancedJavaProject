package Model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Deviltech on 04.02.2016.
 */
public class WatsonCrick {

    public final ArrayList<AResidue> myResidues;


    private  ArrayList<Adenine> myAdenines;
    private ArrayList<Cytosine> myCytosines;
    private ArrayList<Guanine> myGuanines;
    private ArrayList<Uracil> myUracils;

    private int[] myPairs;
    private char[]  myDotBracketNotation;

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
        myPairs = new int[myResidues.size()];
        // Fill array with "."
        myDotBracketNotation = new char[myResidues.size()];
        Arrays.fill(myDotBracketNotation, '.');
    }

    /**
     * Generates Watson-Crick Pairs
     * @return
     */
    private void generatePairs(){
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
            if (myPairedIndex >= 0) {
                // Set Pairs
                myPairs[i] = myPairedIndex;
                myPairs[myPairedIndex] = i;
                // Set Dot Bracket notation, if both Residues are empty
                if (Character.toString(myDotBracketNotation[myPairedIndex]).equals(".") && Character.toString(myDotBracketNotation[i]).equals(".")) {
                    if (myPairs.length > myPairedIndex) {
                        myDotBracketNotation[myPairedIndex] = ')';
                        myDotBracketNotation[i] = '(';
                    } else {
                        myDotBracketNotation[i] = ')';
                        myDotBracketNotation[myPairedIndex] = '(';
                    }
                }
            }
        }
    }

    /**
     * generates the dot bracket nonation
     * @return
     */
    public String getMyDotBracketNotation(){
        generatePairs();
        return new String(myDotBracketNotation);
    }

    /**
     * generates the pair notaion
     * @return
     */
    public int[] getMyPairs(){
        generatePairs();
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
