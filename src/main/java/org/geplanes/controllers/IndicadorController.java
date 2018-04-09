package org.geplanes.controllers;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.geplanes.models.Indicador;
import org.geplanes.models.ResumoIndicador;
import org.geplanes.models.UnidadeGerencial;
import org.geplanes.repository.IndicadorRepository;
import org.geplanes.repository.UnidadeGerencialRepository;
import org.geplanes.services.IndicadorService;
import org.geplanes.validators.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/indicador")
public class IndicadorController {

	@Autowired
	IndicadorRepository indicadorRepository;
	
	@Autowired
	IndicadorService indicadorService;
	
	@Autowired
	UnidadeGerencialRepository unidadeGerencialRepository;
	
	@GetMapping("{id}")
	@ResponseBody
	public Indicador get(@PathVariable Integer id){
		return indicadorRepository.findOne(id);
	}
	
	@GetMapping("{id}/pendencias")
	@ResponseBody
	public ValidationResult findPendencias(@PathVariable Integer id){
		Indicador indicador = indicadorRepository.findOne(id);
		
		return indicadorService.validarIndicador(indicador);
	}
	
	@GetMapping("{unidadeId}/responsavel")
	@ResponseBody
	public Collection<Indicador> get(
			@PathVariable Integer unidadeId,
			@RequestParam("q") String responsavel,
			@RequestParam("atrasados") Optional<Boolean> atrasados){
		
		UnidadeGerencial unidade = unidadeGerencialRepository.findOne(unidadeId);
		List<Indicador> indicadores = indicadorRepository.findByUnidadeAndResponsavelIgnoreCaseContaining(unidade,responsavel);
		
		if(atrasados.isPresent() && atrasados.get())
			indicadores = indicadores.stream().filter(i -> i.isAtrasado()).collect(Collectors.toList());
			
		return indicadores;
	}
	
	@GetMapping("perspectiva/{perspectivaId}/{perspectivaURL}/unidade/{unidadeId}/{unidadeURL}/indicadores")
	@ResponseBody
	public List<Indicador> listIndicadoresByPerspectiva(
			@PathVariable("unidadeId") Integer unidadeId, 
			@PathVariable("perspectivaId") Integer perspectivaId,
			@RequestParam("atrasados") Optional<Boolean> atrasados){
		
		List<Indicador> indicadores = indicadorRepository.findByUnidadeIdAndPerspectivaId(unidadeId, perspectivaId);
		if(atrasados.isPresent() && atrasados.get())
			indicadores = indicadores.stream().filter(i -> i.isAtrasado()).collect(Collectors.toList());
		
		return indicadores;
	}
	
	@GetMapping("{ano}/list")
	@ResponseBody
	public List<Indicador> findIndicadoresReferencia(@PathVariable Integer ano){
		UnidadeGerencial unidadeReferencia = unidadeGerencialRepository.findUnidadeReferenciaAno(ano);
		return indicadorRepository.findByUnidade(unidadeReferencia);
	}
	
	@GetMapping("{idIndicadorReferencia}/indicadores")
	@ResponseBody
	public List<Indicador> findIndicadoresFromReferencia(@PathVariable Integer idIndicadorReferencia){
		Indicador indicadorReferencia = indicadorRepository.findOne(idIndicadorReferencia);
		return indicadorService.findFromIndicador(indicadorReferencia);
	}
	
	@GetMapping("{idIndicadorReferencia}/resumo")
	@ResponseBody
	public ResumoIndicador getResumoIndicadorReferencia(@PathVariable Integer idIndicadorReferencia){
		Indicador indicadorReferencia = indicadorRepository.findOne(idIndicadorReferencia);
		return indicadorService.getResumoIndicadorReferencia(indicadorReferencia);
	}
	
	@GetMapping("view/{indicadorId}")
	public ModelAndView view(@PathVariable Integer indicadorId){
		ModelAndView modelAndView = new ModelAndView("app/indicador/view");
	
		return modelAndView;
	}
	

}
