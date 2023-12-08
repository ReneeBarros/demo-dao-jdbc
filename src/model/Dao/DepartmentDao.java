package model.Dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {
	
	void insert (Department obj);
	void update(Department obj);
	void deleteById(Integer id);
	DepartmentDao findById(Integer id);
	List<Department> findAll();

}
