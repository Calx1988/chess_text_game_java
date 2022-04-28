package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {
    private Board board;
    private int turn;
    private Color currentPlayer;
    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();
    private boolean check;

    public ChessMatch(){
        board = new Board(8,8);
        turn = 1;
        currentPlayer=Color.WHITE;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getCheck() {
        return check;
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
        piecesOnTheBoard.add(piece);
    }
    public boolean[][] possibleMoves(ChessPosition originPosition) {
        Position position = originPosition.toPosition();
        validateOriginalPosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition originPosition, ChessPosition targetPosition){
        Position origin = originPosition.toPosition();
        Position target = targetPosition.toPosition();
        validateOriginalPosition(origin);
        validateTargetPosition(origin,target);
        Piece capturedPiece = makeMove(origin, target);
        if (testCheck(currentPlayer)) {
            undoMove(origin, target, capturedPiece);
            throw new ChessException("You cannot put yourself in check");
        }

        check = (testCheck(opponent(currentPlayer))) ? true : false;
        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    private void validateOriginalPosition(Position position){
        if(!board.thereIsPiece(position)){
            throw new ChessException("There's no piece in the origin position.");
        }
        if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
            throw new ChessException("This piece doesn't belong to you. ");
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

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece locateKing(Color color) {
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list) {
            if (p instanceof King) {
                return (ChessPiece)p;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    private boolean testCheck(Color color) {
        Position kingPosition = locateKing(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    private Piece makeMove(Position origin, Position target){
        Piece piece = board.removePiece(origin);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(piece, target);
        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        Piece p = board.removePiece(target);
        board.placePiece(p, source);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private void initialSetup(){
        //black pieces
        placeNewPiece('a',8,new Rook(board,Color.BLACK));
        placeNewPiece('h',8,new Rook(board,Color.BLACK));
        placeNewPiece('e',8,new King(board,Color.BLACK));
        placeNewPiece('d',8,new Rook(board,Color.BLACK));
        placeNewPiece('f',8,new Rook(board,Color.BLACK));
        placeNewPiece('d',7,new Rook(board,Color.BLACK));
        placeNewPiece('f',7,new Rook(board,Color.BLACK));
        placeNewPiece('e',7,new Rook(board,Color.BLACK));

        //white pieces
        placeNewPiece('a',1,new Rook(board,Color.WHITE));
        placeNewPiece('h',1,new Rook(board,Color.WHITE));
        placeNewPiece('e',1 ,new King(board,Color.WHITE));
        placeNewPiece('d',1,new Rook(board,Color.WHITE));
        placeNewPiece('f',1,new Rook(board,Color.WHITE));
        placeNewPiece('d',2,new Rook(board,Color.WHITE));
        placeNewPiece('f',2,new Rook(board,Color.WHITE));
        placeNewPiece('e',2,new Rook(board,Color.WHITE));
    }
}
