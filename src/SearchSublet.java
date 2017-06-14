

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.DriverManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SearchSublet
 */
@WebServlet("/SearchSublet")
public class SearchSublet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DBconnect d;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchSublet() {
        super();
        // TODO Auto-generated constructor stub
        d = new DBconnect("myNewDB");
    }
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		HttpSession session = request.getSession();
		
		String act = request.getParameter("act");
		String msg = "";
		String nextURL = "";
		//String custId = request.getParameter("lName");
		switch (act) {
		
			case "lSearch": 
				msg = searchLastName(request.getParameter("lName"), request);
				if(msg.contains("The name")){
					nextURL = "/fix.jsp";
				}
				else{
					nextURL = "/results.jsp";
				}
				break;
			case "fSearch":
				String fullName = request.getParameter("fullName");
				
				request.setAttribute("fullName", fullName);
				session.setAttribute("f", fullName);
				msg = setAtts(fullName, request, true);
				if(msg.contains("The name")){
					nextURL = "/fix.jsp";
				}
				else{
					nextURL = "/edit.jsp";
				}
				break;
			case "add":
				msg = addToDB(request);
				
				nextURL = "/results.jsp";
				break;
			case "edit":
				msg = doUpdate(request, session);
				nextURL = "/results.jsp";
				break;
		}
		
		
		request.setAttribute("message", msg);
		System.out.print(nextURL + "\n");
		getServletContext().getRequestDispatcher(nextURL).forward(request,response);
		
		
	}
	
	private String[] cols = {"FirstName", "Title", "Company", "Position", "StreetAddress", 
			"ZipCode", "State", "City", "EmailAddress", "LastName" };
	
	private String addToDB(HttpServletRequest request) {
		// TODO Auto-generated method stub
		ArrayList<String> upd = new ArrayList<String>();
		ArrayList<String> vals = new ArrayList<String>();
		for(String s : cols){
			upd.add(request.getParameter(s));
			vals.add(s);
		}
		upd.add(upd.get(0) + " " + upd.get(9));
		vals.add("FullName");
		d.addRecord("customers", vals, upd);
		return "Updated database";
	}


	
	private String doUpdate(HttpServletRequest r, HttpSession se) {
		String full = (String) se.getAttribute("f");
		for (int i = 0; i < cols.length; i++)
			d.updateRecord("customers", cols[i], "fullName", full, r.getParameter(cols[i]));
		String full2 = r.getParameter("FirstName") + " " + r.getParameter("LastName");
		d.updateRecord("customers", "FullName", "fullName", full, full2);
		se.setAttribute("f", full2);
		return "UPDATED!";
	}

	private String searchLastName(String lName, HttpServletRequest r) {
		
		ArrayList<ArrayList<String>> results = d.getData("customers", "LastName", r.getParameter("lName"), "FullName", "Title", "Company", "Position", "StreetAddress", 
				"ZipCode", "State", "City", "EmailAddress");
		String msg = "";//Customer.fetchCustomer(custId, new Scanner(System.in));
		if(results.size() > 0){
			for(int i = 0; i<results.size();i++){
				msg += results.get(i).get(1) + " " + results.get(i).get(0) + "<br>" + results.get(i).get(4) + "<br>" + results.get(i).get(7) + ", " + results.get(i).get(6)
						+ " " + results.get(i).get(5) + "<br>" + results.get(i).get(8) + "<br>" + results.get(i).get(3) + " at " + results.get(i).get(2) + "<br><br>";
			}
		}
		else{
			msg = wrongName(r, r.getParameter("lName"));
		}
		return msg;
		
		
	}
	
	
	private String setAtts(String fullName, HttpServletRequest r, boolean edit) {
		
			ArrayList<ArrayList<String>> results = d.getData("customers", "FullName", r.getParameter("fullName"), "FirstName",
					"LastName", "Title", "Company", "Position", "StreetAddress", 
					"ZipCode", "State", "City", "EmailAddress");
			ArrayList<String> x;
			if (results.size() > 0) {
				x = results.get(0);
				r.setAttribute("title", x.get(2));
				r.setAttribute("firstName", x.get(0));
				r.setAttribute("lastName", x.get(1));
				r.setAttribute("street", x.get(5));
				r.setAttribute("city", x.get(8));
				r.setAttribute("state", x.get(7));
				r.setAttribute("zip", x.get(6));
				r.setAttribute("email", x.get(9));
				r.setAttribute("position", x.get(4));
				r.setAttribute("company", x.get(3));
				return "";
			}
			else{
				return wrongName(r, r.getParameter("fullName"));
			}
			
		
	}

	private String wrongName(HttpServletRequest r, String name) {
		// TODO results.jsp needs to be done
		return "The name you entered does not exist in the database.  Either add " + name + " to the database or do a new search.";
	}
	
	
	/*"Customer Number: 9
	Mr. Robert Dupree
	4101 Pickens Way
	Longview, TX 75601
	RobertODupree@einrot.com
	Mapping technician at Irving's Sporting Goods"*/
	
}
