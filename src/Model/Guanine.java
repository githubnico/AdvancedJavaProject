package Model;

import javafx.geometry.Point3D;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Shape3D;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Deviltech on 21.12.2015.
 */
public class Guanine extends AResidue {

    public Guanine() {
        this.myAtoms = new HashMap<>();
    }


    @Override
    public int checkForPairing(WatsonCrick myWatsonCrick) {
        int myPairedIndex = -1;
        for(Cytosine currentCytosine: myWatsonCrick.getMyCytosines()) {
            Point3D PointGuanineO6 = this.getAtom("O6").toPoint3D();
            Point3D PointGuanineN1 = this.getAtom("N1").toPoint3D();
            Point3D PointGuanineH1 = this.getAtom("H1").toPoint3D();
            Point3D PointGuanineN2 = this.getAtom("N2").toPoint3D();
            Point3D PointGuanineH2 = this.getAtom("H21").toPoint3D();
            Point3D PointCytosineN4 = currentCytosine.getAtom("N4").toPoint3D();
            Point3D PointCytosineH4 = currentCytosine.getAtom("H41").toPoint3D();
            Point3D PointCytosineN3 = currentCytosine.getAtom("N3").toPoint3D();
            Point3D PointCytosineO2 = currentCytosine.getAtom("O2").toPoint3D();



            boolean isPaired = checkForLenghAndAngles(PointGuanineO6, PointCytosineH4, PointCytosineN4);
            isPaired = isPaired && checkForLenghAndAngles(PointCytosineN3, PointGuanineH1, PointGuanineN1);
            isPaired = isPaired && checkForLenghAndAngles(PointCytosineO2, PointGuanineH2, PointGuanineN2);

            // if lengh and angles are in range, return pair index
            if (isPaired) {
                myPairedIndex = currentCytosine.getIndexInArrayList();
                return myPairedIndex;
            }

        }
        return myPairedIndex;
    }

    @Override
    public void sortIntoArrayList(ArrayList<Adenine> myAdenines, ArrayList<Cytosine> myCytosines, ArrayList<Guanine> myGuanines, ArrayList<Uracil> myUracils) {
        myGuanines.add(this);
    }

    @Override
    public MeshView generateSugarMesh() {
        return generateGeneralMesh( myValues.RESIDUE_SUGAR_NAMES,  myValues.RESIDUE_SUGAR_FACES);
    }

    @Override
    public MeshView generateBaseMesh() {
        return generateGeneralMesh( myValues.RESIDUE_PURINE_BASE_NAMES,  myValues.RESIDUE_PURINE_BASE_FACES);

    }

    @Override
    public Shape3D generateLine() {
        return generateLine(myAtoms.get( myValues.RESIDUE_PURINE_LINE[0]), myAtoms.get( myValues.RESIDUE_PURINE_LINE[1]), myValues.LINE_WIDTH_SMALL);
    }

    @Override
    public void addToStructure(Structure myStructure) {
        myStructure.addResidue(this);
    }



}
