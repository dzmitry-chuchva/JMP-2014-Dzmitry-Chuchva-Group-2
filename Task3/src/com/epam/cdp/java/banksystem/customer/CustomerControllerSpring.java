package com.epam.cdp.java.banksystem.customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.cdp.java.banksystem.admin.AdminService;
import com.epam.cdp.java.banksystem.dto.Account;
import com.epam.cdp.java.banksystem.dto.User;
import com.epam.cdp.java.banksystem.exception.handler.BanksystemExceptionHandler;

@Controller
@RequestMapping(value = "CustomerController")
public class CustomerControllerSpring extends BanksystemExceptionHandler {

	private final String HOME_ACTION = "redirect:/CustomerController/home";
	private final String EXCHANGE_PAGE = "customer/exchange";
	private final String HOME_PAGE = "customer/home";

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

	@RequestMapping(value = "/home/{userId}", method = RequestMethod.GET)
	public String home(@PathVariable(value = "userId") final Long userId, ModelMap model) {
		List<Account> accountList = new ArrayList<Account>();
		User user = adminService.readUserById(userId);
		accountList = adminService.readUserAccountList(userId);
		model.addAttribute("user", user);
		model.addAttribute("accounts", accountList);
		return HOME_PAGE;
	}

	@RequestMapping(value = "/startExchange/{userId}", method = RequestMethod.GET)
	public String startExchange(@PathVariable(value = "userId") final Long userId, ModelMap model) {
		User user = adminService.readUserById(userId);
		List<Account> accountList = adminService.readUserAccountList(userId);
		model.addAttribute("user", user);
		model.addAttribute("accounts", accountList);
		return EXCHANGE_PAGE;
	}

	@RequestMapping(value = "/completeExchange/{userId}", method = RequestMethod.POST)
	public String completeExchange(@PathVariable(value = "userId") final Long userId, @RequestParam("accountFromId") Long fromId, @RequestParam("accountToId") Long toId, @RequestParam("exchangeValue") Double exchangeValue) {
		customerService.performExchange(fromId, toId, exchangeValue);
		return HOME_ACTION + "/" + userId;
	}

}
