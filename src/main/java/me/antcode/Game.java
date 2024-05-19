package me.antcode;

import java.util.Scanner;

public class Game {


    private final String[][] grid;

    private final int NUM_ROWS;

    private final int NUM_COLS;

    private int turn;

    public Game(){
        NUM_COLS = 7;
        NUM_ROWS = 6;
        turn = 1;

        grid = new String[NUM_ROWS][NUM_COLS];
        for (int row = 0; row < NUM_ROWS; row ++){
            for (int col = 0; col < NUM_COLS; col++) {
                grid[row][col] = "-";
            }
        }
        printBoard();
        Scanner scanner = new Scanner(System.in);
        //main game loop
        while (hasOpenSpace() && checkWinner() == null) {
            System.out.println("Enter a number (1-6) ('0' to quit):");
            int move = scanner.nextInt();
            if (move == 0) return;

            //loop if player's entry wasn't valid
            while (!executeMove(move)){
                if (move == 0) return;
                System.out.println("Enter a number (1-6) ('0' to quit):");
                move = scanner.nextInt();
            }
        }
        //Executes if a winner was found, or there was not any space left.
        if (checkWinner() != null){
            String winner = checkWinner();
            if (winner != null) {
                switch (winner){
                    case "X":
                        System.out.println("Player One Wins!");
                        break;
                    case "O":
                        System.out.println("Player Two Wins!");
                        break;
                }
            }
        }else{
            System.out.println("Game is a tie!");
        }
    }


    /**
     * Prints the current board
     */
    private void printBoard(){
        for (int row = 0; row < NUM_ROWS; row ++){
            for (int col = 0; col < NUM_COLS; col++) {
                if (col == 0){
                    System.out.print("| " + grid[row][col] + " | ");
                }else
                if (col == NUM_COLS - 1) {
                    System.out.println(grid[row][col] + " |");
                } else {
                    System.out.print(grid[row][col] + " | ");
                }
            }
        }
    }


    /**
     * Place the appropriate player's piece on the board depending on the player in turn. Prints the board whether action
     * was successful or not.
     * @param column Position of where to attempt to place the piece
     * @return true if there was a successful placing; false if otherwise.
     */
    private boolean executeMove(int column){
        if (column <= 0 || column > 6) return false;
        String playerMove = turn == 1 ? "X" : "O";
        for (int row = NUM_ROWS - 1; row >= 0; row--){
            if (grid[row][column -1].equals(("-"))){
                grid[row][column - 1] = playerMove;
                turn = turn == 1 ? 2 : 1;
                printBoard();
                return true;
            }
        }
    System.out.println("This column is full, try a different one!");
        printBoard();
    return false;
    }


    /**
     * Checks if there is a winning combination on the board.
     * @return returns the winner's piece if they have a winning combination; null if no winning combinations are found.
     */
    private String checkWinner(){
        // Check horizontal
        for (int row = 0; row < NUM_ROWS; row++){
            for (int col = 0; col < NUM_COLS - 3; col++){
                if (isWinningCombination(grid[row][col], grid[row][col+1], grid[row][col+2], grid[row][col+3])){
                    return grid[row][col];
                }
            }
        }

        // Check vertical
        for (int row = 0; row < NUM_ROWS - 3; row++){
            for (int col = 0; col < NUM_COLS; col++){
                if (isWinningCombination(grid[row][col], grid[row+1][col], grid[row+2][col], grid[row+3][col])){
                    return grid[row][col];
                }
            }
        }

        // Check diagonal (bottom-left to top-right)
        for (int row = 0; row < NUM_ROWS - 3; row++){
            for (int col = 0; col < NUM_COLS - 3; col++){
                if (isWinningCombination(grid[row][col], grid[row+1][col+1], grid[row+2][col+2], grid[row+3][col+3])){
                    return grid[row][col];
                }
            }
        }

        // Check diagonal (top-left to bottom-right)
        for (int row = 3; row < NUM_ROWS; row++){
            for (int col = 0; col < NUM_COLS - 3; col++){
                if (isWinningCombination(grid[row][col], grid[row-1][col+1], grid[row-2][col+2], grid[row-3][col+3])){
                    return grid[row][col];
                }
            }
        }

        return null;
    }

    /**
     * Checks of each of the Strings have the same value
     * @param t1 String one to check.
     * @param t2 String two to check.
     * @param t3 String three to check.
     * @param t4 String four to check.
     * @return true if all values match and are not "-"; false otherwise.
     */
    private boolean isWinningCombination(String t1, String t2, String t3, String t4) {
        return !t1.equals("-") && t1.equals(t2) && t1.equals(t3) && t1.equals(t4);
    }


    /**
     * Checks if there is an open space on the board.
     * @return true if there is an open space; false otherwise.
     */
    private boolean hasOpenSpace(){
        for (String[] row : grid){
            for (String s : row){
                if (s.equals("-")) return true;
            }
        }
        return false;
    }

}
