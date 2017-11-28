import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Vector;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //parse the training data and store in arrays yesData and noData
        Parser parser = new Parser();
        /*ArrayList<ArrayList<String>> puzzle = parser.Parse("dataFiles/no_train.txt");
        Character[][][] noData = parser.toMatrix(puzzle);
        ArrayList<ArrayList<String>> puzzle0 = parser.Parse("dataFiles/yes_train.txt");
        Character[][][] yesData = parser.toMatrix(puzzle0);

        //train the AI with the training data for no and implement laplace smoothing
        Train trainer = new Train();
        Double[][] noTrainingOutput;
        noTrainingOutput = trainer.trainInput(noData);
        Double k = 3.1;
        //System.out.println();
        //System.out.println(k);
        noTrainingOutput = trainer.laplaceSmoothing(noTrainingOutput, k, 2, noData.length);

        //train the AI with the training data for yes and implement laplace smoothing
        Train yesTrainer = new Train();
        Double[][] yesTrainingOutput;
        yesTrainingOutput = yesTrainer.trainInput(yesData);
        yesTrainingOutput = yesTrainer.laplaceSmoothing(yesTrainingOutput, k, 2, yesData.length);

        //Parse the no and yes tests
        ArrayList<ArrayList<String>> noTest = parser.Parse("dataFiles/no_test.txt");
        Character[][][] noTestData = parser.toMatrix(noTest);

        ArrayList<ArrayList<String>> yesTest = parser.Parse("dataFiles/yes_test.txt");
        Character[][][] yesTestData = parser.toMatrix(yesTest);

        //Test the no and yes tests, print the result to the console
        double probNo = ((double)noData.length / ((double)noData.length + (double)yesData.length));
        double probYes = 1 - probNo;
        Double percent = trainer.test(noTrainingOutput, yesTrainingOutput, noTestData, probNo, probYes);
        Double percent2 = yesTrainer.test(noTrainingOutput, yesTrainingOutput, yesTestData, probNo, probYes);*/
        //System.out.println(percent);
        //System.out.println(1-percent2);

        //Part1
        System.out.println("Starting Part1");
        Train1 digitTrainer = new Train1();
        Parser parse1 = new Parser();

        //Parse all training data
        ArrayList<ArrayList<String>> unsorted = parse1.parseDigits("dataFiles/train.txt");
        Character[][][] images = parse1.toMatrix(unsorted);
        int[] labels = parse1.digitLabelParse("dataFiles/trainl.txt");

        //train digits
        int[] digitTotals = digitTrainer.getDigitTotals(labels);
        Double[][][] TrainedData = digitTrainer.digitalTrain(images, labels);
        Double[] digitProbabilities = digitTrainer.normalizeDigitTotals(digitTotals, labels);
        //Parse test digits
        ArrayList<ArrayList<String>> testparsing = parser.parseDigits("dataFiles/testimages.txt");
        Character[][][] tests = parser.toMatrix(testparsing);
        int[] testlabels = parser.digitLabelParse("dataFiles/testlabels.txt");
        int[] testDigitTotals = digitTrainer.getDigitTotals(testlabels);

        Double[] bestk = new Double[100];
        //Laplace loop
        /*for (int k1 = 1; k1 < 101; k1++){
            TrainedData = digitTrainer.digitalTrain(images, labels);
            TrainedData = digitTrainer.laplaceSmoothing(TrainedData, (double)k1/10.00, 2, digitTotals);
            //test testing digits against trained digits
            int[] guesses = digitTrainer.guess(TrainedData, tests, digitProbabilities);
            Double[][] conMatrix = digitTrainer.generateConfusionMatrix(guesses, testlabels, testDigitTotals);

            bestk[k1-1] = digitTrainer.AddLeastErrorSum(conMatrix);
            System.out.println("K: " + k1); //+ " Sum: " + digitTrainer.AddSum(conMatrix));
        }*/
        int k1 = 1;//digitTrainer.bestK(bestk);
        //print results
        System.out.println("K: " + k1/10.0);
        TrainedData = digitTrainer.digitalTrain(images, labels);
        TrainedData = digitTrainer.laplaceSmoothing(TrainedData, k1/10.00, 2, digitTotals);
        int[] guesses = digitTrainer.guess(TrainedData, tests, digitProbabilities);
        Double[][] conMatrix = digitTrainer.generateConfusionMatrix(guesses, testlabels, testDigitTotals);


        for(int i = 0; i < 10; i ++){
            System.out.print(i + ": ");
            for(int j = 0; j < 10; j++){
                if(conMatrix[i][j] != 0){ System.out.printf("%10.5f", conMatrix[i][j]);}
                else{System.out.printf("          ");}
            }
            System.out.println();
        }

        digitTrainer.OddsRatio(TrainedData, 4, 9);
        digitTrainer.OddsRatio(TrainedData, 5, 3);
        digitTrainer.OddsRatio(TrainedData, 7, 9);
        digitTrainer.OddsRatio(TrainedData, 8, 3);
    }

}
