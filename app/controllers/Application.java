package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.SqlUpdate;

import play.*;
import play.db.ebean.Transactional;
import play.mvc.*;
import scala.Int;
import views.html.*;

public class Application extends Controller {

	@Transactional
    public static Result index() { 
	
//        SqlUpdate insert = Ebean.createSqlUpdate("INSERT INTO db values(2)");
//        insert.execute(); 
//        
//        String sql = "select count(*) as count from db";  
//        SqlRow row =   
//            Ebean.createSqlQuery(sql)  
//            .findUnique();  
//          
//        Integer i = row.getInteger("count");  
//          
//        System.out.println(i);  
       
		System.out.println("test");
 
		
		
        return ok(index.render("Your new application is ready."));
    }

}
