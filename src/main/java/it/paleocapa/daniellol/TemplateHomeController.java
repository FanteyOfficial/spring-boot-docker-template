package it.paleocapa.daniellol;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TemplateHomeController {

    @GetMapping("/")
	public String index(
        @RequestParam(name="name", required=false, defaultValue="World") 
        String name, 
        Model model
    ) {
		model.addAttribute("name", name);
		return "index";
	}
}
