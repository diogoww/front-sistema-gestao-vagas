package br.com.diogow.modules.company;

import br.com.diogow.modules.company.dto.CreateCompanyDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("company", new CreateCompanyDTO());
        return "company/create";
    }
}
