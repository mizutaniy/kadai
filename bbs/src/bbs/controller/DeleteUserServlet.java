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

import bbs.beans.User;
import bbs.service.UserListService;

@WebServlet(urlPatterns = { "/deleteuser" })
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		User user = (User) request.getSession().getAttribute("loginUser");

		if(user != null) {
			HttpSession session = request.getSession();
			List<String> messages = new ArrayList<String>();

			int branch_id = user.getBranch_id();
			int department_id = user.getDepartment_id();

			if(branch_id == 1 && department_id == 1) {
				response.sendRedirect("usermanager");
			} else {
				messages.add("ユーザー管理画面へのアクセス権限がありません。");
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("home");
			}
		} else {
			response.sendRedirect("./");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		User user = (User) request.getSession().getAttribute("loginUser");

		if(user != null) {

			int id = Integer.parseInt(request.getParameter("id"));

			UserListService userListService = new UserListService();
			userListService.deleteUser(id);

			response.sendRedirect("usermanager");
		} else {
			response.sendRedirect("./");
		}
	}

}