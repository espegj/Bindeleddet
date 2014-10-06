package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;

public class Application extends Controller {

	@Transactional
	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}
	
	public static Result json() {
		return ok(json.render("Your new application is ready."));
	}

	
	public static Result detail() {
		
		return ok(detail.render(""));
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
	
	public static Result addAdvertisement() {
		return ok(addAdvertisement.render("Log inn"));
	}
	
	@Transactional
	public static Result angular() throws IOException {
		String sql = "Select * from annonse"; 
		List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).findList();
		String test = "TEST[{\"annonseid\":7, \"info\":\"info\", \"typeid\":1, \"varighet\":2, \"linjeid\":1, \"trinnid\":1, \"url\":\"eyfe\", \"kontaktnavn\":\"Petter\", \"kontaktemail\":\"mail\", \"frist\":\"2014-09-10\", \"teller\":0, \"prioritet\":\"true\", \"sted\":\"grimstad\", \"bedriftsnavn\":\"Feier as\", \"tittel\":\"Feier\"}]";
		
		String fileName = "/home/thomas/Dokumenter/Bindeleddet/public/jSon/phones.json";
		
		    BufferedReader br = new BufferedReader(new FileReader(fileName));
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();

		        while (line != null) {
		            sb.append(line);
		            sb.append("\n");
		            line = br.readLine();
		        }
		        test = sb.toString();
		        StringBuilder contentInnerHtml = new StringBuilder();
		        contentInnerHtml.append(test);
		    } finally {
		        br.close();
		    }
		    return ok(angular.render(test));
		}
		
	
	
	

}
