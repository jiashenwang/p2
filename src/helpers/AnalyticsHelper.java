package helpers;

import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AnalyticsHelper {
	static private  Connection conn = null;
    static private Statement stmt = null;
    static private ResultSet rs = null;
    static private List<ProductForAnalytic> products = new ArrayList<ProductForAnalytic>();
    static private List<SingleAnalytic> rows = new  ArrayList<SingleAnalytic>();
    static public boolean MoreH = true;
    static public boolean MoreV = true;
    public static boolean init(){
        try {
            conn = HelperUtils.connect();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.err.println("Internal Server Error. This shouldn't happen.");
            return false;
        }
        return true;
    }
    public static List<ProductForAnalytic> ShowProducts(String dp1, String dp2, String dp3, String page_h){
        // retrieve products
        	try {
                String query2 = "";
                if(dp3.equals("all") && dp2.equals("alphabetical")){
                	query2 = "CREATE TEMP TABLE horizontal_tmp AS SELECT products.id, products.name FROM products GROUP BY name, products.id  ORDER BY name ASC LIMIT "+ (Integer.parseInt(Data.LIMIT_H)+1) +" OFFSET "+Integer.parseInt(page_h)*10+";";
                	stmt.execute(query2);
                 	conn.commit(); 
                	query2 = "SELECT horizontal_tmp.id, horizontal_tmp.name, SUM(sales.price*sales.quantity) FROM horizontal_tmp LEFT OUTER JOIN sales ON horizontal_tmp.id = sales.pid GROUP BY horizontal_tmp.name,horizontal_tmp.id ORDER BY horizontal_tmp.name;";
                	rs = stmt.executeQuery(query2);
                	conn.commit();
                }else if(dp3.equals("all") && dp2.equals("topk")){
                	query2 = "";
                	stmt.execute(query2);
                 	conn.commit(); 
                	query2 = "";
                	rs = stmt.executeQuery(query2);
                	conn.commit();
                }else if(!dp3.equals("all") && dp2.equals("alphabetical")){
                	query2 = "";
                	stmt.execute(query2);
                 	conn.commit(); 
                	query2 = "";
                	rs = stmt.executeQuery(query2);
                	conn.commit();
                }else{
                	query2 = "";
                	stmt.execute(query2);
                 	conn.commit(); 
                	query2 = "";
                	rs = stmt.executeQuery(query2);
                	conn.commit();
                }
                if(rs!=null){
                	int counter = 0;
                	products.clear();
                    while (rs.next()) {
                    	counter++;
                        if(counter<=Integer.parseInt(Data.LIMIT_H)){
                        	MoreH = false;
                        	String temp = "0";
                        	if(rs.getString(3) != null){
                        		temp = rs.getString(3);
                        	}
                        	products.add(new ProductForAnalytic(rs.getString(1), rs.getString(2), temp)); 
                        }else{
                        	MoreH = true;
                        }
                    }
                }
                return products;

			} catch (SQLException e) {
				e.printStackTrace();
				return new ArrayList<ProductForAnalytic>();
			}

    }
    
	public static List<SingleAnalytic> ExeQuery(String dp1, String dp2, String dp3, String page_v) {
		
    	try {
            String query1 = "";
            if(dp1.equals("customers") && dp2.equals("alphabetical")){
            	query1 = "CREATE TEMP TABLE vertical_tmp AS SELECT users.id, users.name FROM users GROUP BY name, users.id ORDER BY name ASC LIMIT "+ (Integer.parseInt(Data.LIMIT_V)+1) +" OFFSET "+Integer.parseInt(page_v)*20+";";
            	stmt.execute(query1);
             	conn.commit();
            	query1 = "SELECT vertical_tmp.id,vertical_tmp.name,SUM(sales.price*sales.quantity) FROM vertical_tmp LEFT OUTER JOIN sales ON vertical_tmp.id = sales.uid GROUP BY vertical_tmp.name, vertical_tmp.id ORDER BY vertical_tmp.name;";
            	rs = stmt.executeQuery(query1);
            	conn.commit();
            }else if(dp1.equals("customers") && dp2.equals("topk")){
            	query1 = "";
            	stmt.execute(query1);
             	conn.commit();
            	query1 = "";
            	rs = stmt.executeQuery(query1);
            	conn.commit();
            }else if(dp1.equals("states") && dp2.equals("alphabetical")){
            	query1 = "";
            	stmt.execute(query1);
             	conn.commit();
            	query1 = "";
            	rs = stmt.executeQuery(query1);
            	conn.commit();
            }else{
            	query1 = "";
            	stmt.execute(query1);
             	conn.commit();
            	query1 = "";
            	rs = stmt.executeQuery(query1);
            	conn.commit();
            }
            if(rs!=null){
            	rows.clear();
                int counter = 0;
                while (rs.next()) {
                	counter++;
                	List<String> mid = new ArrayList<String>();
                	if(counter <= Integer.parseInt(Data.LIMIT_V)){
                    	for(int i=0; i<products.size(); i++){
                    		query1 = "SELECT sum(sales.price*sales.quantity) AS totalprice FROM sales,users,products WHERE users.id = "+rs.getString(1) +" AND products.id = "+products.get(i).getId()+" AND sales.uid = users.id AND sales.pid = products.id";
                    	    ResultSet rs2 = null;
                    	    Statement stmt2 = conn.createStatement();
                    		rs2 = stmt2.executeQuery(query1);
                        	conn.commit();
                        	while(rs2.next()){
                        		String temp = "0";
                        		if(rs2.getString(1)!=null){
                        			temp = rs2.getString(1);
                        		}
                        		mid.add(temp);
                        	} 
                        	stmt2.close();
                    	}
                    	String temp = "0";
                    	if(rs.getString(3) != null){
                    		temp = rs.getString(3);
                    	}
                    	rows.add(new SingleAnalytic(rs.getString(1), rs.getString(2), temp, mid)); 
                    	MoreV = false;
                	}else{ 
                		MoreV = true;
                	}
                }
            }
            return rows;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<SingleAnalytic>();
		}
	}

	public static void CloseConnection(){
        try {
        	if(stmt!=null && conn!=null){
	            stmt.close();
	            conn.close();
	            products.clear();
	            rows.clear();
        	}
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
}
