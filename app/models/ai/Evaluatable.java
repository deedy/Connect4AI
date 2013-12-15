package models.ai;

import java.util.List;

public interface Evaluatable {
    /**
    * Returns a list of moves that can be played
    */
    public List<Object> possibleMoves();
    /**
    * Plays the move on this Evaluatable and returns a new
    * copy of the Evaluatable with the move played.
    */
    public Evaluatable playMoveImmutable(Object m);
}

