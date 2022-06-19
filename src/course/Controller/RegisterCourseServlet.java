package course.Controller;

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

import persistant.dao.CourseDAO;
import persistant.dto.CourseRequestDTO;
import persistant.dto.CourseResponseDTO;

/**
 * Servlet implementation class RegisterClassServlet
 */
@WebServlet("/RegisterCourseServlet")
public class RegisterCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterCourseServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String newCourseId = GetNewCourseId(request);
		request.getServletContext().setAttribute("newCourseId", newCourseId);
		request.getRequestDispatcher("BUD003.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		String cname = request.getParameter("cname");
		CourseBean courseBean = new CourseBean(cid, cname);

		if (cid.equals("") || cname.equals("")) {
			request.setAttribute("msg", "Fields can not be BLANK!!");
			request.setAttribute("courseBean", courseBean);
		} else {
			CourseDAO dao = new CourseDAO();
			CourseRequestDTO dto = new CourseRequestDTO();
			dto.setCid(cid);
			dto.setName(cname);
			int i = dao.insertCourse(dto);
			if (i > 0) {
				request.setAttribute("msg", "Insert Course Successful!!");
			} else {
				request.setAttribute("msg", "Insert Fail!!");
			}
			String newCourseId = GetNewCourseId(request);
			request.getServletContext().setAttribute("newCourseId", newCourseId);
			request.setAttribute("courseBean", courseBean);
			request.getRequestDispatcher("BUD003.jsp").forward(request, response);
		}

	}

	private String GetNewCourseId(HttpServletRequest request) {
		int lastCourseId = 0;
//		List<CourseBean> courseList = (List<CourseBean>) request.getServletContext().getAttribute("courseList");
		CourseDAO dao = new CourseDAO();
		List<CourseResponseDTO> courseList = dao.selectAll();
		if (courseList != null) {
			Iterator<CourseResponseDTO> itr = courseList.iterator();
			while (itr.hasNext()) {
				String courseId = itr.next().getCid();
				int count = Integer.parseInt(courseId.split("_")[1].trim());

				if (count > lastCourseId) {
					lastCourseId = count;
				}
			}
		}
		String newCourseId = "COU_" + String.format("%03d", lastCourseId + 1);
		return newCourseId;
	}

}
