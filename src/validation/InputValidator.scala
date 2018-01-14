package validation

import board.BoardDimensions._
import board.BoardState

import scala.io.StdIn._

class InputValidator(boardState: BoardState) {

  def readAndValidateWhoMovesFirst(): Char = {

    val invalidationMessage = "Please enter either Y or N"
    val input = readLine().toLowerCase
    if(input.nonEmpty && (input.startsWith("y") || input.startsWith("n"))) return input.charAt(0)

    println("Invalid input.")
    readAndValidateWhoMovesFirst()
  }

  def readAndValidateMove(): Int = {

    try {
      val input = readLine().toInt
      if(input >= 1 && input <= COLUMNS && !boardState.isFilledColumn(input))return input
    } catch {
      case e : NumberFormatException =>
        doWhenInvalidMove()
    }

    doWhenInvalidMove()
  }

  def doWhenInvalidMove() : Int = {
    println("Move input should be an integer between 1 and 7 (inclusive). The corresponding column shouldn't be filled already.")
    readAndValidateMove()
  }

  case class InvalidInputException(message : String) extends Exception(message)
}