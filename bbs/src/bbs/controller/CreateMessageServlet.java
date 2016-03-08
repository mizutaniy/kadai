package bbs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import bbs.beans.Message;
import bbs.beans.User;
import bbs.service.MessageService;

@WebServlet(urlPatterns = { "/createmessage" })
public class CreateMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

			User user = (User) request.getSession().getAttribute("loginUser");

			if(user != null) {
				request.getRequestDispatcher("createmessage.jsp").forward(request, response);
			} else {
				response.sendRedirect("./");
			}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();

		User user = (User) session.getAttribute("loginUser");

		Message message = new Message();
		message.setUser_id(user.getId());
		message.setTitle(request.getParameter("title"));
		message.setText(request.getParameter("text"));
		message.setCategory(request.getParameter("category"));

		if(isValid(request, messages) == true) {

			new MessageService().register(message);

			response.sendRedirect("home");
		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("inputData", message);
			request.getRequestDispatcher("errorcreatemessage.jsp").forward(request, response);
		}
	}


	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String title = request.getParameter("title");
		String text = request.getParameter("text");
		String category = request.getParameter("category");

		if(StringUtils.isEmpty(title) || 50 < title.length()) {
			messages.add("件名は50文字以下で入力してください。");
		}
		if(StringUtils.isEmpty(text) || 1000 < text.length()) {
			messages.add("本文は1000文字以下で入力してください。");
		}
		if(StringUtils.isEmpty(category) || 10 < category.length()) {
			messages.add("カテゴリーは10文字以下で入力してください。");
		}
		if(messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
