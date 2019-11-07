/*
 *  Code created by Joan Gil Rigo aka MASACR
 */
package sudoku;

import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author MASACR
 */
public class Sudoku {

    //Will empty at most 65 cells from the sudoku
    private static final int EMPTY = 50;
    public static int sudoku_generated[][];

    /**
     * Generate a sudoku and clear with NULL a max of EMPTY cells
     *
     * @return
     */
    private static int[][] sudokuGenerator() {
        int numbers[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int aux, aux2;
        int sudoku[][] = new int[9][9];
        Random ran = new Random();
        //Fill top-left, center and bottom-right 3x3
        //Top-left square
        for (int i = 0; i < 9; i++) {
            aux = ran.nextInt(9);
            aux2 = numbers[i];
            numbers[i] = numbers[aux];
            numbers[aux] = aux2;
        }
        aux = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sudoku[i][j] = numbers[aux];
                aux++;
                if (aux == 9) {
                    aux = 0;
                }
            }
        }
        //Center square
        for (int i = 0; i < 9; i++) {
            aux = ran.nextInt(9);
            aux2 = numbers[i];
            numbers[i] = numbers[aux];
            numbers[aux] = aux2;
        }
        aux = 0;
        for (int i = 3; i < 6; i++) {
            for (int j = 3; j < 6; j++) {
                sudoku[i][j] = numbers[aux];
                aux++;
                if (aux == 9) {
                    aux = 0;
                }
            }
        }
        //Bottom-right square
        for (int i = 0; i < 9; i++) {
            aux = ran.nextInt(9);
            aux2 = numbers[i];
            numbers[i] = numbers[aux];
            numbers[aux] = aux2;
        }
        aux = 0;
        for (int i = 6; i < 9; i++) {
            for (int j = 6; j < 9; j++) {
                sudoku[i][j] = numbers[aux];
                aux++;
                if (aux == 9) {
                    aux = 0;
                }
            }
        }
        //Fill rest of sudoku with recursive function
        recursive_fill(sudoku, 0, 3);

        //Saving the complete sudoku for comparing both results
        sudoku_generated = sudoku;
        //Empty a max of EMPTY from the sudoku
        empt(sudoku);
        return sudoku;
    }
    /**
     * Empties some part of th sudoku, a max of Empty so it stays solvable
     * @param sudoku 
     */
    private static void empt(int[][] sudoku) {
        Random ran = new Random();
        for (int i = 0; i < EMPTY; i++) {
            sudoku[ran.nextInt(9)][ran.nextInt(9)] = 0;
        }
    }

    /**
     * Recursive function to fill the remaining gaps
     * @param sudoku
     * @param i
     * @param j
     * @return 
     */
    private static boolean recursive_fill(int[][] sudoku, int i, int j) {
        if (j == 9 && i < 9) {
            i = i + 1;
            j = 0;
        }
        if (j == 9 && i == 9) {
            return true;
        }
        if (i < 3) {
            if (j < 3) {
                j = 3;
            }
        } else if (i < 6) {
            if (j > 2 && j < 6) {
                j = j + 3;
            }
        } else {
            if (j == 6) {
                i++;
                j = 0;
                if (i == 9) {
                    return true;
                }
            }
        }
        //fill and check, then call recursive
        for (int z = 1; z <= 9; z++) {
            sudoku[i][j] = z;
            if (checkNumber(sudoku, i, j)) {
                if (recursive_fill(sudoku, i, j + 1)) {
                    return true;
                } else {
                    sudoku[i][j] = 0;
                }
            }
        }
        sudoku[i][j] = 0;
        return false;
    }

    /**
     * Checks if the number put on X, Y is correct
     * @param sudoku
     * @param x
     * @param y
     * @return 
     */
    private static boolean checkNumber(int[][] sudoku, int x, int y) {
        boolean right = true;
        int square_x = 0;
        int square_y = 0;
        int aux = sudoku[x][y];
        //Check lines and rows
        for (int b = 0; ((b < 9) && (right == true)); b++) {
            if (b == y) {
                if (sudoku[b][y] == aux) {
                    right = false;
                }
            } else if (b == x) {
                if (sudoku[x][b] == aux) {
                    right = false;
                }
            } else if ((sudoku[x][b] == aux) || (sudoku[b][y] == aux)) {
                right = false;
            }
        }
        //Check same square
        //Find where X falls
        if (x < 3) {
            square_x = 0;
        } else if (x < 6) {
            square_x = 3;
        } else if (x < 9) {
            square_x = 6;
        }
        //Find where Y falls
        if (y < 3) {
            square_y = 0;
        } else if (y < 6) {
            square_y = 3;
        } else if (y < 9) {
            square_y = 6;
        }

        //Check squares
        for (int i = 0; i < 3 && right == true; i++) {
            for (int j = 0; j < 3 && right == true; j++) {
                if ((i + square_x) != x && (j + square_y) != y) {
                    if (sudoku[i + square_x][j + square_y] == aux) {
                        right = false;
                    }
                }
            }
        }
        return right;
    }
    
    /**
     * Checks if the number will fit correctly on a row
     * @param i
     * @param num
     * @param sudoku
     * @return 
     */
    private static boolean usedRow(int i, int num, int[][]sudoku){
        for (int j = 0; j<9; j++) 
           if (sudoku[i][j] == num){
               return false; 
           }  
        return true; 
    }
    
    /**
     * Checks if the number will fit correctly on a column
     * @param j
     * @param num
     * @param sudoku
     * @return 
     */
    private static boolean usedCol(int j, int num, int[][]sudoku){
        for (int i = 0; i<9; i++) 
           if (sudoku[i][j] == num){
               return false; 
           }  
        return true;
    }
    
    /**
     * Checks if the number will fit correclty on a 3x3 square
     * @param x
     * @param y
     * @param num
     * @param sudoku
     * @return 
     */
    private static boolean checkSquare(int x, int y, int num, int[][]sudoku){
        int square_x = 0;
        int square_y = 0;
        if (x < 3) {
            square_x = 0;
        } else if (x < 6) {
            square_x = 3;
        } else if (x < 9) {
            square_x = 6;
        }
        //Find where Y falls
        if (y < 3) {
            square_y = 0;
        } else if (y < 6) {
            square_y = 3;
        } else if (y < 9) {
            square_y = 6;
        }

        //Check squares
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((i + square_x) != x && (j + square_y) != y) {
                    if (sudoku[i + square_x][j + square_y] == num) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * Checks if a number will fit on a row, column and 3x3 square
     * @param i
     * @param j
     * @param num
     * @param sudoku
     * @return 
     */
    private static boolean checker(int i, int j, int num, int[][] sudoku){
        boolean end;
        end = usedRow(i, num, sudoku);
        if(end == true){
            end = usedCol(j, num, sudoku);
            if(end == true){
                end = checkSquare(i,j,num,sudoku);
            }
        }
        return end;
    }
    
    /**
     * This bad boi can solve so many 9x9 sudokus!
     * (I'm planning on making it a variable width/length solver, just being
     * veeeeeery lazy about it)
     * @param sudoku
     * @param i
     * @param j
     * @return 
     */
    private static boolean sudokuSolver9000(int[][] sudoku, int i, int j) {
        if (j >= 9 && i >= 9) {
            return true;
        }
        if (j >= 9 && i < 9) {
            i = i + 1;
            j = 0;
        }
        if(i >= 9){
            return true;
        }
        if (sudoku[i][j] != 0) {
            if(sudokuSolver9000(sudoku, i, j + 1)){
                return true;
            }else{
                return false;
            }
        } else {
            for (int z = 1; z <= 9; z++) {
                if (checker(i,j,z,sudoku)) {
                    sudoku[i][j] = z;
                    if (sudokuSolver9000(sudoku, i, j + 1)) {
                        return true;
                    }
                    sudoku[i][j] = 0;
                }
            }
            return false;
        }
    }

    /**
     * A lot of shit calling things and the user interface
     * (just the console for now, someday will improve it)
     * @param args
     */
    public static void main(String[] args) {
        int input;
        int sudoku[][] = new int[9][9];
        boolean matrix = false;
        Scanner obj = new Scanner(System.in);
        System.out.println("Choose one number:");
        System.out.println("1- Generate a new random sudoku");
        System.out.println("2- Place the sudoku data");
        input = obj.nextInt();
        while (!matrix) {
            switch (input) {
                case 1:
                    //Generate a random sudoku
                    sudoku = sudokuGenerator();
                    break;

                case 2:
                    //Wait input for sudoku and place instructions
                    System.out.println("Instructions: If the value isn't"
                            + " known place 0, "
                            + "in other cases place the number");
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            sudoku[i][j] = obj.nextInt();
                        }
                    }
                    break;
            }
            System.out.println("Is this sudoku correct? (0 = No, 1 = Yes)");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.print(sudoku[i][j] + " ");
                }
                System.out.println();
            }
            if (obj.nextInt() == 1) {
                matrix = true;
            }
        }
        //Solve the sudoku and show the answer
        sudokuSolver9000(sudoku, 0, 0);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(sudoku[i][j] + " ");
            }
            System.out.println();
        }
    }

}