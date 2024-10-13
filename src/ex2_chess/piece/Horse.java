package ex2_chess.piece;

import ex2_chess.ChessBoard;
import ex2_chess.PlayerType;

public class Horse extends ChessPiece {

    public Horse(PlayerType color) {
        super(color);
    }

    @Override
    public PlayerType getColor() {
        return this.color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // Проверка, чтобы конь не выходил за пределы доски
        if (!chessBoard.checkPos(toLine) || !chessBoard.checkPos(toColumn)) {
            return false;
        }

        // Конь не может оставаться на том же месте
        if (line == toLine && column == toColumn) {
            return false;
        }

        // Проверка движения коня по букве "Г"
        int deltaX = Math.abs(line - toLine);
        int deltaY = Math.abs(column - toColumn);

        // Конь ходит либо 2 клетки по одной оси и 1 клетку по другой
        return (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);
    }

    @Override
    public String getSymbol() {
        return "H";
    }
}