package com.epam.cdp.java.banksystem.customer;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.epam.cdp.java.banksystem.admin.AdminService;
import com.epam.cdp.java.banksystem.dto.Account;
import com.epam.cdp.java.banksystem.dto.User;
import com.epam.cdp.java.banksystem.exception.TechnicalException;

/**
 * Servlet implementation class CustomerController
 */
@WebServlet("/CustomerController")
public class CustomerController extends HttpServlet {

	private final String HOME_ACTION = "CustomerController?action=home";
	private final String HOME_PAGE = "view/customer/home.jsp";
	private final String EXCHANGE_PAGE = "view/customer/exchange.jsp";
	private final String ERROR_PAGE = "view/error.jsp";

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("AdminService")
	private AdminService adminService;

	@Autowired
	@Qualifier("CustomerService")
	private CustomerService customerService;

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CustomerController() {
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
			} else if ("startExchange".equals(action)) {
				String URL = handleStartExchange(request);
				request.getRequestDispatcher(URL).forward(request, response);
			} else if ("completeExchange".equals(action)) {
				String URL = handleCompleteExchange(request);
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
		initAccounts(request);
		return HOME_PAGE;
	}

	private String handleStartExchange(HttpServletRequest request) throws TechnicalException {
		initAccounts(request);
		return EXCHANGE_PAGE;
	}

	private String handleCompleteExchange(HttpServletRequest request) throws TechnicalException {
		long accFromId = Long.parseLong(request.getParameter("accountFromId"));
		long accToId = Long.parseLong(request.getParameter("accountToId"));
		double exchangeValue = Double.parseDouble(request.getParameter("exchangeValue"));
		customerService.performExchange(accFromId, accToId, exchangeValue);
		return HOME_ACTION;
	}

	private void initAccounts(HttpServletRequest request) throws TechnicalException {
		long userId = ((User) request.getSession().getAttribute("user")).getId();
		List<Account> accountList = adminService.readUserAccountList(userId);
		request.setAttribute("accounts", accountList);
	}

}
