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
			String info = sqlRow.getString("info");
			list.add(info);
			int type = Integer.parseInt(sqlRow.getString("typeId"));
			if(type==1){
				list.add("Sommerjobb");
			}
			else if(type==2){
				list.add("Deltidsjobb");
			}
			else{
				list.add("Fulltidsjobb");
			}
			String varighet = sqlRow.getString("varighet");
			list.add(varighet);
			String linjeId = sqlRow.getString("linjeId");
			list.add(linjeId);
			String trinnId = sqlRow.getString("trinnId");
			list.add(trinnId);
			String url = sqlRow.getString("url");
			list.add(url);
			String kontaktNavn = sqlRow.getString("kontaktNavn");
			list.add(kontaktNavn);
			String kontaktEmail = sqlRow.getString("kontaktEmail");
			list.add(kontaktEmail);
			String frist = sqlRow.getString("frist");
			list.add(frist);
			String sted = sqlRow.getString("sted");
			list.add(sted);
			String bedriftsNavn = sqlRow.getString("bedriftsNavn");
			list.add(bedriftsNavn);
			String tittel = sqlRow.getString("tittel");
			list.add(tittel);
			
		}
		return list;
	}
	
	public ArrayList<String> jsonList(String query) {
		ArrayList<String> list = new ArrayList<>();
		String sql = query;
		List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).findList();
		for (SqlRow sqlRow : sqlRows) {
			String annonseId = sqlRow.getString("annonseId");
			list.add(annonseId);
			String info = sqlRow.getString("info");
			list.add(info);
			String type = sqlRow.getString("typeId");
			list.add(type);		
			String varighet = sqlRow.getString("varighet");
			list.add(varighet);
			String linjeId = sqlRow.getString("linjeId");
			list.add(linjeId);
			String trinnId = sqlRow.getString("trinnId");
			list.add(trinnId);
			String url = sqlRow.getString("url");
			list.add(url);
			String kontaktNavn = sqlRow.getString("kontaktNavn");
			list.add(kontaktNavn);
			String kontaktEmail = sqlRow.getString("kontaktEmail");
			list.add(kontaktEmail);
			String frist = sqlRow.getString("frist");
			list.add(frist);
			String teller = sqlRow.getString("teller");
			list.add(teller);
			String prioritet = sqlRow.getString("prioritet");
			list.add(prioritet);
			String sted = sqlRow.getString("sted");
			list.add(sted);
			String bedriftsNavn = sqlRow.getString("bedriftsNavn");
			list.add(bedriftsNavn);
			String tittel = sqlRow.getString("tittel");
			list.add(tittel);
			
		}
		return list;
	}

}
