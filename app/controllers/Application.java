package controllers;

import static play.data.Form.form;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;

import play.data.DynamicForm;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render("Bindeleddet"));
	}

	public static Result json() {
		return ok(json.render("Your new application is ready."));
	}

	@Transactional
	public static Result detail() {

		DynamicForm dynamicForm = form().bindFromRequest();
		int id = Integer.parseInt(dynamicForm.get("id"));
		Databasen db = new Databasen();
		List<String> annonseList = db.select("select * from annonse where annonseId=" + id + "");
		return ok(detail.render("test", annonseList));
	}

	public static Result login() {
		try {

			String content = "This is the content to write into file";

			File file = new File("/home/thomas/Dokumenter/Bindeleddet/public/jSon/filename.txt");

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return ok(login.render("Log inn"));
	}

	public static String annonseJson() {
		String sql = "Select * from annonse";
		List<SqlRow> list = Ebean.createSqlQuery(sql).findList();
		String json = "";
		String tmp = "";
		String test = "";
		ArrayList<String> list1 = new ArrayList<>();
		for (Object i : list) {
			tmp = String.valueOf(i);
			list1.add(tmp);
		}

		int teller = 0;

		for (Object json1 : list) {
			tmp = String.valueOf(json1);
			int t = 0;
			int e = 0;
			int k = 0;
			int u = 0;
			int l = 0;
			int p = 0;
			int q = 0;
			int d = 0;
			int count = 0;
			int date = tmp.indexOf("frist=");
			for (char ch : tmp.toCharArray()) {
				String s = String.valueOf(ch);
				count++;
				if (t == 1) {
					test = test + "\"";
					t = 0;
				}
				if (s.equals("=")) {
					e = 1;
				}
				if (s.equals("{")) {
					t = 1;
				}
				if (s.equals("{")) {
					d = 1;
				}

				if (p != 1) {
					if (s.equals(",")) {
						k = 1;
					}
				}

				if (count == date + 7 || count == date + 17) {
					test = test + "\"";
				}

				if (q == 1) {
					test = test + ", \"";
					q = 0;
				} else if (d == 1 && teller > 0) {
					test = test + ", " + s;
					d = 0;
				}

				else if (p == 1) {
					if (s.equals(",")) {
						test = test + "\"";
						p = 0;
						q = 1;
					} else if (s.equals("}")) {
						test = test + "\"" + s;
						p = 0;
					} else {
						test = test + s;
					}

				} else if (l == 1) {
					if (isNumeric(s)) {
						test = test + s;
					} else {
						test = test + "\"" + s;
						p = 1;
					}
					l = 0;
				} else if (e == 1) {
					test = test + "\": ";
					e = 0;
					l = 1;
				} else if (u == 1) {
					test = test + "\"";
					u = 0;
				} else if (k == 1) {
					test = test + ", ";
					k = 0;
					u = 1;
				} else {
					test = test + s;
				}

			}
			teller++;
		}
		json = "[" + test + "]";
		return json;
	}

	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static Result addAdvertisement() {
		return ok(addAdvertisement.render("Legg til"));
	}

	@Transactional
	public static Result angular() throws IOException {
		String test = annonseJson1();
		return ok(angular.render(test, "Sorlandsportalen"));
	}

	@Transactional
	public static Result uploadData() {

		ArrayList<String> input = new ArrayList<>();
		ArrayList<String> inputValue = new ArrayList<>();

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

	public static String annonseJson1() {
		Databasen db = new Databasen();
		ArrayList<String> list = db.jsonList("select * from annonse");
		String jsonTmp = "";
		String json = "";
		for (int i = 0; i < list.size(); i += 15) {

			if (list.size() == i + 15) {
				jsonTmp = jsonTmp + "{\"annonseId\": " + list.get(i) + ", \"info\": \""
						+ list.get(i + 1) + "\", \"typeId\": " + list.get(i + 2)
						+ ",  \"varighet\": " + list.get(i + 3) + ",  \"linjeId\": "
						+ list.get(i + 4) + ",  \"trinnId\": " + list.get(i + 5) + ""
						+ ", \"url\": \"" + list.get(i + 6) + "\", \"kontaktNavn\": \""
						+ list.get(i + 7) + "\", \"kontaktEmail\": \"" + list.get(i + 8)
						+ "\", \"frist\": \"" + list.get(i + 9) + "\", \"teller\": \""
						+ list.get(i + 10) + "\"" + ", \"prioritet\": \"" + list.get(i + 11)
						+ "\", \"sted\": \"" + list.get(i + 12) + "\", \"bedriftsNavn\": \""
						+ list.get(i + 13) + "\", \"tittel\": \"" + list.get(i + 14) + "\"}";
			} else {
				jsonTmp = jsonTmp + "{\"annonseId\": " + list.get(i) + ", \"info\": \""
						+ list.get(i + 1) + "\", \"typeId\": " + list.get(i + 2)
						+ ",  \"varighet\": " + list.get(i + 3) + ",  \"linjeId\": "
						+ list.get(i + 4) + ",  \"trinnId\": " + list.get(i + 5) + ""
						+ ", \"url\": \"" + list.get(i + 6) + "\", \"kontaktNavn\": \""
						+ list.get(i + 7) + "\", \"kontaktEmail\": \"" + list.get(i + 8)
						+ "\", \"frist\": \"" + list.get(i + 9) + "\", \"teller\": \""
						+ list.get(i + 10) + "\"" + ", \"prioritet\": \"" + list.get(i + 11)
						+ "\", \"sted\": \"" + list.get(i + 12) + "\", \"bedriftsNavn\": \""
						+ list.get(i + 13) + "\", \"tittel\": \"" + list.get(i + 14) + "\"}, ";
			}

		}

		json = "[" + jsonTmp + "]";
		return json;

	}
}
