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

import bbs.beans.BranchList;
import bbs.beans.DepartmentList;
import bbs.beans.User;
import bbs.service.BranchDepartmentService;
import bbs.service.UserListService;

@WebServlet(urlPatterns = { "/createuser" })
public class CreateUserServlet extends HttpServlet {
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

				List<BranchList> branchList = new BranchDepartmentService().getBranchList();
				List<DepartmentList> departmentList = new BranchDepartmentService().getDepartmentList();

				request.setAttribute("branchList", branchList);
				request.setAttribute("departmentList", departmentList);

				request.getRequestDispatcher("createuser.jsp").forward(request, response);
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

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();

		User user = new User();
		user.setLogin_id(request.getParameter("login_id"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setBranch_id(Integer.parseInt(request.getParameter("branch_id")));
		user.setDepartment_id(Integer.parseInt(request.getParameter("department_id")));

		List<BranchList> branchList = new BranchDepartmentService().getBranchList();
		List<DepartmentList> departmentList = new BranchDepartmentService().getDepartmentList();

		request.setAttribute("branchList", branchList);
		request.setAttribute("departmentList", departmentList);

		if(isValid(request, messages) == true) {
			new UserListService().register(user);
			response.sendRedirect("usermanager");
		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("inputData", user);
			request.getRequestDispatcher("errorcreateuser.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String login_id = request.getParameter("login_id");
		String password = request.getParameter("password");
		String passwordConfirm = request.getParameter("passwordConfirm");
		String name = request.getParameter("name");
		String branch_id = request.getParameter("branch_id");
		String department_id = request.getParameter("department_id");

		if(StringUtils.isEmpty(login_id) == true) {
			messages.add("ログインIDを入力してください。");
		}
		if(!login_id.matches("^[0-9A-Za-z]{6,20}$")) {
			messages.add("ログインIDは6字以上20字以下の半角英数字で入力してください。");
		}

		if(StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください");
		}
		if(!login_id.matches("^[a-zA-Z0-9-/:-@\\[-\\`\\{-\\~]{6,255}$")) {
			messages.add("パスワードは6字以上255字以下の記号を含むすべての半角文字で入力してください。");
		}

		if(StringUtils.isEmpty(passwordConfirm) == true) {
			messages.add("確認用パスワードを入力してください");
		}
		if(!password.equals(passwordConfirm)) {
			messages.add("パスワードと確認用パスワードが違います。");
		}

		if(StringUtils.isEmpty(name) == true) {
			messages.add("名称を入力してください");
		}
		if(!login_id.matches("^.{1,10}$")) {
			messages.add("名称は10字以内で入力してください。");
		}

		if(StringUtils.isEmpty(branch_id) == true) {
			messages.add("支店を選択してください");
		}
		if(StringUtils.isEmpty(department_id) == true) {
			messages.add("部署・役職を選択してください");
		}

		UserListService signupService = new UserListService();
		User user = signupService.duplicateUser(login_id);
		if(user != null) {
			messages.add("ログインIDがすでに利用されています");
		}
		if(messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
