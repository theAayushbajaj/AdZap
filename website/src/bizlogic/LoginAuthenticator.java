package bizlogic;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet("/LoginAuthenticator")
public class LoginAuthenticator extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Connection con;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	
	String driver,loc,dbu,dbp;
	
	int nattempts,attempts;
	int straffic; 
	
	
    public LoginAuthenticator() {
        super();
    
    }

	
	public void init(ServletConfig config) throws ServletException {
		
		ServletContext contxt = config.getServletContext();
		
		driver = contxt.getInitParameter("dbdriver");
		loc = contxt.getInitParameter("dbloc");
		dbu = contxt.getInitParameter("dbuname");
		dbp = contxt.getInitParameter("dbpwd");
		
		nattempts = Integer.parseInt(config.getInitParameter("noofattempts"));
		attempts = nattempts;
		
		
		straffic = Integer.parseInt(contxt.getInitParameter("sitetraffic"));
		
		
		
	}

	
	

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();
		
		
		String un = request.getParameter("txtuname");
		String pwd = request.getParameter("txtupwd");
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			
			con = DriverManager.getConnection(loc,dbu,dbp);
			pstmt = con.prepareStatement("Select * from tbllogreg where UName = ? and UPwd = ?");
			
			pstmt.setString(1, un);
			pstmt.setString(2, pwd);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				attempts = nattempts;
				
				HttpSession tokenID = request.getSession();
				System.out.println(tokenID.getId());
				
				tokenID.setAttribute("unkey", un);
				tokenID.setAttribute("pkey", pwd);
				
//				out.println("<a href=profserv>Profile</a>");
//				out.println("Hellllo");
//				String url = "file:///home/mb11797/Desktop/hey.html";
//				File htmlFile = new File(url);
//				Desktop.getDesktop().browse(htmlFile.toURI());
				RequestDispatcher redirect = request.getRequestDispatcher("hey.html");
				redirect.include(request, response);
				
			}
			else
			{
				attempts--;
				
//				out.println("<body bgcolor=#B210FF>");
//				out.println("<center><font color=Red>Incorrect Username and/or Password</font></center>");
//				out.println("<br/><br/>");
//				out.println("<center");
//				out.println("Login : Site average traffic is "+ straffic +"<br/>");
////				out.println("You are left with "+ attempts +" attempts"+"<br/>");
//				out.println("</center>");
//				out.println("<br/><br/>");
//				out.println("</body>");
				
				RequestDispatcher redirect1 = request.getRequestDispatcher("errr.html");
				redirect1.include(request, response);
			
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request,response);
	}

}
