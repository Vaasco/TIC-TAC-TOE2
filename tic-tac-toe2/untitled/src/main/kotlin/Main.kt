//First version of the program!!!
const val ROW_COL_SIZE = 3
val board = Position.totalPositions.toMutableList()

enum class Symbol(val s: String) {
    CIRCLE("O"), CROSS("X")
}

data class Position(var number: Int, var symbol: String) {
    companion object {
        val totalPositions = List(ROW_COL_SIZE * ROW_COL_SIZE) { Position(it + 1, (it + 1).toString()) }
        operator fun get(numberOfPosition: Int) = totalPositions[numberOfPosition]
    }

}

data class Player(val name: String, val turn: Boolean, val symbol: Symbol)

fun drawBoardWithNumbers() {
    println("\n-------------------------")
    for (i in board) {
        print("|")
        print("   ")
        print(i.number)
        print("   ")
        if (i.number % 3 == 0) {
            print("|")
            println("\n-------------------------")
        }
    }
}

fun drawBoardWithSymbols(board: MutableList<Position>) {
    println("\n-------------------------")
    for (i in board) {
        print("|")
        print("   ")
        print(i.symbol)
        print("   ")
        if (i.number % 3 == 0) {
            print("|")
            println("\n-------------------------")
        }
    }
}

fun isGameOver(board: MutableList<Position>):Boolean {
    val horizontalCheckFirstLine =
        board[0].symbol == board[1].symbol && board[1].symbol == board[2].symbol
    val horizontalCheckSecondLine =
        board[3].symbol == board[4].symbol && board[4].symbol == board[5].symbol
    val horizontalCheckThirdLine =
        board[6].symbol == board[7].symbol && board[7].symbol == board[8].symbol
    val verticalCheckFirstColumn =
        board[0].symbol == board[3].symbol && board[3].symbol == board[6].symbol
    val verticalCheckSecondColumn =
        board[1].symbol == board[4].symbol && board[4].symbol == board[7].symbol
    val verticalCheckThirdColumn =
        board[2].symbol == board[5].symbol && board[5].symbol == board[8].symbol
    val diagonalCheckFirstLine = board[0].symbol == board[4].symbol && board[4].symbol == board[8].symbol
    val diagonalCheckSecondLine = board[2].symbol == board[4].symbol && board[4].symbol == board[6].symbol

    return (horizontalCheckFirstLine || horizontalCheckSecondLine || horizontalCheckThirdLine || verticalCheckFirstColumn || verticalCheckSecondColumn || verticalCheckThirdColumn || diagonalCheckFirstLine || diagonalCheckSecondLine)
}

fun main() {
    var player1: Player
    var player2: Player
    val board = Position.totalPositions.toMutableList()
    println("Player 1, please enter your name:")
    val playerName1 = readln().trim()
    println("Player 2, please enter your name:")
    val playerName2 = readln().trim()
    while (true) {
        println("Hello $playerName1, please choose cross or circle?")
        when (val pAnswer = readln().trim()) {
            "circle" -> {
                player1 = Player(name = playerName1, turn = true, symbol = Symbol.CIRCLE)
                player2 = Player(name = playerName2, turn = false, symbol = Symbol.CROSS)
                println("${player2.name} will be using cross")
                break
            }
            "cross" -> {
                player1 = Player(name = playerName1, turn = true, symbol = Symbol.CROSS)
                player2 = Player(name = playerName2, turn = false, symbol = Symbol.CIRCLE)
                println("${player2.name} will be using circle")
                break
            }
            else -> println("Invalid symbol $pAnswer")
        }
    }
    drawBoardWithNumbers()
    var tK = false
    while (!isGameOver(board)) {
        if (!isDraw(board)) {
            tK = true
            break
        }
        if (player1.turn) println("It's your turn $playerName1, chose a number to place your ${player1.symbol}") else println(
            "It's your turn $playerName2, chose a number to place your ${player2.symbol}"
        )
        val numberPicked = readln().trim().toInt()
        if (!(1..9).any { it == numberPicked }) continue
        if (!Position.totalPositions.any { numberPicked == it.number }) {
            println("Invalid position!")
        } else {
            val position = board.find { it.number == numberPicked }
            if (checkIfSpotIsEmpty(position)) {
                println("Invalid position pick another")
                continue
            }
            val number = position!!.number
            val index = board.indexOf(position)
            if (player1.turn) {
                insertSymbol(index, player1, board)
                player1 = player1.copy(turn = false)
                player2 = player2.copy(turn = true)
                drawBoardWithSymbols(board)
            } else {
                insertSymbol(index, player2, board)
                player1 = player1.copy(turn = true)
                player2 = player2.copy(turn = false)
                drawBoardWithSymbols(board)
            }
        }
    }
    if (tK) println("Game is draw, no winner awarded!")
    else if (player1.turn) println("$playerName2 wins!!!") else println("$playerName1 wins!!!")

}

fun isDraw(board: MutableList<Position>) = board.any { it.symbol != Symbol.CIRCLE.s && it.symbol != Symbol.CROSS.s }

fun insertSymbol(index: Int,player: Player, board: MutableList<Position>): MutableList<Position> {
    board[index] = Position(index + 1, player.symbol.s)
    return board
}

fun checkIfSpotIsEmpty(position: Position?) = position!!.symbol == Symbol.CROSS.s || position.symbol == Symbol.CIRCLE.s