
import java.util.Scanner;
import java.util.Random;

public class ConnectFour {
    static Scanner kb = new Scanner(System.in);
    static Random rand = new Random();
    static int nextMovePcRow = 0;
    static int nextMovePcColumn = 0;

    public static void main(String[] args) {
        while (true) {
            System.out.println("---------------------------");
            System.out.println("Connect 4");
            System.out.println("1. New Game");
            System.out.println("2. Rules");
            System.out.println("3. Quit");
            System.out.println("---------------------------");
            System.out.print("Choose: ");
            int choice = kb.nextInt();
            System.out.println("---------------------------");
            if (choice == 1) {
//play connect 4
                char[][] game = newGame();
                int turn, column, row;
                boolean gamePlay = true;
                displayGame(game);
//display game
                while (gamePlay == true) {
                    for (turn = 0; turn < 22; turn++) {
//player 1's turn
                        while (true) {
                            System.out.print("Enter Column: ");
                            column = kb.nextInt() - 1;
                            if (column <= 6 && column >= 0) {
                                //check that entry is valid
                                if (game[0][column] == ' ') {
                                    //quick check for at least one space in that column
                                    row = findRow(column, game);
                                    //obtain last empty row in column
                                    game[row][column] = 'X';
                                    displayGame(game);
                                    break;
                                }
                            }
                        }
                        if (checkWin(column, row, game)) {
//check for 4 in a row
                            System.out.println("You Won!\n");
                            gamePlay = false;
                            break;
                        }
//computer's turn
                        while (true) {

                            if(bestPCMove(game)){
                                game[nextMovePcRow][nextMovePcColumn] = 'O';
                                displayGame(game);
                                break;
                            }
                            else {
                                column = rand.nextInt(7);
                                if (game[0][column] == ' ') {
                                    row = findRow(column, game);
                                    game[row][column] = 'O';
                                    displayGame(game);
                                    break;
                                }
                            }
                        }
                        if (checkWin(column, row, game)) {
                            System.out.println("Computer Won!");
                            gamePlay = false;
                            break;
                        }
                    }
                    if (turn == 22) {
                        System.out.println("Out of turns! Game over");
                        gamePlay = false;
                        break;
                    }
                }
            } else if (choice == 2) {
//see game rules
                System.out.println("Rules");
                System.out.println("---------------------------");
                System.out.println("Be the first to get 4 in a row!");
                System.out.println("---------------------------");
                System.out.println("1. Choose 1 of the 7 columns\n   to place your piece");
                System.out.println("2. Computer will take a turn");
                System.out.println("3. Repeat until you or\n   computer gets 4 in a row");
                System.out.println("3. Game can end in a\n   draw after 21 turns");
            } else if (choice == 3) {
//quit
                System.out.println("Thank you for playing!");
                kb.close();
                System.exit(0);
            }
        }
    }
    public static boolean bestPCMove(char[][] g) {
        boolean bestmove = false;
        for(int i = g.length-1; i>=0; i--){
            //checks horizontal from column 0 to column length
            for(int j = 0; j<g[0].length; j++){
                if(g[i][j]=='X'){
                    int y = j;
                    int counter = 1;
                    while(g[i][y] == 'X' && y<g[0].length-1){
                        if(g[i][y+1] == 'X'){
                            counter++;
                        }
                        if(counter==3 && g[i][y+1] == ' ' && findRow(y+1,g) == i){
                            nextMovePcRow = i;
                            nextMovePcColumn = y+1;
                            bestmove = true;
                        }
                        y++;
                    }
                }
            }
            //checks horizontal from column length to column 0
            for(int j = g[0].length-1; j>0; j--){
                if(g[i][j]=='X'){
                    int y = j;
                    int counter = 1;
                    while(g[i][y] == 'X' && y > 0){
                        if(g[i][y-1] == 'X'){
                            counter++;
                        }
                        if(counter==3 && g[i][y-1] == ' ' && findRow(y-1,g) == i){
                            nextMovePcRow = i;
                            nextMovePcColumn = y-1;
                            bestmove = true;
                        }
                        y--;
                    }
                }
            }

            //checks Vertically
            for(int j = 0; j<g[0].length; j++){
                if(g[i][j]=='X'){
                    int x = i;
                    int counter = 1;
                    while(g[x][j] == 'X' && x > 0 && x<6){
                        if(g[x-1][j] == 'X'){
                            counter++;
                        }
                        if(counter==3 && g[x-1][j] == ' '){// && findRow(j-1,g) == i){
                            nextMovePcRow = findRow(j,g);
                            nextMovePcColumn = j;
                            bestmove = true;
                        }
                        x--;
                    }
                }
            }

        }

        return bestmove;
    }

    public static char[][] newGame() {
//create game board
        char[][] setUp = new char[6][7];
        int i, j;
        for (i = 0; i < setUp.length; i++) {
//add game spaces into game board
            for (j = 0; j < setUp[i].length; j++) {
                setUp[i][j] = ' ';
            }
        }
        return setUp;
    }

    public static void displayGame(char[][] gameBoard) {
//display game board after each turn with column numbers
        int i, j;
        for (i = 0; i < gameBoard.length; i++) {
            for (j = 0; j < gameBoard[i].length; j++) {
                if (j == 6) {
                    System.out.println(" " + gameBoard[i][j]);
                } else {
                    System.out.print(" " + gameBoard[i][j] + " |");
                }
            }
        }
        System.out.println("---------------------------");
        System.out.println(" 1 | 2 | 3 | 4 | 5 | 6 | 7\n");
    }

    public static int findRow(int cc, char[][] gameFind) {
        int i, rr = 0;
        for (i = 0; i < gameFind.length; i++) {
            if (gameFind[i][cc] == ' ') {
                rr = i;
            }
        }
        return rr;
    }

    public static boolean checkWin(int c, int r, char[][] g) {
        boolean win = false;
//check column, c, for win
        for (int p = 0; p < 3; p++) {
            if (g[p][c] != ' ' && g[p][c] == g[p + 1][c] && g[p][c] == g[p + 2][c] && g[p][c] == g[p + 3][c]) {
                win = true;
            }
        }
//check row, r, for win
        for (int q = 0; q < 4; q++) {
            if (g[r][q] != ' ' && g[r][q] == g[r][q + 1] && g[r][q] == g[r][q + 2] && g[r][q] == g[r][q + 3]) {
                win = true;
            }
        }
//check diagonals for win
        for (int t = 0; t < g.length - 3; t++) { //row
            for (int s = 0; s < g[t].length - 3; s++) { //column
                if (g[t][s] != ' ' && g[t][s] == g[t + 1][s + 1] && g[t][s] == g[t + 2][s + 2] && g[t][s] == g[t + 3][s + 3]) {
                    win = true;
                }
            }
        }
        for (int t = 3; t < g.length; t++) { //row
            for (int s = 0; s < g[t].length - 3; s++) { //column
                if (g[t][s] != ' ' && g[t][s] == g[t - 1][s + 1] && g[t][s] == g[t - 2][s + 2] && g[t][s] == g[t - 3][s + 3]) {
                    win = true;
                }
            }
        }
        return win;
    }
}
