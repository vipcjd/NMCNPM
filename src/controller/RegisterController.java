package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Users;
import model.Validate;
import modelDAO.CheckRegister;
import modelDAO.UserDAO;

@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CheckRegister checkRegister = new CheckRegister();

	public RegisterController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		String repass = request.getParameter("repass");
		Users users = new Users(username, email, pass);
		UserDAO userDAO = new UserDAO();
		CheckRegister checkRegister = new CheckRegister();

		RequestDispatcher dis = request.getRequestDispatcher("Register.jsp");

		if (Validate.checkFormatName(username) == false) {
			request.setAttribute("error",
					" <i class=\"fas fa-exclamation-triangle\"></i> Tên Đăng nhập từ 2-50 ký tự (vd: abc) ");
			dis.forward(request, response);

		} else {
			try {
				if (checkRegister.checkUser(users) == true) {
					request.setAttribute("error",
							" <i class=\"fas fa-exclamation-triangle\"></i> Tên tài khoản đã tồn tại");
					dis.forward(request, response);
				} else {
					if (!Validate.checkFormatEmail(email)) {
						request.setAttribute("error",
								" <i class=\"fas fa-exclamation-triangle\"></i> Bạn phải nhập đúng định dạng gmail ( vd: abc@gmail.com)");
						dis.forward(request, response);
					} else {
						if (!Validate.checkFormatPass(pass)) {
							request.setAttribute("error",
									" <i class=\"fas fa-exclamation-triangle\"></i> password từ 4-30 ký tự (vd: abcd )");
							dis.forward(request, response);

						} else {
							if (checkRegister.checkGmail(users) == true) {
								request.setAttribute("error",
										" <i class=\"fas fa-exclamation-triangle\"></i> Gmail đã tồn tại");
								dis.forward(request, response);
							}

							else {
								if (Validate.checPass(pass, repass) == false) {
									request.setAttribute("error",
											"<i class=\"fas fa-exclamation-triangle\"></i> nhập lại password không giống nhau");
									dis.forward(request, response);
								} else {
									userDAO.insertUser(users);
									response.sendRedirect("Login.jsp");

								}
							}
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}