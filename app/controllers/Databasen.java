package controllers;

import java.util.ArrayList;
import java.util.List;

import play.db.ebean.Transactional;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.SqlUpdate;


@Transactional
public class Databasen {

	public void insert(String table, String info, Integer typeId, Integer varighet,
			Integer linjeId, Integer trinnId, String url, String kontaktNavn, String kontaktEmail,
			String frist, String prioritet, String sted, String bedriftsNavn, String tittel) {
			SqlUpdate insert = Ebean.createSqlUpdate("insert into " + table + " values(null,\"" + info + "\",\"" + typeId + "\", \"" + varighet + "\",\"" + linjeId
				+ "\",\"" + trinnId + "\",\"" + url + "\",\"" + kontaktNavn + "\",\""
				+ kontaktEmail + "\",\"" + frist + "\",\"0\"," + prioritet + ",\"" + sted
				+ "\",\"" + bedriftsNavn + "\",\"" + tittel + "\")");
		insert.execute();
	}

	public ArrayList<String> select(String query) {
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
