package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return " B";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0, 0);

        // check up-left
        p.setValues(position.getRow() - 1, position.getColumn()-1);
        while (getBoard().checkPositionExists(p) && !getBoard().thereIsPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() - 1);
            p.setColumn(p.getColumn() - 1);
        }
        if (getBoard().checkPositionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // check down-left
        p.setValues(position.getRow()+1, position.getColumn() - 1);
        while (getBoard().checkPositionExists(p) && !getBoard().thereIsPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow()+1);
            p.setColumn(p.getColumn() - 1);
        }
        if (getBoard().checkPositionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // check up-right
        p.setValues(position.getRow()-1, position.getColumn() + 1);
        while (getBoard().checkPositionExists(p) && !getBoard().thereIsPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() - 1);
            p.setColumn(p.getColumn() + 1);
        }
        if (getBoard().checkPositionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // check down-right
        p.setValues(position.getRow() + 1, position.getColumn()+1);
        while (getBoard().checkPositionExists(p) && !getBoard().thereIsPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() + 1);
            p.setColumn(p.getColumn() + 1);
        }
        if (getBoard().checkPositionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        return mat;
    }
}
