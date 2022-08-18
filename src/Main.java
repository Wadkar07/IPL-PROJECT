import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static final int MATCH_ID = 0;
    public static final int YEAR = 1;
    public static final int MATCH_TEAM1 = 4;
    private static final int MATCH_TEAM2 = 5;
    public static final int MATCH_WINNER = 10;
    public static final int DELIVERIES_ID = 0;
    public static final int BATTING_TEAM = 2;
    public static final int EXTRA_RUN = 16;
    public static final int BOWLER = 8;
    public static final int TOTAL_RUN = 17;
    public static final String MATCH_DATA = "src/matches.csv";
    public static final String DELIVERIES_DATA = "src/deliveries.csv";
    public static final int MAX_NUM = 100;

    public static void main(String[] args) throws IOException {
        List<Match> matches = getMatchesData();
        List<Delivery> deliveries = getDeliveriesData();
        System.out.println("\nNumber of matches Played Per Year");
        findNumberOfMatchesPlayedPerYear(matches);
        System.out.println("\nNumber Of Matches Won Per Team In All Years");
        findNumberOfMatchesWonPerTeamInAllYears(matches);
        System.out.println("\nExtra Runs Conceded Per Team");
        findExtraRunsConcededPerTeam(matches, deliveries);
        findMostEconomicalBowlerIn2016(matches, deliveries);
        System.out.print("\nMost Loosing Team In Requested year ");
        findMostLoosingTeamInRequestedYear(matches);
    }

    private static void findMostLoosingTeamInRequestedYear(List<Match> matches) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter year : ");
        int requestedYear = sc.nextInt();
        List<String> losingTeams = new ArrayList<>();
        Set<String> teams = new HashSet<>();
        for (Match match : matches) {
            int year = match.getYear();
            if(year == requestedYear){
                String loosingTeam;
                loosingTeam = match.getTeam1().equals(match.getWinner())?match.getTeam2():match.getTeam1();
                losingTeams.add(loosingTeam);
                teams.add(loosingTeam);
            }
        }
        HashMap<String,Integer> loosingTable = new HashMap<>();
        String maximumLostTeam = null;
        int maximumLoose = 0;
        for (String team : teams){
            int loosingFrequency = Collections.frequency(losingTeams,team);
            loosingTable.put(team,loosingFrequency);
            if (loosingFrequency > maximumLoose){
                maximumLoose = loosingFrequency;
                maximumLostTeam = team;
            }
        }
        System.out.println(maximumLostTeam + " lost the most matches in "+requestedYear+" that are " + maximumLoose);
        Set<String> teamName = loosingTable.keySet();
        System.out.println("________________________________Loosing summary___________________________");
        for(String s: teamName)
            System.out.println(s + " " + loosingTable.get(s));
    }

    private static void findExtraRunsConcededPerTeam(List<Match> matches, List<Delivery> deliveries) {
        HashMap<Integer,String> idAndTeamsOf2016 = new HashMap<>();
        for (Match match : matches) {
            if (match.getYear() == 2016)
                idAndTeamsOf2016.put(match.getId(),match.getTeam1());
        }
        Set<Integer> idSetOfTeams = idAndTeamsOf2016.keySet();
        HashMap<String, Integer> extrasScoredByIndividualTeam = new HashMap<>();
        for (Integer idOfTeam : idSetOfTeams) {
            int extraRunCount = 0;
            for (Delivery delivery : deliveries) {
                String battingTeam = delivery.getBattingTeam();
                int id = delivery.getId();
                int extraRun = delivery.getExtraRun();
                if (idSetOfTeams.contains(id) && battingTeam.equals(idAndTeamsOf2016.get(idOfTeam)) && extraRun != 0)
                    extraRunCount += extraRun;
            }
            extrasScoredByIndividualTeam.put(idAndTeamsOf2016.get(idOfTeam), extraRunCount);
        }

        Set<String> teamName = extrasScoredByIndividualTeam.keySet();
        for (String Team : teamName) {
            System.out.println(Team + " = " + extrasScoredByIndividualTeam.get(Team));
        }
    }

    private static void findNumberOfMatchesPlayedPerYear(List<Match> matches) {
        Set<Integer> yearOfMatch = new HashSet<>();
        List<Integer> yearsOfMatches = new ArrayList<>();
        for (Match match : matches) {
            yearOfMatch.add(match.getYear());
            yearsOfMatches.add(match.getYear());
        }

        for (Integer year : yearOfMatch) {
            System.out.println(year + " = " + Collections.frequency(yearsOfMatches, year));
        }
    }

    private static void findMostEconomicalBowlerIn2016(List<Match> matches, List<Delivery> deliveries) {
        int index = 0;
        List<Integer> idOf2015Matches = new ArrayList<>();
        for (Match match : matches) {
            index++;
            if (match.getYear() == 2015)
                idOf2015Matches.add(match.getId());
        }

        for (Match match : matches) {
            if (match.getYear() == 2015) {
                idOf2015Matches.add(match.getId());
            }
        }

        List<String> bowlersDeliveries = new ArrayList<>();
        Set<String> bowlersOf2015 = new HashSet<>();
        for (int id : idOf2015Matches) {
            for (Delivery delivery : deliveries) {
                String bowler = delivery.getBowler();
                int deliveryId = delivery.getId();
                if (id == deliveryId) {
                    bowlersDeliveries.add(bowler);
                    bowlersOf2015.add(bowler);
                }
            }
        }

        List<String> totalBowler = new ArrayList<>();
        for (String bowlers : bowlersOf2015) {
            totalBowler.add(bowlers);
        }

        List<String> bowlingOrder = new ArrayList<>();
        bowlingOrder.add(totalBowler.get(0));
        index = 0;
        for (String bowler : bowlersDeliveries) {
            if (bowler.equals(bowlingOrder.get(index))){
            }
            else {
                bowlingOrder.add(bowler);
                index++;
            }
        }

        HashMap<String, Integer> overPerBowler = new HashMap<>();
        HashMap<String, Integer> runsPerBowler = new HashMap<>();
        HashMap<Double, String> economyTable = new HashMap<>();

        for (String bowler : totalBowler) {
            int run = 0;
            for (Delivery delivery : deliveries) {
                int id = delivery.getId();
                String deliveryBowler = delivery.getBowler();
                int totalRun = delivery.getTotalRun();
                if (idOf2015Matches.contains(id) && deliveryBowler.equals(bowler))
                    run += totalRun;
            }
            runsPerBowler.put(bowler, run);
        }

        for (String bowlers : totalBowler) {
            overPerBowler.put(bowlers, Collections.frequency(bowlingOrder, bowlers));
        }

        Set<String> oversPerBowler = overPerBowler.keySet();
        double minimumEco = MAX_NUM;
        for (String bowler : oversPerBowler) {
            double over = overPerBowler.get(bowler), run = runsPerBowler.get(bowler), economy = 0;
            economy = run / over;
            economyTable.put(economy, bowler);
            if (economy < minimumEco)
                minimumEco = economy;
        }
        System.out.println("\n"+ economyTable.get(minimumEco) + " was the most economic bowler in 2015 with economy ");
    }

    private static void findNumberOfMatchesWonPerTeamInAllYears(List<Match> matches) {
        Set<String> teamsOfAllSeason = new HashSet<>();
        ArrayList<String> matchWinner = new ArrayList<>();
        for (Match match : matches) {
            teamsOfAllSeason.add(match.getWinner());
            matchWinner.add(match.getWinner());
        }
        for (String team : teamsOfAllSeason) {
            System.out.println(team + " = " + Collections.frequency(matchWinner, team));
        }
    }

    private static List<Match> getMatchesData() throws IOException {
        List<Match> matches = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(MATCH_DATA));
        String line = reader.readLine();

        while ((line = reader.readLine()) != null) {
            Match match;

            String[] data = line.split(",");

            match = new Match();
            match.setId(Integer.parseInt(data[MATCH_ID]));
            match.setYear(Integer.parseInt(data[YEAR]));
            match.setTeam1(data[MATCH_TEAM1]);
            match.setTeam2(data[MATCH_TEAM2]);
            match.setWinnerTeam(data[MATCH_WINNER]);

            matches.add(match);
        }
        return matches;
    }

    private static List<Delivery> getDeliveriesData() throws IOException {
        List<Delivery> deliveries = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(DELIVERIES_DATA));
        String line = reader.readLine();

        while ((line = reader.readLine()) != null) {
            Delivery delivery = new Delivery();;
            String[] data = line.split(",");
            delivery.setId(Integer.parseInt(data[DELIVERIES_ID]));
            delivery.setTotalRun(Integer.parseInt(data[TOTAL_RUN]));
            delivery.setBowler(data[BOWLER]);
            delivery.setBattingTeam(data[BATTING_TEAM]);
            delivery.setExtraRun(Integer.parseInt(data[EXTRA_RUN]));

            deliveries.add(delivery);
        }
        return deliveries;
    }
}