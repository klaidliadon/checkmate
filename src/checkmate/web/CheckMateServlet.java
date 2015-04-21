package checkmate.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import checkmate.Board;
import checkmate.Combination;
import checkmate.Piece;
import checkmate.Squares;

public class CheckMateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MAIN_PAGE = "/WEB-INF/checkmate.jsp";
	

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
		for (String s : Piece.NAME_SET.keySet()) {
			String v = request.getParameter(s);
			if (v == "" || v == null) {
				continue;
			}
			int n = Integer.parseInt(v);
			request.setAttribute(s, n);
			for (int i=0; i<n; i++) {
				pieces.add(s);
			}
			input.put(Piece.NAME_SET.get(s), n);
		}
		request.setAttribute("pieces", pieces);
		Date d1 = new Date();
		try {
			Map<Combination, Squares> result = new Board(width, height, input).resolve();
			request.setAttribute("result", result);
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
