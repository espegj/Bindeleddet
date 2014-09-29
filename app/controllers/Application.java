package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.List;

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
		return ok(index.render("Bindeleddet"));
	}

	public static Result login() {
		return ok(login.render("Log inn"));
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
		
		for(String i : input){
			if(dynamicForm.get(i) == null || dynamicForm.get(i) == ""){
				if(i=="prioritet"){
				inputValue.add("false");
				}
				else{
				inputValue.add("");
				}
			}
		
			else{
				if(i=="prioritet"){
					inputValue.add("true");
					}
					else{
				inputValue.add(dynamicForm.get(i));	
					}
			}
		}

		Databasen db = new Databasen();
		db.insert("annonse", inputValue.get(0), Integer.parseInt(inputValue.get(1)), Integer.parseInt(inputValue.get(2)),
				Integer.parseInt(inputValue.get(3)), Integer.parseInt(inputValue.get(4)), inputValue.get(5), inputValue.get(6), 
				inputValue.get(7), inputValue.get(8), inputValue.get(9), inputValue.get(10), inputValue.get(11), 
				inputValue.get(12));
		
		
		return ok(index.render("Bindeleddet"));
		
		    
	}
}
