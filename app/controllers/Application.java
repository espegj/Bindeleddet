package controllers;

import static play.data.Form.form;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import play.data.DynamicForm;


public class Application extends Controller {

	public static Result index() {
		String test = getJson("sorlandsportalen.no/public/webservice/test.php");
		return ok(angular.render(test, "Sorlandsportalen"));
	}
	
	public static Result detail() {
		
		List<String> annonseList = null;

		return ok(detail.render("test", annonseList));
	}
	
	public static Result login() {
	
		return ok(login.render("Log inn"));
	}

	public static Result addAdvertisement() {
		return ok(addAdvertisement.render("Legg til"));
	}

	public static Result angular() throws IOException {
		String test = getJson("sorlandsportalen.no/public/webservice/test.php");
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

	public static String getJson(String link){

		String str="";
		try {
			URL url = new URL(link);
			URLConnection con = url.openConnection();
			Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
			Matcher m = p.matcher(con.getContentType());
			/* If Content-Type doesn't match this pre-conception, choose default and 
			 * hope for the best. */
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String json="";
		int last = str.length();
		int count = 0;
		for (char ch : str.toCharArray()) {
			String s = String.valueOf(ch);
			count++;
			if(count>21 && count<last){
				json+=s;
			}
		}
		return json;
	}
}
