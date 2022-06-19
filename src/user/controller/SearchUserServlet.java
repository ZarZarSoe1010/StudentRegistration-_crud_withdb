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
 * Servlet implementation class SearchUserServlet
 */
@WebServlet("/SearchUserServlet")
public class SearchUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchUserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		UserBean userBean = new UserBean(id, name, null, null, null, null);
		UserRequestDTO dto = new UserRequestDTO();
		dto.setUid(id);
		dto.setName(name);
		UserDAO dao = new UserDAO();
		ArrayList<UserResponseDTO> userResList = new ArrayList<UserResponseDTO>();
		ArrayList<UserBean>userBeanList=new ArrayList<UserBean>();
		if (id.isEmpty() && name.isEmpty()) {
			userResList = dao.selectAllUser();
		} 	
		else {
			userResList = dao.selectByFilter(dto);   		
		}
		if(userResList.size()==0) {
			request.setAttribute("msg", "User not found!!");
		}else {
			for(UserResponseDTO userRes :userResList) {
				UserBean user=new UserBean();
				user.setId(userRes.getUid());
				user.setName(userRes.getName());
				user.setEmail(userRes.getEmail());
				user.setPassword(userRes.getPassword());
				user.setCpwd(userRes.getCpwd());
				user.setUserRole(userRes.getUserRole());
				userBeanList.add(user);
			}
		}		
		request.setAttribute("userList", userBeanList);
		request.setAttribute("userBean", userBean);
		request.getRequestDispatcher("USR003.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
