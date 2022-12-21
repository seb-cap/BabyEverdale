package everdale;

public class Build implements Action {
    private Building b;
    private int x;
    private int y;
    public Build(Building building, int x, int y) {
        this.b = building;
        this.x = x;
        this.y = y;
    }

    public Building getB() {
        return b;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
