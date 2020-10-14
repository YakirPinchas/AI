import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class java_ex1 {
    // Members
    static UsefulEnums.SearchAlgorithms serachAlgo;
    static int boardSize;
    static Integer[][] initialState;
    /**
     * Read the input.txt file with the problem definitions,
     * runs the suitable search algorithm and writes the results
     * to output.txt file
     * @param args command line arguments
     */
    public static void main(String[] args) {
        getProblemDefinesFromFile("input.txt");
        ISearchAlgo searchAlgorithm = null;
        switch (serachAlgo) {
            case BFS:
                searchAlgorithm = new BFS(initialState);
                break;
            case IDS:
                searchAlgorithm = new IDS(initialState);
                break;
            case A_STAR:
                searchAlgorithm = new A_Star(initialState);
                break;
                default:
                    System.exit(1);
        }
        //run the specific algorithm
        searchAlgorithm.runSearchAlgo();
        System.out.println(searchAlgorithm.getSolutionPath());
        //write the result to file "output.txt"
        writeResultsToFile(searchAlgorithm, "output.txt");
        return;
    }
    /**
     * Read the input.txt file with the problem definitions:
     * algorithm type, board size, board initial state. 
     * @param fileName is the file with data
     */
    public static void getProblemDefinesFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Couldn't file input file");
            System.exit(1);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            line = br.readLine().trim();
            serachAlgo = UsefulEnums.SearchAlgorithms.values()[Integer.parseInt(line) - 1];
            line = br.readLine().trim();
            boardSize = Integer.parseInt(line);
            line = br.readLine().trim();
            List<Integer> ints = Arrays.stream(line.split("-"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            initialState = new Integer[boardSize][boardSize];
            for (int j = 0; j < boardSize; j++) {
                for (int k = 0; k < boardSize; k++) {
                    initialState[j][k] = ints.get(boardSize * j + k);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
    /**
     * write result to output file
     * @param algorithm the search algorithm according to input.txt
     * @param fileName is file name of result
     */
    public static void writeResultsToFile(ISearchAlgo algorithm, String fileName) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(algorithm.getSolutionPath() + " ");
        sb.append(algorithm.getClosedListSize() + " ");
        sb.append(algorithm.getCost());
        pw.write(sb.toString());
        pw.close();
    }
}