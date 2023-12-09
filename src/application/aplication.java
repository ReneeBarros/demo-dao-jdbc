package application;

import java.util.Date;
import java.util.List;

import model.Dao.DaoFactory;
import model.Dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class aplication {

	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao();

		System.out.println(" ====   TEST 1 Seller method findById");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);

		
		System.out.println(" ====   TEST 2 Seller method findByDepartment");
		Department department = new Department(2, null);
		
		List<Seller> list = sellerDao.findByDepartment(department);
		
		for ( Seller obj: list) {
		System.out.println(obj);
		}
		
		System.out.println(" ====   TEST 3 Seller method findAll");
		
		List<Seller> listFindAll = sellerDao.findAll();
		
		for ( Seller findall: listFindAll) {
		System.out.println(findall);
		}
		
		
		System.out.println(" ====   TEST 4 Seller method Insert");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.00, department);
		
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id: " + newSeller.getId());
		
		
		System.out.println(" ====   TEST 5 Seller method UpDate");

		seller = sellerDao.findById(1);
		seller.setName("Renee Barros");
		seller.setBaseSalary(1000000.00);
		sellerDao.update(seller);
		System.out.println("Done!");
		
		
		System.out.println(" ====   TEST 6 Seller method deleteById");
		sellerDao.deleteById(1);
		System.out.println(" Delete Done!");
		
	}
	

}
