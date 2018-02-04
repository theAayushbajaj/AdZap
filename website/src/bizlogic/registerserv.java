package bizlogic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class registerserv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Connection con;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	
	String driver,loc,dbu,dbp;
	
	
	
    public registerserv() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		ServletContext contxt = config.getServletContext();
		
		driver = contxt.getInitParameter("dbdriver");
		loc = contxt.getInitParameter("dbloc");
		dbu = contxt.getInitParameter("dbuname");
		dbp = contxt.getInitParameter("dbpwd");
		
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String un = request.getParameter("uname");
		String ue = request.getParameter("uemail");
		String uph = request.getParameter("uphone");
		String up = request.getParameter("upwd");
		
		try {
			Class.forName(driver);
		}catch(ClassNotFoundException e1)
		{
			e1.printStackTrace();
		}
		
		
		try {
			con = DriverManager.getConnection(loc, dbu, dbp);
			con.createStatement();
			stmt.executeUpdate("Insert into tbllogreg values (un,ue,uph,up)");
			
			
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
