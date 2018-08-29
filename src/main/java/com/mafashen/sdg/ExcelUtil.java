package com.mafashen.sdg;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtil {
	public static final String url = "jdbc:mysql://poi.slave.mysql.sdg.com:3306/itemcenter";
	public static final String username = "mafashen";
	public static final String password = "mafashen19950813";
	public static final String driveClass = "com.mysql.jdbc.Driver";

	static String str = "798 + 132";

	static String string = "";

	public static void main(String[] args){
		System.out.println(string);
//		Map<String , String> map = new HashMap<String , String>();
//		String[] split = string.split(",");
//		for (String s : split) {
//			map.put(s , null);
//		}
//		System.out.println(map.keySet().size());
//		map.clear();

//		String[] split1 = str.split(",");
//		for (String s : split1) {
//			map.put(s , null);
//		}
//		System.out.println(map.keySet().size());
	}

	public static Connection getConn(){
		Connection conn = null;
		try {
			Class.forName(driveClass);
			conn = DriverManager.getConnection(url , username , password);
			String sql = "select barcode , main_image_url from i_spu where barcode in " + str;
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while(resultSet.next()){
				//map.put(resultSet.getLong(1) , resultSet.getString(2));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
