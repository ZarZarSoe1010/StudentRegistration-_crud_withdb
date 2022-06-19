package student.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import persistant.dto.StudentRequestDTO;

/**
 * Servlet implementation class UpdateStuServlet
 */
@WebServlet("/UpdateStuServlet")
public class UpdateStuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateStuServlet() {
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
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String dob = request.getParameter("dob");
		String gender = request.getParameter("gender");
		String phone = request.getParameter("phone");
		String education = request.getParameter("education");
		List<String> attend = new ArrayList<>();
		Collections.addAll(attend, request.getParameterValues("attend"));
		StudentBean stuBean = new StudentBean(id, name, dob, gender, phone, education);

		if (name.isEmpty() || dob.isEmpty() || gender.isEmpty() || phone.isEmpty() || education.isEmpty()
				|| attend.size()==0) {
			request.setAttribute("msg", " Fields can not be BLANK!!");
			request.setAttribute("stuBean", stuBean);
			request.getRequestDispatcher("STU003.jsp").forward(request, response);
		} else {
			StudentDAO dao = new StudentDAO();
			StudentRequestDTO dto = new StudentRequestDTO();
			dto.setSid(id);
			dto.setName(name);
			dto.setDob(dob);
			dto.setGender(gender);
			dto.setPhone(phone);
			dto.setEducation(education);
			int i = dao.updateStudent(dto);

			if (i > 0) {
				request.setAttribute("msg", " Update Successful!!!");
				dao.deleteStudent_Course(id);
				dao.insertStudent_Course(id,attend);
			} else {
				request.setAttribute("msg", "Update Fail!!");
			}
			CourseDAO courseDao=new CourseDAO();
			ArrayList<CourseResponseDTO>courseResList=courseDao.selectAll();
			ArrayList<CourseBean>courseBeanList=new ArrayList<CourseBean>();
			for(CourseResponseDTO course: courseResList) {
				CourseBean courseBean=new CourseBean();
				courseBean.setId(course.getCid());
				courseBean.setName(course.getName());
				courseBeanList.add(courseBean);
			}
			ArrayList<CourseResponseDTO>stuCourseResList=dao.selectCourseList(id);
			ArrayList<String>stuCourseBeanList=new ArrayList<String>();
			for(CourseResponseDTO course: stuCourseResList) {
				stuCourseBeanList.add(course.getName());
			}		
			stuBean.setAttend(courseBeanList);
			stuBean.setStuCourse(stuCourseBeanList);

			
			request.setAttribute("stuBean", stuBean);
			response.sendRedirect("STU003.jsp");

		}
	}
}
