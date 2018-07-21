package game

import board.BoardState
import board.BoardDimensions.COLUMNS
import validation.InputValidator

object SinglePlayer {

  val boardState = new BoardState
  val inputValidator = new InputValidator(boardState)
  val computer = new Computer

  def main(args: Array[String]): Unit = {
    boardState.printBoard()
    simulateGameUntilEnd()
  }

  def simulateGameUntilEnd() : Unit = {

    println("RED plays first. BLUE follows. Do you want to be RED? Press Y/N")

    val humanPlaysFirst = inputValidator.readAndValidateWhoMovesFirst()

    println(
      """Select Computer's algorithm from the following -
        |1. RandomColumn (R)
        |2. NaiveHeuristics (N)
      """.stripMargin)

    val algoToUse = inputValidator.readAndValidateAlgorithm()
    computer.algorithmToRun = algoToUse

    if(humanPlaysFirst.equals('n')) {
      println("Computer's turn")
      val computerMove = computer.findBestMove(boardState)
      boardState.changeStateAfterMove(computerMove)
      boardState.printBoard()
    }

    while (!boardState.boardFull) {

      println(s"Player ${boardState.getPlayerToMove}'s turn. Enter which column(1 to $COLUMNS) you want to insert your coin.")
      val moveMade = inputValidator.readAndValidateMove()
      boardState.changeStateAfterMove(moveMade)
      boardState.printBoard()

      if(boardState.getWinner.isDefined) {
        println(s"You won! Congrats.")
        return
      }

      println("Computer's turn.")
      val computerMove = computer.findBestMove(boardState)
      boardState.changeStateAfterMove(computerMove)
      boardState.printBoard()

      if(boardState.getWinner.isDefined) {
        println(s"Computer won! Try again.")
        return
      }

    }

    println("Game drawn!")
  }
}
