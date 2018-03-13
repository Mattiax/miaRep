package pkg.controllori;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pkg.oggetti.Utente;

@Controller
public class DefaultController {
	
	/*@Autowired
	Utente u;*/

	@RequestMapping(value = "/")
	public String index(ModelMap map) {
		return "redirect:/index"; //reindirizza alla Home Page
	}

	@RequestMapping(value = "/index")
	public String home(ModelMap map) {
		return "index";
	}
	
	@RequestMapping(value = "/login")
	public String logIn(ModelMap map) {
		return "login";
	}
	
	@RequestMapping(value = "/signin")
	public String signIn(ModelMap map) {
		return "signin";
	}

}
