package checkmate.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import checkmate.Board;
import checkmate.Position;
import checkmate.pieces.Bishop;
import checkmate.pieces.King;
import checkmate.pieces.Queen;
import checkmate.pieces.Rook;

public class CheckMateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MAIN_PAGE = "/WEB-INF/checkmate.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.getRequestDispatcher(MAIN_PAGE).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("width", request.getParameter("width"));
		request.setAttribute("height", request.getParameter("height"));
		for (String s : new String[]{"pawn","bishop","king","knight", "queen", "rook"}) {
			request.setAttribute(s, request.getParameter(s));
		}
		Board b1 = new Board(6, 6);
		b1.add(new King(b1, new Position(0,0)));
		b1.add(new Queen(b1, new Position(1,2)));
		b1.add(new Queen(b1, new Position(2,4)));
		Board b2 = new Board(6, 6);
		b2.add(new Rook(b2, new Position(0,0)));
		b2.add(new Bishop(b2, new Position(3,2)));
		b2.add(new Queen(b2, new Position(4,3)));
		request.setAttribute("result", new Board[]{b1,b2});
		request.getRequestDispatcher(MAIN_PAGE).forward(request, response);
	}

}
