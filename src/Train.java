public class Train {
    /* Input: arg - 3-d character array representing all of the training data
    *  Output: retVal - 2-d Double array which holds the number of times a non-space character was detected at the given coordinates
    *  Description: This function takes the training data and iterates through all data samples, and adds the number one to the output
    *  matrix every time a non space character is detected. The output of this function is used as input to the laplaceSmoothing function
    * */
    public Double[][] trainInput(Character[][][] arg){
        Integer z = arg.length;
        Integer y = arg[0].length;
        Integer x = arg[0][0].length;
        Double[][] retVal = new Double[y][x];
        for(int n=0; n<y; n++){
            for(int m=0; m<x; m++){
                retVal[n][m] = 0.0;
            }
        }
        for(int i=0; i<z; i++){
            for(int j=0; j<y; j++){
                for(int k=0; k<x; k++){
                    if(arg[i][j][k] == '%') {
                        retVal[j][k] += 1.0;
                    }
                }
            }
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
    public Double[][] laplaceSmoothing(Double[][]arg, Double k, Integer v, Integer size){
        Integer y = arg.length;
        Integer x = arg[0].length;
        Double[][] retVal = new Double[y][x];
        for(int i=0; i<y; i++){
            for(int j=0; j<x; j++){
                retVal[i][j] = arg[i][j] + k;
                retVal[i][j] = (retVal[i][j] / (k*v + size));
            }
        }
        return retVal;
    }
    /* Input: no - the laplace smoothed no training data
     * yes - the laplace smoothed yes training data
     * test - the test samples
     * probNo - the probability that a no is seen based on the training data
     * probYes - the probability that a yes is seen based on the training data
     * Output: retVal - returns the number of 'no's that are predicted over the number of test samples. when
     * looking for the number of 'yes's, subtract retVal from 1.
     */
    public Double test(Double[][] no, Double[][] yes, Character[][][] test, Double probNo, Double probYes){
        Double retVal;
        Double numNo = 0.0;
        Double z = (double)test.length;
        Integer y = test[0].length;
        Integer x = test[0][0].length;
        Double noLikelihood = Math.log(probNo);
        Double yesLikelihood = Math.log(probYes);

        for(int i=0; i<z; i++){
            for(int j=0; j<y; j++){
                for(int k=0; k<x; k++){
                    Double noPercent = no[j][k];
                    Double yesPercent = yes[j][k];
                    if(test[i][j][k] == ' '){
                        noPercent = 1-noPercent;
                        yesPercent = 1-yesPercent;
                    }
                    noLikelihood += Math.log(noPercent);
                    yesLikelihood += Math.log(yesPercent);
                }
            }
            if(noLikelihood > yesLikelihood){
                numNo++;
            }
            noLikelihood = Math.log(probNo);
            yesLikelihood = Math.log(probYes);
        }
        retVal = numNo / z;
        return retVal;
    }
}
