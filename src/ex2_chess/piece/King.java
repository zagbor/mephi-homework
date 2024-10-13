package ex2_chess.piece;

import ex2_chess.ChessBoard;
import ex2_chess.PlayerType;

public class King extends ChessPiece {

    public King(PlayerType color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // Проверка выхода за границы доски
        if (!chessBoard.checkPos(toLine) || !chessBoard.checkPos(toColumn)) {
            return false;
        }

        // Король не может остаться на той же клетке
        if (line == toLine && column == toColumn) {
            return false;
        }

        // Король может двигаться на одну клетку в любом направлении
        if (Math.abs(toLine - line) <= 1 && Math.abs(toColumn - column) <= 1) {
            if (isUnderAttack(chessBoard, toLine, toColumn)) {
                return false; // Движение недопустимо, если клетка под атакой
            }
            return true; // Движение допустимо
        }

        return false; // Движение недопустимо
    }

    @Override
    public String getSymbol() {
        return "K";
    }

    public boolean isUnderAttack(ChessBoard board, int line, int column) {
        // Проверяем каждую фигуру противника, чтобы определить, атакует ли она поле (line, column)
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board.board[i][j];
                if (piece != null && !piece.getColor().equals(this.getColor()) && piece.canMoveToPosition(board, i, j, line, column)) {
                    return true; // Поле под атакой
                }
            }
        }
        return false; // Поле не под атакой
    }

}