package br.com.diogow.modules.candidate.controller;

import br.com.diogow.modules.candidate.dto.Token;
import br.com.diogow.modules.candidate.service.CandidateService;
import br.com.diogow.modules.candidate.service.FindJobsService;
import br.com.diogow.modules.candidate.service.ProfileCandidateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

	@Autowired
	private ProfileCandidateService profileCandidateService;

	@Autowired
	private CandidateService candidateService;

	@Autowired
	private FindJobsService findJobsService;

	@GetMapping("/login")
	public String login(){
		return "candidate/login";
	}

	@PostMapping("/signIn")
	public String signIn(RedirectAttributes redirectAttributes, HttpSession session, String username, String password){

		try{
			var token = this.candidateService.login(username, password);

			var roles = token.getRoles();
			if (roles == null || roles.isEmpty()) {
				roles = List.of("CANDIDATE");
				token.setRoles(roles);
			}

			var grants = roles.stream()
					.map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();

			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(null, null, grants);
			auth.setDetails(token.getAccess_token());

			SecurityContextHolder.getContext().setAuthentication(auth);
			SecurityContext securityContext = SecurityContextHolder.getContext();
			session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
			session.setAttribute("token", token);

			return "redirect:/candidate/profile";

		} catch (HttpClientErrorException e) {
			redirectAttributes.addFlashAttribute("error_message", "Usuário/Senha incorretos");
			return "redirect:/candidate/login";
		}
	}

	@GetMapping("/profile")
	@PreAuthorize("hasRole('CANDIDATE')")
	public String profile(Model model, HttpSession session) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return "redirect:/candidate/login";
		}

		Token token = (Token) session.getAttribute("token");
		if (token == null || token.getAccess_token() == null) {
			return "redirect:/candidate/login";
		}

		var user = this.profileCandidateService.execute(token.getAccess_token());

		model.addAttribute("user", user);

		return "candidate/profile";
	}

	@GetMapping("/jobs")
	@PreAuthorize("hasRole('CANDIDATE')")
	public String jobs(Model model, String filter){
		System.out.println("vaga: " + filter);

		try {
			if (filter != null){
				var jobs = this.findJobsService.execute(getToken(), filter);
				model.addAttribute("jobs", jobs);
			}
		} catch (HttpClientErrorException e){
			return "redirect:/candidate/login";
		}

		return "candidate/jobs";
	}

	private String getToken() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getDetails().toString();
	}

}
