package validation

import algorithm.{AlgoTrait, NaiveAlgoWithSimpleHeuristics, RandomColumn}
import board.BoardDimensions._
import board.BoardState

import scala.io.StdIn._

class InputValidator(boardState: BoardState) {

  def readAndValidateWhoMovesFirst(): Char = {

    val invalidationMessage = "Please enter either Y or N"
    val input = readLine().toLowerCase
    if(input.nonEmpty && (input.startsWith("y") || input.startsWith("n"))) return input.charAt(0)

    println(invalidationMessage)
    readAndValidateWhoMovesFirst()
  }

  def readAndValidateAlgorithm(): AlgoTrait = {

    val invalidationMessage = "Please enter only the letters added in parenthesis for the algorithms"
    val input = readLine().toLowerCase

    if(input.nonEmpty && input.startsWith("r")) return new RandomColumn
    else if (input.nonEmpty && input.startsWith("n")) return new NaiveAlgoWithSimpleHeuristics

    println(invalidationMessage)
    readAndValidateAlgorithm()
  }

  def readAndValidateMove(): Int = {

    try {
      val input = readLine().toInt
      if(input >= 1 && input <= COLUMNS && !boardState.isFilledColumn(input))
        input
      else
        doWhenInvalidMove()
    } catch {
      case e : NumberFormatException =>
        doWhenInvalidMove()
    }
  }

  def doWhenInvalidMove() : Int = {
    println(s"Move input should be an integer between 1 and $COLUMNS (inclusive). The corresponding column shouldn't be filled already.")
    readAndValidateMove()
  }

  case class InvalidInputException(message : String) extends Exception(message)
}