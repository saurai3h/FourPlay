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

    /* Rule 1 */
    (1 to COLUMNS).foreach(col => {
      val didMove = boardState.changeStateAfterMove(col)
      if(boardState.getWinner.isDefined) {
        if(didMove)
          boardState.undoMove(col)
        return col
      } else {
        if(didMove)
          boardState.undoMove(col)
      }
    })

    /* Rule 2 and 3 */
    val badMoves = new scala.collection.mutable.HashSet[Int]
    (1 to COLUMNS).foreach(col => {
      val didMove = boardState.changeStateAfterMove(col)
      (1 to COLUMNS).foreach(humanCol => {

        val didMoveHuman = boardState.changeStateAfterMove(humanCol)
        if(boardState.getWinner.isDefined) {
          // `col` move was bad. Human wins after some `humanCol` move.
          badMoves += col
        }
        if(didMoveHuman)
          boardState.undoMove(humanCol)
      })
      if(didMove)
        boardState.undoMove(col)
    })

    if(badMoves.size < COLUMNS) {
      val goodMoves = (1 to COLUMNS).filterNot(col => badMoves.contains(col))
      /* TODO : Implement Rule 4 */
      goodMoves.apply(0)
    } else {
      /* Anyway lost, make a random move */
      1 + scala.util.Random.nextInt(COLUMNS - 1)
    }
  }
}
