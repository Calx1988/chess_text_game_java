package application;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        while (true) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch);
                UI.printBoard(chessMatch.getPieces());
                System.out.println();
                System.out.print("Origin: ");
                ChessPosition origin = UI.readChessPosition(scanner);
                boolean[][] possibleMoves = chessMatch.possibleMoves(origin);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(scanner);
                ChessPiece capturedPiece = chessMatch.performChessMove(origin, target);
            }catch (ChessException e){
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
            catch (InputMismatchException e){
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }
}
