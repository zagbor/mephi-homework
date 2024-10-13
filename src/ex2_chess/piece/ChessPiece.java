package ex2_chess.piece;

import ex2_chess.ChessBoard;
import ex2_chess.PlayerType;

public abstract class ChessPiece {
    protected PlayerType color;
    protected boolean check = true;

    public ChessPiece(PlayerType color) {
        this.color = color;
    }

    // Геттер для цвета
    public PlayerType getColor() {
        return color;
    }

    // Геттер для переменной check
    public boolean isCheck() {
        return !check;
    }

    // Установка значения переменной check
    public void setCheck(boolean check) {
        this.check = check;
    }

    public abstract boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn);

    public abstract String getSymbol();
}