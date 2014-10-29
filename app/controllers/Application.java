package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.*;
import java.io.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import play.data.DynamicForm;

public class Application extends Controller {

	public static Result index() throws JSONException {
		return ok(angular.render(getAnnonseString2(), "Sorlandsportalen"));
	}

	public static Result detail() throws JSONException {

		DynamicForm dynamicForm = form().bindFromRequest();
		int id = Integer.parseInt(dynamicForm.get("id"));
		ArrayList<ArrayList<String>> list = getAnnonse(id);
		ArrayList<String> divInfo = list.get(0);
		ArrayList<String> linje = list.get(1);
		ArrayList<String> stilling = list.get(2);
		ArrayList<String> trinn = list.get(3);
		return ok(detail.render("test", divInfo, linje, stilling, trinn));
	}

	public static Result login() {

		return ok(login.render("Log inn"));
	}

	public static Result addAdvertisement() {
		return ok(addAdvertisement.render("Legg til"));
	}

	public static Result angular() throws IOException {

		String test = getJson("sorlandsportalen.no/public/webservice/alle_annonser.php");
		return ok(angular.render(test, "Sorlandsportalen"));
	}
	
	public static Result uploadData() throws IOException, InterruptedException {	
		DynamicForm dynamicForm = form().bindFromRequest();
		String tittel = dynamicForm.get("tittel");
		String sted = dynamicForm.get("sted");
		String kontaktNavn = dynamicForm.get("kontaktNavn");
		String kontaktEmail = dynamicForm.get("kontaktEmail");
		String bedriftsNavn = dynamicForm.get("bedriftsNavn");
		String url = dynamicForm.get("url");
		String info = dynamicForm.get("info");
		String frist = dynamicForm.get("frist");
		String varighet = dynamicForm.get("varighet");
		
		String klasse1 = dynamicForm.get("1.klasse");
		String klasse2 = dynamicForm.get("2.klasse");
		String klasse3 = dynamicForm.get("3.klasse");
		String klasse4 = dynamicForm.get("4.klasse");
		String klasse5 = dynamicForm.get("5.klasse");
		
		String Sommerjobb = dynamicForm.get("Sommerjobb");
		String Deltid = dynamicForm.get("Deltid");
		String Fulltid = dynamicForm.get("Fulltid");
		
		String Mekatronikk = dynamicForm.get("Mekatronikk");
		String Data = dynamicForm.get("Data");
		String Fornybar = dynamicForm.get("Fornybar");
		String Byggdesign = dynamicForm.get("Byggdesign");
		String Elektronikk = dynamicForm.get("Elektronikk");
		String Indok = dynamicForm.get("Indok");
		
		ArrayList<String> trinn = new ArrayList<String>();
		ArrayList<String> linje = new ArrayList<String>();
		ArrayList<String> jobb = new ArrayList<String>();
		
		if(klasse1 != null){
			trinn.add("1");
		}
		if(klasse2 != null && !klasse2.isEmpty()){
			trinn.add("2");
		}
		if(klasse3 != null && !klasse3.isEmpty()){
			trinn.add("3");
		}
		if(klasse4 != null && !klasse4.isEmpty()){
			trinn.add("4");
		}
		if(klasse5 != null && !klasse5.isEmpty()){
			trinn.add("5");
		}
		if(Sommerjobb != null && !Sommerjobb.isEmpty()){
			jobb.add("2");
		}
		if(Deltid != null && !Deltid.isEmpty()){
			jobb.add("3");
		}
		if(Fulltid != null && !Fulltid.isEmpty()){
			jobb.add("1");
		}
		if(Mekatronikk != null){
			linje.add("4");
		}
		if(Data != null){
			linje.add("1");
		}
		if(Fornybar != null && !Fornybar.isEmpty()){
			linje.add("3");
		}
		if(Byggdesign != null && !Byggdesign.isEmpty()){
			linje.add("5");
		}
		if(Elektronikk != null && !Elektronikk.isEmpty()){
			linje.add("7");
		}
		if(Indok != null && !Indok.isEmpty()){
			linje.add("6");
		}
	
			String AnnonseUrl = "http://sorlandsportalen.no/public/webservice/insert_annonse.php?info="+info+"&tittel="+tittel+"&bedriftsNavn="+bedriftsNavn+"&kontaktEmail="+kontaktEmail+"&varighet="+varighet+"&frist="+frist+"&url="+url+"&sted="+sted+"&kontaktNavn="+kontaktNavn+"";			
			
			
			URL yahoo = new URL(AnnonseUrl);
	        URLConnection yc = yahoo.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                yc.getInputStream()));
	       
	        Thread.sleep(100);
	        in.close();
		

			String typeId = jobb.get(0);
			String trinnId = trinn.get(0);
			String linjeId = linje.get(0);
	        
	        int t = Math.max(trinn.size(), Math.max(linje.size(),jobb.size()));	 
			for (int i=0;i<t;i++){
				try {
					typeId=jobb.get(i);
				} catch (Exception e) {
					
				}
				
				try {
					trinnId=trinn.get(i);
				} catch (Exception e) {
					
				}
				
				try {
					linjeId=linje.get(i);
				} catch (Exception e) {
					
				}
				
				String KoblingUrl = "http://sorlandsportalen.no/public/webservice/insert_kobling.php?type="+typeId+"&trinn="+trinnId+"&linje="+linjeId+"";
				
				URL yahoo2 = new URL(KoblingUrl);
		        URLConnection yc2 = yahoo2.openConnection();
		        BufferedReader in2 = new BufferedReader(
		                                new InputStreamReader(
		                                yc2.getInputStream()));
		        Thread.sleep(100);
		        in2.close();
			}
	        
	        
		
		return redirect("/");
	}
	
	public static void runPHP(String link) throws IOException{
		 
	}

	public static String getJson(String link) {
		String str = "";
		try {
			URL url = new URL(link);
			URLConnection con = url.openConnection();
			Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
			Matcher m = p.matcher(con.getContentType());
			String charset = m.matches() ? m.group(1) : "ISO-8859-1";
			Reader r = new InputStreamReader(con.getInputStream(), charset);
			StringBuilder buf = new StringBuilder();
			while (true) {
				int ch = r.read();
				if (ch < 0)
					break;
				buf.append((char) ch);
			}
			str = buf.toString();
		} catch (IOException e) {
			System.out.println("Feil JSON-adresse!");
		}
		String json = "";
		int last = str.length();
		int count = 0;
		for (char ch : str.toCharArray()) {
			String s = String.valueOf(ch);
			count++;
			if (count > 21 && count < last) {
				json += s;
			}
		}
		return json;
	}

	public static String getJsonAll(String link) {
		String str = "";
		try {
			URL url = new URL(link);
			URLConnection con = url.openConnection();
			Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
			Matcher m = p.matcher(con.getContentType());
			String charset = m.matches() ? m.group(1) : "ISO-8859-1";
			Reader r = new InputStreamReader(con.getInputStream(), charset);
			StringBuilder buf = new StringBuilder();
			while (true) {
				int ch = r.read();
				if (ch < 0)
					break;
				buf.append((char) ch);
			}
			str = buf.toString();
		} catch (IOException e) {
			System.out.println("Feil JSON-adresse!");
		}
		return str;
	}

	public static String getAnnonseString() throws JSONException {
		ArrayList<String> divInfo = new ArrayList<String>();
		ArrayList<String> linje = new ArrayList<String>();
		ArrayList<String> trinn = new ArrayList<String>();
		ArrayList<String> stilling = new ArrayList<String>();
		ArrayList<String> jsonList = new ArrayList<String>();
		String json;
		String jsonFinal;

		String JSON_DATA = getJsonAll("http://sorlandsportalen.no/public/webservice/alle_annonser.php");
		JSONObject obj = new JSONObject(JSON_DATA);
		JSONArray geodata = obj.getJSONArray("posts");
		int n = geodata.length();
		int idNy = 0;
		int idGammel = 0;
		int teller = 0;

		for (int i = 0; i < n + 1; i++) {
			JSONObject person = null;
			try {
				person = geodata.getJSONObject(i);
			} catch (Exception e) {
				idNy = -1;
			}

			try {
				idNy = person.getInt("annonseId");
			} catch (Exception e) {

			}

			if (teller == 0 || idGammel == idNy) {
				idGammel = idNy;
				if (divInfo.isEmpty()) {
					divInfo.add(person.getString("annonseId"));
					divInfo.add(person.getString("tittel"));
					divInfo.add(person.getString("sted"));
					divInfo.add(person.getString("frist"));
					divInfo.add(person.getString("teller"));
					divInfo.add(person.getString("info"));
					divInfo.add(person.getString("bedriftsNavn"));
					divInfo.add(person.getString("varighet"));
					divInfo.add(person.getString("url"));
					divInfo.add(person.getString("kontaktNavn"));
					divInfo.add(person.getString("kontaktEmail"));
					divInfo.add(person.getString("prioritet"));
				}

				if (!linje.contains(person.getString("navn"))) {
					linje.add(person.getString("navn"));
				}
				if (!stilling.contains(person.getString("stilling"))) {
					stilling.add(person.getString("stilling"));
				}
				if (!trinn.contains(person.getString("trinn"))) {
					trinn.add(person.getString("trinn"));
				}
			} else {
				idGammel = idNy;
				json = "{\"annonseId\":\"" + divInfo.get(0) + "\", \"tittel\":\"" + divInfo.get(1)
						+ "\", \"sted\":\"" + divInfo.get(2) + "\", \"frist\":\"" + divInfo.get(3)
						+ "\",  \"info\":\"" + divInfo.get(5) + "\",";
				int t = 0;
				for (String item : linje) {
					t++;
					json += "  \"name" + t + "\":\"" + item + "\",";
				}
				for (String item : trinn) {
					t++;
					json += "  \"trinn" + t + "\":\"" + item + "\",";
				}
				for (int e = 0; e < stilling.size(); e++) {
					t++;
					if (e < stilling.size() - 1) {
						json += "  \"stilling" + t + "\":\"" + stilling.get(e) + "\",";
					} else {
						json += "  \"stilling" + t + "\":\"" + stilling.get(e) + "\"";
					}

				}

				json += "}";
				jsonList.add(json);
				divInfo.clear();
				linje.clear();
				trinn.clear();
				stilling.clear();
			}
			teller = 1;

		}

		jsonFinal = jsonList.toString();
		return jsonFinal;
	}

	public static ArrayList<ArrayList<String>> getAnnonse(int id) throws JSONException {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> divInfo = new ArrayList<String>();
		ArrayList<String> linje = new ArrayList<String>();
		ArrayList<String> trinn = new ArrayList<String>();
		ArrayList<String> stilling = new ArrayList<String>();

		String JSON_DATA = getJsonAll("http://sorlandsportalen.no/public/webservice/alle_annonser.php");
		JSONObject obj = new JSONObject(JSON_DATA);
		JSONArray geodata = obj.getJSONArray("posts");
		int n = geodata.length();
		for (int i = 0; i < n; ++i) {
			final JSONObject person = geodata.getJSONObject(i);
			if (person.getInt("annonseId") == id) {
				if (divInfo.isEmpty()) {
					divInfo.add(person.getString("annonseId"));
					divInfo.add(person.getString("tittel"));
					divInfo.add(person.getString("sted"));
					divInfo.add(person.getString("frist"));
					divInfo.add(person.getString("teller"));
					divInfo.add(person.getString("info"));
					divInfo.add(person.getString("bedriftsNavn"));
					divInfo.add(person.getString("varighet"));
					divInfo.add(person.getString("url"));
					divInfo.add(person.getString("kontaktNavn"));
					divInfo.add(person.getString("kontaktEmail"));
					divInfo.add(person.getString("prioritet"));
				}

				if (!linje.contains(person.getString("navn"))) {
					linje.add(person.getString("navn"));
				}
				if (!stilling.contains(person.getString("stilling"))) {
					stilling.add(person.getString("stilling"));
				}
				if (!trinn.contains(person.getString("trinn"))) {
					trinn.add(person.getString("trinn"));
				}

			}

		}

		list.add(divInfo);
		list.add(linje);
		list.add(stilling);
		list.add(trinn);
		return list;
	}

	public static String getAnnonseString2() throws JSONException {

		String json = "";
		String jsonFinal;
		ArrayList<Integer> idList = new ArrayList<Integer>();
		ArrayList<String> jsonList = new ArrayList<String>();
		String JSON_DATA = getJsonAll("http://sorlandsportalen.no/public/webservice/alle_annonser.php");
		JSONObject obj = new JSONObject(JSON_DATA);
		JSONArray geodata = obj.getJSONArray("posts");
		int n = geodata.length();
		int id = 0;

		for (int i = 0; i < n; i++) {
			JSONObject person = geodata.getJSONObject(i);
			id = person.getInt("annonseId");
			if (!idList.contains(id)) {
				idList.add(id);
			}

		}

		for (int idItem : idList) {
			ArrayList<ArrayList<String>> list = getAnnonse(idItem);
			json = "{\"annonseId\":\"" + list.get(0).get(0) + "\", \"tittel\":\""
					+ list.get(0).get(1) + "\", \"sted\":\"" + list.get(0).get(2)
					+ "\", \"frist\":\"" + list.get(0).get(3) + "\",  \"info\":\""
					+ list.get(0).get(5) + "\", \"bedriftsNavn\":\"" + list.get(0).get(6)
					+ "\", \"varighet\":\"" + list.get(0).get(7) + "\", \"url\":\""
					+ list.get(0).get(8) + "\", \"kontaktnavn\":\"" + list.get(0).get(9) + "\", \"kontaktEmail\":\"" + list.get(0).get(10) + "\",";
			int t = 0;
			for (String item : list.get(1)) {
				t++;
				json += "  \"name" + t + "\":\"" + item + "\",";
			}
			for (String item : list.get(3)) {
				t++;
				json += "  \"trinn" + t + "\":\"" + item + "\",";
			}
			for (int e = 0; e < list.get(2).size(); e++) {
				t++;
				if (e < list.get(2).size() - 1) {
					json += "  \"stilling" + t + "\":\"" + list.get(2).get(e) + "\",";
				} else {
					json += "  \"stilling" + t + "\":\"" + list.get(2).get(e) + "\"";
				}

			}

			json += "}";
			jsonList.add(json);

		}

		jsonFinal = jsonList.toString();

		return jsonFinal;
	}

}
