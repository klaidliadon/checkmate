package checkmate.pieces;

import checkmate.Position;

public interface PieceInterface {
	Position getPosition();
	boolean canEat(PieceInterface piece);
}
