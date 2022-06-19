package student.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.CourseBean;
import com.model.StudentBean;

import persistant.dao.CourseDAO;
import persistant.dao.StudentDAO;
import persistant.dto.CourseResponseDTO;
import persistant.dto.StudentResponseDTO;

/**
 * Servlet implementation class SeeMoreServlet
 */
@WebServlet("/SeeMoreServlet")
public class SeeMoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SeeMoreServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String selectedStuId = request.getParameter("selectedStuId");
		StudentDAO dao=new StudentDAO();
		CourseDAO courseDao=new CourseDAO();
		StudentResponseDTO studentInfo=dao.selectOne(selectedStuId);
		ArrayList<CourseResponseDTO>courseResList=courseDao.selectAll();
		ArrayList<CourseBean>courseBeanList=new ArrayList<CourseBean>();
		for(CourseResponseDTO course: courseResList) {
			CourseBean courseBean=new CourseBean();
			courseBean.setId(course.getCid());
			courseBean.setName(course.getName());
			courseBeanList.add(courseBean);
		}
		ArrayList<CourseResponseDTO>stuCourseResList=dao.selectCourseList(selectedStuId);;
		ArrayList<String>stuCourseBeanList=new ArrayList<String>();
		for(CourseResponseDTO course: stuCourseResList) {
			stuCourseBeanList.add(course.getName());
		}
		StudentBean stuBean=new StudentBean();
		stuBean.setId(selectedStuId);
		stuBean.setName(studentInfo.getName());
		stuBean.setDob(studentInfo.getDob());
		stuBean.setGender(studentInfo.getGender());
		stuBean.setPhone(studentInfo.getPhone());
		stuBean.setEducation(studentInfo.getEducation());
		stuBean.setAttend(courseBeanList);
		stuBean.setStuCourse(stuCourseBeanList);

		
		request.setAttribute("studentInfo", stuBean);	
		request.getRequestDispatcher("STU002.jsp").forward(request, response);

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
