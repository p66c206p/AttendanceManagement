package jp.water.attendancemanagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddClockIn
 */
@WebServlet("/AddClockIn")
public class AddClockIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddClockIn() {
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
		HttpSession session = request.getSession();
		
		boolean login = false;
		if (session.getAttribute("login") != null) {
			login = (boolean)session.getAttribute("login");
		}
		
		String displayUserName = (String)session.getAttribute("displayUserName");
		
        ZonedDateTime nowZonedDt = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
        String strNowTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm").format(nowZonedDt);
		String timeClockIn = strNowTime;
		
		response.setContentType("text/html; charset=UTF-8");
		
		// INSERT���̍쐬
		String sql = "INSERT INTO attendance_tb" +
				"(user_name,time_clock_in)" +
				"VALUES" +
				"('" + displayUserName + "','" + timeClockIn + "')";
		
		// database�̃��O�C�����
		String database = "jdbc:mysql://localhost/sample_db" +
				"?useUnicode=true&characterEncoding=UTF-8&serverTimezone=JST";
		String user = "sample_user";
		String password = "sample_pass";
		
		Connection connection = null;
		Statement statement = null;		
		String outMessage = "";		
		
		// 1�ڑ�����
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(database, user, password);
			statement = connection.createStatement();
			
			if (login) {
				statement.executeUpdate(sql);
				outMessage = "���o�΂�o�^���܂����B";
			}
		} catch (Exception e) {
			outMessage = "���o�^�Ɏ��s���܂����B";
		}
		
		// 2�ؒf����
		try {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		} catch (Exception e) {
			outMessage = "���f�[�^�x�[�X�̐ؒf�Ɏ��s���܂����B";
		}
		
		if (!login) response.sendRedirect("login.html");
		
		// cache���N���A����
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		Date today = new Date();
		response.setDateHeader("Last-Modified", today.getTime());
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("Login: <b>" + displayUserName + "</b>");
		out.println("<hr>");
		out.println("<a href=\"Logout\">�y���O�A�E�g�z</a>");
		out.println("<hr>");
		out.println(outMessage);		
		out.println("</body></html>");
		out.close();
	}

}
