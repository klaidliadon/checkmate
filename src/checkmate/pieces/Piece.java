package checkmate.pieces;

import checkmate.Position;

public interface Piece {
	void setPosition(Position p);
	Position getPosition();
	Position[] getMoves();
	boolean canEat(Piece piece);
}