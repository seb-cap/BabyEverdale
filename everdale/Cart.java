package everdale;


public class Cart extends Building {

    private Resource request;
    private int needed;
    private int fulfilled;

    public Cart() {
        super(2);
    }

    /**
     * Accepts an offer by setting the resource and quantity
     * @param r the resource requested
     * @param required the amount of resource needed
     */
    public void acceptOffer(Resource r, int required) {
        this.request = r;
        this.needed = required;
        this.fulfilled = 0;
    }

    /**
     * Adds Resources to the Cart's Request
     * @param n the number of Resources added
     * @return the number of resources overflown
     */
    public int fulfill(int n) {
        this.fulfilled += n;
        return Math.min(0, this.fulfilled - this.needed);
    }

}

