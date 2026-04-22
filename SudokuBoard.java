import java.util.*;
import java.io.*;

public class SudokuBoard
{
	private char[][] board;

	public SudokuBoard(String path)
	{
		board = new char[9][9];
		loadFile(path);
	}

	private void loadFile(String path)
	{
		try
		{
			Scanner scan = new Scanner(new File(path));
			for (int r = 0; r < board.length; r++)
			{
				String line = scan.nextLine();
				for (int c = 0; c < board[r].length; c++)
				{
					if (line.charAt(c) == '.')
					{
						board[r][c] = '-';
					}
					else
					{
						board[r][c] = line.charAt(c);
					}
				}
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Failed to load from file: " + e.getMessage());
		}
	}

	public boolean isValid()
	{
		return checkCharacter() && checkRowDuplicate() && checkColumnDuplicate() && checkMiniSquareDuplicate();
	}

	private boolean checkCharacter()
	{
		for (int r = 0; r < board.length; r++)
		{
			for (int c = 0; c < board[r].length; c++)
			{
				if (board[r][c] < '0' || board[r][c] > '9')
				{
					if (board[r][c] != '-')
					{
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean checkRowDuplicate()
	{
		for (int r = 0; r < board.length; r++)
		{
			Set <Character> rowValue = new HashSet <>();
			for (int c = 0; c < board[r].length; c++)
			{
				if (rowValue.contains(board[r][c]) && board[r][c] != '-')
				{
					return false;
				}
				rowValue.add(board[r][c]);
			}
		}
		return true;
	}

	private boolean checkColumnDuplicate()
	{
		for (int c = 0; c < board.length; c++)
		{
			Set <Character> colValue = new HashSet <>();
			for (int r = 0; r < board[c].length; r++)
			{
				if (colValue.contains(board[r][c]) && board[r][c] != '-')
				{
					return false;
				}
				colValue.add(board[r][c]);
			}
		}
		return true;
	}

	private char[][] miniSquare(int spot)
	{
		char[][] mini = new char[3][3];
		for (int r = 0; r < 3; r++)
		{
			for (int c = 0; c < 3; c++)
			{
				// whoa - wild! This took me a solid hour to figure out (at least)
				// This translates between the "spot" in the 9x9 Sudoku board
				// and a new mini square of 3x3
				mini[r][c] = board[(spot - 1) / 3 * 3 + r][(spot - 1) % 3 * 3 + c];
			}
		}
		return mini;
	}

	private boolean checkMiniSquareDuplicate()
	{
		for (int spot = 1; spot <= 9; spot++)
		{
			char[][] miniSquare = miniSquare(spot);
			Set <Character> gridValue = new HashSet <>();
			for (int r = 0; r < miniSquare.length; r++)
			{
				for (int c = 0; c < miniSquare[r].length; c++)
				{
					if (gridValue.contains(miniSquare[r][c]) && miniSquare[r][c] != '-')
					{
						return false;
					}
					gridValue.add(miniSquare[r][c]);
				}
			}
		}
		return true;
	}

	public boolean isSolved()
	{
		if (!isValid())
		{
			return false;
		}

		Map <Character, Integer> boardMap = new HashMap <>();
		for (int r = 0; r < board.length; r++)
		{
			for (int c = 0; c < board[r].length; c++)
			{
				if (!boardMap.containsKey(board[r][c]))
				{
					boardMap.put(board[r][c], 1);
				}
				else
				{
					boardMap.put(board[r][c], boardMap.get(board[r][c]) + 1);
				}
			}
		}

		for (char key : boardMap.keySet())
		{
			if (boardMap.get(key) != 9)
			{
				return false;
			}
		}
		return true;
	}

	public String toString()
	{
		StringBuilder result = new StringBuilder();
		for (int r = 0; r < board.length - 1; r++)
		{
			result.append(" ");
			result.append(board[r][0]);
			for (int c = 1; c < board.length; c++)
			{
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
		for (int c = 1; c < board.length; c++)
		{
			result.append(" | ");
			result.append(board[board.length - 1][c]);
		}
		return result.toString();
	}
}