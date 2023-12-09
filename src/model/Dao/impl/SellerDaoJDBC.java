package model.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.Dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;

	}

	@Override
	public void update(Seller obj) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(

					"UPDATE seller " + "SET Name = ?, Email =?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
							+ "WHERE Id =?");

			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBithDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		} finally {
			DB.closeStatement(st);

		}

	}

	@Override
	public void deleteById(Integer id) {

		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"DELETE FROM seller WHERE Id = ?"
					);
		st.setInt(1, id);
		
		
		
		int valueT = st.executeUpdate();
		
		if (valueT==0) {
			
			
			throw new DbException("Error ao digitar o codigo");
		}
		
		}catch(SQLException e) {
			
			throw new DbException(e.getMessage());
			
		}finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Seller findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT seller.* , department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Department dep = instantiatedepartment(rs);
				Seller sl = instantiateSeller(rs, dep);
				return sl;
			}
			return null;

		} catch (SQLException e) {

			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller sl = new Seller();
		sl.setId(rs.getInt("Id"));
		sl.setName(rs.getString("Name"));
		sl.setEmail(rs.getString("Email"));
		sl.setBaseSalary(rs.getDouble("BaseSalary"));
		sl.setBithDate(rs.getDate("BirthDate"));
		sl.setDepartment(dep);

		return sl;
	}

	private Department instantiatedepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(

					"INSERT INTO seller " + "(Name, Email, BirthDate, BaseSalary, DepartmentId ) " + "VALUES "
							+ "(?,?,?,?,?)",
					java.sql.Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBithDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);

			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		} finally {
			DB.closeStatement(st);

		}
	}

	@Override
	public List<Seller> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT seller.* , department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "ORDER BY Name");

			rs = st.executeQuery();
			List<Seller> listSeller = new ArrayList<>();
			Map<Integer, Department> mapSeller = new HashMap<>();

			while (rs.next()) {

				Department dep = mapSeller.get(rs.getInt("DepartmentId"));

				if (dep == null) {

					dep = instantiatedepartment(rs);
					mapSeller.put(rs.getInt("DepartmentId"), dep);
				}
				Seller sl = instantiateSeller(rs, dep);
				listSeller.add(sl);
			}
			return listSeller;

		} catch (SQLException e) {

			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT seller.* , department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "ORDER BY Name");
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			List<Seller> listSeller = new ArrayList<>();
			Map<Integer, Department> mapSeller = new HashMap<>();

			while (rs.next()) {

				Department dep = mapSeller.get(rs.getInt("DepartmentId"));

				if (dep == null) {

					dep = instantiatedepartment(rs);
					mapSeller.put(rs.getInt("DepartmentId"), dep);
				}
				Seller sl = instantiateSeller(rs, dep);
				listSeller.add(sl);
			}
			return listSeller;

		} catch (SQLException e) {

			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
