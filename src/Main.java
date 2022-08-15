import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
    public static final String DIVISION_LINE = "_____________________________________________________________________";

    public static void main(String[] args) throws IOException {
        List<Match> matches = getMatchesData();
        List<Delivery> deliveries = getDeliveriesData();
        System.out.println(DIVISION_LINE);
        findNumberOfMatchesPlayedPerYear(matches);
        System.out.println(DIVISION_LINE);
        findNumberOfMatchesWonPerTeamInAllYears(matches);
        System.out.println(DIVISION_LINE);
        findExtraRunsConcededPerTeam(matches);
        System.out.println(DIVISION_LINE);
        findMostEconomicalBowlerIn2016(matches, deliveries);
        System.out.println(DIVISION_LINE);
    }

    private static void findExtraRunsConcededPerTeam(List<Match> matches) throws IOException {
        Set<String> teamsOf2016 = new HashSet<>();
        int index = 0, flag = 0;
        for (Match match : matches) {
            index++;
            if (match.getYear(++index) == 2016) {
                if (flag == 0) {
                    flag = 1;
                }
                teamsOf2016.add(match.getTeam1(index));
            }
        }
        HashMap<String, Integer> extrasScoredByIndividualTeam = new HashMap<>();
        for (String team : teamsOf2016) {
            int extraRunCount = 0;
            BufferedReader reader = new BufferedReader(new FileReader("src/deliveries.csv"));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String Team = data[BATTING_TEAM];
                int id = Integer.parseInt(data[DELIVERIES_ID]), extraRun = Integer.parseInt(data[EXTRA_RUN]);
                if (id > 576 && Team.equals(team) && extraRun != 0) {
                    extraRunCount += extraRun;
                }
                extrasScoredByIndividualTeam.put(team, extraRunCount);
            }
        }
        Set<String> teamName = extrasScoredByIndividualTeam.keySet();
        for (String Team : teamName) {
            System.out.println(Team + " " + extrasScoredByIndividualTeam.get(Team));
        }
    }

    private static void findNumberOfMatchesPlayedPerYear(List<Match> matches) {
        Set<Integer> yearOfMatch = new HashSet<>();
        ArrayList<Integer> yearsOfMatches = new ArrayList<>();
        int index = 0;
        for (Match match : matches) {
            yearOfMatch.add(match.getYear(++index));
            yearsOfMatches.add(match.getYear(++index));
        }
        System.out.println("Number of matches Played Per Year ");
        for (Integer year : yearOfMatch) {
            System.out.println(year + " = " + Collections.frequency(yearsOfMatches, year));
        }
    }


    private static void findMostEconomicalBowlerIn2016(List<Match> matches, List<Delivery> deliveries) throws IOException {
        int index = 0;
        ArrayList<Integer> idOf2015Matches = new ArrayList<>();
        for (Match match : matches) {
            index++;
            if (match.getYear(index) == 2015)
                idOf2015Matches.add(match.getId(index));
        }

        ArrayList<String> bowlersDelivery = new ArrayList<>();
        Set<String> bowlersOf2015 = new HashSet<>();
        for (int id : idOf2015Matches) {
            BufferedReader reader = new BufferedReader(new FileReader("src/deliveries.csv"));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (Integer.parseInt(data[DELIVERIES_ID]) == id) {
                    bowlersDelivery.add(data[BOWLER]);
                    bowlersOf2015.add(data[BOWLER]);
                }
            }
        }

        ArrayList<String> totalBowler = new ArrayList<>();
        for (String bowlers : bowlersOf2015) {
            totalBowler.add(bowlers);
        }

        ArrayList<String> ballingOrder = new ArrayList<>();
        ballingOrder.add(totalBowler.get(0));
        index = 0;
        for (String bowler : bowlersDelivery) {
            if (bowler.equals(ballingOrder.get(index)))
                continue;
            else {
                ballingOrder.add(bowler);
                index++;
            }
        }
        HashMap<String, Integer> overPerBowler = new HashMap<>();
        HashMap<String, Integer> runsPerBowler = new HashMap<>();
        HashMap<Double, String> economyTable = new HashMap<>();
        for (String bowler : totalBowler) {
            int runs = 0;
            BufferedReader reader = new BufferedReader(new FileReader("src/deliveries.csv"));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (Integer.parseInt(data[0]) > 517 && Integer.parseInt(data[0]) < 577 && data[8].equals(bowler)) {
                    runs += Integer.parseInt(data[17]);
                }
            }
            runsPerBowler.put(bowler, runs);
        }
        for (String bowlers : totalBowler) {
            overPerBowler.put(bowlers, Collections.frequency(ballingOrder, bowlers));
        }
        Set<String> oversPerBowler = overPerBowler.keySet();
        double maximumEco = 0;
        for (String bowler : oversPerBowler) {
            double over = overPerBowler.get(bowler), run = runsPerBowler.get(bowler), economy = 0;
            economy = run / over;
            economyTable.put(economy, bowler);
            if (economy > maximumEco)
                maximumEco = economy;
        }
        System.out.println(economyTable.get(maximumEco) + " was the most economic bowler in 2015 with economy " + maximumEco);
    }

    private static void findNumberOfMatchesWonPerTeamInAllYears(List<Match> matches) {
        Set<String> teamsOfAllSeason = new HashSet<>();
        ArrayList<String> matchWinner = new ArrayList<>();
        int index = 0;
        for (Match match : matches) {
            teamsOfAllSeason.add(match.getWinnerTeam(++index));
            matchWinner.add(match.getWinnerTeam(++index));
        }
        for (String team : teamsOfAllSeason) {
            System.out.println(team + " = " + Collections.frequency(matchWinner, team));
        }
    }

    private static List<Match> getMatchesData() throws IOException {
        List<Match> matches = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("src/matches.csv"));
        String line = reader.readLine();
        Match match = null;
        while ((line = reader.readLine()) != null) {

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
        BufferedReader reader = new BufferedReader(new FileReader("src/deliveries.csv"));
        String line = reader.readLine();
        Delivery delivery = null;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            delivery = new Delivery();

            delivery.setTotalRun(Integer.parseInt(data[TOTAL_RUN]));
            delivery.setBowler(data[BOWLER]);

            deliveries.add(delivery);
        }
        return deliveries;
    }
}