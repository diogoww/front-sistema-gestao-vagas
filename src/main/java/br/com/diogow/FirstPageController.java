package br.com.diogow;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class FirstPageController {

    @GetMapping("/home")
    public String firstPageHtml(Model model) {

        model.addAttribute("mensagemController", "Primeira mensagem vindo da controller");
        return "firstPage";
    }

    @GetMapping("/login")
    public String loginCandidate() {
        return "candidate/login";
    }
}
