public class Delivery {
    protected int  totalRun,extraRun, id;
    protected String bowler,battingTeam;

    public int getExtraRun() {
        return extraRun;
    }

    public void setExtraRun(int extraRun) {
        this.extraRun = extraRun;
    }

    public String getBattingTeam() {
        return battingTeam;
    }

    public void setBattingTeam(String battingTeam) {
        this.battingTeam = battingTeam;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalRun() {
        return totalRun;
    }

    public void setTotalRun(int totalRun) {
        this.totalRun = totalRun;
    }

    public String getBowler() {
        return bowler;
    }

    public void setBowler(String bowler) {
        this.bowler = bowler;
    }
}
