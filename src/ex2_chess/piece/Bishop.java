package ex2_chess.piece;

import ex2_chess.ChessBoard;
import ex2_chess.PlayerType;

public class Bishop extends ChessPiece {

    public Bishop(PlayerType color) {
        super(color);
    }

    @Override
    public PlayerType getColor() {
        return this.color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // Проверка выхода за границы доски
        if (!chessBoard.checkPos(toLine) || !chessBoard.checkPos(toColumn)) {
            return false;
        }

        // Слон не может остаться на той же клетке
        if (line == toLine && column == toColumn) {
            return false;
        }

        // Проверка, что слон движется по диагонали
        int deltaLine = Math.abs(line - toLine);
        int deltaColumn = Math.abs(column - toColumn);

        if (deltaLine != deltaColumn) {
            return false; // Движение не по диагонали
        }

        // Проверка, что между начальной и конечной клеткой нет других фигур
        int stepLine = (toLine - line) > 0 ? 1 : -1; // Определяем направление по строкам
        int stepColumn = (toColumn - column) > 0 ? 1 : -1; // Определяем направление по столбцам

        int currentLine = line + stepLine;
        int currentColumn = column + stepColumn;

        while (currentLine != toLine && currentColumn != toColumn) {
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
        return "B";
    }
}