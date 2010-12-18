// Written by Casen Davis
//
// Last modified 10/09/10
//
// Class to compare costs of different puzzle nodes
//
//
import java.util.*;
////////////////////////////////////////////////////////////////////////////////
public class ReverseGameBoardTwoComparator implements Comparator<GameBoardTwo>
{
//------------------------------------------------------------------------------

	@Override
	    public int compare(GameBoardTwo x, GameBoardTwo y)
	    {
	        if (x.utility < y.utility)
	        {
	            return 1;
	        }
	        if (x.utility > y.utility)
	        {
	            return -1;
	        }
	        return 0;
	    }

//------------------------------------------------------------------------------
} // end class ReverseGameBoardTwoComparator
////////////////////////////////////////////////////////////////////////////////
