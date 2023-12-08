package application;

import model.Dao.DaoFactory;
import model.Dao.SellerDao;
import model.entities.Seller;

public class aplication {

	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao();

		System.out.println(" ====   TEST 1 findById");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);

	}

}
