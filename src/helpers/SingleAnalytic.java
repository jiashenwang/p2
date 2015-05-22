package helpers;

import java.util.ArrayList;
import java.util.List;

public class SingleAnalytic {
	private String id;
	private String name;
	private String total;
	public List<String> spent;
	//boolean type;
	
	public SingleAnalytic(String id, String name, String total, List<String> spent){
		this.id = id;
		this.name = name;
		this.total = total;
		this.spent = new ArrayList<String>(spent);
	}
	public String getId(){
		return this.id;
	}public String getName(){
		return this.name;
	}public String getTotal(){
		return this.total;
	}public List<String> getSpent(){
		return this.spent;
	}
}
