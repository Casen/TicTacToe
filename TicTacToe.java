// Written by Casen Davis
//
// Last modified 11/17/10
//
// Class to demo Tic Tac Toe game
//
//
import java.io.*;
import java.util.*;
////////////////////////////////////////////////////////////////////////////////
class TicTacToe
{
//------------------------------------------------------------------------------

public static void main ( String [] args ) 
{	
	Scanner input = new Scanner(System.in);
	GameBoardTwo g = new GameBoardTwo();
	TerminatorTwo t1000;
	int player = 1;
	System.out.print("How much time?: ");
	int time = Integer.parseInt(input.nextLine());
	System.out.print("Do you want to play first? (y/n): ");
	String a = input.nextLine();
	
	
	if(a.equals("n")) {
		t1000 = new TerminatorTwo(time);
		player = 9;
		System.out.println("My move: " + t1000.lastMovePlayed());
	}
	else {
		System.out.print("Your Move: ");
		String c = input.nextLine();
		t1000 = new TerminatorTwo(time, c);
		t1000.print();
		System.out.println("Thinking...");
		t1000.makeAlphaBetaMove();
		System.out.println("My move: " + t1000.lastMovePlayed());
	}
	
    while (!(t1000.hasEnded())) {
		t1000.print();
		System.out.print("Your Move: ");
		String c = input.nextLine();
		while(!t1000.open(c)) {
			System.out.println("Illegal move!");
			System.out.print("Your Move: ");
			c = input.nextLine();
		}
		t1000.makeMove(player,c);
		if(t1000.hasEnded()) {
			break;
		}	
		t1000.print();
		
		System.out.println("Thinking...");
	    t1000.makeAlphaBetaMove();
		System.out.println("My move: " + t1000.lastMovePlayed());
    }
	t1000.print();
	System.out.println("Game Over!");

}

//------------------------------------------------------------------------------
} // end class TicTacToe
////////////////////////////////////////////////////////////////////////////////
