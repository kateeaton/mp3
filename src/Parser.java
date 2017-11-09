import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;

//File used to parse the txt files of the mazes
public class Parser {

    public ArrayList<ArrayList<String>> Parse( String args) throws FileNotFoundException {
        //// some code from https://www.mkyong.com/java/java-read-a-text-file-line-by-line/
        ArrayList<String> list = new ArrayList<>();
        ArrayList<ArrayList<String>> retVal = new ArrayList<>();

        try {

            File f = new File(args);

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";

            System.out.println("Reading file using Buffered Reader");
            Integer emptyLines = 0;
            boolean first = true;
            while ((readLine = b.readLine()) != null) {
                System.out.println(readLine);
                if(readLine.length() == 0){
                    emptyLines++;
                }
                if(emptyLines >= 3){
                    emptyLines = 0;
                    if(first){
                        first = false;
                    }
                    else {
                        list.remove(0);
                    }
                    retVal.add(list);
                    list = new ArrayList<>();
                    list.add(readLine);
                }
                else if(emptyLines == 0){
                    if(readLine.length() > 0){
                        list.add(readLine);
                    }

                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return retVal;

    }
    public Character[][][] toMatrix(ArrayList<ArrayList<String>> arg){
        Integer i = arg.size();
        Integer j = arg.get(0).size();
        Integer k = arg.get(0).get(0).length();
        Character[][][] retVal = new Character[i][j][k];
        for(int ii=0; ii<i; ii++){
            for(int jj=0; jj<j; jj++){
                for(int kk = 0; kk<k; kk++){
                    retVal[ii][jj][kk] = arg.get(ii).get(jj).charAt(kk);
                }
            }
        }
        return retVal;
    }
    public ArrayList<Integer> findP(ArrayList<String> arg){
        ArrayList<Integer> retVal = new ArrayList<>();
        for(int i=0; i<arg.size(); i++){
            for(int j=0; j< arg.get(i).length(); j++){
                if( arg.get(i).charAt(j) == 'P') {
                    retVal.add(i);
                    retVal.add(j);
                }
            }
        }
        return retVal;
    }
    public ArrayList<Integer> findE(ArrayList<String> arg){
        ArrayList<Integer> retVal = new ArrayList<>();
        for(int i=0; i<arg.size(); i++){
            for(int j=0; j< arg.get(i).length(); j++){
                if( arg.get(i).charAt(j) == '.') {
                    retVal.add(i);
                    retVal.add(j);
                }
            }
        }
        return retVal;
    }
    public ArrayList<ArrayList<Integer>> findMultipleE(ArrayList<String> arg){
        ArrayList<ArrayList<Integer>> retVal = new ArrayList<>();
        for(int i=0; i<arg.size(); i++){
            for(int j=0; j< arg.get(i).length(); j++){
                if( arg.get(i).charAt(j) == '.') {
                    ArrayList<Integer> pair = new ArrayList<>();
                    pair.add(i);
                    pair.add(j);
                    retVal.add(pair);
                }
            }
        }
        return retVal;
    }

//    public ArrayList<ArrayList<CSP>> getCSP(ArrayList<String> arg, Integer size){
//        ArrayList<ArrayList<CSP>> retVal = new ArrayList<>();
//        Character[][] charMap = new Character[size][size];
//        ArrayList<Character> domain = new ArrayList<>();
//        for(int i=0; i<size; i++){
//            ArrayList<CSP> arr = new ArrayList();
//            retVal.add(arr);
//            for(int j=0; j<size; j++){
//                CSP temp = new CSP();
//                temp.x = i;
//                temp.y = j;
//                if(arg.get(i).charAt(j) != '_'){
//                    temp.initialValue = true;
//                    if(domain.indexOf(arg.get(i).charAt(j)) < 0){
//                        domain.add(arg.get(i).charAt(j));
//                    }
//                }
//                else{
//                    temp.initialValue = false;
//                    temp.parent = false;
//                }
//                temp.setValue(arg.get(i).charAt(j));
//                retVal.get(i).add(temp);
//                charMap[i][j] = toLowerCase(temp.getValue());
//                //charMap[i][j] = toLowerCase(charMap[i][j]);
//                //retVal[i][j].setValue(arg.get(i).charAt(j));
//            }
//        }
//        charMap[0][0] = toUpperCase(charMap[0][0]);
//        for(int i=0; i<size; i++){
//            for(int j=0; j<size; j++){
//                retVal.get(i).get(j).setDomain(domain);
//                retVal.get(i).get(j).updateMap(charMap);
//                for(int x = 0; x<domain.size(); x++){
//                    retVal.get(i).get(j).visited.add(false);
//                }
//            }
//        }
//        return retVal;
//    }


    public Parser() throws FileNotFoundException {
    }

}
