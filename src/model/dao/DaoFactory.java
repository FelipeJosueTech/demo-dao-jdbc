package model.dao;

import database.DataBase;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DataBase.getConnection());
	}

}
