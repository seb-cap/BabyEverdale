package everdale;


/**
 * The Cart class represents a Cart with an order that must be fulfilled.
 */
public class Cart extends Building {

    private Resource request;
    private int needed;
    private int fulfilled;

    /**
     * Creates a new Cart at the given Coordinate
     * @param c The Coordinate of the Cart
     */
    public Cart(Coordinate c) {
        super(2, c);
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

    /**
     * @return whether the Cart's fulfillment is zero.
     */
    public boolean progressIsEmpty() {
        return this.fulfilled == 0;
    }
}

