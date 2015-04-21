package checkmate.cmd;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import checkmate.Board;
import checkmate.Combination;
import checkmate.Piece;
import checkmate.Squares;

public class Command {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: <command> <properties.file>");
			System.exit(2);
		}
		Properties properties = readProperties(args[0]);
		int width = Integer.parseInt(properties.getProperty("board.width"));
		int height = Integer.parseInt(properties.getProperty("board.height"));
		System.out.println(String.format("Board size: %dX%d", width, height));
		Map<Piece, Integer> input = new LinkedHashMap<Piece, Integer>();
		List<String> pieces = new ArrayList<String>();
		for (String s : Piece.NAME_SET.keySet()) {
			String v = properties.getProperty(s);
			if (v == "" || v == null) {
				continue;
			}
			int n = Integer.parseInt(v);
			if (n == 0) continue;
			for (int i=0; i<n; i++) {
				pieces.add(s);
			}
			input.put(Piece.NAME_SET.get(s), n);
		}
		Date d1 = new Date();
		Map<Combination, Squares> result = null;
		try {
			result = new Board(width, height, input).resolve();
	
		} catch (Exception e) {
			System.err.println("Error in execution");
			e.printStackTrace(System.err);
		} finally {
			long t = new Date().getTime() - d1.getTime();
			System.out.println(String.format("Execution time: %d ms", t));
			if (result != null) {
				System.out.println(String.format("%d results found", result.size()));
				long i = 0;
				for (Entry<Combination, Squares> r : result.entrySet()) {
					System.out.println(String.format("%d: %s (free squares %s)", ++i, r.getKey(), r.getValue()));
				}
			}
		}
		
	}

	private static Properties readProperties(String path) {
		Properties properties = new Properties();
		File file = null;
		FileInputStream fileInput = null;
		try {
			file = new File(path);
			fileInput = new FileInputStream(file);
			properties.load(fileInput);
			fileInput.close();
		} catch (Exception e) {
			System.err.println("Cannot read properties file:");
			e.printStackTrace(System.err);
		} finally {
			if (file != null) {
				try {
					fileInput.close();
				} catch(Exception e) {
					System.err.println("Cannot close properties file:");
					e.printStackTrace(System.err);
				}
			}
		}
		return properties;
	}
}