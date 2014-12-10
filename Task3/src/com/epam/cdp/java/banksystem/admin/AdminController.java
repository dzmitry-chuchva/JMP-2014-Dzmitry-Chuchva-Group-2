package com.epam.cdp.java.banksystem.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.cdp.java.banksystem.dto.Account;
import com.epam.cdp.java.banksystem.dto.Currency;
import com.epam.cdp.java.banksystem.dto.User;
import com.epam.cdp.java.banksystem.exception.TechnicalException;

/**
 * Servlet implementation class AdminController
 */
@WebServlet("/AdminController")
public class AdminController extends HttpServlet {

	private final String HOME_PAGE = "view/admin/home.jsp";
	private final String HOME_ACTION = "AdminController?action=home";
	private final String ADD_ACCOUNT_PAGE = "view/admin/addAccount.jsp";
	private final String ERROR_PAGE = "view/error.jsp";

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminController() {
		super();
		// TODO Auto-generated constructor stub
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
			if ("home".equals(action)) {
				String URL = handleHome(request);
				request.getRequestDispatcher(URL).forward(request, response);
			} else if ("addAccount".equals(action)) {
				String URL = handleAddAccount(request);
				request.getRequestDispatcher(URL).forward(request, response);
			} else if ("createAccount".equals(action)) {
				String URL = handleCreateAccount(request);
				request.getRequestDispatcher(URL).forward(request, response);
			} else {
				request.setAttribute("message", "Action cannot be handled.");
				request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
			}
		} catch (TechnicalException e) {
			e.printStackTrace();
			request.setAttribute("message", "Application error.");
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
	}

	private String handleHome(HttpServletRequest request) throws TechnicalException {
		AdminService service = new AdminService(new JPAAdminDAO());
		List<Account> accountList = service.readAccountList();
		request.setAttribute("accounts", accountList);
		return HOME_PAGE;
	}

	private String handleAddAccount(HttpServletRequest request) throws TechnicalException {
		AdminService service = new AdminService(new JPAAdminDAO());
		List<Currency> currencyTypeList = service.readCurrencyTypeList();
		request.setAttribute("currencyTypes", currencyTypeList);
		List<User> userList = service.readUserList();
		request.setAttribute("users", userList);
		return ADD_ACCOUNT_PAGE;
	}

	private String handleCreateAccount(HttpServletRequest request) throws TechnicalException {
		AdminService service = new AdminService(new JPAAdminDAO());
		long userId = Long.parseLong(request.getParameter("userId"));
		long currencyId = Long.parseLong(request.getParameter("currencyId"));
		double value = Double.parseDouble(request.getParameter("value"));
		service.createAccount(userId, currencyId, value);
		return HOME_ACTION;
	}

}
