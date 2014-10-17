package controllers;

import static play.data.Form.form;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import play.data.DynamicForm;

public class Application extends Controller {

	public static Result index() throws JSONException {
		String test = getJson("http://sorlandsportalen.no/public/webservice/alle_annonser.php");
		return ok(angular.render(test, "Sorlandsportalen"));
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

	@Transactional
	public static Result uploadData() {

		ArrayList<String> input = new ArrayList<String>();
		ArrayList<String> inputValue = new ArrayList<String>();

		DynamicForm dynamicForm = form().bindFromRequest();
		input.add("info");
		input.add("typeId");
		input.add("varighet");
		input.add("linjeId");
		input.add("trinnId");
		input.add("url");
		input.add("kontaktNavn");
		input.add("kontaktEmail");
		input.add("frist");
		input.add("prioritet");
		input.add("sted");
		input.add("bedriftsNavn");
		input.add("tittel");

		for (String i : input) {
			if (dynamicForm.get(i) == null || dynamicForm.get(i) == "") {
				if (i == "prioritet") {
					inputValue.add("false");
				} else {
					inputValue.add("");
				}
			}

			else {
				if (i == "prioritet") {
					inputValue.add("true");
				} else {
					inputValue.add(dynamicForm.get(i));
				}
			}
		}

		Databasen db = new Databasen();
		db.insert("annonse", inputValue.get(0), Integer.parseInt(inputValue.get(1)),
				Integer.parseInt(inputValue.get(2)), Integer.parseInt(inputValue.get(3)),
				Integer.parseInt(inputValue.get(4)), inputValue.get(5), inputValue.get(6),
				inputValue.get(7), inputValue.get(8), inputValue.get(9), inputValue.get(10),
				inputValue.get(11), inputValue.get(12));

		return ok(index.render("Bindeleddet"));

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
}
