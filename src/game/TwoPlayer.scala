package game

import board.BoardState
import board.BoardDimensions.COLUMNS
import validation.InputValidator

object TwoPlayer {
  val boardState = new BoardState
  val inputValidator = new InputValidator(boardState)

  def main(args: Array[String]): Unit = {
    boardState.printBoard()
    simulateGameUntilEnd()
  }

  def simulateGameUntilEnd() : Unit = {
    while (!boardState.boardFull) {
      println(s"Player ${boardState.getPlayerToMove}'s turn. Enter which column(1 to $COLUMNS) you want to insert your coin.")
      val moveMade = inputValidator.readAndValidateMove()
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