package models;  
  
import java.sql.Timestamp;  
  
import javax.persistence.Entity;  
import javax.persistence.Id;  
import javax.persistence.Table;  
import javax.persistence.Version;  
 
@Entity  
@Table(name="linje")  
public class linje {  
      
    @Id  
    Integer linjeId;  
      
    String name;  
      
      
    public Integer getId() {  
        return linjeId;  
    }  
  
    public void setId(Integer id) {  
        this.linjeId = id;  
    }  
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }   
  
}