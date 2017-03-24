package com.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dimeng.p2p.S61.enums.T6118_F02;
import com.dimeng.util.StringHelper;

public class TestCardupSex {
	public static void cardutilt() throws Throwable{
		try (Connection connection = Config.getConnection()) {
			try (PreparedStatement pstmt = connection
					.prepareStatement("SELECT F01,F07 from S61.T6141 where F07 is not null and sex is null and F01 >= 42348")) {
				try (ResultSet resultSet = pstmt.executeQuery();
					BufferedWriter writer = new BufferedWriter(new FileWriter(new File("D:\\idcardsex2.sql")));) {
					while(resultSet.next()){
						int uid = resultSet.getInt(1);
						String idcard =StringHelper.decode(resultSet.getString(2));
						Integer ms = Integer.parseInt(idcard.substring(
								idcard.length() - 2, idcard.length() - 1));
						if (ms % 2 == 0) {
							writer.write("update S61.T6141 set sex = 0 where F01= "+uid+";");
						}else{
							writer.write("update S61.T6141 set sex = 1 where F01= "+uid+";");
						}
						writer.newLine();
						//execute(connection, "update S61.T6141 set sex = 0 where F01= "+uid);}
					}
				}
			     	}
			
				}
		
	
	}

	public static void main(String[] args) throws Throwable{
		// TODO Auto-generated method stub
		cardutilt();
	}

}
