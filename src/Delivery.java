public class Delivery {
    int  totalRun,extraRun, id;
    String bowler,battingTeam;

    public int getExtraRun(int index) {
        return extraRun;
    }

    public void setExtraRun(int extraRun) {
        this.extraRun = extraRun;
    }

    public String getBattingTeam(int index) {
        return battingTeam;
    }

    public void setBattingTeam(String battingTeam) {
        this.battingTeam = battingTeam;
    }

    public int getId(int index) {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getTotalRun(int index) {
        return totalRun;
    }

    public void setTotalRun(int totalRun) {
        this.totalRun = totalRun;
    }

    public String getBowler(int index) {
        return bowler;
    }

    public void setBowler(String bowler) {
        this.bowler = bowler;
    }
}
