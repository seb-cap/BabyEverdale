package everdale;

/**
 * Represents an Action in Everdale. Actions are passed into the Game to play the game.
 * Each Action contains unique information to help carry out that Action.
 */
public interface Action {

    Action PASS = new Pass();
    Action VIEW = new View();


}
