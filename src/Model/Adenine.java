package Model;

import javafx.geometry.Point3D;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Shape3D;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Deviltech on 21.12.2015.
 */
public class Adenine extends AResidue {

    public Adenine() {
        this.myAtoms = new HashMap<>();
    }


    @Override
    public int checkForPairing(WatsonCrick myWatsonCrick) {
        int myPairedIndex = -1;
        for(Uracil currentUracil: myWatsonCrick.getMyUracils()) {
            Point3D PointUracilO4 = currentUracil.getAtom("O4").toPoint3D();
            Point3D PointUracilN3 = currentUracil.getAtom("N3").toPoint3D();
            Point3D PointUracilH3 = currentUracil.getAtom("H3").toPoint3D();
            Point3D PointAdenineN6 = this.getAtom("N6").toPoint3D();
            Point3D PointAdenineH6 = this.getAtom("H61").toPoint3D();
            Point3D PointAdenineN1 = this.getAtom("N1").toPoint3D();

            // check for correct lengths and angles for 2 H bonds
            boolean isPaired = checkForLenghAndAngles(PointUracilO4, PointAdenineH6, PointAdenineN6);
            isPaired = isPaired && checkForLenghAndAngles(PointAdenineN1, PointUracilH3, PointUracilN3);

            // if lengh and angles are in range, return pair index
            if (isPaired) {
                myPairedIndex = currentUracil.getIndexInArrayList();
                return myPairedIndex;
            }

        }
        return myPairedIndex;
    }

    @Override
    public void sortIntoArrayList(ArrayList<Adenine> myAdenines, ArrayList<Cytosine> myCytosines, ArrayList<Guanine> myGuanines, ArrayList<Uracil> myUracils) {
        myAdenines.add(this);
    }

    @Override
    public MeshView generateSugarMesh() {
        return generateGeneralMesh(myValues.RESIDUE_SUGAR_NAMES, myValues.RESIDUE_SUGAR_FACES);
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
