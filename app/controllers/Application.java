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

import play.data.Form;
import play.db.ebean.Transactional;
import play.mvc.*;
import models.linje;
import views.html.*;

public class Application extends Controller {

	@Transactional
	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}

	public static Result login() {
		return ok(login.render("Log inn"));
	}
	
	public static Result addAdvertisement() {
		return ok(addAdvertisement.render("Log inn"));
	}
	

	
	

}
