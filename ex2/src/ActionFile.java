import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * class of actions about files (read/write)
 */
public class ActionFile {
    /**
     * function that get data from "train.txt" and move them to arr of arr of string
     * @param dataSource is data from train data
     * @return arr of arr of string with data from "train.txt"
     */
    public String[][] getData(String dataSource) {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(dataSource));
        } catch (Exception e) {
            System.out.println("File \"" + dataSource + "\" not found");
            return null;
        }
        ArrayList<ArrayList<String>> allLines = new ArrayList<>();
        String currentLine;
        try {
            //Read from file line and line and add to a list
            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] items = currentLine.split("\t");
                List<String> arr = Arrays.asList(items);
                ArrayList<String> values = new ArrayList<>(arr);
                allLines.add(values);
            }
        } catch (Exception e) {
            System.out.println("Problem reading \"" + dataSource + "\"");
            return null;
        }
        // try to close the file reader
        try {
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Problem closing link to \"" + dataSource + "\"");
            return null;
        }
        return moveData(allLines);
    }
    /**
     * function that move data from list to arr
     * @param allLines is list
     * @return array
     */
    public String[][] moveData(ArrayList<ArrayList<String>> allLines) {
        int row = allLines.size();
        int col = allLines.get(0).size();
        String[][] linesData = new String[row][col];
        for (int i = 0; i < allLines.size(); i++) {
            for (int j = 0; j < allLines.get(i).size(); j++) {
                linesData[i][j] = allLines.get(i).get(j);
            }
        }
        return linesData;
    }

    /**
     * function that writes to output.txt
     * @param results is the results of running this decision algorithm
     * @param accuracies is the accuracies of success running this decision algorithm
     * @param sumAlgo is the amount of decision algorithm
     * @return true if success , false else.
     */
    public boolean writeResultsToFile(String[][] results, double[] accuracies, int sumAlgo) {
        File file = new File("output.txt");
        if(file.exists()) {
            file.delete();
        }

        int col = sumAlgo, rowCount = results[0].length;
        StringBuilder textOutputFile = new StringBuilder();
        textOutputFile.append("Num\tDT\tKNN\tnaiveBase\n");

        //we need to write the results from second line
        int row = 1;

        for(int i = 0; i < rowCount; i++) {
            textOutputFile.append(row).append("\t");
            for(int j = 0; j < col - 1; j++) {
                textOutputFile.append(results[j][i]).append("\t");
            }
            textOutputFile.append(results[col - 1][i]).append("\n");
            row++;
        }

        //in end of file , the accuracy of algorithm
        textOutputFile.append("\t").append(String.format("%.2f",accuracies[0]))
                .append("\t").append(String.format("%.2f",accuracies[1]))
                .append("\t").append(String.format("%.2f",accuracies[2]));

        try(java.io.FileWriter fw = new java.io.FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw))
        {
            // write to the file
            bw.write(textOutputFile.toString());
        } catch (Exception e) {
            System.out.println("Problem writing output file");
            return false;
        }
        return true;
    }
    /**
     * function that write text to txt file
     * @param filePath is the name of txt file
     * @param text is the text the i want to write
     * @return true if success , else false
     */
    public boolean write(String filePath, String text) {
        File file = new File(filePath);
        if(file.exists()) {
            file.delete();
        }
        try(java.io.FileWriter fw = new java.io.FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw))
        {
            // write to the file
            bw.write(text);
        } catch (Exception e) {
            System.out.println("Problem writing output file");
            return false;
        }
        return true;
    }
}
