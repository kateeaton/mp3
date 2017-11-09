import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Parser parser = new Parser();
        ArrayList<ArrayList<String>> puzzle = parser.Parse("no_train.txt");
        Character[][][] data = parser.toMatrix(puzzle);
    }
}
