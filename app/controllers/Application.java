package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import play.db.ebean.Transactional;

import play.mvc.*;
import views.html.*;

public class Application extends Controller {

	@Transactional
    public static Result index() { 
		
//		RawSql rawSql = RawSqlBuilder.parse("SELECT * from linje").create();

		
		
		String sql = "select count(*) as count from linje";  
        SqlRow row =   
            Ebean.createSqlQuery(sql)  
            .findUnique();  
          
        Integer i = row.getInteger("count");  
          
        System.out.println(i); 
			
 
		
		
        return ok(index.render("Your new application is ready."));
    }

}
