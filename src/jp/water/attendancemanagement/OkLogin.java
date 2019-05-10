package jp.water.attendancemanagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class OkLogin
 */
@WebServlet("/OkLogin")
public class OkLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OkLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		
		boolean login = false;
		if (session.getAttribute("login") != null) {
			login = (boolean)session.getAttribute("login");
		}
		if (!login) response.sendRedirect("login.html");
		
		String displayUserName = (String)session.getAttribute("displayUserName");
		
		// cacheをクリアする
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
		out.println("<a href=\"Logout\">【ログアウト】</a>");
		out.println("<hr>");
		out.println("<p id=\"RealtimeClockArea\">現在の時刻を表示しています...</p>");

		out.println("<div style=\"display:inline-flex\">");
		out.println("<form action=\"AddClockIn\" method=\"POST\">");
		out.println("<input type=\"submit\" value=\"出勤\" onClick=\"return checkSubmit(this)\">");
		out.println("</form>");
		out.println("<form action=\"AddClockOut\" method=\"POST\">");
		out.println("<input type=\"submit\" value=\"退勤\" onClick=\"return checkSubmit(this)\">");
		out.println("</form>");
		out.println("</div>");
		
		out.println("<script src=\"clock.js\"></script>");
		out.println("<script src=\"checkSubmit.js\"></script>");
		out.println("</body></html>");
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
