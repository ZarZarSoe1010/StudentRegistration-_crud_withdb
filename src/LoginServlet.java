
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
import persistant.dto.UserResponseDTO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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

		String uid = request.getParameter("uid");
		String upwd = request.getParameter("upwd");
		UserBean userBean = new UserBean();
		userBean.setId(uid);
		userBean.setPassword(upwd);

		// List<UserBean> userList = (List<UserBean>)
		// request.getServletContext().getAttribute("userList");
		UserDAO dao = new UserDAO();
		ArrayList<UserResponseDTO> userResList = dao.selectAllUser();

		for (UserResponseDTO userInfo : userResList) {
			if (userInfo.getUid().equals(uid) && userInfo.getPassword().equals(upwd)) {
				request.getSession().setAttribute("userInfo", userInfo);
				request.getRequestDispatcher("MNU001.jsp").forward(request, response);
			} else {
				request.setAttribute("msg", "Email and password do not match");
				request.setAttribute("data", userBean);
				request.getRequestDispatcher("LGN001.jsp").include(request, response);
			}
		}

		/*
		 * Iterator<UserBean> itr = userList.iterator(); while (itr.hasNext()) { if
		 * (itr.next().getName().equals(uname) && itr.next().getPassword().equals(upwd))
		 * { String userName = itr.next().getName();
		 * request.getSession().setAttribute("loginUserName", userName);
		 * request.getRequestDispatcher("MNU001.jsp").forward(request, response);
		 * 
		 * } else { request.setAttribute("error", "Email and password do not match");
		 * request.setAttribute("data", user);
		 * request.getRequestDispatcher("LGN001.jsp").include(request, response);
		 * 
		 * }
		 */
	}

}
