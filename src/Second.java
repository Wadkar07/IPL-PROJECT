import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Second {
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {
        ArrayList<T> newList = new ArrayList<T>();
        for (T element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("src/matches.csv"));
        String line= sc.nextLine();
        ArrayList<String> winner = new ArrayList<>();
        int c=0;
        while (sc.hasNext()){
            line= sc.nextLine();
            String[] data=line.split(",");
            winner.add((data[10]));
            c++;
        }
        ArrayList uniqueWinner = removeDuplicates(winner);
        for (Object i : uniqueWinner) {
            if(i=="")
                System.out.print("Draw");
                System.out.println(i+" = "+ Collections.frequency(winner, i));
        }

    }
}