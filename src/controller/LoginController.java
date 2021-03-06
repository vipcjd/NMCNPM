package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Users;
import modelDAO.CheckLogin;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("UTF-8");
		request.setCharacterEncoding("UTF-8");
		// 2.	Lấy dữ liệu từ trang login.jsp gồm Username và password đã nhập vào 
		String username = request.getParameter("username");
		String password = request.getParameter("pass");
		HttpSession session = request.getSession();
		// 3.	Kiểm tra trong database
		CheckLogin checkLogin = new CheckLogin();
		Users users;
		
		try {
			users = checkLogin.checkLogin(username, password);
			// 3.1	Nếu tồn tại chuyển đến trang welcome.jsp bao gồm thông tin của user
			if (users != null) {
				session.setAttribute("user", users);
				response.sendRedirect("Welcome.jsp");
			// 3.2	Không tồn tại hiển thị thông báo bạn đã nhập sai tên tài khoản hoặc mật khẩu và quay về trang login
			} else {
				session.setAttribute("error", " <i class=\"fas fa-exclamation-triangle\"></i>  Bạn đã Nhập sai tên tài khoản hoặc mật khẩu");
				response.sendRedirect("Login.jsp");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
