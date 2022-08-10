import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class First {
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
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
        String line = sc.nextLine();
        ArrayList<Integer> season = new ArrayList<>();
        int i = 0;
        while (sc.hasNext()) {
            line = sc.nextLine();
            String[] data = line.split(",");
            season.add(Integer.parseInt(data[1]));
            i++;
        }
        ArrayList UniqueYear = removeDuplicates(season);
        for (Object j : UniqueYear) {
            System.out.println(j + " = " + Collections.frequency(season, j));//j is for years
        }
    }
}
