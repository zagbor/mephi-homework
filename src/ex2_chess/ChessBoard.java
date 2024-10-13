package ex2_chess;

import ex2_chess.piece.ChessPiece;
import ex2_chess.piece.King;
import ex2_chess.piece.Rook;

public class ChessBoard {

    public ChessPiece[][] board = new ChessPiece[8][8]; // creating a field for game
    private static PlayerType nowPlayer = PlayerType.WHITE ;

    public PlayerType getNowPlayerColor() {
        return nowPlayer;
    }

    private void togglePlayer() {
        nowPlayer = nowPlayer.equals(PlayerType.WHITE) ? PlayerType.BLACK : PlayerType.WHITE;
    }


    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        // Проверка на допустимость начальных позиций
        if (checkPos(startLine) && checkPos(startColumn)) {
            ChessPiece movingPiece = board[startLine][startColumn];

            // Проверка на цвет фигуры
            if (!nowPlayer.equals(movingPiece.getColor())) return false;

            // Проверка, может ли фигура переместиться
            if (movingPiece.canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {
                // Перемещение фигуры
                board[endLine][endColumn] = movingPiece;
                board[startLine][startColumn] = null; // Установка предыдущей ячейки в null

                // Установка переменных check в false для королей и ладей
                if (movingPiece instanceof King) {
                    movingPiece.setCheck(false); // Предполагается, что в King есть метод setCheck
                } else if (movingPiece instanceof Rook) {
                    movingPiece.setCheck(false); // Предполагается, что в Rook есть метод setCheck
                }

                // Смена игрока
                togglePlayer();

                return true; // Успешное перемещение
            } else return false; // Невозможное перемещение
        } else return false; // Неправильная позиция
    }


    public void printBoard() {  //print board in console
        System.out.println("Turn " + nowPlayer);
        System.out.println();
        System.out.println("Player 2(Black)");
        System.out.println();
        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");

        for (int i = 7; i > -1; i--) {
            System.out.print(i + "\t");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(".." + "\t");
                } else {
                    System.out.print(board[i][j].getSymbol() + board[i][j].getColor().name().substring(0, 1).toLowerCase() + "\t");
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("Player 1(White)");
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }

    public boolean castling0(PlayerType color) {

        // Определяем короля и ладью
        King king = (King) board[color.equals(PlayerType.WHITE) ? 0 : 7][4]; // A1 или A8
        Rook rook = (Rook) board[color.equals(PlayerType.WHITE) ? 0 : 7][0]; // A1 или A8

        // Проверяем, что король и ладья не двигались
        if (king == null || rook == null || king.isCheck() || rook.isCheck()) {
            return false;
        }

        // Проверяем, что между королем и ладьей нет других фигур
        if (board[color.equals(PlayerType.WHITE) ? 0 : 7][1] != null ||
                board[color.equals(PlayerType.WHITE) ? 0 : 7][2] != null ||
                board[color.equals(PlayerType.WHITE) ? 0 : 7][3] != null) {
            return false; // Есть фигуры между королем и ладьей
        }

        // Проверяем, не под угрозой ли король после рокировки
        if (king.isUnderAttack(this, color.equals(PlayerType.WHITE) ? 0 : 7, 2) ||
                king.isUnderAttack(this, color.equals(PlayerType.WHITE) ? 0 : 7, 3)) {
            return false; // Король под угрозой
        }

        // Выполняем рокировку
        board[color.equals(PlayerType.WHITE) ? 0 : 7][2] = king; // Перемещаем короля на C1 или C8
        board[color.equals(PlayerType.WHITE) ? 0 : 7][3] = rook; // Перемещаем ладью на D1 или D8
        board[color.equals(PlayerType.WHITE) ? 0 : 7][4] = null; // Убираем короля из E1 или E8
        board[color.equals(PlayerType.WHITE) ? 0 : 7][0] = null; // Убираем ладью из A1 или A8

        togglePlayer();


        return true; // Рокировка успешно выполнена
    }

    public boolean castling7(PlayerType color) {

        // Определяем короля и ладью
        King king = (King) board[color.equals(PlayerType.WHITE) ? 0 : 7][4]; // H1 или H8
        Rook rook = (Rook) board[color.equals(PlayerType.WHITE) ? 0 : 7][7]; // H1 или H8

        // Проверяем, что король и ладья не двигались
        if (king == null || rook == null || king.isCheck() || rook.isCheck()) {
            return false;
        }

        // Проверяем, что между королем и ладьей нет других фигур
        if (board[color.equals(PlayerType.WHITE) ? 0 : 7][5] != null ||
                board[color.equals(PlayerType.WHITE) ? 0 : 7][6] != null) {
            return false; // Есть фигуры между королем и ладьей
        }

        // Проверяем, не под угрозой ли король после рокировки
        if (king.isUnderAttack(this, color.equals(PlayerType.WHITE) ? 0 : 7, 6) ||
                king.isUnderAttack(this, color.equals(PlayerType.WHITE) ? 0 : 7, 5)) {
            return false; // Король под угрозой
        }

        // Выполняем рокировку
        board[color.equals(PlayerType.WHITE) ? 0 : 7][6] = king; // Перемещаем короля на G1 или G8
        board[color.equals(PlayerType.WHITE) ? 0 : 7][5] = rook; // Перемещаем ладью на F1 или F8
        board[color.equals(PlayerType.WHITE) ? 0 : 7][4] = null; // Убираем короля из E1 или E8
        board[color.equals(PlayerType.WHITE) ? 0 : 7][7] = null; // Убираем ладью из H1 или H8

        togglePlayer();
        return true; // Рокировка успешно выполнена
    }


}