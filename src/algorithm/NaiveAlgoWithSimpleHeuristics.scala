package algorithm

import board.BoardState
import board.BoardDimensions.COLUMNS

/**
  * This algorithm is very naive and makes moves according to very simple rules
  * 1. If there is a move where the AI wins right away, make it.
  * 2. If there is a move which if not made, the human wins right away, make it.
  * 3. If there is a move which if made, the human wins right away, DON'T make it.
  * 4. Shallow Heuristic => make the move which connects the most number of AI coins.
  */
class NaiveAlgoWithSimpleHeuristics extends AlgoTrait {

  override def solve(boardState: BoardState) : Int = {

    var changedBoardState = boardState

    /* Rule 1 */
    (1 to COLUMNS).foreach(col => {
      changedBoardState.changeStateAfterMove(col)
      if(changedBoardState.getWinner.isDefined) {
        return col
      } else {
        changedBoardState = boardState
      }
    })

    /* Implement other rules*/
    0
  }
}
