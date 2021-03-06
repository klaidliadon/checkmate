package checkmate.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import checkmate.Combination;
import checkmate.Piece;
import checkmate.Squares;

public class Verify extends Command {

	static Pattern pattern = Pattern.compile("\\[([\\d]+),(\\d+)\\]");
	static Squares squares;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err
					.println("Usage: <command> <configuration.file>");
			System.exit(2);
		}
		Properties properties = readProperties(args[0]);
		squares = createSquares(properties);
		List<Piece> pieces = new ArrayList<Piece>();
		for (String s : Piece.NAME_SET.keySet()) {
			String v = properties.getProperty(s);
			if (v == "" || v == null) {
				continue;
			}
			int n = Integer.parseInt(v);
			if (n == 0)
				continue;
			for (int i = 0; i < n; i++) {
				pieces.add(Piece.NAME_SET.get(s));
			}
		}
		int i=0;
		try {
			File file = new File(properties.getProperty("filename"));
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			
			while ((line = bufferedReader.readLine()) != null) {
				if (line.charAt(0) != '[')
					continue;
				verifyLine(line, pieces);
				i++;
			}
			fileReader.close();
			System.out.println(String.format("All combinations (%d) are valid", i));
		} catch (Exception e) {
			System.err.println(String.format("Error at combination %d: %s", i, e.getMessage()));
			e.printStackTrace();
		}
	}

	private static Squares createSquares(Properties properties) {
		int width = Integer.parseInt(properties.getProperty("board.width"));
		int height = Integer.parseInt(properties.getProperty("board.height"));
		return new Squares(width,height);
	}

	private static void verifyLine(String line, List<Piece> pieces) throws Exception {
		Combination comb = new Combination(squares.w, squares.h);
		Matcher m = pattern.matcher(line);
		while (m.find()) {
			String point = m.group();
			String[] xy = point.substring(1, point.length()-1).split(",");
			comb.add(squares.xy(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
		}
		if (!line.equals(comb.toString())) throw new Exception(line+"-"+comb.toString());
		comb.verify(pieces);
	}

}
