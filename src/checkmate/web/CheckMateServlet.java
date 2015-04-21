package checkmate.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import checkmate.Board;
import checkmate.Combination;
import checkmate.Piece;
import checkmate.Position;
import checkmate.Squares;

public class CheckMateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MAIN_PAGE = "/WEB-INF/checkmate.jsp";
	
	private static Map<String, Piece> pieceNames;
	
	static {
		pieceNames = new LinkedHashMap<String, Piece>();
		pieceNames.put("queen", Piece.QUEEN);
		pieceNames.put("rook", Piece.ROOK);
		pieceNames.put("bishop", Piece.BISHOP);
		pieceNames.put("king", Piece.KING);
		pieceNames.put("knight", Piece.KNIGTH);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.getRequestDispatcher(MAIN_PAGE).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		request.setAttribute("width", width);
		request.setAttribute("height", height);
		Map<Piece, Integer> input = new LinkedHashMap<Piece, Integer>();
		List<String> pieces = new ArrayList<String>();
		for (String s : pieceNames.keySet()) {
			String v = request.getParameter(s);
			if (v == "" || v == null) {
				continue;
			}
			int n = Integer.parseInt(v);
			request.setAttribute(s, n);
			for (int i=0; i<n; i++) {
				pieces.add(s);
			}
			input.put(pieceNames.get(s), n);
		}
		request.setAttribute("pieces", pieces);
		Date d1 = new Date();
		try {
			Map<Combination, Squares> result = new Board(width, height, input).resolve();
			Set<Set<Position>> combinations = new HashSet<Set<Position>>();
			Map<Set<Position>, Squares> free = new LinkedHashMap<Set<Position>, Squares>();
			for (Combination c : result.keySet()) {
				Squares s = result.get(c);
				Set<Position> r = new LinkedHashSet<Position>();
				for (int p : c) {
					r.add(new Position(s.x(p), s.y(p)));
				}
				free.put(r, s);
				combinations.add(r);			
			}
			request.setAttribute("result", combinations);
			request.setAttribute("free", free);
		} catch (Exception e) {
			request.setAttribute("error", e);
			e.printStackTrace();
		} finally {
			long t = new Date().getTime() - d1.getTime();
			System.out.println(String.format("Execution time: %d ms", t));
			request.setAttribute("ms", (t));
			request.getRequestDispatcher(MAIN_PAGE).forward(request, response);
		}
	}

}
