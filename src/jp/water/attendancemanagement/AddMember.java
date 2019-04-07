package jp.water.attendancemanagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddMember
 */
@WebServlet("/AddMember")
public class AddMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMember() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		String userName = request.getParameter("uname");
		String userPassword = request.getParameter("upass");
		
		String sql = "INSERT INTO user_tb" +
				"(user_name,login_password)" +
				"VALUES" +
				"('" + userName + "','" + userPassword + "')";
		
		// databaseのログイン情報
		String database = "jdbc:mysql://localhost/sample_db" +
				"?useUnicode=true&characterEncoding=UTF-8&serverTimezone=JST";
		String user = "sample_user";
		String password = "sample_pass";
		
		Connection connection = null;
		Statement statement = null;
		String outMessage;
		
		// 1接続処理
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(database, user, password);
			statement = connection.createStatement();
			
			statement.executeUpdate(sql);
			outMessage = "■新規ユーザー：" + userName + "を登録しました。";
		} catch (Exception e) {
			outMessage = "■登録に失敗しました。";
		}
		
		// 2切断処理
		try {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		} catch (Exception e) {
			outMessage = "■データベースの切断に失敗しました。";
		}
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("<hr>");
		out.println("<a href=\"login.html\">【戻る】</a>");
		out.println("<hr>");
		out.println(outMessage);		
		out.println("</body></html>");
		out.close();
	}

}
