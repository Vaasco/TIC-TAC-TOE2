import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

//The tests on this file use the same logic and functions of the software on the main file.
class Testes {
    @Test
    fun `Putting a cross in position 1 on the player turn`() {
        val board = Position.totalPositions.toMutableList()
        val player = Player("Vasco", true, Symbol.CROSS)
        if (player.turn) {
            insertSymbol(0, player, board)
        }
        assertEquals(Symbol.CROSS.s, board[0].symbol)
    }

    @Test
    fun `Player tries to insert on a position that's eady filled, on his turn`() {
        val board = Position.totalPositions.toMutableList()
        var player1 = Player("Vasco", true, Symbol.CROSS)
        var player2 = Player("Rodrigo", false, Symbol.CIRCLE)
        val numberPicked = 2
        insertSymbol(1, player1, board)
        player1 = player1.copy(turn = false)
        player2 = player2.copy(turn = true)
        val position = board.find { it.number == numberPicked }
        if (checkIfSpotIsEmpty(position) && player2.turn) {
            println("Invalid position pick another")
        } else {
            insertSymbol(1, player2, board)
        }
        assertEquals(Symbol.CROSS.s, board[1].symbol)
    }

    @Test
    fun `Player makes horizontal winning play on his turn`() {
        val board = Position.totalPositions.toMutableList()
        val player1 = Player("Vasco", false, Symbol.CIRCLE)
        val player2 = Player("Rodrigo", true, Symbol.CROSS)
        board.map {
            if (it.number == 1 || it.number == 5 || it.number == 7 || it.number == 8) {
                it.symbol = Symbol.CROSS.s
            }
            if (it.number == 2 || it.number == 3 || it.number == 4) {
                it.symbol = Symbol.CIRCLE.s
            }
            drawBoardWithSymbols(board)
        }
        if (player2.turn) {
            insertSymbol(8, player2, board)
        }
        assertEquals(true, isGameOver(board))
    }

    @Test
    fun `Player makes vertical winning play on his turn`() {
        val board = Position.totalPositions.toMutableList()
        val player1 = Player("Vasco", false, Symbol.CIRCLE)
        val player2 = Player("Rodrigo", true, Symbol.CROSS)
        board.map {
            if (it.number == 1 || it.number == 4) {
                it.symbol = "X"
            }
            if (it.number == 2 || it.number == 5) {
                it.symbol = "O"
            }
            if (player2.turn) {
                insertSymbol(6, player2, board)
            }
        }
        assertEquals(true, isGameOver(board))
    }

    @Test
    fun `Player makes diagonal winning play on his turn`() {
        val board = Position.totalPositions.toMutableList()
        val player1 = Player("Vasco", false, Symbol.CIRCLE)
        val player2 = Player("Rodrigo", true, Symbol.CROSS)
        board.map {
            if (it.number == 1 || it.number == 5) {
                it.symbol = "X"
            }
            if (it.number == 2 || it.number == 3) {
                it.symbol = "O"
            }
            if (player2.turn) {
                insertSymbol(8, player2, board)
            }
        }
        assertEquals(true, isGameOver(board))
    }
}
