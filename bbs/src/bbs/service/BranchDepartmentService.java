package bbs.service;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bbs.beans.BranchList;
import bbs.beans.DepartmentList;
import bbs.dao.UserListDao;

public class BranchDepartmentService {

	public List<BranchList> getBranchList() {

		Connection connection = null;
		try {
			connection = getConnection();

			UserListDao userlistDao = new UserListDao();
			List<BranchList> ret = userlistDao.getBranchList(connection);

			commit(connection);
			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}


	public List<DepartmentList> getDepartmentList() {

		Connection connection = null;
		try {
			connection = getConnection();

			UserListDao userlistDao = new UserListDao();
			List<DepartmentList> ret = userlistDao.getDepartmentList(connection);

			commit(connection);
			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

}