package controllers;

import static play.data.Form.form;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import akka.actor.dsl.Inbox.Select;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.SqlUpdate;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.db.ebean.Transactional;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import models.linje;
import views.html.*;

public class Application extends Controller {

	public static Result index() {
		String sql = "Select * from annonse";
		List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).findList();
		String t = toJson(sqlRows);
		return ok(index.render("Bindeleddet"));
	}

	public static Result login() {
		return ok(login.render("Log inn"));
	}

	public static String toJson(List list) {
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
			int count=0;
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

				if(count==date+7 || count ==date+17){
					test=test+"\"";	
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

	public static void newFile() {
		String sql = "Select * from annonse";
		List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).findList();
		try {
			String content = sqlRows.toString();
			toJson(sqlRows);
			File file = new File(
					"/Volumes/Macintosh HD/Documents/Prosjekt/Bindeleddet/public/jSon/filename.txt");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Result addAdvertisement() {
		return ok(addAdvertisement.render("Legg til"));
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
}
