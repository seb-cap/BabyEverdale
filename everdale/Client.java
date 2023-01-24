package everdale;

/**
 * The Client interface must be implemented by a Client to use Everdale.
 * A Client must be specified through Game to run the game. The Client must
 * create a UI to display the information from the Everdale package.
 */
public interface Client {

    public enum Type {
        /**
         * Basic Information from the Game. Low Priority.
         */
        Information,
        /**
         * A warning from the game. Middle Priority.
         */
        Warning,
        /**
         * An important notice from the game. High Priority.
         */
        Notice,
        /**
         * A notice of success from the game (finished research, building, etc.).
         */
        Success
    }

    /**
     * Prompts the user with some information in the
     * way specified by the Client
     * @param o The Object to be prompted
     */
    default void prompt(Object o) {
        prompt(o, Type.Information);
    }

    void prompt(Object o, Type t);

}
