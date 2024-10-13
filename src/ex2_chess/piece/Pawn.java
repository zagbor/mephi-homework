package ex2_chess.piece;

import ex2_chess.ChessBoard;
import ex2_chess.PlayerType;

public class Pawn extends ChessPiece {

    public Pawn(PlayerType color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // Проверка выхода за границы доски
        if (!chessBoard.checkPos(toLine) || !chessBoard.checkPos(toColumn)) {
            return false;
        }

        // Пешка не может остаться на той же клетке
        if (line == toLine && column == toColumn) {
            return false;
        }

        // Определяем направление движения пешки в зависимости от цвета
        int direction = this.color.equals(PlayerType.WHITE) ? 1 : -1;

        // Пешка может двигаться на одну клетку вперед
        if (column == toColumn && toLine - line == direction && chessBoard.board[toLine][toColumn] == null) {
            return true;
        }

        // Пешка может двигаться на две клетки вперед, если она на начальной позиции
        if (column == toColumn && toLine - line == 2 * direction && chessBoard.board[toLine][toColumn] == null &&
                chessBoard.board[line + direction][column] == null && ((this.color.equals(PlayerType.WHITE) && line == 1) || (this.color.equals(PlayerType.BLACK) && line == 6))) {
            return true;
        }

        // Пешка может бить фигуры противника по диагонали
        if (Math.abs(column - toColumn) == 1 && toLine - line == direction &&
                chessBoard.board[toLine][toColumn] != null && !chessBoard.board[toLine][toColumn].getColor().equals(this.color)) {
            return true;
        }

        return false;
    }

    @Override
    public String getSymbol() {
        return "P";
    }
}