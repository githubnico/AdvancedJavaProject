package Model;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

/**
 * Created by Deviltech on 20.01.2016.
 */
public class myValues {



    /**
     * Read in
     */
    // separator indices for pdb standard
    public static final int PDB_INDICES[] = new int[]{0, 6, 11, 16, 17, 20, 22, 26, 27, 38, 46, 54, 60, 66, 76, 78};

    /**
     * General Labeling
     */
    // Strings
    public static final String PANE_SEQUENCE = "Sequence";

    public static final String PANE_2D_STRUCTURE= "2D Structure";

    public static final String PANE_3D_STRUCTURE = "3D Structure";

    public static final String FILE_NOT_FOUND = "File not found.";

    public static final String NO_FILE_SELECTED = "No file selected";

    public static final String MENU_FILE = "File";

    public static final String MENU_SEQUENCE = "Sequence";

    public static final String MENU_2D_STRUCTURE = "2D Structure";

    public static final String MENU_3D_STRUCTURE = "3D Structure";

    public static final String MENU_OPEN = "Open";

    public static final String MENU_PAIRING = "Watson-Crick Pairing";

    public static final String MENU_RESET_VIEW = "Reset View";

    public static final String MENU_CLEAR = "Clear View";

    public static final String MENU_COLORING = "Coloring";

    public static final String MENU_AGCU = "A/G/C/U";

    public static final String MENU_PURINE_PYRIMIDINE = "AG/CU";

    public static final String MENU_PAIRED = "Paired";

    public static final String MENU_GREEN_PHOSPHORUS = "Green Phosphorus";


    /**
     * Draw values
     */

    // Integers

    public static final int NUSSINOV_ITERATIONS = 20;

    // Doubles

    public static final  double NODESIZE = 10;

    public static final double NODE_VALUE = 7;

    public static final double LINE_WIDTH_SMALL = 0.06;

    public static final double LINE_WIDTH_MEDIUM = 0.35;

    public static final double PHOSPHORUS_WIDTH = 2.0;

    // Standard: 140.0
    public static final double HBOND_MIN_ANGLE = 110.0;

    // Standard: 3.0
    public static final double HBOND_MAX_DISTANCE = 5.0;

    // Materials

    public static final PhongMaterial MATERIAL_SEA_GREEN = new PhongMaterial(Color.SEAGREEN);

    public static final PhongMaterial MATERIAL_GREEN = new PhongMaterial((Color.GREEN));

    public static final PhongMaterial MATERIAL_LIGHT_GREEN = new PhongMaterial((Color.LIGHTGREEN));

    public static final PhongMaterial MATERIAL_BLACK = new PhongMaterial((Color.BLACK));

    public static final PhongMaterial MATERIAL_DARK_RED = new PhongMaterial((Color.DARKRED));

    public static final PhongMaterial MATERIAL_RED = new PhongMaterial((Color.RED));

    public static final PhongMaterial MATERIAL_ORANGE = new PhongMaterial((Color.ORANGE));

    public static final PhongMaterial MATERIAL_BLUE = new PhongMaterial((Color.BLUE));

    public static final PhongMaterial MATERIAL_DARK_BLUE = new PhongMaterial((Color.DARKBLUE));

    public static final PhongMaterial MATERIAL_YELLOW = new PhongMaterial((Color.YELLOW));

    public static final PhongMaterial MATERIAL_GRAY = new PhongMaterial((Color.GRAY));






    /**
     * Residue mesh values
     */

    // names for structures
    public static final String[] RESIDUE_PYRIMIDINE_LINE = {"N1", "C1\'"};

    public static final String[] RESIDUE_PURINE_LINE = {"N9", "C1\'"};

    public static final String[] RESIDUE_PURINE_BASE_NAMES = {"C4", "C5", "C6", "N1", "C2", "N3", "N9", "C8", "N7"};

    public static final String[] RESIDUE_PYRIMIDINE_BASE_NAMES = {"N1", "C2", "N3", "C4", "C5", "C6"};

    public static final String[] RESIDUE_SUGAR_NAMES = {"C1\'", "C2\'", "C3\'", "C4\'", "O4\'"};

    public static final int RESIDUE_PURINE_BASE_FACES[] = {
            0, 0, 1, 0, 5, 0, // front
            1, 0, 2, 0, 5, 0,
            2, 0, 3, 0, 5, 0,
            3, 0, 4, 0, 5, 0,
            0, 0, 6, 0, 7, 0,
            0, 0, 7, 0, 8, 0,
            0, 0, 8, 0, 1, 0,
            0, 0, 5, 0, 1, 0, // back
            1, 0, 5, 0, 2, 0,
            2, 0, 5, 0, 3, 0,
            3, 0, 5, 0, 4, 0,
            0, 0, 7, 0, 6, 0,
            0, 0, 8, 0, 7, 0,
            0, 0, 1, 0, 8, 0

    };

    public static final int RESIDUE_PYRIMIDINE_BASE_FACES[] = {
            0, 0, 1, 0, 5, 0,// front
            1, 0, 2, 0, 5, 0,
            2, 0, 3, 0, 5, 0,
            3, 0, 4, 0, 5, 0,
            0, 1, 5, 0, 1, 0,//back
            1, 0, 5, 0, 2, 0,
            2, 0, 5, 0, 3, 0,
            3, 0, 5, 0, 4, 0
    };

    public static final int RESIDUE_SUGAR_FACES[] = {
            0, 0, 1, 0, 4, 0, //front
            1, 0, 2, 0, 4, 0,
            2, 0, 3, 0, 4, 0,
            0, 0, 4, 0, 1, 0,//back
            1, 0, 4, 0, 2, 0,
            2, 0, 4, 0, 3, 0
    };



}
