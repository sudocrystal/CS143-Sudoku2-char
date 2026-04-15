import java.util.*;
import java.io.*;

public class SudokuBoard {
	private char[][] board;

	public SudokuBoard(String path) {
		board = new char[9][9];
		loadFile(path);
	}

	private void loadFile(String path) {
		try {
			Scanner scan = new Scanner(new File(path));
			for(int r = 0; r < board.length; r++) {
				String line = scan.nextLine();
				for(int c = 0; c < board[r].length; c++) {
					if(line.charAt(c) == '.') {
						board[r][c] = '-';
					}
					else {
						board[r][c] = line.charAt(c);
					}
				}
			}
		}catch(FileNotFoundException e) {
			System.out.println("Failed to load from file: " + e.getMessage());
		}
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		for(int r = 0; r < board.length - 1; r++) {
			result.append(" ");
			result.append(board[r][0]);
			for(int c = 1; c < board.length; c++) {
				result.append(" | ");
				result.append(board[r][c]);
			}
			result.append("\n");
			result.append("–––");
			result.append("|–––".repeat(8));
			result.append("\n");
		}
		result.append(" ");
		result.append(board[board.length - 1][0]);
		for(int c = 1; c < board.length; c++) {
			result.append(" | ");
			result.append(board[board.length - 1][c]);
		}
		return result.toString();
	}
}