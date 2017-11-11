import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //parse the training data and store in arrays yesData and noData
        Parser parser = new Parser();
        ArrayList<ArrayList<String>> puzzle = parser.Parse("no_train.txt");
        Character[][][] noData = parser.toMatrix(puzzle);
        ArrayList<ArrayList<String>> puzzle0 = parser.Parse("yes_train.txt");
        Character[][][] yesData = parser.toMatrix(puzzle0);

        //train the AI with the training data for no and implemnt laplace smoothing
        Train trainer = new Train();
        Double[][] noTrainingOutput;
        noTrainingOutput = trainer.trainInput(noData);
        Double k = 3.1;
        System.out.println();
        System.out.println(k);
        noTrainingOutput = trainer.laplaceSmoothing(noTrainingOutput, k, 2, noData.length);

        //train the AI with the training data for yes and implemnt laplace smoothing
        Train yesTrainer = new Train();
        Double[][] yesTrainingOutput;
        yesTrainingOutput = yesTrainer.trainInput(yesData);
        yesTrainingOutput = yesTrainer.laplaceSmoothing(yesTrainingOutput, k, 2, yesData.length);

        //Parse the no and yes tests
        ArrayList<ArrayList<String>> noTest = parser.Parse("no_test.txt");
        Character[][][] noTestData = parser.toMatrix(noTest);

        ArrayList<ArrayList<String>> yesTest = parser.Parse("yes_test.txt");
        Character[][][] yesTestData = parser.toMatrix(yesTest);

        //Test the no and yes tests, print the result to the console
        double probNo = ((double)noData.length / ((double)noData.length + (double)yesData.length));
        double probYes = 1 - probNo;
        Double percent = trainer.test(noTrainingOutput, yesTrainingOutput, noTestData, probNo, probYes);
        Double percent2 = yesTrainer.test(noTrainingOutput, yesTrainingOutput, yesTestData, probNo, probYes);
        System.out.println(percent);
        System.out.println(1-percent2);

    }
}
