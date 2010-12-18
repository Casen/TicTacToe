// Written by Casen Davis
//
// Last modified 11/17/10
//
// Class to define the state of Tic Tac Toe game board
//
//
import java.io.*;
import java.util.*;
////////////////////////////////////////////////////////////////////////////////
class GameBoardTwo
{
//------------------------------------------------------------------------------
	public int[] board = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	public int player = 9;
	public int depth = 0;
	public int utility = 0;
	public boolean winner = false;
	public GameBoardTwo successor;
	public int lastMovePlayed;
	static Evaluator eval = new Evaluator();
	     
   	// Create blank gameboard
   	public GameBoardTwo() {

   	}

	//constructor for creating a new gameboard from an old one
	public GameBoardTwo(GameBoardTwo n) {
		System.arraycopy(n.board,0,board,0,64);
		player = n.player;
		lastMovePlayed = n.lastMovePlayed;
		this.depth = n.depth;
		utility = eval.utility(board, player);
	}

	//constructor for placing a move
	public GameBoardTwo(GameBoardTwo prev, int player, int location) {
		System.arraycopy(prev.board,0,board,0,64);
		placeMove(player, location);
		this.depth = prev.depth + 1;
		utility = eval.utility(board, player);
   	}
	
	private void placeMove(int p, int location) {
		player = p;
		lastMovePlayed = location;
		board[location] = player;
	}
	
	//AB search finds the successor for this state and uses this method to add it
	public void addSuccessor(GameBoardTwo s) {
		successor = new GameBoardTwo(s);
	}
	
	//fetch an object's successor
	public GameBoardTwo getSuccessor() {
		return successor;
	}
	
	public boolean onBoard(int i)
	{
		if(i>63 || i<0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	//wrapper function for computing utility... think I did this for code compatibility in other classes...
	public void computeUtility() {
		utility = eval.utility(board, player);
	}
	
	public int hashCode () {
	        
	        return Arrays.hashCode(this.board);
	}

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        GameBoardTwo rhs = (GameBoardTwo) obj;
        return Arrays.equals(this.board, rhs.board);
    }
	

//------------------------------------------------------------------------------
} // end class GameBoardTwo
////////////////////////////////////////////////////////////////////////////////


