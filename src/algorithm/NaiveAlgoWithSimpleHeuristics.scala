package algorithm

import board.BoardState

/**
  * This algorithm is very naive and makes moves according to very simple rules
  * 1. If there is a move where the AI wins right away, make it.
  * 2. If there is a move which if not made, the human wins right away, make it.
  * 3. If there is a move which if made, the human wins right away, DON'T make it.
  * 4. Shallow Heuristic => make the move which connects the most number of AI coins.
  */
class NaiveAlgoWithSimpleHeuristics extends Solver {

  override def solve(boardState: BoardState) : Int = {
    /*
    TODO : Implement the rules for this class
     */
    1
  }
}
