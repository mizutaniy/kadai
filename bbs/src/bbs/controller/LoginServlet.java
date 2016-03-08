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

import bbs.beans.User;
import bbs.service.LoginService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		User user = (User) request.getSession().getAttribute("loginUser");

		if(user != null) {
			response.sendRedirect("home");
		} else {
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();

		String login_id = request.getParameter("login_id");
		String password = request.getParameter("password");

		LoginService loginService = new LoginService();
		User user = loginService.login(login_id, password);

		if(isValid(request, messages) == true) {
				session.setAttribute("loginUser", user);
				response.sendRedirect("home");
		} else {
			session.setAttribute("errorMessages", messages);

			request.setAttribute("login_id", login_id);
			request.getRequestDispatcher("errorlogin.jsp").forward(request, response);
		}
	}


	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String login_id = request.getParameter("login_id");
		String password = request.getParameter("password");

		LoginService loginService = new LoginService();
		User user = loginService.login(login_id, password);

		if(StringUtils.isEmpty(login_id)) {
			messages.add("ログインIDを入力してください。");
		}
		if(StringUtils.isEmpty(password)) {
			messages.add("パスワードを入力してください。");
		}
		if(StringUtils.isNotEmpty(login_id) && StringUtils.isNotEmpty(password) && user == null) {
			messages.add("ログインに失敗しました。");
		}
		if(user != null && user.getStatus() == 1) {
			messages.add("アカウントが停止中です。");
		}
		if(messages.size() == 0 && user != null && user.getStatus() == 0) {
			return true;
		} else {
			return false;
		}
	}


}