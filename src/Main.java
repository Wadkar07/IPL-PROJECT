import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int MATCH_TEAM1 = 1;
    public static final int MATCH_ID = 0;
    public static final int MATCH_TEAM2 = 2;

    public static void main(String[] args) throws IOException {
        List<Match> matches = getMatchesData();
        List<Delivery> deliveries = getDeliveriesData();
    }

    private static List<Match> getMatchesData() throws IOException {
        List<Match> matches = new ArrayList<>();
        BufferedReader reader = null;
        while (true) {
            String line = reader.readLine();
            String[] data = line.split(",");

            Match match = new Match();
            match.setId(Integer.parseInt(data[MATCH_ID]));
            match.setTeam1(data[MATCH_TEAM1]);
            match.setTeam2(data[MATCH_TEAM2]);

            matches.add(match);
        }
    }

    private static List<Delivery> getDeliveriesData() {
        return null;
    }
}
