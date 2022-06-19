package persistant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistant.dto.CourseResponseDTO;
import persistant.dto.StudentRequestDTO;
import persistant.dto.StudentResponseDTO;

public class StudentDAO {
	public static Connection con = null;
	static {
		con = MyConnection.getConnection();
	}

	public int insertStudent(StudentRequestDTO dto) {
		int result = 0;
		String sql = "insert into student(sid,name,dob,gender,phone,education)values(?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getSid());
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getDob());
			ps.setString(4, dto.getGender());
			ps.setString(5, dto.getPhone());
			ps.setString(6, dto.getEducation());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database insert ERROR!!");
		}
		return result;
	}

	public int updateStudent(StudentRequestDTO dto) {
		int result = 0;
		String sql = "update student set name=?,dob=?,gender=?,phone=?,education=? where sid=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getDob());
			ps.setString(3, dto.getGender());
			ps.setString(4, dto.getPhone());
			ps.setString(5, dto.getEducation());
			ps.setString(6, dto.getSid());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database updateERROR!!");
		}
		return result;
	}

	public int deleteStudent(String studentId) {
		int result = 0;
		String sql = "delete from student where sid=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, studentId);
			result=ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database Delete error");
		}
		return result;
	}

	public ArrayList<StudentResponseDTO> selectByFilter(String  studentId,String name,String course) {
		ArrayList<StudentResponseDTO> studentList = new ArrayList<StudentResponseDTO>();
		String sql = "select * from student where sid=? or name=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, studentId);
			ps.setString(2, name);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				StudentResponseDTO res = new StudentResponseDTO();
				res.setSid(rs.getString("sid"));
				res.setName(rs.getString("name"));
				res.setDob(rs.getString("dob"));
				res.setGender(rs.getString("gender"));
				res.setPhone(rs.getString("phone"));
				res.setEducation(rs.getString("education"));
				studentList.add(res);
			}
		} catch (SQLException e) {
			System.out.println("Database selectByfilter error!!");
		}
		return studentList;
	}

	public ArrayList<StudentResponseDTO> selectAll() {
		ArrayList<StudentResponseDTO> studentList = new ArrayList<StudentResponseDTO>();
		String sql = "select * from student";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				StudentResponseDTO res = new StudentResponseDTO();
				res.setSid(rs.getString("sid"));
				res.setName(rs.getString("name"));
				res.setDob(rs.getString("dob"));
				res.setGender(rs.getString("gender"));
				res.setPhone(rs.getString("phone"));
				res.setEducation(rs.getString("education"));
				studentList.add(res);
			}
		} catch (SQLException e) {
			System.out.println("Database select all error!!");
		}
		return studentList;
	}

	public StudentResponseDTO selectOne(String student_id) {
		StudentResponseDTO res = new StudentResponseDTO();
		String sql = "select * from student where sid=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, student_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				res.setSid(rs.getString("sid"));
				res.setName(rs.getString("name"));
				res.setDob(rs.getString("dob"));
				res.setGender(rs.getString("gender"));
				res.setPhone(rs.getString("phone"));
				res.setEducation(rs.getString("education"));
			}

		} catch (SQLException e) {
			System.out.println("Database select one error!!");
		}
		return res;
	}

	public void insertStudent_Course(String sid, List<String> cIds) {
		try {
			for (String cid : cIds) {
				int result = 0;
				String sql = "insert into student_course(sid,cid)values(?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, sid);
				ps.setString(2, cid);
				result = ps.executeUpdate();
				System.out.println("insert student_ course result" + result);
			}
		} catch (SQLException e) {
			System.out.println("Database stu_course insert ERROR!!");
		}

	}

	public ArrayList<CourseResponseDTO> selectCourseList(String studentId) {
		ArrayList<CourseResponseDTO> courseList = new ArrayList<CourseResponseDTO>();
		ArrayList<String> courseIdList = new ArrayList<String>();
		String sql = "select cid from student_course where sid=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, studentId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				courseIdList.add(rs.getString("cid"));
			}
			for(String cid: courseIdList) {
				 sql="select * from course where cid=?";
				 ps=con.prepareStatement(sql);
				 ps.setString(1, cid.trim());
				 rs=ps.executeQuery();
				 while(rs.next()) {
					 CourseResponseDTO course=new CourseResponseDTO();
					 course.setCid(rs.getString("cid"));
					 course.setName(rs.getString("name"));
					 courseList.add(course);
				 }
			}
		} catch (SQLException e) {
			System.out.println("Database selectByfilter error!!");
		}
		return  courseList;
	}
	
	public int deleteStudent_Course( String studentId) {
		int result = 0;
		String sql = "delete from student_course where sid=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,studentId);
			result=ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database Delete error");
		}
		return result;
	}
}
