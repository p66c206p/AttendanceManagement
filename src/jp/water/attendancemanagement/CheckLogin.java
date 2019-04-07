package jp.water.attendancemanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CheckLogin
 */
@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckLogin() {
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
		
		// requestのデータをUTF-8で受け取る
		request.setCharacterEncoding("UTF-8");
		
		String userName = request.getParameter("uname");
		String userPassword = request.getParameter("upass");
		
		String sql = "SELECT user_name FROM user_tb" +
				" WHERE user_name = '" + userName + "'" +
				" AND login_password = '" + userPassword + "'";
		
		String displayUserName = "";
		boolean login = false;
		
		// databaseのログイン情報
		String database = "jdbc:mysql://localhost/sample_db" +
				"?useUnicode=true&characterEncoding=UTF-8&serverTimezone=JST";
		String user = "sample_user";
		String password = "sample_pass";
		
		Connection connection = null;
		Statement statement = null;
		
		// 1接続処理
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(database, user, password);
			statement = connection.createStatement();
			
			ResultSet resultSet = statement.executeQuery(sql);
			
			while (resultSet.next()) {
				displayUserName = resultSet.getString("user_name");
				login = true;
			}
		} catch (Exception e) {
			login = false;
			log("接続失敗：" + e);
		}
		
		// 2切断処理
		try {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		} catch (Exception e) {
			log("切断失敗：" + e);
		}
		
		// sessionを作成する
		HttpSession session = request.getSession();
		session.setAttribute("login", login);
		
		session.setAttribute("displayUserName", displayUserName);
		
		if (login) {
			response.sendRedirect("OkLogin");
		} else {
			response.sendRedirect("login.html");
		}
	}

}
