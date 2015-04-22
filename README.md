# Checkmate
Find all unique configurations of a custom chess board where none of the pieces is in a position to take any of the others.

## Usage
	
	// execute the calculation and save the output to a file
	java Command.class <config.properties> > output.txt
	// to verify your result
	java Command.class output.txt <config.properties>
	
## Configuration

	# board size
	board.width = 7
	board.height = 7
	# number of pieces
	queen = 2
	rook = 0
	bishop = 2
	knight = 1
	king = 2
