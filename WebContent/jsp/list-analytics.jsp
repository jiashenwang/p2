
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
    action = (action != null) ? action : "run_query";
    
	List<CategoryWithCount> categories = CategoriesHelper
			.listCategories();
	System.out.println(categories.size());
    %>
    
    <select name="dp1" <%if(!action.equals("run_query")){%>disabled<%}%>>
		<option value="customers" <%if(dp1.equals("customers")){%>selected <%}%>>Customers</option>
		<option value="states" <%if(dp1.equals("states")){%>selected <%}%>>States</option>
	</select>
    <select name="dp2" <%if(!action.equals("run_query")){%>disabled<%}%>>
		<option value="alphabetical" <%if(dp2.equals("alphabetical")){%>selected <%}%>>Alphabetical</option>
		<option value="topk" <%if(dp2.equals("topk")){%>selected <%}%>>Top K</option>
	</select>
    <select name="dp3" <%if(!action.equals("run_query")){%>disabled<%}%>>
		<option value="all" <%if(dp3.equals("all")){%>selected <%}%>>All</option>
		<option value="temp1" <%if(dp3.equals("temp1")){%>selected <%}%>>temp1</option>
		<%for(int i=0; i<categories.size(); i++){
			System.out.println(categories.get(i).getId()+" - "+dp3);
			%>
			<option value="<%=categories.get(i).getId()+""%>" 
			<%if(dp3.equals(categories.get(i).getId()+"")){%>selected <%}%>>
			<%=categories.get(i).getName()%></option>
		<%} %>
	</select>
    <input type="text" name="action" value="run_query" style="display: none" />
    <input type="text" name="dp1" id="dp1" value="<%=dp1%>" style="display: none" /> 
    <input type="text" name="dp2" id="dp2" value="<%=dp2%>" style="display: none" /> 
    <input type="text" name="dp3" id="dp3" value="<%=dp3%>" style="display: none" /> 

	<br><br>
    <input type="submit" value="Run">
</form>

<form>
	<input type="text" name="action" value="next_v" style="display: none" />
	<input type="text" name="dp1" id="dp1" value="<%=dp1%>" style="display: none" /> 
    <input type="text" name="dp2" id="dp2" value="<%=dp2%>" style="display: none" /> 
    <input type="text" name="dp3" id="dp3" value="<%=dp3%>" style="display: none" /> 
	<input type="submit" value="next-v">
</form>
<form>
	<input type="text" name="action" value="next_h" style="display: none" />
    <input type="text" name="dp1" id="dp1" value="<%=dp1%>" style="display: none" /> 
    <input type="text" name="dp2" id="dp2" value="<%=dp2%>" style="display: none" /> 
    <input type="text" name="dp3" id="dp3" value="<%=dp3%>" style="display: none" /> 
	<input type="submit" value="next-h">
</form>
