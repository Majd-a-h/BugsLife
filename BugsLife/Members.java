//this class will be used only to get the top performer with the most points, by getting all the assignees, then each time he got resolved issue, it will add him a point
public class Members {
    private String name;
    private int points;
    public Members(String name, int points) {
        this.name = name;
        this.points = points;
    }
    public Members() {}
    public String getName() {return name;}
    public int getPoints() { return points; }
    public void setName(String name) { this.name = name; }
    public void setPoints() {points++;}
}