package checkmate.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import checkmate.Resolver;
import checkmate.pieces.Piece;

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
		Resolver r = new Resolver(width, height);
		List<Piece> pieces = new ArrayList<Piece>();
		for (String s : new String[]{"pawn","bishop","king","knight", "queen", "rook"}) {
			String v = request.getParameter(s);
			if (v == "" || v == null) {
				continue;
			}
			int n = Integer.parseInt(v);
			request.setAttribute(s, n);
			for (int i = 0; i<n; i++) {
				pieces.add(r.newPiece(s));
			}
		}
		r.setPieces(pieces);
		request.setAttribute("pieces", pieces);
		Date d1 = new Date();
		try {
			request.setAttribute("result", r.resolve());
		} catch (Exception e) {
			request.setAttribute("error", e);
		} finally {
			Date d2 = new Date();
			request.setAttribute("ms", (d2.getTime() - d1.getTime()));
		}
		request.getRequestDispatcher(MAIN_PAGE).forward(request, response);
	}

}
