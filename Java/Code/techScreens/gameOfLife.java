package Code.techScreens;


// Was given this game of life challenge from a tech screen from Dropbox (https://www.dropbox.com)
// https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
// Input:
// an n by m grid of "alive" or "dead" cells
//
// Output:
// A transformation on the input grid using the following rules:
//   - An "alive" cell remains alive if 2 or 3 neighbors are "alive." Otherwise, it becomes "dead."
//   - A "dead" cell becomes alive if exactly 3 neighbors are "alive." Otherwise, it remains "dead."
//     (The term "neighbor" refers to the at-most-8 adjacent cells horizontally, vertically, and diagonally.)
//
//Example: Suppose 0 is dead and 1 is alive, what would the following input yield?
//Input:
//1001
//0110
//1001 ->

// Cleaned it up and added test cases. Sure it could be optimized but I'd rather preserve my work as it is
// when I attempted it.

// Gotchas. The rules they give were vastly simplified and different then the usual "Game of life" rules.


import java.util.*;

class GameOfLife {
    public static void main(String[] args) {

        ArrayList<ArrayList<Integer>> firstTest = new ArrayList<ArrayList<Integer>>();

        firstTest.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0)));
        firstTest.add(new ArrayList<Integer>(Arrays.asList(1, 1, 1, 0)));
        firstTest.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0)));

        System.out.println("\nThe first board looks like this: ");
        for (List<Integer> list : firstTest) {
            for (Integer i : list) {
                System.out.print(i+" ");
            }
            System.out.println();
        }

        ArrayList<ArrayList<Integer>> firstTestResults = nextBoardState(firstTest);

        System.out.println("\nThe first iteration from the first board looks like this: ");
        for (List<Integer> list : firstTestResults) {
            for (Integer i : list) {
                System.out.print(i+" ");
            }
            System.out.println();
        }

        ArrayList<ArrayList<Integer>> secondTest = new ArrayList<ArrayList<Integer>>();

        secondTest.add(new ArrayList<Integer>(Arrays.asList(1, 0, 0, 1)));
        secondTest.add(new ArrayList<Integer>(Arrays.asList(0, 1, 1, 0)));
        secondTest.add(new ArrayList<Integer>(Arrays.asList(1, 0, 0, 1)));

        System.out.println("\nThe second board looks like this (Dropbox example!): ");
        for (List<Integer> list : secondTest) {
            for (Integer i : list) {
                System.out.print(i+" ");
            }
            System.out.println();
        }

        ArrayList<ArrayList<Integer>> secondTestResults = nextBoardState(secondTest);

        System.out.println("\nThe first iteration from the second board (Dropbox example!) looks like this: ");
        for (List<Integer> list : secondTestResults) {
            for (Integer i : list) {
                System.out.print(i+" ");
            }
            System.out.println();
        }

    }

    /*
     * This function takes in a 2D Arraylist that represents a game board of conway's game of life.
     * It then attempts to return the next iteration of that board state from the rules below.
     * For integers, 1 is alive, 0 is dead.
     */

    public static ArrayList<ArrayList<Integer>> nextBoardState(ArrayList<ArrayList<Integer>> inputBoard) {

        // Create empty board to return after filling it with the next board state.
        ArrayList<ArrayList<Integer>> newBoardState = new ArrayList<ArrayList<Integer>>();

        // Make an empty board, so we don't have copy problems.

        newBoardState.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0)));
        newBoardState.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0)));
        newBoardState.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0)));

        // Go through every cell in the board.
        for (int xIndex = 0; xIndex < inputBoard.size(); xIndex++) {

            for (int yIndex = 0; yIndex < inputBoard.get(xIndex).size(); yIndex++) {

                newBoardState.get(xIndex).set(yIndex, aliveOrDead(inputBoard, xIndex, yIndex));

            }

        }

        return newBoardState;
    }


    // Helper function, toss in the board, x, and y, and returns see if that cell is alive or dead in the next
    // iteration.
    public static int aliveOrDead(ArrayList<ArrayList<Integer>> inputBoard, Integer xIndex, Integer yIndex) {

        // Fetch the current state.
        Integer currentState = inputBoard.get(xIndex).get(yIndex);
        // System.out.println("I'm currently looking at index x,y of: (" + xIndex + ", " + yIndex + ")" );
        // System.out.println("It's current state is: " + inputBoard.get(xIndex).get(yIndex) );


        Integer currentAliveNeighbors = 0;
        Integer newState = currentState;

        for (int xIndexModifier = -1; xIndexModifier <= 1; xIndexModifier++) {

            for (int yIndexModifier = -1; yIndexModifier <= 1; yIndexModifier++) {

                // Things we want to skip: Out of bounds X and Y (Below 0 or bigger then size)

                if ( (xIndex + xIndexModifier) < 0 ||
                     (yIndex + yIndexModifier) < 0 ||
                     (xIndexModifier + xIndex) >= (inputBoard.size() - 1) ||
                     (yIndexModifier + yIndex) >= (inputBoard.get(xIndex).size() - 1) ||
                     (xIndexModifier == 0 && yIndexModifier == 0) ) {

                    continue;

                } else if (inputBoard.get(xIndex + xIndexModifier).get(yIndex + yIndexModifier) == 1) {
                        currentAliveNeighbors++;
                        //System.out.println("I'm currently looking at index x,y of: (" + xIndex + ", " + yIndex + ")" );
                        //System.out.println("I'm thinking the following neighbor is alive: (" + (xIndex +xIndexModifier) + ", " + (yIndex + yIndexModifier) + ")" +
                                // " It's value actually is: " + inputBoard.get(xIndex + xIndexModifier).get(yIndex + yIndexModifier));
                        //System.out.println("I'm thinking the number of alive neighbors I have is: " + currentAliveNeighbors );
                    }

                }
            }

        // System.out.println("I believe the current number of alive neighbors is: " + currentAliveNeighbors);
        // If dead, check to see if alive again.
        if (currentState == 0 && (currentAliveNeighbors == 3)) {
            //System.out.println("I'm currently looking at DEAD cell at index x,y of: (" + xIndex + ", " + yIndex + ")" );
            //System.out.println("I'm seeing this many neighbors: " + currentAliveNeighbors);
            //System.out.println("I'm currently setting my next state for this cell to ALIVE \n");
            newState = 1;
        } else if (currentState == 0) {
            newState = 0;
        }

        // If alive, check to see if I died.
        if (currentState == 1 && (currentAliveNeighbors == 2 || currentAliveNeighbors == 3)) {
            // System.out.println("I'm currently looking at ALIVE cell at index x,y of: (" + xIndex + ", " + yIndex + ")" );
            // System.out.println("I'm seeing this many neighbors: " + currentAliveNeighbors);
            // System.out.println("I'm currently setting my next state for this cell to ALIVE");
            newState =  1;
        } else if (currentState == 1){
            newState =  0;
        }

        return newState;

    }


}
