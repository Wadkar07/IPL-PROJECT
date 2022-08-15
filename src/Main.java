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
    public static final String DIVISION_LINE = "_____________________________________________________________________";

    public static void main(String[] args) throws IOException {
        List<Match> matches = getMatchesData();
        List<Delivery> deliveries = getDeliveriesData();
        System.out.println(DIVISION_LINE);
        findNumberOfMatchesPlayedPerYear(matches);
        System.out.println(DIVISION_LINE);
        findNumberOfMatchesWonPerTeamInAllYears(matches);
        System.out.println(DIVISION_LINE);
        findMostEconomicalBowlerIn2016(matches,deliveries);
        System.out.println(DIVISION_LINE);
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


    private static void findMostEconomicalBowlerIn2016(List<Match> matches, List<Delivery> deliveries) {

    }

    private static void findNumberOfMatchesWonPerTeamInAllYears(List<Match> matches) {

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

    private static List<Delivery> getDeliveriesData() {
        return null;
    }
}