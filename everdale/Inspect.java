package everdale;

public class Inspect implements Action {

    private int x;
    private int y;

    public Inspect(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

}
