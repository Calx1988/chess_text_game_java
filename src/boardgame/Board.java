package boardgame;

import boardgame.exceptions.BoardException;

public class Board {
    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        if (rows<1 || columns<1){
            throw new BoardException("Error creating board! There needs to be at least one row and column.");
        }
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Piece piece(int row, int column){
        if(!checkPositionExists(row,column)){
            throw new BoardException("Position doesn't exists!");
        }
        return pieces[row][column];
    }

    public Piece piece(Position position){
        if(!checkPositionExists(position)){
            throw new BoardException("Position doesn't exists!");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    public void placePiece(Piece piece, Position position){
        if(thereIsPiece(position)){
            throw new BoardException("The position " + position + " is already occupied!");
        }
        pieces[position.getRow()][position.getColumn()]=piece;
        piece.position=position;
    }

    private boolean checkPositionExists(int row, int column){
        return row>=0 && row<rows && column>=0 && column<columns;
    }
    public boolean checkPositionExists(Position position){
        return checkPositionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsPiece(Position position){
        if(!checkPositionExists(position)){
            throw new BoardException("Position doesn't exists!");
        }
        return piece(position)!=null;
    }

    public Piece removePiece(Position position){
        if(!checkPositionExists(position)){
            throw new BoardException("Position doesn't exists!");
        }
        if(piece(position)==null){
            return null;
        }
        Piece aux = piece(position);
        aux.position = null;
        pieces[position.getRow()][position.getColumn()]=null;
        return aux;
    }
}
