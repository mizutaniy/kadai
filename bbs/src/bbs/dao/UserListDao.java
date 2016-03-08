package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.BranchList;
import bbs.beans.DepartmentList;
import bbs.beans.User;
import bbs.beans.UserList;
import bbs.exception.NoRowsUpdatedRuntimeException;
import bbs.exception.SQLRuntimeException;

public class UserListDao {

	public List<UserList> getUserList(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT users.id, login_id, name, branch_name, department_name, status, update_date FROM users, branches, departments ");
			sql.append("WHERE branch_id = branches.id AND department_id = departments.id ORDER BY users.id");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserList> ret = toAllUserList(rs);

			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	public List<UserList> getUserList(Connection connection, int user_id) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id, login_id, name, status, update_date FROM users ");
			sql.append("ORDER BY id");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserList> ret = toAllUserList(rs);

			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	private List<UserList> toAllUserList(ResultSet rs) throws SQLException {

		List<UserList> ret = new ArrayList<UserList>();
		try {
			while(rs.next()) {
				int id = rs.getInt("id");
				String login_id = rs.getString("login_id");
				String name = rs.getString("name");
				String branch_name = rs.getString("branch_name");
				String department_name = rs.getString("department_name");
				int status = rs.getInt("status");
				Timestamp updateDate = rs.getTimestamp("update_date");

				UserList userlist = new UserList();
				userlist.setId(id);
				userlist.setLogin_id(login_id);
				userlist.setName(name);
				userlist.setBranch_name(branch_name);
				userlist.setDepartment_name(department_name);
				userlist.setStatus(status);
				userlist.setUpdate(updateDate);

				ret.add(userlist);
			}
			return ret;
		} finally {
			close(rs);
		}
	}


	public List<BranchList> getBranchList(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM branches ORDER BY id");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<BranchList> ret = toBranchList(rs);

			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	public List<DepartmentList> getDepartmentList(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM departments ORDER BY id");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<DepartmentList> ret = toDepartmentList(rs);

			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	private List<BranchList> toBranchList(ResultSet rs) throws SQLException {

		List<BranchList> ret = new ArrayList<BranchList>();
		try {
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("branch_name");

				BranchList branchlist = new BranchList();
				branchlist.setId(id);
				branchlist.setName(name);

				ret.add(branchlist);
			}
			return ret;
		} finally {
			close(rs);
		}
	}


	private List<DepartmentList> toDepartmentList(ResultSet rs) throws SQLException {

		List<DepartmentList> ret = new ArrayList<DepartmentList>();
		try {
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("department_name");


				DepartmentList departmentlist = new DepartmentList();
				departmentlist.setId(id);
				departmentlist.setName(name);

				ret.add(departmentlist);
			}
			return ret;
		} finally {
			close(rs);
		}
	}


	public void insert(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users ( ");
			sql.append(" login_id");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", department_id");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append(" ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLogin_id());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getBranch_id());
			ps.setInt(5, user.getDepartment_id());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	public void update(Connection connection, int user_id, int status) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append(" status = ?");
			sql.append(", update_date = CURRENT_TIMESTAMP");
			sql.append(" WHERE");
			sql.append(" id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, status);
			ps.setInt(2, user_id);

			int count = ps.executeUpdate();
			if(count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	public void update(Connection connection, User user, String password) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append(" login_id = ?");
			sql.append(", password = ?");
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", department_id = ?");
			sql.append(", update_date = CURRENT_TIMESTAMP");
			sql.append(" WHERE");
			sql.append(" id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLogin_id());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getBranch_id());
			ps.setInt(5, user.getDepartment_id());
			ps.setInt(6, user.getId());

			int count = ps.executeUpdate();
			if(count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

}
