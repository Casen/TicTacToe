// Written by Casen Davis
//
// Last modified 11/29/10
//
// Class to define the adversarial search
//
//
import java.util.*;
////////////////////////////////////////////////////////////////////////////////
public class TerminatorTwo {
//------------------------------------------------------------------------------

	public int maxDepth = 1;
	protected int level, timeAllowed;
	protected double startTime;
	protected GameBoardTwo presentState, initialState;
	protected Evaluator eval = new Evaluator();
	//public ArrayList<GameBoardTwo> transpositions = new ArrayList<GameBoardTwo>();
	public ArrayList<GameBoardTwo> pruned = new ArrayList<GameBoardTwo>();
	static final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
	static final int[] centerGrid = {18,19,20,21,26,27,28,29,34,35,36,37,42,43,44,45};
	static final int[] minusPerimeter = {9,10,11,12,13,14,17,18,19,20,21,22,25,26,27,28,29,30,33,34,35,36,37,38,41,42,43,44,45,46,49,50,51,52,53,54};
	static final String[] moves = {
		"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8",
		"B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8",
		"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8",
		"D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8",
		"E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8",
		"F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8",
		"G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8",
		"H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8",
	};
//------------------------------------------------------------------------------

	// constructor for computer's first move
	public TerminatorTwo(int t)
	{
		timeAllowed = t*1000;
		initialState = new GameBoardTwo();
		presentState = new GameBoardTwo(initialState, 1, 35);
		level = 0;
	}
	
	//constructor for human's first move
	public TerminatorTwo(int t, String firstMove)
	{
		timeAllowed = t*1000;
		initialState = new GameBoardTwo();
		presentState = new GameBoardTwo(initialState, 1, getIndex(firstMove));
		level = 0;
	}
	
	public boolean hasEnded() {
		return eval.winner(getState().board) || eval.draw(getState().board);
	}
	
	public String lastMovePlayed() {
		return moves[getState().lastMovePlayed];
	}

	public int getPlayerToMove(GameBoardTwo state) {
		if(state.player == 1) {
			return 9;
		}
		else {
			return 1;
		}	
	}

	public GameBoardTwo getState() {
		return presentState;
	}
	
	public void makeMove(int player, String location) {
		presentState = new GameBoardTwo(getState(),player,getIndex(location));
		level++;
	}
	
	//method to initiate alpha beta search and supporting heuristics
	public void makeAlphaBetaMove() {
		
		int player = getPlayerToMove(getState());
		GameBoardTwo nextState = new GameBoardTwo();
		
		startTime = System.currentTimeMillis();
		int killerMove = eval.killerMove(getState().board, player);
		int f = openingMove(getState().lastMovePlayed);
		if( f != 100 && level < 1) {
			presentState = new GameBoardTwo(getState(),player,f);
			level++;
		}
		else if(killerMove !=64) {
			presentState = new GameBoardTwo(getState(),player,killerMove);
			level++;
		}
		else {
			// Iterative Deepening search
			for(int i=1; i<64; i+=2) {
				getAlphaBetaValue(getState(), i);
				nextState = (GameBoardTwo) getState().getSuccessor();
				if (nextState == null) {
					throw new RuntimeException("Alpha Beta Move failed");
				}
			}
			

			presentState = new GameBoardTwo(nextState);
			level++;
		}
		int c = 0;
		while(!cutoffTest() && c<pruned.size()) {
			if(pruned.get(c).depth < level) {
				pruned.remove(c);
			}
			c++;
		}
		
	}
	
	// initiate alpha beta search on a particular state for a given depth
	public int getAlphaBetaValue(GameBoardTwo state, int d) {	
	    if (getPlayerToMove(state) == 1) {
            return maxValue(state, d, Integer.MIN_VALUE, Integer.MAX_VALUE);
	    } else {
            return minValue(state, d, Integer.MIN_VALUE, Integer.MAX_VALUE);
	    }
    }


	public int minValue(GameBoardTwo state, int d, int alpha, int beta) {
		int v = Integer.MAX_VALUE;
		if (cutoffTest() || eval.winner(state.board) || (d==0) ) {
			return state.utility;
		} else {
			ArrayList<GameBoardTwo> successorList = getSuccessorStates(state,1);
			for (int i = 0; i < successorList.size(); i++) {
				GameBoardTwo successor = successorList.get(i);
				int maximumValueOfSuccessor = maxValue(successor, (d-1), alpha, beta);
				if (maximumValueOfSuccessor < v) {
					v = maximumValueOfSuccessor;
					state.addSuccessor(successor);
				}
				if (v <= alpha) {
					pruned.add(state);
					return v;
				}
				beta = Math.min(beta, v);
			}
			return v;
		}

	}
	
	public int maxValue(GameBoardTwo state, int d, int alpha, int beta) {
		int v = Integer.MIN_VALUE;
		if (cutoffTest() || eval.winner(state.board) || (d==0))  {	
			return state.utility;
		} else {
			ArrayList<GameBoardTwo> successorList = getSuccessorStates(state, 0);
			for (int i = 0; i < successorList.size(); i++) {
				GameBoardTwo successor = (GameBoardTwo) successorList.get(i);
				int minimumValueOfSuccessor = minValue(successor,(d-1), alpha, beta);
				if (minimumValueOfSuccessor > v) {
					v = minimumValueOfSuccessor;
					state.addSuccessor(successor);
				}
				if (v >= beta) {
					pruned.add(state);
					return v;
				}
				alpha = Math.max(alpha, v);
			}
			return v;
		}
	}


	

	// heuristic for placing opening move
	protected int openingMove(int move){
		if(move == 27){
			return 35;
		}
		else if(move == 28) {
			return 27;
		}
		else if(move == 35) {
			return 36;
		}
		else if(move == 36) {
			return 35;
		}
		else {
			return 100;
		}
	}
	
	//decides if a move is close to the center of the board
	protected boolean inCenter(int move) {
		for(int i=0; i<16; i++) {
			if(centerGrid[i] == move) {
				return true;
			}
		}
		return false;
	}
	
	protected ArrayList<GameBoardTwo> getSuccessorStates(GameBoardTwo s, int min)
	{
		Comparator<GameBoardTwo> comparator;
		ArrayList<GameBoardTwo> successors = new ArrayList<GameBoardTwo>();
		int player = getPlayerToMove(s);
		int move = s.lastMovePlayed;	
		//transpositions.add(s);
		maxDepth = Math.max(maxDepth, s.depth);
		// if a move is close to the center, ignore searching the perimeter for successors
		if(inCenter(move) && level < 16) {
			for(int i=0; i<36; i++) {
				if(s.board[minusPerimeter[i]] == 0) {
					GameBoardTwo temp = new GameBoardTwo(s,player,minusPerimeter[i]);
					if(!pruned.contains(temp)) {
						successors.add(temp);
					}
				
				}
			}
		}
		else {
			for(int i=0; i<64; i++) {
				if(s.board[i] == 0) {
					GameBoardTwo temp = new GameBoardTwo(s,player,i);
					if(!pruned.contains(temp)) {
						successors.add(temp);
					}
				}
			}
		}
		
		// sort in ascending or descending order for max and min
		if(min==1) {
			comparator = new ReverseGameBoardTwoComparator();
		}
		else {
			comparator = new GameBoardTwoComparator();
		}
		Collections.sort(successors,comparator);
		return successors;
		
	}
	
	protected boolean cutoffTest() {
		if((System.currentTimeMillis() - startTime) > timeAllowed){	
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
	public void print()
	{	
		for(int i=1; i<9; i++){
			System.out.print("  " +i);
		}
		for(int i=0; i<64; i++)
		{	
			if(i%8 == 0)
			{
				System.out.println();
				System.out.print(letters[i/8]);
				if(getState().board[i] == 0){System.out.print(" - ");}
				else if(getState().board[i] == 1){System.out.print(" X ");}
				else{System.out.print(" O ");}
			}
			else
			{
				if(getState().board[i] == 0){System.out.print(" - ");}
				else if(getState().board[i] == 1){System.out.print(" X ");}
				else{System.out.print(" O ");}
			}
			
		}
		System.out.println();
		
	}
	
	// returns the array index for a board location described E5, D4, etc
	public int getIndex(String location)
	{
		int index = 0;
		while(index<64 && !moves[index].equals(location))
		{
			index++;
		}
		return index;
	}
	
	// determines if a position is available on the board
	public boolean open(String location)
	{
		int i = getIndex(location);
		
		if(getState().onBoard(i))
		{
			if(getState().board[i] == 0){return true;}
			else{return false;}
		}
		return false;
	}
	
//------------------------------------------------------------------------------
} // end class TerminatorTwo
////////////////////////////////////////////////////////////////////////////////