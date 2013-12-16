package models.ai;

import java.util.List;
public interface Evaluator<Evaluatable, M> {
    public Double evaluate(Evaluatable board);
    public List<M> possibleMoves(Evaluatable board);
}
