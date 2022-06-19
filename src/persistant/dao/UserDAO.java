package persistant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import persistant.dto.UserRequestDTO;
import persistant.dto.UserResponseDTO;

public class UserDAO {
	public static Connection con = null;
	static {
		con = MyConnection.getConnection();
	}

	public int insertUser(UserRequestDTO dto) {
		int result = 0;
		String sql = "insert into user (uid,name,email,password,cpwd,userRole) values(?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getUid());
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getEmail());
			ps.setString(4, dto.getPassword());
			ps.setString(5, dto.getCpwd());
			ps.setString(6, dto.getUserRole());
			result=ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Database Insert error!!");
		}
		return result;
	}

	public int updateUser(UserRequestDTO dto) {
		int result = 0;
		String sql = "update user set name=?,email=?,password=?,cpwd=?,userRole=? where uid=? ";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getEmail());
			ps.setString(3, dto.getPassword());
			ps.setString(4, dto.getCpwd());
			ps.setString(5, dto.getUserRole());
			ps.setString(6, dto.getUid());
			result=ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Database Update error!!");
		}
		return result;
	}

	public int deleteUser(String userId) {
		int result = 0;
		String sql = "delete from user where uid=? ";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			result=ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database Delete error!!");
		}
		return result;
	}

	public ArrayList<UserResponseDTO> selectAllUser() {
		ArrayList<UserResponseDTO> userList = new ArrayList<UserResponseDTO>();
		String sql = "select * from user";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserResponseDTO res = new UserResponseDTO();
				res.setUid(rs.getString("uid"));
				res.setName(rs.getString("name"));
				res.setPassword(rs.getString("password"));
				res.setCpwd(rs.getString("cpwd"));
				res.setUserRole(rs.getString("userRole"));
				userList.add(res);
			}
		} catch (SQLException e) {
			System.out.println("Database Selecr one Error!!");
		}
		return userList;
	}

	public UserResponseDTO selectOneUser(String  userId) {
		UserResponseDTO res = new UserResponseDTO();
		String sql = "select * from user where uid=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				res.setUid(rs.getString("uid"));
				res.setName(rs.getString("name"));
				res.setEmail(rs.getString("email"));
				res.setPassword(rs.getString("password"));
				res.setCpwd(rs.getString("cpwd"));
				res.setUserRole(rs.getString("userRole"));
			}
		} catch (SQLException e) {
			System.out.println("Database Selecr one Error!!");
		}
		return res;
	}

	public ArrayList<UserResponseDTO> selectByFilter(UserRequestDTO dto) {
		ArrayList<UserResponseDTO> userList = new ArrayList<UserResponseDTO>();
		String sql = "select * from user where uid=? or name=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getUid());
			ps.setString(2, dto.getName());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserResponseDTO res = new UserResponseDTO();
				res.setUid(rs.getString("uid"));
				res.setName(rs.getString("name"));
				res.setEmail(rs.getString("email"));
				res.setPassword(rs.getString("password"));
				res.setCpwd(rs.getString("cpwd"));
				res.setUserRole(rs.getString("userRole"));
				userList.add(res);
			}
		} catch (SQLException e) {
			System.out.println("Database select one error!!");
		}
		return userList;
	}

}
