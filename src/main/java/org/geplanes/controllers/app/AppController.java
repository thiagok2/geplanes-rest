package org.geplanes.controllers.app;

import java.time.LocalDate;

import org.geplanes.models.UnidadeGerencial;
import org.geplanes.services.UnidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app")
public class AppController {
	
	@Autowired
	UnidadeService unidadeService;
	
	@GetMapping({"index","/"})
	public ModelAndView index(){
		ModelAndView modelAndView = new ModelAndView("app/index");
		
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String authoritySigla = auth.getAuthorities().stream().findFirst().get().getAuthority();
		Integer ano = LocalDate.now().getYear();
		
		ano = 2017; 
		UnidadeGerencial unidade = unidadeService.findBySigla(authoritySigla, ano);
		
		modelAndView.addObject("unidade",unidade);
		
		return modelAndView;
	}
}
