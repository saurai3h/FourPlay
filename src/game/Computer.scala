package game

import algorithm.RandomColumn
import board.BoardState

class Computer {

  val ALGORITHM_TO_RUN = new RandomColumn

  def findBestMove(boardState: BoardState) : Int = {
    ALGORITHM_TO_RUN.solve(boardState)
  }
}
