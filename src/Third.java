import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Third {
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
        ArrayList<String>TeamsOf2016 =new ArrayList<>();
        HashMap<String ,Integer>Extrarun=new HashMap<>();
        while (sc.hasNext()){
            String[] data=sc.nextLine().split(",");
            if(Integer.parseInt(data[1])==2016){
                int mid=Integer.parseInt(data[0]);
                String team=data[10];
                TeamsOf2016.add(data[4]);
            }
        }
        ArrayList<String> Teams = removeDuplicates(TeamsOf2016);
        HashMap<String,Integer> TeamExtras= new HashMap<>();
        for (String i : Teams) {
            int extra=0;
            Scanner sc1 =new Scanner(new File("src/deliveries.csv"));
            sc1.nextLine();
            while (sc1.hasNext()){
                String[] data= sc1.nextLine().split(",");
                if(Integer.parseInt(data[0])>576 && (data[2].equals(i)) && (Integer.parseInt(data[16])!=0))
                    extra+=Integer.parseInt(data[16]);
            }
            TeamExtras.put(i,extra);
        }
        Set<String> Team_name = TeamExtras.keySet();
        for (String i : Team_name)
            System.out.println(i+" "+TeamExtras.get(i));
    }
}
