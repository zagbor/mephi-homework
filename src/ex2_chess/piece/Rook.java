package ex2_chess.piece;

import ex2_chess.ChessBoard;
import ex2_chess.PlayerType;

public class Rook extends ChessPiece {

    public Rook(PlayerType color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // Проверка выхода за границы доски
        if (!chessBoard.checkPos(toLine) || !chessBoard.checkPos(toColumn)) {
            return false;
        }

        // Ладья не может остаться на той же клетке
        if (line == toLine && column == toColumn) {
            return false;
        }

        // Ладья может двигаться только по прямой (по строкам или по столбцам)
        if (line != toLine && column != toColumn) {
            return false; // Движение не по прямой
        }

        // Проверка, что между начальной и конечной клеткой нет других фигур
        int stepLine = (toLine - line) == 0 ? 0 : (toLine - line) > 0 ? 1 : -1; // Определяем направление по строкам
        int stepColumn = (toColumn - column) == 0 ? 0 : (toColumn - column) > 0 ? 1 : -1; // Определяем направление по столбцам

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
        return "R";
    }

    // Метод для установки значения check
    public void setCheck(boolean value) {
        this.check = value;
    }

    // Метод для получения значения check
    public boolean isCheck() {
        return !check;
    }
}