
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

public class TicTacToe {

	/*3x3 tic-tac-toe board*/
	private static String[][] board = new String[3][3];
	/*array of the index of open spaces available for play*/
	private static  ArrayList<Integer> openSpaces = new ArrayList<Integer>()
			{{add(0); add(1); add(2); add(3); add(4); add(5); add(6); add(7); add(8);}};
	private static ArrayList<Integer> playerMoves = new ArrayList<Integer>();
	private static ArrayList<Integer> computerMoves = new ArrayList<Integer>();
	private static int turns = 0;
	private static String player = "";
	private static String computer = "";

	public static void main(String args[]){
		int grid = 0;
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board.length; j++){
				board[i][j] = Integer.toString(grid);
				grid++;
			}
		}
		String input = "";

		System.out.println("You are playing tic-tac-toe!");
		System.out.println("For instructions enter 'help'. To quit type 'q'");
		System.out.print("What letter would you like to be? (x or o): ");
		input = System.console().readLine();

		while(!input.equals("x") && !input.equals("o")){
			if(input.equals("help")){
				System.out.println("tic-tac-toe is played on a 3x3 grid with 2 players.");
				System.out.println("to win you must place three of your letters in a row");
				System.out.println("taking turns, each player chooses a grid to place their letter");
				System.out.println("**to place a letter on the board simply type in the grid# you would like to play");
				System.out.println("the top left corner is grid0. middle left is grid3. bottom left is grid6.");
				System.out.println("the grid#'s increment by 1 as you move right (top middle is grid1)");
				System.out.print("What letter would you like to be? (x or o): ");
				input = System.console().readLine();
			}
			else if(input.equals("q")){
				System.out.println("Thank you for playing.");
				System.exit(0);
			}
			else{
				System.out.println("improper input.");
				System.out.print("What letter would you like to be? (x or o): ");
				input = System.console().readLine();
			}
		}
		player = input;
		if(player.equals("o")){
			computer = "x";
		}
		else{
			computer = "o";
		}
		play();
	}

	public static void play(){
		playersTurn();
		
	}

	public static void playersTurn(){
//		System.out.println("turn #: "+turns);
		int gridNum = -1;
		System.out.println("It's your turn!");
		printBoard();
		System.out.println("Where would you like to play? (enter grid#): ");

		try{
			gridNum = Integer.parseInt(System.console().readLine());
			if(checkMove(gridNum)){
				placeMove(gridNum, player);
				if(turns < 9){
					computersTurn();
				}

			}
			else{
				System.out.print("That grid# is already taken. Please choose a different grid#: ");
				gridNum = Integer.parseInt(System.console().readLine());
				while(!checkMove(gridNum)){
					System.out.println("Open grid#s are:");
					for(int i = 0; i < openSpaces.size(); i++){
						System.out.print("[" + openSpaces.get(i) + "]");
					}
					System.out.print("Please choose a space: ");
					gridNum = Integer.parseInt(System.console().readLine());
				}
				placeMove(gridNum, player);
				if(turns < 9){
					computersTurn();
				}
			}
		}
		catch(Exception e){
			System.out.println("Thanks for playing!");
			System.exit(0);
		}

	}

	public static void computersTurn(){
		System.out.println("It's the computer's turn...");
		Random num = new Random();
		int index = num.nextInt((openSpaces.size()-1)+1);
		int move = openSpaces.get(index);
		if(checkMove(move)){
			placeMove(move, computer);
			if(turns < 9){
//				System.out.println("Open grid#s are:");
//				for(int i = 0; i < openSpaces.size(); i++){
//					System.out.print("[" + openSpaces.get(i) + "]");
//				}
				playersTurn();
			}
		}
		else{
			computersTurn();
		}
	}
	
	public static boolean checkMove(int gridSpace){
		if(openSpaces.contains(gridSpace)){
			openSpaces.remove(openSpaces.indexOf(gridSpace));
			return true;
		}
		return false;
	}
	
	public static void placeMove(int gridSpace, String whoseTurn){
		if(player.equals(whoseTurn)){
			playerMoves.add(gridSpace);
		}
		else{
			computerMoves.add(gridSpace);
		}
		if(gridSpace < 3){
			board[0][gridSpace] = whoseTurn;
		}
		else if(gridSpace > 2 && gridSpace < 6){
			board[1][gridSpace-3] = whoseTurn;
		}
		else{
			board[2][gridSpace-6] = whoseTurn;
		}
		if(turns > 3){
			if(checkWin(gridSpace, whoseTurn)){
				if(player.equals(whoseTurn)){
					printBoard();
					System.out.println("You win!");
					System.exit(0);
				}
				else{
					printBoard();
					System.out.println("Computer wins!");
					System.exit(0);
				}
			}
		}
		turns++;
		if((turns > 8) && !(checkWin(gridSpace, whoseTurn))){
			printBoard();
			System.out.println("Cat's game");
			System.exit(0);
		}
	}
	
	public static boolean checkWin(int gridSpace, String whoseTurn){
		boolean won = false;
		ArrayList<Integer> checkList;
		if(player.equals(whoseTurn)){
			checkList = playerMoves;
		}
		else{
			checkList = computerMoves;
		}
		/* check verticals */
		if(gridSpace == 4 ||
				gridSpace == 0 || gridSpace == 1 || gridSpace == 2 || 
				gridSpace == 6 || gridSpace == 7 || gridSpace == 8){
			if((checkList.contains(0) && checkList.contains(3) && checkList.contains(6)) || 
					(checkList.contains(1) && checkList.contains(4) && checkList.contains(7)) || 
					(checkList.contains(2) && checkList.contains(5) && checkList.contains(8))){
				won = true;
				return won;
			}
		}
		/* check horizontals */
		if(gridSpace == 4 || 
				gridSpace == 0 || gridSpace == 3 || gridSpace == 6 || 
				gridSpace == 2 || gridSpace == 5 || gridSpace == 8){
			if((checkList.contains(0) && checkList.contains(1) && checkList.contains(2)) || 
					(checkList.contains(3) && checkList.contains(4) && checkList.contains(5)) || 
					(checkList.contains(6) && checkList.contains(7) && checkList.contains(8))){
				won = true;
				return won;
			}
		}
		/* check diagonals */
		if(gridSpace == 4 || gridSpace == 0 || gridSpace == 2 || gridSpace == 6 || gridSpace == 8){
			if((checkList.contains(0) && checkList.contains(4) && checkList.contains(8)) || 
					(checkList.contains(2) && checkList.contains(4) && checkList.contains(6))){
				won = true;
				return won;
			}
		}
		return won;
	}
	public static void printBoard(){
		System.out.println("Here is the board (numbers indicate grid#'s):");
		for(int x = 0; x < board.length; x++){
			for(int y = 0; y < board.length; y++){
				if(y == 2 || y == 5 || y == 8){
					System.out.println(board[x][y] + " ");
				}
				else{
					System.out.print(board[x][y] + " | ");
				}
			}
			if(x != 2){
				System.out.println("----------");
			}
		}
	}
}
