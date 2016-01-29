package Model;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.Shape3D;

import java.util.HashMap;

/**
 * Created by Deviltech on 21.12.2015.
 */
public class Cytosine extends AResidue {

    public Cytosine() {
        this.myAtoms = new HashMap<>();
    }


    @Override
    public MeshView generateSugarMesh() {
        return generateGeneralMesh( myValues.RESIDUE_SUGAR_NAMES,  myValues.RESIDUE_SUGAR_FACES);
    }

    @Override
    public MeshView generateBaseMesh() {
        return generateGeneralMesh( myValues.RESIDUE_PYRIMIDINE_BASE_NAMES,  myValues.RESIDUE_PYRIMIDINE_BASE_FACES);

    }

    @Override
    public Shape3D generateLine() {
        return generateLine(myAtoms.get( myValues.RESIDUE_PYRIMIDINE_LINE[0]), myAtoms.get( myValues.RESIDUE_PYRIMIDINE_LINE[1]), myValues.LINE_WIDTH_SMALL);
    }

    @Override
    public void addToStructure(Structure myStructure) {
        myStructure.addResidue(this);
    }


}
