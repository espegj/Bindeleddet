package controllers;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.SqlUpdate;

public class Databasen {
	
	
	
	public void insert(String table, String name, String value){
		SqlUpdate insert = Ebean.createSqlUpdate("insert into "+table+" ("+name+") values(\""+value+"\")");
		insert.execute(); 
	}
	
	public ArrayList<String> select(String query){
		ArrayList<String> list = new ArrayList<>();
		String sql = query;
		List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).findList();		 
		for (SqlRow sqlRow : sqlRows) {
		    String name = sqlRow.getString("navn");
		    list.add(name);
		}
		return list;
	}

}
