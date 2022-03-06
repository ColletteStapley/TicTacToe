import java.util.Scanner

class Board {
    private var board = arrayListOf(
        "X", "X", "X", "O", "5", "6", "7", "O", "9"
    )
    // This above array with pre-chosen characters is useful in showing the issue
    // the code has

    // The init function can be commented out to let the pre-written array be used.
    // Only works when code is first run.
        init {
        for (i in 1..9) {
            board[i - 1] = i.toString()
            i + 1
        }
    }
    /*****************************************************************************
     * ResetBoard : Reset's all board back to the basic board.
     *
     * NOTE: there is an issue here too, related to the set function. When using
     * the prewritten list above, reset changes the board back to 1-9, but set
     * shows old values form original rewritten set.
     *****************************************************************************/
    fun resetBoard () {
        for (i in 1..9) {
            board[i - 1] = i.toString()
            i + 1
        }
    }

    /*****************************************************************************
     * GetBoard : returns the board list to whatever calls it
     *****************************************************************************/
    fun getBoard() : ArrayList<String> {
        return board
    }

    /*****************************************************************************
     * DisplayBoard : Displays the board with correct formatting
     *****************************************************************************/
    fun displayBoard() {
        // this shows the list not formatted, isn't necessary for end code, used to
        // fix "Set" issue
        print("$board \n")

        // Board display code
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

    /*****************************************************************************
     * DisplayAllBoard : I used this function to test what was wrong with set
     * issue seems localized to set only
     *
     * the change made here not only changes, but it stays changed.
     *****************************************************************************/
//    fun displayAllBoard() {
//        board[0] = "X"
//        print("$board")
//    }

    /*****************************************************************************
     * Set : Updates the board spot given with the necessary variable
     *
     * NOTE : This is where my issue is, this could be a single line with
     * just a return, but it isn't working so I got more and more creative in
     * how it was written to get it to work. I failed.
     *
     * It was originally called "Update", and was a regular function, not an operator
     * but that didn't change the issue either.
     *
     * The Issue is that the change made in this function doesn't change elsewhere.
     * it shows the before and after of the board slot, and it looks correct until
     * other functions call it. Then it says it was never changed.
     *****************************************************************************/
    operator fun set (spot : Int, ticTac : String) {
        // prints the before state of the spot, not necessary in finished project
        print("${board[spot - 1]} ")

        // this is honestly the same thing as board[spot - 1] = ticTac, but it
        // wasn't working
        when (ticTac) {
            "X" -> board[spot - 1] = "X"
            "O" -> board[spot - 1] = "O"
            else -> {
                board[spot - 1] = "?"
            }
        }
        // prints the after state of the spot, not necessary in finished project
        println("${board[spot - 1]} ")
    }
}

// creates a board object to be used in main, and throughout.
val b = Board()

/*****************************************************************************
 * PlayerTurns : decides who's turn it is, gets their input,and updates the
 * board.
 *****************************************************************************/
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

/*****************************************************************************
 * Options : Prompts the player to play again
 *****************************************************************************/
private fun options(): String {
    print("Would you like to play again? y/n > ")
    return readLine()!!
}

/*****************************************************************************
 * GameOver : Checks winning/ tie conditions and declares outcome
 *****************************************************************************/
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

/*****************************************************************************
 * Main : runs the game
 *****************************************************************************/
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
            if (end == "y") {
                b.resetBoard()
                turn = 1
                end = "continue"
                b.displayBoard()
            }
        }
    }
    println(end)
}