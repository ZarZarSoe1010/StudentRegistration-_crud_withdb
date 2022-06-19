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
 * Servlet implementation class UserRegisterServlet
 */
@WebServlet("/RegisterUserServlet")
public class RegisterUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterUserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// STU001
		
		String newUserId = GetNewUserId(request);
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String cpwd = request.getParameter("cpwd");	
		String userRole = request.getParameter("userRole");
		UserBean userBean = new UserBean(newUserId, name, email, password, cpwd, userRole);

		if (name.isEmpty() || email.isEmpty() || password.isEmpty() || cpwd.isEmpty() || userRole.isEmpty()) {
			request.setAttribute("error", "Field can not be BLANK!!");
			request.setAttribute("userBean", userBean);
			request.getRequestDispatcher("USR001.jsp").forward(request, response);
		} else {
			UserRequestDTO dto = new UserRequestDTO();
			UserDAO dao = new UserDAO();
			dto.setUid(newUserId);
			dto.setName(name);
			dto.setEmail(email);
			dto.setPassword(password);
			dto.setCpwd(cpwd);
			dto.setUserRole(userRole);
			int i = dao.insertUser(dto);
			if (i > 0) {
				request.setAttribute("msg", "Register Successful!!");
			} else {
				request.setAttribute("msg", "Insert Fail!");
				request.setAttribute("userBean", userBean);
			}
			request.getRequestDispatcher("USR001.jsp").forward(request, response);
		}

	}

	private String GetNewUserId(HttpServletRequest request) {
		int lastUserId = 0;
	//	List<UserBean> userList = (List<UserBean>) request.getServletContext().getAttribute("userList");
		UserDAO dao=new UserDAO();
		List<UserResponseDTO>userList=dao.selectAllUser();
		if (userList != null) {
			Iterator<UserResponseDTO> itr = userList.iterator();
			while (itr.hasNext()) {
				String userId = itr.next().getUid();
				int count = Integer.parseInt(userId.split("_")[1]);

				if (count > lastUserId) {
					lastUserId = count;
				}
			}
		}
		String newUserId = "USR_" + String.format("%03d", lastUserId + 1);
		return newUserId;
	}
}
