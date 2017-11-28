public class Train1 {
    /* Input: arg - 3-d character array representing all of the training data
    *  Output: retVal - 2-d Double array which holds the number of times a non-space character was detected at the given coordinates
    *  Description: This function takes the training data and iterates through all data samples, and adds the number one to the output
    *  matrix every time a non space character is detected. The output of this function is used as input to the laplaceSmoothing function
    * */
    public Double[][][] digitalTrain(Character[][][] arg, int[] label){
        Integer z = arg.length;
        Integer y = arg[0].length;
        Integer x = arg[0][0].length;
        //System.out.println("Z: " + z + "\nY: " + y + "\nX: " + x);
        Double[][][] trained = new Double[10][y][x];

        //initialize
        for(int i = 0; i < 10; i++){
            for(int n=0; n<y; n++) {
                for (int m = 0; m < x; m++) {
                    trained[i][n][m] = 0.0;
                }
            }
        }

        //mark gray/dark areas
        for(int i=0; i<z; i++) {
            //System.out.println("i: " + i);
            int digit = label[i];
            //System.out.println("Label: " + digit);
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < x; k++) {
                    if (arg[i][j][k] == '#' || arg[i][j][k] == '+')
                        trained[digit][j][k] += 1.0;
                }
            }
        }
        return trained;
    }

    public int[] getDigitTotals(int[] labels){
        int[] retVal = new int[10];
        for(int i = 0; i < 10; i++){
            retVal[i] = 0;
        }
        for(int i = 0; i < labels.length; i++){
            retVal[labels[i]] += 1;
        }
        return retVal;
    }
    /* Input: arg - 2-d Double array which holds the number of times a non-space character was detected at the given coordinates
    *  k - the k value used for laplace smoothing
    *  v - the v value used for laplace smoothing
    *  size - the number of samples from the training data
    *  Output: retVal - 2-d Double array which holds the laplace-smoothed training data
    *  Description: this function takes in raw data from the training and implements laplace smoothing by adding k to the numerator and
    *  k*v to the denominator.
     */
    public Double[][][] laplaceSmoothing(Double[][][] arg, Double k, Integer v, int[] digitSize){
        Integer y = arg[0].length;
        Integer x = arg[0][0].length;
        Double[][][] retVal = new Double[10][y][x];
        for(int m = 0; m < 10; m ++){
            for(int i=0; i<y; i++){
                for(int j=0; j<x; j++){
                    retVal[m][i][j] = arg[m][i][j] + k;
                    retVal[m][i][j] = (retVal[m][i][j] / (k*v + digitSize[m]));
                }
            }
        }
        return retVal;
    }

    public Double[] normalizeDigitTotals(int[] digitTotals, int[] labels){
        Double[] retVal = new Double[10];
        for(int i = 0; i < 10; i++){
            retVal[i] = ((double)digitTotals[i] / (double)labels.length);
        }
        return retVal;
    }
    /* Input:
     * probNo - the probability that a no is seen based on the training data
     * probYes - the probability that a yes is seen based on the training data
     * Output: retVal - returns the number of 'no's that are predicted over the number of test samples. when
     * looking for the number of 'yes's, subtract retVal from 1.
     */
    public int[] guess(Double[][][] trainedDigits, Character[][][] tests, Double[] probDigit){
        Double z = (double)tests.length;
        int[] retVal = new int[tests.length];
        Integer y = tests[0].length;
        Integer x = tests[0][0].length;
        //System.out.println("X: " + x + " Y: " + y + " Z: " + z);
        //initialize chance with frequency in training
        Double[] digitLikelihoods = resetLikelihoods(probDigit);

        Double[] pixelMatch = new Double[10];
        //m -- digit being tested
        //i -- test being tested
        for(int i = 0 ; i < z; i++){
            for(int j = 0; j < y; j++) {
                for (int k = 0; k < x; k++) {
                    for (int m = 0; m < 10; m++) {
                        //get the percent chance to match against digit M
                        //System.out.println("M: " + m + " (i = " + i + ")");
                        pixelMatch[m] = trainedDigits[m][j][k];
                        if (tests[i][j][k] == ' ') {
                            pixelMatch[m] = 1 - pixelMatch[m];
                        }
                        digitLikelihoods[m] += Math.log(pixelMatch[m]);
                    }
                }
            }
            retVal[i] = bestGuess(digitLikelihoods);
            digitLikelihoods = resetLikelihoods(probDigit);
        }

        return retVal;
    }

    Double[] resetLikelihoods(Double[] P){
        Double retVal[] = new Double[10];
        for(int i = 0; i < 10; i++){
            retVal[i] = P[i];
        }
        return retVal;
    }

    int bestGuess(Double[] likelihood){
        int total = likelihood.length;
        int maxIndex = 0;
        for(int i = 0; i < total; i++){
            if(likelihood[i] > likelihood[maxIndex]){ maxIndex = i;}
        }
        return maxIndex;
    }

    int bestK(Double[] likelihood){
        int total = likelihood.length;
        int minIndex = 0;
        for(int i = 0; i < total; i++){
            if(likelihood[i] < likelihood[minIndex]){ minIndex = i;}
        }
        return minIndex;
    }

    public Double AddSum( Double[][] matrix){
        int x = matrix.length;
        int y = matrix[0].length;

        Double sum = 0.0;
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                sum += matrix[i][j];
            }
        }
        return sum;
    }

    public Double[][] generateConfusionMatrix(int[] Guess, int[] True, int[] DigitCount){
        Double[][] retVal = new Double[10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j ++){
                retVal[i][j] = (double)0;
            }
        }

        for(int i = 0; i < Guess.length; i++){
            retVal[True[i]][Guess[i]] += 1;
        }

        for(int i = 0; i < 10; i++){
            int rowTotal = DigitCount[i];
            for(int j = 0; j < 10; j ++){
                retVal[i][j] = retVal[i][j]/rowTotal;
            }
        }
        return retVal;
    }

    public void OddsRatio(Double[][][] trainedMatrix, int compare1, int compare2){
        Double[][] retVal = new Double[28][28];
        System.out.println();
        for(int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                retVal[i][j] = (trainedMatrix[compare1][i][j] / trainedMatrix[compare2][i][j]);
                if(retVal[i][j] < -3){System.out.print('5');}
                else if(retVal[i][j] < -2){System.out.print('4');}
                else if(retVal[i][j] < -1){ System.out.print('3');}
                else if(retVal[i][j] < 0){System.out.print('2');}
                else if(retVal[i][j] < 1){System.out.print('1');}
                else {System.out.print(' ');}
            }
            System.out.println();
        }
    }
}