package bbs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.beans.CategoryList;
import bbs.beans.User;
import bbs.beans.UserComment;
import bbs.beans.UserMessage;
import bbs.service.CommentService;
import bbs.service.MessageService;


@WebServlet(urlPatterns = { "/narrow" })
public class NarrowMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		User user = (User) request.getSession().getAttribute("loginUser");


		String category = request.getParameter("category");
		List<UserMessage> messages = null;


		List<UserComment>comments = new CommentService().getComment();
		List<CategoryList>categories = new MessageService().getCategory();

		request.setAttribute("messages", messages);
		request.setAttribute("comments", comments);
		request.setAttribute("categories", categories);

		request.getRequestDispatcher("/top.jsp").forward(request, response);

	}

}