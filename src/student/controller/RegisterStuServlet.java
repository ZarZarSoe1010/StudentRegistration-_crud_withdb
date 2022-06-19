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
import persistant.dto.StudentResponseDTO;

/**
 * Servlet implementation class RegisterStuServlet
 */
@WebServlet("/RegisterStuServlet")
public class RegisterStuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterStuServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String newStuId = GetNewStudentId(request);
		CourseDAO dao = new CourseDAO();
		ArrayList<CourseResponseDTO> courseListRes = dao.selectAll();
		ArrayList<CourseBean> courseListBean = new ArrayList<CourseBean>();
		for (CourseResponseDTO courseRes : courseListRes) {
			CourseBean courseBean = new CourseBean();
			courseBean.setId(courseRes.getCid());
			courseBean.setName(courseRes.getName());
			courseListBean.add(courseBean);
		}
		StudentBean stuBean=new StudentBean();
		stuBean.setId(newStuId);
		stuBean.setAttend(courseListBean);
		request.setAttribute("stuBean", stuBean);
		request.getRequestDispatcher("STU001.jsp").forward(request, response);

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
		if (id.equals("") || name.equals("") || dob.equals("") || gender.equals("") || phone.equals("")
				|| education.equals("") || attend.equals("")) {
			request.setAttribute("msg", " Fields can not be BLANK!!");
			request.setAttribute("stuBean", stuBean);
			request.getRequestDispatcher("STU001.jsp").forward(request, response);
		} else {

			StudentDAO dao = new StudentDAO();
			StudentRequestDTO dto = new StudentRequestDTO();
			dto.setSid(id);
			dto.setName(name);
			dto.setDob(dob);
			dto.setGender(gender);
			dto.setPhone(phone);
			dto.setEducation(education);
			int i = dao.insertStudent(dto);
			if (i > 0) {
				request.setAttribute("msg", " Register Successful!!!");
				dao.insertStudent_Course(id, attend);
				stuBean = new StudentBean();
				String newStuId = GetNewStudentId(request);
				stuBean.setId(newStuId);
			} else {
				request.setAttribute("msg", "Insert Fail!!");
			}
			ArrayList<CourseBean> courseBeanList = new ArrayList<CourseBean>();
			CourseDAO courseDao = new CourseDAO();
			ArrayList<CourseResponseDTO> courseResList = courseDao.selectAll();
			for (CourseResponseDTO courseRes : courseResList) {
				CourseBean courseBean = new CourseBean();
				courseBean.setId(courseRes.getCid());
				courseBean.setName(courseRes.getName());
				courseBeanList.add(courseBean);
			}
			stuBean.setAttend(courseBeanList);
			request.setAttribute("stuBean", stuBean);
			request.getRequestDispatcher("STU001.jsp").forward(request, response);
		}
	}

	@SuppressWarnings("unchecked")
	private String GetNewStudentId(HttpServletRequest request) {
		int lastStuId = 0;
		// List<StudentBean> stuList=(List<StudentBean>)
		// request.getServletContext().getAttribute("stuList");
		StudentDAO dao = new StudentDAO();
		List<StudentResponseDTO> stuList = dao.selectAll();

		if (stuList != null) {
			Iterator<StudentResponseDTO> itr = stuList.iterator();
			while (itr.hasNext()) {
				String stuId = itr.next().getSid();
				int count = Integer.parseInt(stuId.split("_")[1]);

				if (count > lastStuId) {
					lastStuId = count;
				}
			}
		}
		String newStuId = "STU_" + String.format("%03d", lastStuId + 1);

		return newStuId;
	}

}
