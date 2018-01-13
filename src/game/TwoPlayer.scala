package game

import board.BoardState
import scala.io.StdIn._

object TwoPlayer {
  val boardState = new BoardState

  def main(args: Array[String]): Unit = {

    boardState.printBoard()

    while (!boardState.boardFull) {
      println(s"Player ${boardState.getPlayerToMove}'s turn. Enter which column(1 to 7) you want to insert your coin.")
      val moveMade = readInt()
      boardState.changeStateAfterMove(moveMade)
      boardState.printBoard()
      if(boardState.getWinner.isDefined) {
        println(s"Player ${boardState.getWinner.get} won! Congrats.")
        return
      }
    }

    println("Game drawn!")

  }
}