package student.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.StudentBean;

import persistant.dao.StudentDAO;
import persistant.dto.StudentRequestDTO;


/**
 * Servlet implementation class DeleteStuServlet
 */
@WebServlet("/DeleteStuServlet")
public class DeleteStuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteStuServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String selectedStuId=request.getParameter("selectedStuId");
		StudentDAO dao=new StudentDAO();
		int i=dao.deleteStudent(selectedStuId);
		dao.deleteStudent_Course(selectedStuId);
		if(i>0) {
			request.setAttribute("msg", "Deletesuccessful!");
		}else {
			request.setAttribute("msg", "Delete Fail!");
		}		
		response.sendRedirect("STU003.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
