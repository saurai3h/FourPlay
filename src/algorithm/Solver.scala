package algorithm

import board.BoardState

trait Solver {
  def solve(boardState: BoardState) : Int
}
