import java.io.File;
import java.io.IOException;
import java.util.*;

public class Fourth {
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
        sc.nextLine();

        ArrayList<Integer> ID = new ArrayList<>();
        while (sc.hasNext()) {
            String[] data = sc.nextLine().split(",");
            if (Integer.parseInt(data[1]) == 2015) {
                ID.add(Integer.parseInt(data[0]));
            }
        }
        ArrayList<String> bowlers = new ArrayList<>();
        for (int i : ID) {
            Scanner sc1 = new Scanner(new File("src/deliveries.csv"));
            sc1.nextLine();
            while (sc1.hasNext()) {
                String[] data = sc1.nextLine().split(",");
                if (Integer.parseInt(data[0]) == i) {
                    bowlers.add(data[8]);
                }
            }
        }
        ArrayList<String> Over_Bowler = new ArrayList<>();
        Over_Bowler.add(bowlers.get(0));
        int x=0;
        for (String s : bowlers) {
            if(s.equals(Over_Bowler.get(x)))
                continue;
            else {
                Over_Bowler.add(s);
                x++;
            }
        }
        ArrayList<String> Bowlers = removeDuplicates(bowlers);

        HashMap<String,Integer> Over_Per_Bowler = new HashMap<>();
        HashMap<String,Integer> Runs_Per_Bowler = new HashMap<>();
        for (String s : Bowlers) {
            int runs=0;
            Scanner sc1 = new Scanner(new File("src/deliveries.csv"));
            sc1.nextLine();
            while(sc1.hasNext()){
                String[] data = sc1.nextLine().split(",");
                if(Integer.parseInt(data[0])>517 && Integer.parseInt(data[0])<577 && data[8].equals(s)){
                    runs+=Integer.parseInt(data[17]);
                }
            }
            Runs_Per_Bowler.put(s,runs);
        }
        for (String s : Bowlers) {
            Over_Per_Bowler.put(s,Collections.frequency(Over_Bowler, s));
        }
        Set<String> OPB = Over_Per_Bowler.keySet();
        for (String s : OPB) {
            double o=Over_Per_Bowler.get(s),r=Runs_Per_Bowler.get(s),e;
            e=r/o;
            System.out.println(s+" "+e);
        }
    }

}
