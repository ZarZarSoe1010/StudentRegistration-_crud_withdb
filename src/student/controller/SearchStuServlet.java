package student.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

import persistant.dao.StudentDAO;
import persistant.dto.CourseResponseDTO;
import persistant.dto.StudentRequestDTO;
import persistant.dto.StudentResponseDTO;

/**
 * Servlet implementation class SearchStuServlet
 */
@WebServlet("/SearchStuServlet")
public class SearchStuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchStuServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sid = request.getParameter("sid");
		String sname = request.getParameter("sname");
		String scourse=request.getParameter("scourse");
	
		StudentDAO dao = new StudentDAO();
		ArrayList<StudentResponseDTO> stuResList = new ArrayList<StudentResponseDTO>();
		ArrayList<StudentBean> stuBeanList = new ArrayList<StudentBean>();
		if (sid.isEmpty() && sname.isEmpty() && scourse.isEmpty()) {
			stuResList = dao.selectAll();
		} else {
			stuResList = dao.selectByFilter(sid,sname,scourse);		
		}
		if (stuResList.size() == 0) {
			request.setAttribute("msg", "Student not found!!");
		} else {
			for (StudentResponseDTO stuRes : stuResList) {
				ArrayList<CourseBean>courseBeanList=new ArrayList<CourseBean>();
				ArrayList<CourseResponseDTO> courseResList=dao.selectCourseList(stuRes.getSid());
				for(CourseResponseDTO courseRes : courseResList) {
					CourseBean courseBean=new CourseBean();
					courseBean.setId(courseRes.getCid());
					courseBean.setName(courseRes.getName());
					courseBeanList.add(courseBean);		
				}
				
				StudentBean stuBean = new StudentBean();
				stuBean.setId(stuRes.getSid());
				stuBean.setName(stuRes.getName());
				stuBean.setDob(stuRes.getDob());
				stuBean.setGender(stuRes.getGender());
				stuBean.setPhone(stuRes.getPhone());
				stuBean.setEducation(stuRes.getEducation());		
				stuBean.setAttend(courseBeanList);
				stuBeanList.add(stuBean);
			}
		}
		
		request.setAttribute("stuList", stuBeanList);
		request.getRequestDispatcher("STU003.jsp").forward(request, response);
	}
//		List<String> course = new ArrayList<String>();
//		for (StudentBean student : stuList) {		
//				course=student.getAttend();
//					for (int i = 0; i < course.size(); i++) {
//						System.out.println(course.get(i));
//						System.out.println("result : "+course.contains(scourse));
//					}			
//					if(student.getId().contains(sid) || student.getName().contains(sname) ||student.getAttend().contains(scourse)) {
//				System.out.println(student.getAttend().contains(scourse));
//				updateStuList.add(student);
//				request.setAttribute("stuList", updateStuList);
//				request.getRequestDispatcher("STU003.jsp").forward(request, response);
//			}
//		}
//		if(updateStuList.size() == 0) {
//			request.setAttribute("stuList", stuList);
//
//		} else {
//		}
//			request.getRequestDispatcher("STU003.jsp").forward(request, response);
//		}
//		if (sid.isEmpty() && sname.isEmpty() && scourse.equals("932#$@#434") || stuList == null) {
//			request.setAttribute("stuList", stuList);
//			request.getRequestDispatcher("STU003.jsp").forward(request, response);
//
//		}
//		else if(sid.isEmpty() && sname.isEmpty() && !scourse.equals("932#$@#434")){
//			Iterator<StudentBean> itr = stuList.iterator();
//			while (itr.hasNext()) {
//				StudentBean stuBean1 = itr.next();
////				List<String>list=Arrays.asList(stuBean1.getAttend());
//				if (stuBean1.getAttend().contains(scourse)) {
//					updateStuList.add(stuBean1);
//				}
//			}
//		}
//		else {
//			Iterator<StudentBean> itr = stuList.iterator();
//			while (itr.hasNext()) {
//				StudentBean stuBean1 = itr.next();
////				List<String>list=Arrays.asList(stuBean1.getAttend());
//				if (stuBean1.getName().equals(sname) || stuBean1.getId().equals(sid)
//						|| stuBean1.getAttend().contains(scourse)) {
//					updateStuList.add(stuBean1);
//				}
//			}
//			request.setAttribute("stuList", updateStuList);
//		}
//		request.getRequestDispatcher("STU003.jsp").forward(request, response);

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
