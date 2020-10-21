package com.omnirio.Account.resource;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.omnirio.Account.model.Account;
import com.omnirio.Account.model.AuthenticationRequest;
import com.omnirio.Account.model.AuthenticationResponse;
import com.omnirio.Account.model.Role;
import com.omnirio.Account.model.User;
import com.omnirio.Account.repository.AccountRepository;
import com.omnirio.Account.service.AccountService;
import com.omnirio.Account.service.NewUserDetailsService;
import com.omnirio.Account.util.JwtUtility;

@RestController
@RequestMapping("/accountApp")
public class AccountController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private NewUserDetailsService userDetailsService;

	@GetMapping("/accounts")
	public List<Account> getAllAccounts() {
		return accountService.getAllAccounts();
	}

	@GetMapping("/accounts/{id}")
	public Account getAccountById(@PathVariable int id) throws Exception {
		Optional<Account> account = accountService.findById(id);

		if (!account.isPresent())
			throw new Exception("Account not found with id-" + id);

		return account.get();
	}

	@DeleteMapping("/accounts/{id}")
	public void deleteAccount(@PathVariable int id) {
		accountService.deleteAccountById(id);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request)
			throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
		} catch (Exception e) {
			throw new Exception("Incorrect Username or password");
		}

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());

		final String token = JwtUtility.generateJWTToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(token));

	}

	@PostMapping("/account")
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {

		try {
			User user = account.getUser();
			Role role = user.getRole();
			//
//			user.getRole().setUser(user);
//			user.setAccount(account);
			//
//			account.setUser(user);
////			account.setCustomerName(user.getUserName());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			JSONObject userJsonObject = new JSONObject();
			userJsonObject.put("userName", user.getUserName());

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			String sendDateInUTC = formatter.format(user.getDob());

//			String dob = objectMapper.
			userJsonObject.put("dob", sendDateInUTC);
			userJsonObject.put("gender", user.getGender());
			userJsonObject.put("phoneNo", user.getPhoneNo());

			JSONObject roleJsonObject = new JSONObject();
			roleJsonObject.put("roleName", role.getRoleName());
			roleJsonObject.put("roleCode", role.getRoleCode());

			userJsonObject.put("role", roleJsonObject);

			String createUserUrl = "http://OMNIRIO-CUSTOMER-SERVICE/customer/user";
			HttpEntity<String> request = new HttpEntity<String>(userJsonObject.toString(), headers);
			User newUser = restTemplate.postForObject(createUserUrl, request, User.class);

			account.setUser(newUser);
			account.setCustomerId(newUser.getUserId());
			account.setCustomerName(newUser.getUserName());

			if (isMinor(newUser)) {
				account.setMinor(true);
			} else {
				account.setMinor(false);
			}

			Account savedAccount = accountService.insertAccount(account);

			return ResponseEntity.ok(savedAccount);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	private static boolean isMinor(User user) {
		Date userDob = user.getDob();

		Date currentDate = new Date();

		Calendar a = getCalendar(userDob);
		Calendar b = getCalendar(currentDate);
		int noOfYears = b.get(b.YEAR) - a.get(a.YEAR);
		if (a.get(a.MONTH) > b.get(b.MONTH) || (a.get(a.MONTH) == b.get(b.MONTH) && a.get(a.DATE) > b.get(b.DATE))) {
			noOfYears--;
		}

		noOfYears = Math.abs(noOfYears);

		if (noOfYears < 18) {
			return true;
		}
		return false;
	}

	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(date);
		return cal;
	}
}
