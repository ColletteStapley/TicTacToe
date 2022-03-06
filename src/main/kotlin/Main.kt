import java.util.Scanner

class Board {
    private var board = arrayListOf(
        "X", "X", "X", "O", "5", "6", "7", "O", "9"
    )
    //    init {
//        for (i in 1..9) {
//            board[i - 1] = i.toString()
//            i + 1
//        }
//    }
    fun resetBoard () {
        for (i in 1..9) {
            board[i - 1] = i.toString()
            i + 1
        }
    }

    fun getBoard() : ArrayList<String> {
        return board
    }

    fun displayBoard() {
        print("$board \n")
        print("${board[0]} ")
        for (i in 1..8) {
            if (i == 3 || i == 6) {
                println("")
                println("-+-+-")
                print("${board[i]} ")
            } else {
                print("${board[i]} ")
            }
        }
        println("\n")
    }

//    fun displayAllBoard() {
//        board[0] = "X"
//        print("$board")
//    }

    operator fun set (spot : Int, ticTac : String) {
        print("${board[spot - 1]} ")
        when (ticTac) {
            "X" -> board[spot - 1] = "X"
            "O" -> board[spot - 1] = "O"
            else -> {
                board[spot - 1] = "?"
            }
        }
        println("${board[spot - 1]} ")
    }
}


val b = Board()
private fun playerTurns(turn: Int): Int {
    val reader = Scanner(System.`in`)
    val slot : Int
    if (turn % 2 != 0) {
        print("X's turn to choose a square (1-9): ")
        slot = reader.nextInt()
        b[slot] = "X"
    } else {
        print("O's turn to choose a square (1-9): ")
        slot = reader.nextInt()
        b[slot] = "O"
    }
    val turns = turn + 1
    println("")
    return turns
}

private fun options(): String {
    print("Would you like to play again? y/n > ")
    return readLine()!!
}

private fun gameOver(turn: Int, b: Board): String {
    val board : ArrayList<String> = b.getBoard()
    
    val player = when (turn % 2) {
        0 -> "X"
        1 -> "O"
        else -> {
            println("Something is wrong here?")
            "?"
        }
    }

    for (i in 0..8 step (3)) {
        if (board[i] == player && board[i + 1] == player && board[i + 2] == player) {
            return "$player Wins! Thanks for Playing!"
        }
    }

    if ((board[0] == player && board[4] == player && board[8] == player) ||
        board[2] == player && board[4] == player && board[6] == player
    ) {
        return "$player Wins! Thanks for Playing!"
    }

    var count = 0

    for (i in 0..8) {
        if (board[i] != "X") {
            if (board[i] != "O") {
                count += 1
            }
        }
    }

    if (count == 0) {
        return "Cats Cradle, IT'S A TIE! Thanks for Playing!"
    }
    return "continue"
}

fun main() {
    val b = Board()

    println("")
    b.displayBoard()
    var end = "continue"
    var turn = 1

    while (end == "continue") {
        turn = playerTurns(turn)
        // b.displayAllBoard()
        b.displayBoard()
        end = gameOver(turn, b)
        if (end != "continue") {
            println(end)
            end = options()
            if (end == "Play Again") {
                b.resetBoard()
                turn = 1
                end = "continue"
            }
        }
    }
    println(end)

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    // println("Program arguments: ${args.joinToString()}")
}