package Model;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Deviltech on 13.12.2015.
 */
public class PDB_Reader {


    public ArrayList<Atom> readInFile(File filePath) throws FileNotFoundException {

        // Checks if file exists
        if (!filePath.exists()) {
            throw new FileNotFoundException(myValues.FILE_NOT_FOUND + filePath);
        }

        ArrayList<Atom> myAtoms = new ArrayList<>();

        try {
            BufferedReader input = new BufferedReader(new FileReader(filePath));
            try {
                String line = null;
                while ((line = input.readLine()) != null) {
                    if(line.startsWith("ATOM")){
                        String[] currentValues = splitByIndices(line, myValues.PDB_INDICES);
                        myAtoms.add(new Atom(currentValues));
                    }

                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return myAtoms;

    }


    /**
     * Splits a string according given indices
     * @param myString
     * @param indices
     * @return
     */
    public String[] splitByIndices(String myString, int[] indices){
        String[] result = new String[indices.length-1];
        for(int i= 0; i< indices.length-1; i++){
            result[i] = myString.substring(indices[i], indices[i+1]);
        }
        return result;
    }

}
