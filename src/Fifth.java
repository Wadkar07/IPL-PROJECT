// It will give most loosing team in entered year
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Fifth {
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
        ArrayList<T> newList = new ArrayList<T>();
        for (T element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        Scanner sc = new Scanner(new File("src/matches.csv"));
        System.out.print("Enter year : ");
        int year= in.nextInt();
        ArrayList<String> losing_teams=new ArrayList<>();
        ArrayList<String> teams=new ArrayList<>();
        sc.nextLine();
        while (sc.hasNext()){
            String[] data = sc.nextLine().split(",");
            if(Integer.parseInt(data[1])==year){
                String name= data[4].equals(data[10])?data[5] : data[4];
                losing_teams.add(name);
            }
        }
        HashMap<String,Integer> losingtable=new HashMap<>();
        teams=removeDuplicates(losing_teams);
        int max = 0;
        String maxlt="init";
        for (String s:teams) {
            int lf = Collections.frequency(losing_teams,s);
            losingtable.put(s,lf);
            if(lf>max)
            {
                max=lf;
                maxlt=s;
            }
        }
        System.out.println("__________________________________________________________________________");
        System.out.println(maxlt+" lost the most matches in "+year+" that are "+max);
        Set<String> teamname = losingtable.keySet();
        System.out.println("________________________________Loosing summary___________________________");
        for(String s: teamname)
            System.out.println(s + " " + losingtable.get(s));
        System.out.println("__________________________________________________________________________");
    }
}