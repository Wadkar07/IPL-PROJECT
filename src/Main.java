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
    public static final int DELIVERIES_MATCH_ID = 0;
    public static final int BATTING_TEAM = 2;
    public static final int EXTRA_RUN = 16;
    public static final int BOWLER = 8;
    public static final int TOTAL_RUN = 17;
    public static final String MATCH_DATA = "src/matches.csv";
    public static final String DELIVERIES_DATA = "src/deliveries.csv";

    public static void main(String[] args) throws IOException {
        List<Match> matches = getMatchesData();
        List<Delivery> deliveries = getDeliveriesData();
        System.out.println("Number of matches Played Per Year ");
        findNumberOfMatchesPlayedPerYear(matches);
        System.out.println();
        System.out.println("\nNumber Of Matches Won Per Team In All Years");
        findNumberOfMatchesWonPerTeamInAllYears(matches);
        System.out.println();
        System.out.println("\nExtra Runs Conceded Per Team");
        findExtraRunsConcededPerTeam(matches, deliveries);
        System.out.println();
        findMostEconomicalBowlerIn2015(matches, deliveries);
        System.out.println();
        System.out.print("\nMost Loosing Team In Requested year ");
        findMostLoosingTeamInRequestedYear(matches);
    }



    private static void findMostLoosingTeamInRequestedYear(List<Match> matches) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter year : ");
        int requestedYear = sc.nextInt();
        TreeMap<String, Integer> loosingStatus = new TreeMap<>();
        for (Match match : matches) {
            int year = match.getYear();
            if (year == requestedYear) {
                String loosingTeam = match.getWinner().equals(match.getTeam1()) ? match.getTeam2() : match.getTeam1();
                if (loosingStatus.containsKey(loosingTeam)) {
                    loosingStatus.put(loosingTeam, loosingStatus.get(loosingTeam) + 1);
                } else {
                    loosingStatus.put(loosingTeam, 1);
                }
            }
        }
        for (Map.Entry<String, Integer> entry : loosingStatus.entrySet()) {
            String Team = entry.getKey();
            Integer Lost = entry.getValue();
            System.out.println(Team + " : " + Lost);
        }
    }

    private static void findExtraRunsConcededPerTeam(List<Match> matches, List<Delivery> deliveries) {
        HashMap<Integer, String> idAndTeamsOf2016 = new HashMap<>();
        for (Match match : matches) {
            if (match.getYear() == 2016)
                idAndTeamsOf2016.put(match.getId(), match.getTeam1());
        }
        HashMap<String, Integer> extrasScoredByIndividualTeam = new HashMap<>();
        for (Delivery delivery : deliveries) {
            if (idAndTeamsOf2016.containsKey(delivery.getId())) {
                String battingTeam = delivery.getBattingTeam();
                int extraRun = delivery.getExtraRun();
                int extraRunCount;
                if (extrasScoredByIndividualTeam.containsKey(battingTeam)) {
                    extraRunCount = extrasScoredByIndividualTeam.get(battingTeam) + extraRun;
                } else {
                    extraRunCount = extraRun;
                }
                extrasScoredByIndividualTeam.put(battingTeam, extraRunCount);
            }
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

    private static void findMostEconomicalBowlerIn2015(List<Match> matches, List<Delivery> deliveries) {
        Set<Integer> idOf2015Matches = new HashSet<>();
        for (Match match : matches)
            if (match.getYear() == 2015)
                idOf2015Matches.add(match.id);

        HashMap<String, Integer> runsPerBowler = new HashMap<>();
        HashMap<String, Integer> ballsPerBowlers = new HashMap<>();
        for (Delivery delivery : deliveries) {
            if (idOf2015Matches.contains(delivery.id)) {
                if (runsPerBowler.containsKey(delivery.getBowler())) {
                    int run = runsPerBowler.get(delivery.getBowler()) + delivery.getTotalRun();
                    runsPerBowler.put(delivery.getBowler(), run);
                    ballsPerBowlers.put(delivery.getBowler(), ballsPerBowlers.get(delivery.getBowler()) + 1);
                } else {
                    runsPerBowler.put(delivery.getBowler(), delivery.getTotalRun());
                    ballsPerBowlers.put(delivery.getBowler(), 1);

                }
            }
        }
        Map<Double, String> economyTable = new TreeMap<>();
        Set<String> bowlers = runsPerBowler.keySet();
        for (String bowler : bowlers) {
            double eco = (runsPerBowler.get(bowler) / (ballsPerBowlers.get(bowler) / 6.0));
            economyTable.put(eco, bowler);
        }
        economyTable.forEach((economy, bowler) -> System.out.println(bowler + ":" + economy));

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
        reader.readLine();
        String line;
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
        reader.readLine();
        String line;
        while ((line = reader.readLine()) != null) {
            Delivery delivery = new Delivery();
            String[] data = line.split(",");
            delivery.setId(Integer.parseInt(data[DELIVERIES_MATCH_ID]));
            delivery.setTotalRun(Integer.parseInt(data[TOTAL_RUN]));
            delivery.setBowler(data[BOWLER]);
            delivery.setBattingTeam(data[BATTING_TEAM]);
            delivery.setExtraRun(Integer.parseInt(data[EXTRA_RUN]));

            deliveries.add(delivery);
        }
        return deliveries;
    }

}