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
    public static List<ProductForAnalytic> ShowProducts(String dp3, String page_h){
        // retrieve products
        	try {
                String query2 = "";
                if(dp3.equals("all")){
                	query2 = "CREATE TEMP TABLE horizontal_tmp AS SELECT products.id AS pid, products.name AS pname FROM products GROUP BY name, products.id LIMIT "+(Integer.parseInt(page_h)+1)*10+" OFFSET "+Integer.parseInt(page_h)*10+";";
                	
                	stmt.execute(query2);
                 	conn.commit();
                	query2 = "SELECT horizontal_tmp.pid, horizontal_tmp.pname, SUM(sales.price) FROM horizontal_tmp LEFT OUTER JOIN sales ON horizontal_tmp.pid = sales.pid GROUP BY horizontal_tmp.pname,horizontal_tmp.pid;";
                	rs = stmt.executeQuery(query2);
                	conn.commit();
                }else{
                	//TODO
                }
                if(rs!=null){
                	products.clear();
                    while (rs.next()) {
                    	products.add(new ProductForAnalytic(rs.getString(1), rs.getString(2), Integer.parseInt(rs.getString(3)))); 
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
            	query1 = "CREATE TEMP TABLE vertical_tmp AS SELECT users.id, users.name AS username FROM users GROUP BY name, users.id ORDER BY name ASC LIMIT "+ (Integer.parseInt(page_v)+1)*20 +" OFFSET "+Integer.parseInt(page_v)*20+";";
            	stmt.execute(query1);
             	conn.commit();
            	query1 = "SELECT vertical_tmp.id,vertical_tmp.username,SUM(sales.price) FROM vertical_tmp LEFT OUTER JOIN sales ON vertical_tmp.id = sales.uid GROUP BY vertical_tmp.username, vertical_tmp.id ORDER BY vertical_tmp.username;";
            	rs = stmt.executeQuery(query1);
            	conn.commit();
            }else if(dp1.equals("customers") && dp2.equals("topk")){
            	//TODO
            }else if(dp1.equals("states") && dp2.equals("alphabetical")){
            	//TODO
            }else{
            	//TODO
            }
            if(rs!=null){
            	rows.clear();
                while (rs.next()) {
                	List<Integer> mid = new ArrayList<Integer>();
                	for(int i=0; i<products.size(); i++){
                		mid.add(0);
                	}
                	rows.add(new SingleAnalytic(rs.getString(1), rs.getString(2), rs.getString(3), mid)); 
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
