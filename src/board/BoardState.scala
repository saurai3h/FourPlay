package board

import board.Cell.Cell
import board.Player.Player

class BoardState {

  private val ROWS = 6
  private val COLUMNS = 7
  private var playerToMove : Player = Player.RED
  private var board : Array[Array[Cell]] = initializeBoard()
  private var stackSize : Array[Int] = Array.fill(COLUMNS)(0)

  def boardFull : Boolean = {
    stackSize.forall(sz => sz == 6)
  }

  def changeStateAfterMove(moveColumn : Int) : Unit = {
    val rowNumber = ROWS - stackSize(moveColumn - 1)

    if(playerToMove.equals(Player.RED)) {
      board(rowNumber - 1)(moveColumn - 1) = Cell.RED
      playerToMove =  Player.BLUE
    } else {
      board(rowNumber - 1)(moveColumn - 1) = Cell.BLUE
      playerToMove = Player.RED
    }

    stackSize(moveColumn - 1) = stackSize(moveColumn - 1) + 1
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
        board(rowNumber - 1)(colNumber - 1) = Cell.EMPTY
      })
    })

    board
  }

  private def getCellColorOfPlayerNotToMove: Cell = {
    if(playerToMove.equals(Player.RED)) Cell.BLUE
    else Cell.RED
  }
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
  val RED, BLUE, EMPTY = Value
}