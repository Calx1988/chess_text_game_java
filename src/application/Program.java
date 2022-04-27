package application;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        while (true) {
            UI.printBoard(chessMatch.getPieces());
            System.out.println();
            System.out.print("Origin: ");
            ChessPosition origin = UI.readChessPosition(scanner);
            System.out.print("Target: ");
            ChessPosition target = UI.readChessPosition(scanner);
            ChessPiece capturedPiece = chessMatch.performChessMove(origin, target);
        }
    }
}
