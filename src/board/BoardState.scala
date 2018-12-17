package board

import board.Cell.Cell
import board.Player.Player
import BoardDimensions._

class BoardState extends Cloneable {

  private var playerToMove : Player = Player.RED
  private val board : Array[Array[Cell]] = initializeBoard()
  private val stackSize : Array[Int] = Array.fill(COLUMNS)(0)

  def boardFull : Boolean = {
    stackSize.forall(sz => sz == ROWS)
  }

  /* Returns true if the move was legal and changes the state. False otherwise. */
  def changeStateAfterMove(moveColumn : Int) : Boolean = {
    val rowNumber = ROWS - stackSize(moveColumn - 1)

    /* Illegal move. */
    if(rowNumber <= 0)
      return false

    if(playerToMove.equals(Player.RED)) {
      board(rowNumber - 1)(moveColumn - 1) = Cell.RED
      playerToMove =  Player.BLUE
    } else {
      board(rowNumber - 1)(moveColumn - 1) = Cell.BLUE
      playerToMove = Player.RED
    }

    stackSize(moveColumn - 1) = stackSize(moveColumn - 1) + 1

    true
  }

  /* Given a column number, removes the top coin and updates the board state accordingly. */
  def undoMove(moveColumn : Int) : Unit = {
    val rowNumber = ROWS - stackSize(moveColumn - 1)

    if(playerToMove.equals(Player.RED)) {
      playerToMove =  Player.BLUE
    } else {
      playerToMove = Player.RED
    }

    board(rowNumber)(moveColumn - 1) = Cell.E
    stackSize(moveColumn - 1) = stackSize(moveColumn - 1) - 1
  }

  /**
    * Return the winning player if there is any.
    * @return
    */
  def getWinner : Option[Player] = {
    (1 to ROWS).foreach(rowNumber => {
      (1 to COLUMNS).foreach(colNumber => {
        if(horizontal(rowNumber, colNumber) || vertical(rowNumber, colNumber)
          || diagonalUp(rowNumber, colNumber) || diagonalDown(rowNumber, colNumber)) {
          return Option.apply(getPlayerNotToMove)
        }
      })
    })

    None
  }

  def getPlayerToMove: Player = playerToMove

  def getPlayerNotToMove: Player = if(playerToMove.equals(Player.RED)) Player.BLUE else Player.RED

  private def horizontal(r: Int, c: Int) : Boolean = {
    val cellColor = getCellColorOfPlayerNotToMove
    if(c + 3 <= COLUMNS) {
      board(r - 1)(c - 1).equals(cellColor) && board(r - 1)(c).equals(cellColor) && board(r - 1)(c + 1).equals(cellColor) && board(r - 1)(c + 2).equals(cellColor)
    } else {
      false
    }
  }

  private def vertical(r: Int, c: Int) : Boolean = {
    val cellColor = getCellColorOfPlayerNotToMove
    if(r + 3 <= ROWS) {
      board(r - 1)(c - 1).equals(cellColor) && board(r)(c - 1).equals(cellColor) && board(r + 1)(c - 1).equals(cellColor) && board(r + 2)(c - 1).equals(cellColor)
    } else {
      false
    }
  }

  private def diagonalUp(r: Int, c: Int) : Boolean = {
    val cellColor = getCellColorOfPlayerNotToMove
    if(r - 3 >= 1 && c + 3 <= COLUMNS) {
      board(r - 1)(c - 1).equals(cellColor) && board(r - 2)(c).equals(cellColor) && board(r - 3)(c + 1).equals(cellColor) && board(r - 4)(c + 2).equals(cellColor)
    } else {
      false
    }
  }

  private def diagonalDown(r: Int, c: Int) : Boolean = {
    val cellColor = getCellColorOfPlayerNotToMove
    if(r + 3 <= ROWS && c + 3 <= COLUMNS) {
      board(r - 1)(c - 1).equals(cellColor) && board(r)(c).equals(cellColor) && board(r + 1)(c + 1).equals(cellColor) && board(r + 2)(c + 2).equals(cellColor)
    } else {
      false
    }
  }

  def printBoard() : Unit = {

    (1 to COLUMNS).foreach(c => print(f"$c%6s"))
    println()
    println()
    println()

    (1 to ROWS).foreach(rowNumber => {
      (1 to COLUMNS).foreach(colNumber => {
        print(f"${board(rowNumber - 1)(colNumber - 1)}%6s")
      })
      println()
    })
  }

  /**
    * Initially
    * 1. The board will be empty.
    * [E E E E E E E]
    * [E E E E E E E]
    * [E E E E E E E]
    * [E E E E E E E]
    * [E E E E E E E]
    * [E E E E E E E]
    *
    * 2. The stackSize for every column will be 0.
    * @return
    */
  private def initializeBoard() : Array[Array[Cell]] = {

    val board: Array[Array[Cell]] = Array.ofDim(ROWS, COLUMNS)

    (1 to ROWS).foreach(rowNumber => {
      (1 to COLUMNS).foreach(colNumber => {
        board(rowNumber - 1)(colNumber - 1) = Cell.E
      })
    })

    board
  }

  def getCellColorOfPlayerToMove: Cell = {
    if(playerToMove.equals(Player.RED)) Cell.RED
    else Cell.BLUE
  }

  def getCellColorOfPlayerNotToMove: Cell = {
    if(playerToMove.equals(Player.RED)) Cell.BLUE
    else Cell.RED
  }

  def isFilledColumn(c : Int) : Boolean = {
    stackSize(c - 1) == ROWS
  }
}

object BoardDimensions {
  val ROWS = 6
  val COLUMNS = 7
}

/**
  * We'll call the first player Red as they have red coins.
  */
object Player extends Enumeration {
  type Player = Value
  val RED, BLUE = Value
}

/**
  * Every board cell is either occupied by red or blue coin or is empty.
  */
object Cell extends Enumeration {
  type Cell = Value
  val RED, BLUE, E = Value
}