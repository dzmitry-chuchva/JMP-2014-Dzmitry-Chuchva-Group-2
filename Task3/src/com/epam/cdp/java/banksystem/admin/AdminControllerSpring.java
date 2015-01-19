package com.epam.cdp.java.banksystem.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.epam.cdp.java.banksystem.dto.Account;
import com.epam.cdp.java.banksystem.dto.Currency;
import com.epam.cdp.java.banksystem.dto.User;
import com.epam.cdp.java.banksystem.exception.handler.BanksystemExceptionHandler;

@Controller
@RequestMapping(value = "AdminController")
public class AdminControllerSpring extends BanksystemExceptionHandler {

	private final String HOME_PAGE = "admin/home";
	private final String HOME_ACTION = "redirect:/AdminController/home";
	private final String NEW_ACCOUNT_PAGE = "admin/newAccount";

	@Autowired
	@Qualifier("AdminService")
	private AdminService adminService;

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(ModelMap model) {
		List<Account> accountList = adminService.readAccountList();
		model.addAttribute("accounts", accountList);
		return HOME_PAGE;
	}

	@RequestMapping(value = "/newAccount", method = RequestMethod.GET)
	public ModelAndView newAccount(ModelMap model) {
		Map<Long, String> currencyTypeMap = new HashMap<Long, String>();
		List<Currency> currencyTypeList = adminService.readCurrencyTypeList();
		for (Currency currency : currencyTypeList) {
			currencyTypeMap.put(currency.getId(), currency.getType());
		}
		model.addAttribute("currencyTypes", currencyTypeList);
		Map<Long, String> userMap = new HashMap<Long, String>();
		List<User> userList = adminService.readUserList();
		for (User user : userList) {
			userMap.put(user.getId(), user.getFirstName() + " " + user.getLastName());
		}
		model.addAttribute("currencyTypes", currencyTypeMap);
		model.addAttribute("users", userMap);
		return new ModelAndView(NEW_ACCOUNT_PAGE, "account", new Account());
	}

	@RequestMapping(value = "/saveAccount", method = RequestMethod.POST)
	public String saveAccount(@ModelAttribute("account") Account account, ModelMap model) {
		long userId = account.getUser().getId();
		long currencyId = account.getCurrency().getId();
		double value = account.getValue();
		adminService.createAccount(userId, currencyId, value);
		return HOME_ACTION;
	}

}
