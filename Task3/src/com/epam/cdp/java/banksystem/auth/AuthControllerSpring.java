package com.epam.cdp.java.banksystem.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.cdp.java.banksystem.dto.User;
import com.epam.cdp.java.banksystem.exception.UserAlreadyExistsException;
import com.epam.cdp.java.banksystem.exception.UserNotFoundException;
import com.epam.cdp.java.banksystem.exception.handler.BanksystemExceptionHandler;

@Controller
@RequestMapping(value = "AuthController")
public class AuthControllerSpring extends BanksystemExceptionHandler {

	private final String CUSTOMER_HOME = "redirect:/CustomerController/home";
	private final String ADMIN_HOME = "redirect:/AdminController/home";

	@Autowired
	@Qualifier("AuthService")
	private AuthService authService;

	public AuthService getAuthService() {
		return authService;
	}

	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}

	@RequestMapping(value = "/signIn", method = RequestMethod.POST)
	public String signIn(@RequestParam("login") String login, @RequestParam("pass") String pass, ModelMap model) {
		User user = authService.signIn(login, pass);
		String result = "";
		if (user != null) {
			model.addAttribute("user", user);
			if ("admin".equals(user.getRole())) {
				result = ADMIN_HOME;
			} else if ("customer".equals(user.getRole())) {
				result = CUSTOMER_HOME + "/" + user.getId();
			}
		} else {
			throw new UserNotFoundException("User is not found.");
		}
		return result;
	}

	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public String signUp(@RequestParam("login") String login, @RequestParam("pass") String pass, @RequestParam("fName") String firstName, @RequestParam("lName") String lastName, ModelMap model) {
		User user = authService.signUp(firstName, lastName, login, pass);
		String result = "";
		if (user != null) {
			model.addAttribute("user", user);
			result = CUSTOMER_HOME;
		} else {
			throw new UserAlreadyExistsException("User is already exists.");
		}
		return result;
	}

}
