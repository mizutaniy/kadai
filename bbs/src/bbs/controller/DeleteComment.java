package bbs.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.service.CommentService;

@WebServlet(urlPatterns = { "/deletecomment" })
public class DeleteComment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

			int id = Integer.parseInt(request.getParameter("id"));

			CommentService CommentService = new CommentService();
			CommentService.deleteComment(id);

			response.sendRedirect("home");
	}

}