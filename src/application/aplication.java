package application;

import java.util.List;

import model.Dao.DaoFactory;
import model.Dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class aplication {

	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao();

		System.out.println(" ====   TEST 1 findById");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);

		
		System.out.println(" ====   TEST 2 method findByDepartment");
		Department department = new Department(2, null);
		
		List<Seller> list = sellerDao.findByDepartment(department);
		
		for ( Seller obj: list) {
		System.out.println(obj);
		}
	}

}
