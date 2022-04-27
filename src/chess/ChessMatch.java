package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
    private Board board;

    public ChessMatch(){
        board = new Board(8,8);
        initialSetup();
    }

    public ChessPiece[][] getPieces(){
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i,j);
            }
        }
        return mat;
    }
    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    public ChessPiece performChessMove(ChessPosition originPosition, ChessPosition targetPosition){
        Position origin = originPosition.toPosition();
        Position target = targetPosition.toPosition();
        validadeOriginalPosition(origin);
        validateTargetPosition(origin,target);
        Piece capturedPiece = makeMove(origin, target);
        return (ChessPiece) capturedPiece;
    }

    private void validadeOriginalPosition(Position position){
        if(!board.thereIsPiece(position)){
            throw new ChessException("There's no piece in the origin position.");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There's no possible moves for the chosen piece.");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece cannot be moved to target position");
        }
    }

    private Piece makeMove(Position origin, Position target){
        Piece piece = board.removePiece(origin);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(piece, target);
        return capturedPiece;
    }

    private void initialSetup(){
        //black pieces
        placeNewPiece('a',1,new Rook(board,Color.BLACK));
        placeNewPiece('h',1,new Rook(board,Color.BLACK));
        placeNewPiece('e',1,new King(board,Color.BLACK));
        placeNewPiece('d',1,new Rook(board,Color.BLACK));
        placeNewPiece('f',1,new Rook(board,Color.BLACK));
        placeNewPiece('d',2,new Rook(board,Color.BLACK));
        placeNewPiece('f',2,new Rook(board,Color.BLACK));
        placeNewPiece('e',2,new Rook(board,Color.BLACK));

        //white pieces
        placeNewPiece('a',8,new Rook(board,Color.WHITE));
        placeNewPiece('h',8,new Rook(board,Color.WHITE));
        placeNewPiece('e',8 ,new King(board,Color.WHITE));
    }
}
