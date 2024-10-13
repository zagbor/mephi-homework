package ex2_chess.piece;


import ex2_chess.ChessBoard;
import ex2_chess.PlayerType;

public class Queen extends ChessPiece {

    public Queen(PlayerType color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // Проверка выхода за границы доски
        if (!chessBoard.checkPos(toLine) || !chessBoard.checkPos(toColumn)) {
            return false;
        }

        // Ферзь не может остаться на той же клетке
        if (line == toLine && column == toColumn) {
            return false;
        }

        // Проверка, движется ли ферзь по диагонали или по прямой
        boolean diagonalMove = Math.abs(toLine - line) == Math.abs(toColumn - column);
        boolean straightMove = (line == toLine || column == toColumn);

        if (!diagonalMove && !straightMove) {
            return false; // Движение не по диагонали и не по прямой
        }

        // Проверка, что между начальной и конечной клеткой нет других фигур
        int stepLine = (toLine - line) == 0 ? 0 : (toLine - line) > 0 ? 1 : -1;
        int stepColumn = (toColumn - column) == 0 ? 0 : (toColumn - column) > 0 ? 1 : -1;

        int currentLine = line + stepLine;
        int currentColumn = column + stepColumn;

        while (currentLine != toLine || currentColumn != toColumn) {
            if (chessBoard.board[currentLine][currentColumn] != null) {
                return false; // Если есть фигура на пути, движение недопустимо
            }
            currentLine += stepLine;
            currentColumn += stepColumn;
        }

        return true; // Движение допустимо
    }

    @Override
    public String getSymbol() {
        return "Q";
    }
}