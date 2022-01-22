package fatneek.demo;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

	Player masnitz = new Player("mason", "euw", 2, 17);

	@RequestMapping("/home")
	public String displayHome(Model model) {
		Player player = new Player();
		model.addAttribute("player", player);
		List<String> listServer = Arrays.asList("EUW", "EUNE", "NA");
        model.addAttribute("listServer", listServer);
		return "home";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/home")
	public String nameSubmit(@ModelAttribute("player") Player player, Model model) {
		//once api works, actual player info to be displayed
		System.out.println(player);
	    model.addAttribute("player", player);
		List<String> listServer = Arrays.asList("EUW", "EUNE", "NA");
        model.addAttribute("listServer", listServer);
	    return "home2";
	}
}
