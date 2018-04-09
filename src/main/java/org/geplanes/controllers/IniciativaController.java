package org.geplanes.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.geplanes.models.Iniciativa;
import org.geplanes.repository.IniciativaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/iniciativa")
public class IniciativaController {
	
	@Autowired
	IniciativaRepository iniciativaRepository;
	
	@GetMapping("{unidadeURL}/{unidadeId}")
	@ResponseBody
	public List<Iniciativa> findByUnidade(@PathVariable Integer unidadeId){
		return iniciativaRepository.findByUnidadeId(unidadeId);
	}
	
	@GetMapping("{unidadeURL}/{unidadeId}/{objetivoEstrategicoId}/iniciativas")
	@ResponseBody
	public List<Iniciativa> findByUnidadeAndObjetivoEstrategico(
			@PathVariable Integer unidadeId,
			@PathVariable Integer objetivoEstrategicoId,
			@RequestParam("atrasadas") Optional<Boolean> atrasadas){
		
		List<Iniciativa> iniciativas = iniciativaRepository.findByUnidadeIdAndObjetivoMapaEstrategicoObjetivoEstrategicoId(unidadeId, objetivoEstrategicoId);
		
		if(atrasadas.isPresent() && atrasadas.get())
			iniciativas = iniciativas.stream().filter(i -> i.isAtrasada()).collect(Collectors.toList());
		
		return iniciativas;
	}
	
	@GetMapping("planoacao/view/{planoacaoId}")
	public ModelAndView view(@PathVariable Integer planoacaoId){
		ModelAndView modelAndView = new ModelAndView("app/iniciativa/planoacao");
		System.out.println("app/iniciativa/planoacao");
		return modelAndView;
	}
	
}
