import com.sun.istack.internal.Nullable;

/**
  Represent a square chess board for a queens puzzle
  of a particular size.
 */

public class BoardForQueensPuzzle {
    private int lastRankFilled;  /* the highest-numbered rank
      that holds a queen. */
    private int[] filesWithQueens;  /* indexed by rank, giving
      the file number that a queen occupies in that rank.
      Interacts with lastRankFilled: entries in filesWithQueens
      are meaningful only for ranks <= lastRankFilled. Entries
      at higher indexes hold bit patterns that can be interpreted
      as integers, as always. But those entries are meaningless.
      This common thinking was used for the filledElements field
      in List_inArraySlots exactly the same way.
      */


    /**
      Construct an empty instance of the specified size
     */
    public BoardForQueensPuzzle( int ranks) {
    lastRankFilled = null;
    filesWithQueens = new int[ranks];
    for(int x = 0; x < filesWithQueens.length; x++){
        filesWithQueens[x] = null;
        }
    }


    /**
      @return the size of the board
     */
    public int ranks() {
        return filesWithQueens.length;   // invalid value
    }


    /**
      @return the boolean value of the statement
              "The last-added queen introduced a conflict."
      @precondition: Before the latest queen was added,
                     no queen attacked another.
     */
    public boolean lastIsNg() {
        for(int currentRank = 0; currentRank < lastRankFilled; currentRank++){
            int placeholder = filesWithQueens[currentRank];
            int difference = (lastRankFilled - currentRank);
            int previous = filesWithQueens[lastRankFilled];
            if((previous == placeholder) || (placeholder + difference == previous) || (placeholder - difference == previous)){
            return true;
        }
    }


    /**
      @return the boolean value of the statement...
                "The board is fully and legally populated."
      precondition: All ranks except possibly the last-filled
        are populated legally. (That is, considering only the
        ranks before the last-filled rank, no queen attacks
        another.
        This method checks the last-filled rank.
     */
    public boolean accept() {
        if(lastRankFilled + 1 != filesWithQueens.length) return false;
        for(int queen1Rank = 0; queen1Rank < lastRankFilled - 2; queen1Rank++){
            for(int queen2Rank = queen1Rank + 1; queen2Rank < lastRankFilled - 1; queen2Rank++){
                int diff = (queen2Rank - queen1Rank);
                int queen1 = filesWithQueens[queen1Rank];
                int queen2 = filesWithQueens[queen2Rank];
                if((queen1 == queen2) || (queen1 + diff == queen2) || (queen2 - diff == queen2)){
                    return false;
                }
            }
        }        
        return true;
    }


    /**
      Populate the next rank with a queen in position @file
     */
    public void populate( int file) {
        lastRankFilled++;
        filesWithQueens[lastRankFilled] = file;
    }


    /**
      Un-do a populate(), to have no queen in the
      most-recently-populated rank.
      @precondition: Some rank(s) have been populated.
     */
    public void depopulate() {
        lastRankFilled--;
    }


    // ----- skeletal code below here needs no modification -----
    private static final String INDENT = "         ";
    /**
      @return a string representation of this board
     */
    public String toString() {
        int size = filesWithQueens.length; // just for shorthand

        /* Make a header containing file numbers like...
               3 ranks, populated through rank 1
               files
               0  1  2  3 ...
               __________ ...
         */

        // sizes and labels that precede the ranks
        String pic = INDENT
          + size + " ranks, "
          + ( // ?: conditional operator
                lastRankFilled < 0
              ? "unpopulated"
              : "populated through rank " + lastRankFilled
            )
          + System.lineSeparator();

        // short-circuit the picture for a zero-sized board
        if( size == 0) return pic;

        pic += INDENT + "files" + System.lineSeparator()
            +  INDENT;
        String underscores = "";

        // file numbers, each in a 3-column field
        for( int file = 0; file < size; file++) {
            pic += String.format("%-3d", file);
            underscores += "___"; // there's gotta be a better way
        }

        // underline the header
        pic+=   System.lineSeparator()
              + INDENT + underscores + System.lineSeparator();

        // picture each rank
        for( int rank = 0; rank < size; rank++){
            pic += // right-justified rank number:
                   String.format("rank %2d|", rank);
            for( int file = 0; file < size; file++)
                if(    rank > lastRankFilled      // no queen in this rank yet
                    || filesWithQueens[ rank] != file) // no queen in this file
                     pic += " _ ";
                else pic += " Q ";
            pic += System.lineSeparator();
        }
        return pic;
    }
}