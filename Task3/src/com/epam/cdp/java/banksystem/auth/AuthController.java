package com.epam.cdp.java.banksystem.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.cdp.java.banksystem.dto.User;
import com.epam.cdp.java.banksystem.exception.ServiceException;

/**
 * Servlet implementation class AuthController
 */
@WebServlet("/AuthController")
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String homePage = "view/Home.jsp";
		String errorPage = "view/Error.jsp";
		try {
			if ("signIn".equals(action)) {
				String login = request.getParameter("login");
				String pass = request.getParameter("pass");
				AuthService auth = new AuthService(new MySQLAuthDAO());
				User user = auth.signIn(login, pass);
				String page = null;
				if (user != null) {
					request.setAttribute("fName", user.getFirstName());
					request.setAttribute("lName", user.getLastName());
					page = homePage;
				} else {
					request.setAttribute("message", "Such user is not exist.");
					page = errorPage;
				}
				request.getRequestDispatcher(page).forward(request, response);
			} else if ("signUp".equals(action)) {
				String login = request.getParameter("login");
				String pass = request.getParameter("pass");
				String firstName = request.getParameter("fName");
				String lastName = request.getParameter("lName");
				AuthService auth = new AuthService(new MySQLAuthDAO());
				User user = auth.signUp(firstName, lastName, login, pass);
				String page = null;
				if (user != null) {
					request.setAttribute("fName", user.getFirstName());
					request.setAttribute("lName", user.getLastName());
					page = homePage;
				} else {
					request.setAttribute("message", "Application error. User was not created.");
					page = errorPage;
				}
				request.getRequestDispatcher(page).forward(request, response);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			request.setAttribute("message", "Application error.");
			request.getRequestDispatcher(errorPage).forward(request, response);
		}
	}
}
