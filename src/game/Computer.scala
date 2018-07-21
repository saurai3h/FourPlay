package game

import algorithm.AlgoTrait
import board.BoardState

class Computer {

  var algorithmToRun : AlgoTrait = _

  def findBestMove(boardState: BoardState) : Int = {
    algorithmToRun.solve(boardState)
  }
}
