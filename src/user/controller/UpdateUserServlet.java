package user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.UserBean;

import persistant.dao.UserDAO;
import persistant.dto.UserRequestDTO;
import persistant.dto.UserResponseDTO;

/**
 * Servlet implementation class UserUpdateServlet
 */
@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateUserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String selectedUserId=request.getParameter("selectedUserId");
		UserDAO dao = new UserDAO();
		UserResponseDTO userRes=dao.selectOneUser(selectedUserId);
		UserBean userBean=new UserBean();
		userBean.setId(selectedUserId);
		userBean.setName(userRes.getName());
		userBean.setEmail(userRes.getEmail());
		userBean.setPassword(userRes.getPassword());
		userBean.setCpwd(userRes.getCpwd());
		userBean.setUserRole(userRes.getUserRole());	
		request.setAttribute("userBean", userBean);
		request.getRequestDispatcher("USR002.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String cpwd = request.getParameter("cpwd");
		String userRole = request.getParameter("userRole");
		UserBean userBean = new UserBean(id, name, email, password, cpwd, userRole);

		if (name.isEmpty() || email.isEmpty() || password.isEmpty() || cpwd.isEmpty() || userRole.isEmpty()) {
			request.setAttribute("error", "Field can not be BLANK!!");
			request.setAttribute("userBean", userBean);
		} else {
			UserDAO dao = new UserDAO();
			UserRequestDTO dto = new UserRequestDTO();
			dto.setUid(id);
			dto.setName(name);
			dto.setEmail(email);
			dto.setPassword(password);
			dto.setCpwd(cpwd);
			dto.setUserRole(userRole);
			int i = dao.updateUser(dto);
			if (i > 0) {
				request.setAttribute("msg", "Update Successful!!");
			} else {
				request.setAttribute("msg", "Update Fail!");
				request.setAttribute("userBean", userBean);
			}
		}
		request.getRequestDispatcher("USR002.jsp").forward(request, response);


	}

}
