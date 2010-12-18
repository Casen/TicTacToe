// Written by Casen Davis
//
// Last modified 11/27/10
//
// Class with static methods to evaluate board positions
//
//
////////////////////////////////////////////////////////////////////////////////
class Evaluator
{
//------------------------------------------------------------------------------
	
	static int index = 0;
	static int totalHorxValue = 0;
	static int totalHoroValue = 0;
	static int totalVertxValue = 0;
	static int totalVertoValue = 0;
	static int row =0; 
	static int col =0;
	static int xCount = 0;
	static int oCount = 0;
	
	static int utility(int[] board, int p) {
		if(p == 1) {
			return evalHorizontalX(board) + evalVerticalX(board);
		}
		else {
			return evalHorizontalO(board) + evalVerticalO(board);
		}
		
	}
	
	//--------------------------------------
	// Evaluate for Horizontal X
	// creates a value for x on horizontal placements
	//--------------------------------------
	
	static int evalHorizontalX(int[] board)
	{
		index = 0;
		totalHorxValue = 0;
		totalHoroValue = 0;
		row =0; 
		col =0;
		xCount = 0;
		oCount = 0;
		
		while(row<8)
		{
			
			while(col < 8)
			{	
				while((col < 8) && (board[index]==0))
				{
					index++;
					col++;
				}
				
				while((col < 8) && (board[index]==9))
				{
					index++;
					col++;
					oCount++;
				}
				
				if(oCount < 4 && oCount <= col && col<4) {
					if(board[index]==1) {
						oCount--;
					}
					else {
						totalHoroValue -= (int)Math.pow(10,oCount);
					}		
				}
				if(oCount < 4 && col == 8) {
					if(board[index-oCount-1]==1) {
						oCount--;
					}
					else {
						totalHoroValue -= (int)Math.pow(10,oCount);
					}
				}
				
				if(oCount == 3 && onBoard(index) && board[index] == 1) {
					totalHorxValue += 50;
				}
				else if(oCount == 3 && onBoard(index) && board[index] != 1) {
					totalHoroValue += 1000;
				}
				if(oCount == 3 && onBoard(index-oCount-1) && board[index-oCount-1]==1) {
					totalHorxValue += 50;
				}
				else if(oCount == 3 && onBoard(index-oCount-1) && board[index-oCount-1] != 1) {
					totalHoroValue += 1000;
				}
				
				if(oCount == 2 && onBoard(index-oCount-1) && onBoard(index) && board[index-oCount-1] != 1 && board[index] != 1) {
					
					totalHoroValue += 200;
				}
				
				if(oCount==1 && onBoard(index) && board[index] == 0 && onBoard(index + 1) && board[index + 1] == 9 && (col + 1) < 8) {
					totalHoroValue += 200;
				}
				
				if(oCount > 0 && onBoard(index-oCount-1) && board[index-oCount-1]==1){
					oCount--;
				}
				if(oCount > 0 && onBoard(index) && board[index]==1){
					oCount--;
				}
				if(oCount > 0){
					totalHoroValue += (int)Math.pow(10,oCount);
				}
				
				while((col < 8) && (board[index]==1))
				{	
					index++;
					col++;
					xCount++;
				}
				if(xCount < 4 && xCount <= col && col<4) {
					if(board[index]==9) {
						xCount--;
					}
					else {
						totalHorxValue -= (int)Math.pow(10,xCount);
					}		
				}
				if(xCount < 4 && col == 8) {
					if(board[index-xCount-1]==9) {
						xCount--;
					}
					else {
						totalHorxValue -= (int)Math.pow(10,xCount);
					}
				}
				
				if((xCount > 0) && onBoard(index-xCount-1) && (board[index-xCount-1]==9)){
					xCount--;
				}
				if((xCount > 0) && onBoard(index) && (board[index]==9)){
					xCount--;
				}
				if(xCount > 0) {
					totalHorxValue += (int)Math.pow(10,xCount);
				}
				


				xCount = 0;
				oCount = 0;
			}
			row++;
			col = 0;
			
		}
		return totalHorxValue - totalHoroValue;
	}
	
	//--------------------------------------
	// Evaluate for Vertical X
	// creates a value for x on vertical placements
	//--------------------------------------
	
	
	static int evalVerticalX(int[] board)
	{
		index = 0;
		totalVertxValue = 0;
		totalVertoValue = 0;
		row =0; 
		col =0;
		xCount = 0;
		oCount = 0;
		
		while(col<8)
		{
			while(row < 8)
			{
				while((row < 8) && (board[index]==0))
				{
					index += 8;
					row++;
				}
				
				while((row < 8) && (board[index]==9))
				{
					index += 8;
					row++;
					oCount++;
				}
				if(oCount < 4 && oCount<=row && row<4) {
					if(board[index]==1) {
						oCount--;
					}
					else {
						totalVertoValue -= (int)Math.pow(10,oCount);
					}
				}
				if(oCount < 4 && row==8) {
					if(board[index-(oCount*8)-8]==1) {
						oCount--;
					}
					else {
						totalVertoValue -= (int)Math.pow(10,oCount);
					}
				}
				
				if(oCount == 3 && onBoard(index) && board[index] == 1) {
					totalVertxValue += 50;
				}
				else if(oCount == 3 && onBoard(index) && board[index] != 1) {
					totalVertoValue += 1000;
				}
				
				if(oCount == 3 && onBoard(index-(oCount*8)-8) && board[index-(oCount*8)-8]==1) {
					totalVertxValue += 50;

				}
				else if(oCount == 3 && onBoard(index-(oCount*8)-8) && board[index-(oCount*8)-8] != 1) {
					totalVertoValue += 1000;
				}
				
				if(oCount == 2 && onBoard(index-(oCount*8)-8) && onBoard(index) && board[index-(oCount*8)-8] != 1 && board[index] != 1)
				{	
					totalVertoValue += 200;
				}
				
				if(oCount==1 && onBoard(index) && board[index] == 0 && onBoard(index + 8) && board[index + 8] == 9) {
					totalVertoValue += 200;
				}
				
				if(oCount > 0 && onBoard(index-(oCount*8)-8) && board[index-(oCount*8)-8]==1){
					oCount--;
				}
				if(oCount > 0 && onBoard(index) && board[index]==1){
					oCount--;
				}
				if(oCount > 0){
					totalVertoValue += (int)Math.pow(10,oCount);
				}
				
				while((row < 8) && (board[index]==1))
				{
					index += 8;
					row++;
					xCount++;
				}
				if(xCount < 4 && xCount<=row && row<4) {
					if(board[index]==9) {
						xCount--;
					}
					else {
						totalVertxValue -= (int)Math.pow(10,xCount);
					}
				}
				if(xCount < 4 && row==8) {
					if(board[index-(xCount*8)-8]==9) {
						xCount--;
					}
					else {
						totalVertxValue -= (int)Math.pow(10,xCount);
					}
				}
				
				if(xCount > 0 && onBoard(index-(xCount*8)-8) && board[index-(xCount*8)-8]==9){
					xCount--;
				}
				if(xCount > 0 && onBoard(index) && board[index]==9){
					xCount--;
				}
				if(xCount > 0){
					totalVertxValue += (int)Math.pow(10,xCount);
				}

				xCount = 0;
				oCount = 0;
				
			}
			col++;
			row = 0;
			index -= 63;
		}
		return totalVertxValue - totalVertoValue;
	}
	
	
	//--------------------------------------
	// Evaluate for Horizontal O
	// creates a value for o on horizontal placements
	//--------------------------------------

	static int evalHorizontalO(int[] board)
	{
		index = 0;
		totalHorxValue = 0;
		totalHoroValue = 0;
		row =0; 
		col =0;
		xCount = 0;
		oCount = 0;
		
		while(row<8)
		{
			
			while(col < 8)
			{	
				while((col < 8) && (board[index]==0))
				{
					index++;
					col++;
				}
				while((col < 8) && (board[index]==1))
				{	
					index++;
					col++;
					xCount++;
				}
				if(xCount < 4 && xCount <= col && col<4) {
					if(board[index]==9) {
						xCount--;
					}
					else {
						totalHorxValue -= (int)Math.pow(10,xCount);
					}		
				}
				if(xCount < 4 && col == 8) {
					if(board[index-xCount-1]==9) {
						xCount--;
					}
					else {
						totalHorxValue -= (int)Math.pow(10,xCount);
					}
				}
				
				if(xCount == 3 && onBoard(index) && board[index] == 9) {
					totalHoroValue += 50;
				}
				else if(xCount == 3 && onBoard(index) && board[index] != 9) {
					totalHorxValue += 1000;

				}
				if(xCount == 3 && onBoard(index-xCount-1) && board[index-xCount-1]==9) {
					totalHoroValue += 50;
				}
				else if(xCount == 3 && onBoard(index-xCount-1) && board[index-xCount-1] != 9) {
					totalHorxValue += 1000;

				}
				
				if(xCount==2 && onBoard(index-xCount-1) && onBoard(index) && board[index-xCount-1] != 9 && board[index] != 9) {
					totalHorxValue += 200;
				}
				
				if(xCount==1 && onBoard(index) && board[index] == 0 && onBoard(index + 1) && board[index + 1] == 1 && (col + 1) < 8) {
					totalHorxValue += 200;
				}
				
				if((xCount > 0) && onBoard(index-xCount-1) && (board[index-xCount-1]==9)){
					xCount--;
				}
				if((xCount > 0) && onBoard(index) && (board[index]==9)){
					xCount--;
				}
				if(xCount > 0) {
					totalHorxValue += (int)Math.pow(10,xCount);
				}
				

				while((col < 8) && (board[index]==9))
				{
					index++;
					col++;
					oCount++;
				}
				
				if(oCount < 4 && oCount <= col && col<4) {
					if(board[index]==1) {
						oCount--;
					}
					else {
						totalHoroValue -= (int)Math.pow(10,oCount);
					}		
				}
				if(oCount < 4 && col == 8) {
					if(board[index-oCount-1]==1) {
						oCount--;
					}
					else {
						totalHoroValue -= (int)Math.pow(10,oCount);
					}
				}
				
				if(oCount > 0 && onBoard(index-oCount-1) && board[index-oCount-1]==1){
					oCount--;
				}
				if(oCount > 0 && onBoard(index) && board[index]==1){
					oCount--;
				}
				if(oCount > 0){
					totalHoroValue += (int)Math.pow(10,oCount);
				}
				xCount = 0;
				oCount = 0;
			}
			row++;
			col = 0;
			
		}
		return totalHorxValue - totalHoroValue;
	}
	
	
	//--------------------------------------
	// Evaluate for Vertical O
	// creates a value for o on vertical placements
	//--------------------------------------
	
	
	static int evalVerticalO(int[] board)
	{
		index = 0;
		totalVertxValue = 0;
		totalVertoValue = 0;
		row =0; 
		col =0;
		xCount = 0;
		oCount = 0;
		
		while(col<8)
		{
			while(row < 8)
			{
				while((row < 8) && (board[index]==0))
				{
					index += 8;
					row++;
				}
				while((row < 8) && (board[index]==1))
				{
					index += 8;
					row++;
					xCount++;
				}
				if(xCount < 4 && xCount<=row && row<4) {
					if(board[index]==9) {
						xCount--;
					}
					else {
						totalVertxValue -= (int)Math.pow(10,xCount);
					}
				}
				if(xCount < 4 && row==8) {
					if(board[index-(xCount*8)-8]==9) {
						xCount--;
					}
					else {
						totalVertxValue -= (int)Math.pow(10,xCount);
					}
				}
				
				if(xCount == 3 && onBoard(index) && board[index] == 9) {
					totalVertoValue += 50;
				}
				else if(xCount == 3 && onBoard(index) && board[index] != 9) {
					totalVertxValue += 1000;
				}
				if(xCount == 3 && onBoard(index-(xCount*8)-8) && board[index-(xCount*8)-8]==9) {
					totalVertoValue += 50;
				}
				else if(xCount == 3 && onBoard(index-(xCount*8)-8) && board[index-(xCount*8)-8] != 9) {
					totalVertxValue += 1000;
				}
				
				if(xCount==2 && onBoard(index-(xCount*8)-8) && onBoard(index) && board[index-(xCount*8)-8] != 9 && board[index] != 9) {
					totalVertxValue += 200;
				}
				
				if(xCount==1 && onBoard(index) && board[index] == 0 && onBoard(index + 8) && board[index + 8] == 1) {
					totalVertxValue += 200;
				}
				
				if(xCount > 0 && onBoard(index-(xCount*8)-8) && board[index-(xCount*8)-8]==9){
					xCount--;
				}
				if(xCount > 0 && onBoard(index) && board[index]==9){
					xCount--;
				}
				if(xCount > 0){
					totalVertxValue += (int)Math.pow(10,xCount);
				}
				
				
				while((row < 8) && (board[index]==9))
				{
					index += 8;
					row++;
					oCount++;
				}
				if(oCount < 4 && oCount<=row && row<4) {
					if(board[index]==1) {
						oCount--;
					}
					else {
						totalVertoValue -= (int)Math.pow(10,oCount);
					}
				}
				if(oCount < 4 && row==8) {
					if(board[index-(oCount*8)-8]==1) {
						oCount--;
					}
					else {
						totalVertoValue -= (int)Math.pow(10,oCount);
					}
				}
				
				
				if(oCount > 0 && onBoard(index-(oCount*8)-8) && board[index-(oCount*8)-8]==1){
					oCount--;
				}
				if(oCount > 0 && onBoard(index) && board[index]==1){
					oCount--;
				}
				if(oCount > 0){
					totalVertoValue += (int)Math.pow(10,oCount);
				}
				xCount = 0;
				oCount = 0;
				
			}
			col++;
			row = 0;
			index -= 63;
		}
		return totalVertxValue - totalVertoValue;
	}
	
	//checks if a given state is a draw
	static boolean draw(int[] board) {
		int blanks = 0;
		for(int i = 0; i < 64; i++) {
			if(board[i] == 0) {
				blanks++;
			}
		}
		if(blanks < 4) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//checks for winner in horizontal or vertical placements
	static boolean winner(int[] board) {
		return checkHor(board) || checkVert(board);
	}
	
	
	static boolean checkHor(int[] board)
	{
		index = 0;
		row =0; 
		col =0;
		xCount = 0;
		oCount = 0;
		
		while(row<8)
		{
			while(col < 8)
			{	
				while((col < 8) && (board[index]==0))
				{
					index++;
					col++;
				}
				while((col < 8) && (board[index]==1))
				{
					index++;
					col++;
					xCount++;

				}
				while((col < 8) && (board[index]==9))
				{
					index++;
					col++;
					oCount++;

				}
				
				if(xCount == 4 || oCount == 4) {
					return true;
				}
				
				xCount = 0;
				oCount = 0;
			}
			row++;
			col = 0;
			
		}
		
		return false;
	}
	
	static boolean checkVert(int[] board)
	{
		index = 0;
		row =0; 
		col =0;
		xCount = 0;
		oCount = 0;
		
		while(col<8)
		{
			while(row < 8)
			{
				while((row < 8) && (board[index]==0))
				{
					index += 8;
					row++;
				}
				while((row < 8) && (board[index]==1))
				{
					index += 8;
					row++;
					xCount++;

				}
				while((row < 8) && (board[index]==9))
				{
					index += 8;
					row++;
					oCount++;

				}
				if(xCount == 4 || oCount == 4) {
					return true;
				}
				
				xCount = 0;
				oCount = 0;
				
			}
			col++;
			row = 0;
			index -= 63;
		}
		
		return false;
	}
	
	//checks for killer moves and returns blocks, or wins depending on the player's turn.
	static int killerMove(int[] board, int p) {
		return kh(board,p);
	}
	
	
	static int kh(int[] board, int p)
	{
		index = 0;
		row =0; 
		col =0;
		xCount = 0;
		oCount = 0;
		int killerxMove = 64;
		int killeroMove = 64;
		int k = 64;
		while(row<8)
		{
			while(col < 8)
			{	
				while((col < 8) && (board[index]==0))
				{
					index++;
					col++;
				}
				while((col < 8) && (board[index]==1))
				{
					index++;
					col++;
					xCount++;

				}
				
				if(xCount == 1 && onBoard(index) && board[index] != 9 && onBoard(index + 1) && board[index + 1]==1 && onBoard(index + 2) && board[index + 2]==1  && (col + 2) < 8) {
					killerxMove = index;
				}
				
				if(xCount == 2 && onBoard(index) && board[index] != 9 && onBoard(index + 1) && board[index + 1]==1 && (col + 1) < 8) {
					killerxMove = index;
				}
				
				if(xCount == 3 && onBoard(index) && board[index] != 9  && (col) < 8) {
					killerxMove = index;
				}
				else if(xCount == 3 && onBoard(index-xCount-1) && board[index-xCount-1]!=9) {
					killerxMove = index-xCount-1;
				}
				
				
				while((col < 8) && (board[index]==9))
				{
					index++;
					col++;
					oCount++;

				}
				
				if(oCount == 1 && onBoard(index) && board[index] != 1 && onBoard(index + 1) && board[index + 1]==9 && onBoard(index + 2) && board[index + 2]==9  && (col + 2) < 8) {
					killeroMove = index;
				}
				
				if(oCount == 2 && onBoard(index) && board[index] != 1 && onBoard(index + 1) && board[index + 1]==9  && (col + 1) < 8) {
					killeroMove = index;
				}
				
				if(oCount == 3 && onBoard(index) && board[index] != 1 && (col) < 8) {
					killeroMove = index;
				}
				else if(oCount == 3 && onBoard(index-oCount-1) && board[index-oCount-1]!=1 ) {
					killeroMove = index-oCount-1;
				}
				
				xCount = 0;
				oCount = 0;
			}
			row++;
			col = 0;
			
		}
		if(p==1) {
			if(killerxMove != 64) {
				return killerxMove;
			}
			else {
				k = kv(board,p);
				if(k != 64) {
					return k;
				}
				else if(killeroMove != 64) {
					return killeroMove;
				}
				else {
					return 64;
				}
			}
		}
		else {
			if(killeroMove != 64) {
				return killeroMove;
			}
			else {
				k = kv(board,p);
				if(k != 64) {
					return k;
				}
				else if(killerxMove != 64) {
					return killerxMove;
				}
				else {
					return 64;
				}
			}
		}
	}
	
	static int kv(int[] board, int p)
	{
		index = 0;
		row =0; 
		col =0;
		xCount = 0;
		oCount = 0;
		int killerxMove = 64;
		int killeroMove = 64;
		
		while(col<8)
		{
			while(row < 8)
			{
				while((row < 8) && (board[index]==0))
				{
					index += 8;
					row++;
				}
				while((row < 8) && (board[index]==1))
				{
					index += 8;
					row++;
					xCount++;

				}
				
				if(xCount == 1 && onBoard(index) && board[index] != 9 && onBoard(index + 8) && board[index + 8]==1 && onBoard(index + 16) && board[index + 16]==1) {
					killerxMove = index;
				}
				
				if(xCount == 2 && onBoard(index) && board[index] != 9 && onBoard(index + 8) && board[index + 8]==1) {
					killerxMove = index;
				}
				
				if(xCount == 3 && onBoard(index) && board[index] != 9) {
					killerxMove = index;
				}
				else if(xCount == 3 && onBoard(index-(xCount*8)-8) && board[index-(xCount*8)-8]!=9) {
					killerxMove = index-(xCount*8)-8;
				}
				
				
				while((row < 8) && (board[index]==9))
				{
					index += 8;
					row++;
					oCount++;

				}
				
				if(oCount == 1 && onBoard(index) && board[index] != 1 && onBoard(index + 8) && board[index + 8]==9 && onBoard(index + 16) && board[index + 16]==9) {
					killeroMove = index;
				}
				
				if(oCount == 2 && onBoard(index) && board[index] != 1 && onBoard(index + 8) && board[index + 8]==9) {
					killeroMove = index;
				}
				
				if(oCount == 3 && onBoard(index) && board[index] != 1) {
					killeroMove = index;
				}
				else if(oCount == 3 && onBoard(index-(oCount*8)-8) && board[index-(oCount*8)-8]!=1) {
					killeroMove = index-(oCount*8)-8;
				}	
				xCount = 0;
				oCount = 0;
				
			}
			col++;
			row = 0;
			index -= 63;
		}
		
		if(p==1) {
			if(killerxMove != 64) {
				return killerxMove;
			}
			else {
				if(killeroMove != 64) {
					return killeroMove;
				}
				else {
					return 64;
				}
			}
		}
		else {
			if(killeroMove != 64) {
				return killeroMove;
			}
			else {
				if(killerxMove != 64) {
					return killerxMove;
				}
				else {
					return 64;
				}
			}
		}
	}
	
	static boolean onBoard(int i)
	{
		if(i>63 || i<0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

//------------------------------------------------------------------------------
} // end class Evaluate
////////////////////////////////////////////////////////////////////////////////


