package helpers;

import java.util.ArrayList;

public class SingleAnalytic {
	private String id;
	private String name;
	private String total;
	public ArrayList<Integer> spent;
	//boolean type;
	
	public SingleAnalytic(String id, String name, String total, ArrayList<Integer> spent){
		this.id = id;
		this.name = name;
		this.total = total;
		this.spent = new ArrayList<Integer>(spent);
	}
	public String getId(){
		return this.id;
	}public String getName(){
		return this.name;
	}public String getTotal(){
		return this.total;
	}public ArrayList<Integer> getSpent(){
		return this.spent;
	}
}
