package models.ai;

import java.util.List;
import java.util.ArrayList;

public class AlphaBetaMinimax {
    Double value = null;
    Object move = null;
    public Object getMove() {
        return this.move;
    }

    static boolean DEBUG = false;

    public AlphaBetaMinimax(Evaluator eval, Evaluatable e, int depth, double alpha, double beta, boolean isMax) {
        if (depth == 0 || eval.possibleMoves(e).size() == 0) {
            this.value = eval.evaluate(e);
            if (DEBUG) {
                System.out.println("Value: "+this.value);
                System.out.println("Evaluating leaf...");
                System.out.println("Value: "+this.value + " Depth: "+depth+" Move:"+ this.move + "\n" + e.toString() + "\n\n");
            }
        } else {
            if (isMax) {
                double minOrMax = alpha;
                Object bestMove = null;
                for (Object m : eval.possibleMoves(e)) {
                    if (DEBUG) {
                        System.out.println("Calling child from maxnode with move " +m+"...");
                    }
                    AlphaBetaMinimax child = new AlphaBetaMinimax(eval, e.playMoveImmutable(m), depth - 1, minOrMax, beta, false);
                    if (DEBUG) {
                        System.out.println("Leaf value received from maxnode: "+child.value+" minOrmax: "+minOrMax);
                    }
                    if (child.value > minOrMax) {

                        minOrMax = child.value;
                        bestMove = m;
                        if (DEBUG) {
                            System.out.println("New maxnode best move is now: "+bestMove);
                        }
                    }
                    if (DEBUG) {
                        System.out.println("Child val: "+child.value+" minormax"+minOrMax+" move:"+m+" bestmove: "+bestMove);
                    }
                    if (beta <= minOrMax || minOrMax == 1.0d) {
                        break;
                    }
                }

                this.value = minOrMax;
                this.move = bestMove;
                // System.out.println("Value: "+this.value  ng());
            } else {
                double minOrMax = beta;
                Object bestMove = null;
                for (Object m : eval.possibleMoves(e)) {
                    if (DEBUG) {
                        System.out.println("Calling child from minnode with move " +m+"...");
                    }
                    AlphaBetaMinimax child = new AlphaBetaMinimax(eval, e.playMoveImmutable(m), depth - 1, alpha, minOrMax, true);
                    if (DEBUG) {
                        System.out.println("Leaf value received from minnode: "+child.value+" minOrmax: "+minOrMax);
                    }
                    if (child.value < minOrMax) {
                        minOrMax = child.value;
                        bestMove = m;
                        if (DEBUG) {
                            System.out.println("New minnode best move is now: "+bestMove);
                        }
                    }
                    if (minOrMax <= alpha || minOrMax == -1.0d) {
                        break;
                    }
                }
                this.value = minOrMax;
                this.move = bestMove;
            }
            // System.out.println("Value: "+this.value + " Depth: "+depth+" Move:"+ this.move + "\n" + "\n\n");

        }
    }
}
