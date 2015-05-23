
<%@page import="java.util.List" import="helpers.*"%>
<%=ProductsHelper.modifyProducts(request)%>
<form action="analytics">
    <%
    String dp1 = request.getParameter("dp1");
    dp1 = (dp1 != null)? dp1 : "customers";
    String dp2 = request.getParameter("dp2");
    dp2 = (dp2 != null) ? dp2 : "alphabetical";
    String dp3 = request.getParameter("dp3");
    dp3 = (dp3 != null) ? dp3 : "all";
    String action = request.getParameter("action");
    action = (action != null) ? action : "";
    
    String page_h= request.getParameter("page_h");
    page_h = (page_h != null) ? page_h : "0";
    String page_v= request.getParameter("page_v");
    page_v = (page_v != null) ? page_v : "0";
    
	List<CategoryWithCount> categories = CategoriesHelper
			.listCategories();
    %>
    
    <select name="dp1" <%if(!action.equals("run_query") && !action.equals("")){%>disabled<%}%>>
		<option value="customers" <%if(dp1.equals("customers")){%>selected <%}%>>Customers</option>
		<option value="states" <%if(dp1.equals("states")){%>selected <%}%>>States</option>
	</select>
    <select name="dp2" <%if(!action.equals("run_query") && !action.equals("")){%>disabled<%}%>>
		<option value="alphabetical" <%if(dp2.equals("alphabetical")){%>selected <%}%>>Alphabetical</option>
		<option value="topk" <%if(dp2.equals("topk")){%>selected <%}%>>Top K</option>
	</select>
    <select name="dp3" <%if(!action.equals("run_query") && !action.equals("")){%>disabled<%}%>>
		<option value="all" <%if(dp3.equals("all")){%>selected <%}%>>All</option>
		<%for(int i=0; i<categories.size(); i++){%>
			<option value="<%=categories.get(i).getId()+""%>" 
			<%if(dp3.equals(categories.get(i).getId()+"")){%>selected <%}%>>
			<%=categories.get(i).getName()%></option>
		<%} %>
	</select>
    <input type="text" name="action" value="run_query" style="display: none" />
    <input type="text" name="dp1" id="dp1" value="<%=dp1%>" style="display: none" /> 
    <input type="text" name="dp2" id="dp2" value="<%=dp2%>" style="display: none" /> 
    <input type="text" name="dp3" id="dp3" value="<%=dp3%>" style="display: none" />
    <input type="text" name="page_v" id="page_v" value="0" style="display: none" />
    <input type="text" name="page_h" id="page_h" value="0" style="display: none" /> 

	<% 
	if(!action.equals("")){%>
		<%
		AnalyticsHelper.init();
		List<ProductForAnalytic> products = AnalyticsHelper.ShowProducts(dp1, dp2, dp3, page_h);
		List<SingleAnalytic> rows = AnalyticsHelper.ExeQuery(dp1, dp2, dp3, page_v);
		%>
		<table border="1" style="width:100%">
		<thead>
			<tr>
		    	<th></th>
		    	<%for(int i=0; i<products.size(); i++){%>
		    		<th><%=products.get(i).getName()%><br>($
		    		<%
		    			if(products.get(i).getTotal()==null){
		    				%><%=0%><%
		    			}else{
		    				%><%=products.get(i).getTotal()%><%
		    			}	
		    		%>)
		    		</th>
		    	<%}%>
		  	</tr>
		</thead>
		<tbody>
	   		<%for(int i=0; i<rows.size(); i++){%>
		  		<tr>
		  			<td><%=rows.get(i).getName()%><br>($<%=rows.get(i).getTotal()%>)</td>
		  			<%for(int j=0; j<rows.get(i).getSpent().size(); j++){%>
		  				<td>$<%=rows.get(i).getSpent().get(j)%></td>
		  			<%}%>
		  		</tr>
		  	<%}%>
		</tbody>
		</table>
		
	<%}%>
	<br><br>
    <input type="submit" value="Run">
</form>

<form>
	<input type="text" name="action" value="next_v" style="display: none" />
	<input type="text" name="dp1" id="dp1" value="<%=dp1%>" style="display: none" /> 
    <input type="text" name="dp2" id="dp2" value="<%=dp2%>" style="display: none" /> 
    <input type="text" name="dp3" id="dp3" value="<%=dp3%>" style="display: none" /> 
    <input type="text" name="page_v" id="page_v" value="<%=Integer.parseInt(page_v)+1%>" style="display: none" />
    <input type="text" name="page_h" id="page_h" value="<%=page_h%>" style="display: none" />
	<%if(AnalyticsHelper.MoreV && action!=""){%><input type="submit" value="next-v"><%}%>
</form>
<form>
	<input type="text" name="action" value="next_h" style="display: none" />
    <input type="text" name="dp1" id="dp1" value="<%=dp1%>" style="display: none" /> 
    <input type="text" name="dp2" id="dp2" value="<%=dp2%>" style="display: none" /> 
    <input type="text" name="dp3" id="dp3" value="<%=dp3%>" style="display: none" /> 
    <input type="text" name="page_v" id="page_v" value="<%=page_v%>" style="display: none" />
    <input type="text" name="page_h" id="page_h" value="<%=Integer.parseInt(page_h)+1%>" style="display: none" />
	<%if(AnalyticsHelper.MoreH && action!=""){%><input type="submit" value="next-h"><%}%>
</form>
