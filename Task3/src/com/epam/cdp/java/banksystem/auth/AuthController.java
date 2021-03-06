package com.epam.cdp.java.banksystem.auth;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.epam.cdp.java.banksystem.dto.User;
import com.epam.cdp.java.banksystem.exception.TechnicalException;
import com.epam.cdp.java.banksystem.exception.UserAlreadyExistsException;

/**
 * Servlet implementation class AuthController
 */

@WebServlet("/AuthController")
public class AuthController extends HttpServlet {

	private final String CUSTOMER_HOME = "CustomerController?action=home";
	private final String ADMIN_HOME = "AdminController?action=home";
	private final String ERROR_PAGE = "view/error.jsp";

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("AuthService")
	private AuthService authService;

	public AuthService getAuthService() {
		return authService;
	}

	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthController() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	private void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		try {
			if ("signIn".equals(action)) {
				String URL = handleSignIn(request);
				request.getRequestDispatcher(URL).forward(request, response);
			} else if ("signUp".equals(action)) {
				String URL = handleSignUp(request);
				request.getRequestDispatcher(URL).forward(request, response);
			} else {
				request.setAttribute("message", "Action cannot be handled.");
				request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
			}
		} catch (TechnicalException e) {
			e.printStackTrace();
			request.setAttribute("message", "Application error.");
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		} catch (UserAlreadyExistsException e) {
			e.printStackTrace();
			request.setAttribute("message", "User already exists.");
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
	}

	private String handleSignIn(HttpServletRequest request) throws TechnicalException {
		String login = request.getParameter("login");
		String pass = request.getParameter("pass");
		User user = authService.signIn(login, pass);
		String URL = null;
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			if ("admin".equals(user.getRole())) {
				URL = ADMIN_HOME;
			} else if ("customer".equals(user.getRole())) {
				URL = CUSTOMER_HOME;
			}
		} else {
			request.setAttribute("message", "Such user is not exist.");
			URL = ERROR_PAGE;
		}
		return URL;
	}

	private String handleSignUp(HttpServletRequest request) throws TechnicalException, UserAlreadyExistsException {
		String login = request.getParameter("login");
		String pass = request.getParameter("pass");
		String firstName = request.getParameter("fName");
		String lastName = request.getParameter("lName");
		User user = authService.signUp(firstName, lastName, login, pass);
		String URL = null;
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			URL = CUSTOMER_HOME;
		} else {
			request.setAttribute("message", "Application error. User was not created.");
			URL = ERROR_PAGE;
		}
		return URL;
	}
}
