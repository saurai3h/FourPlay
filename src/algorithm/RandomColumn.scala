package algorithm

import board.BoardState
import board.BoardDimensions.COLUMNS

class RandomColumn extends AlgoTrait {
  override def solve(boardState: BoardState): Int = {
    1 + scala.util.Random.nextInt(COLUMNS - 1)
  }
}
