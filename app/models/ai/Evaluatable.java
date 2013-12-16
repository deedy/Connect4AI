package models.ai;

import java.util.List;

public interface Evaluatable<M> {
    /**
    * Plays the move on this Evaluatable and returns a new
    * copy of the Evaluatable with the move played.
    */
    public Evaluatable playMoveImmutable(M m);
}

