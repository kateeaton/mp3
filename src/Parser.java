import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;
import java.nio.file.Files;
import java.nio.file.Paths;

//File used to parse the txt files of the mazes
public class Parser {

    public ArrayList<ArrayList<String>> Parse( String args) throws FileNotFoundException {
        //// some code from https://www.mkyong.com/java/java-read-a-text-file-line-by-line/
        ArrayList<String> list = new ArrayList<>();
        ArrayList<ArrayList<String>> retVal = new ArrayList<>();
        try {
            File f = new File(args);
            //System.out.println("here");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";
            System.out.println("Reading file using Buffered Reader");
            Integer emptyLines = 0;
            boolean first = true;
            while ((readLine = b.readLine()) != null) {
                //System.out.println(readLine);
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

    public ArrayList<ArrayList<String>> parseDigits(String args) throws FileNotFoundException{
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<ArrayList<String>> samples = new ArrayList<>();
        try{
            File f = new File(args);
            System.out.println(f);
            String readline = "";
            BufferedReader b = new BufferedReader(new FileReader(f));
            System.out.println(f);
            int i = 0;
            while((readline = b.readLine()) != null){
                if(i == 28){
                    samples.add(lines);
                    lines = new ArrayList<>();
                    i = 0;
                }

                lines.add(readline);

                i++;

            }

        } catch (IOException e){ e.printStackTrace();}
        return samples;
    }
    public int[] digitLabelParse(String args) throws FileNotFoundException {
        //https://stackoverflow.com/questions/7646392/convert-string-to-int-array-in-java
        //http://javarevisited.blogspot.com/2015/09/how-to-read-file-into-string-in-java-7.html
        int[] failvar = new int[10];
        System.out.println(Paths.get(args));
        try {
            String contents = new String(Files.readAllBytes(Paths.get(args)));
            String[] splitContents = contents.split("\n");

            int[] retVal = new int[splitContents.length];

            for (int i = 0; i < splitContents.length; i++) {
                try {
                    retVal[i] = Integer.parseInt(splitContents[i]);
                } catch (NumberFormatException nfe) {
                    //NOTE: write something here if you need to recover from formatting errors
                }
                ;
            }
            return retVal;
        } catch (IOException e) { e.printStackTrace();}

        return failvar;
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


    public Parser() throws FileNotFoundException {
    }

}
