package org.geplanes.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.geplanes.models.Indicador;
import org.geplanes.models.ResumoUnidade;
import org.geplanes.models.UnidadeGerencial;
import org.geplanes.models.Usuario;
import org.geplanes.repository.IndicadorRepository;
import org.geplanes.repository.UnidadeGerencialRepository;
import org.geplanes.services.UnidadeService;
import org.geplanes.validators.Pendencia;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/unidade")
public class UnidadeController {

	@Autowired
	IndicadorRepository indicadorRepository;
	
	@Autowired
	UnidadeGerencialRepository unidadeGerencialRepository;
	
	@Autowired
	UnidadeService unidadeService;
	
	
	/****
	 * 
	 * REST METHODS JSON RESPONSE
	 */
	
	@GetMapping("{unidadeId}/indicadores")
	@ResponseBody
	public Collection<Indicador> getIndicadoresByUnidade(
			@PathVariable Integer unidadeId,
			@RequestParam("atrasados") Optional<Boolean> atrasados){
		UnidadeGerencial unidade = unidadeGerencialRepository.findOne(unidadeId);
		
		List<Indicador> indicadores =  indicadorRepository.findByUnidade(unidade);
		
		if(atrasados.isPresent() && atrasados.get())
			indicadores = indicadores.stream().filter(i -> i.isAtrasado()).collect(Collectors.toList());
			
		return indicadores;
	}
	
	@GetMapping("{unidadeId}/usuarios")
	@ResponseBody
	public Collection<Usuario> getUsuariosByUnidade(@PathVariable Integer unidadeId){
		UnidadeGerencial unidade = unidadeGerencialRepository.findOne(unidadeId);
	
		return unidadeGerencialRepository.findUsuariosByUnidade(unidade);

	}
	
	@GetMapping("{unidadeId}/resumo")
	@ResponseBody
	public ResumoUnidade resumoUnidade(@PathVariable Integer unidadeId){
	
		UnidadeGerencial unidade = unidadeGerencialRepository.findOne(unidadeId);
		if(unidade.isGestora())
			return unidadeService.createResumoUnidadeGestora(unidade, true);
		else 
			return unidadeService.createResumoUnidade(unidade, true, true, true, true);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("{unidadeId}/pendencias")
	@ResponseBody
	public List<Pendencia> pendenciasUnidade(@PathVariable Integer unidadeId,
			@RequestParam Optional<String> fase, @RequestParam Optional<String> nivel){
	
		UnidadeGerencial unidade = unidadeGerencialRepository.findOne(unidadeId);
		return unidadeService.findPendencias(unidade, fase.orElse(""), nivel.orElse(""));
	}
	
	/***
	 * 
	 * URL REQUEST MAPPING
	 */
	@GetMapping("home")
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView("app/unidade/home");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<String> siglasUnidadesUsuario = auth.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList());
		
		
		List<UnidadeGerencial> unidadesUsuario = new ArrayList<UnidadeGerencial>();
		List<ResumoUnidade> resumoUnidadesUsuario = new ArrayList<ResumoUnidade>();
		
		siglasUnidadesUsuario.stream().forEach(sigla -> unidadesUsuario.add(unidadeService.findBySigla(sigla,2017)));
		unidadesUsuario.stream().forEach(c -> resumoUnidadesUsuario.add(unidadeService.createResumoUnidade(c, true, true, false, false)));
		modelAndView.addObject("unidadesUsuario", resumoUnidadesUsuario);
		
		//List<ResumoUnidade> resumoUnidadesCampus = new ArrayList<ResumoUnidade>();
		//List<UnidadeGerencial> unidadesCampus =  new ArrayList<UnidadeGerencial>();
		//UnidadeGerencial unidadeSuperior = unidadesUsuario.get(0).getUnidadeSuperior();
		//unidadesCampus = unidadeService.findByUnidadeSuperior(unidadeSuperior, true, false);
		//unidadesCampus.stream().forEach(c -> resumoUnidadesCampus.add(unidadeService.createResumoUnidade(c, true, true, false, false)));
		//unidadesReitoria = unidadeService.findByUnidadeSuperior(unidadeSuperior, false, true);
		//modelAndView.addObject("unidadesCampus", resumoUnidadesCampus);
		

		
		
		//List<ResumoUnidade> resumoUnidadesReitoria = new ArrayList<ResumoUnidade>();
		//List<UnidadeGerencial> unidadesReitoria = new ArrayList<UnidadeGerencial>();
		//unidadesReitoria.stream().forEach(c -> resumoUnidadesReitoria.add(unidadeService.createResumoUnidade(c, true, true, false, false)));
		//modelAndView.addObject("unidadesReitoria", resumoUnidadesReitoria);
		
		
	
		
	
		
		return modelAndView;
	}
	
	@RequestMapping(value="{unidadeId}/resumo-unidades",method = RequestMethod.GET)
	@ResponseBody
	public List<ResumoUnidade> resumoUnidades(@PathVariable Integer unidadeId, 
			@RequestParam("campus") Optional<Boolean> hasCampus,
			@RequestParam("reitoria") Optional<Boolean> reitoria,
			@RequestParam("indicador") Optional<Boolean> indicador,
			@RequestParam("pendencia") Optional<Boolean> pendencia,
			@RequestParam("iniciativa") Optional<Boolean> iniciativa,
			@RequestParam("planoacao") Optional<Boolean> planoAcao,
			
			@RequestParam("pendencia") Optional<Boolean> hasPendencia){
		UnidadeGerencial unidade = unidadeGerencialRepository.findOne(unidadeId);
		
		List<UnidadeGerencial> campus = unidadeService.findByUnidadeSuperior(unidade.getUnidadeSuperior(), hasCampus.orElse(true), reitoria.orElse(false));
		List<ResumoUnidade> resumoUnidades = new ArrayList<ResumoUnidade>();
		campus.stream().forEach(c -> resumoUnidades.add(unidadeService.createResumoUnidade(c, 
														indicador.orElse(true), 
														pendencia.orElse(false), 
														iniciativa.orElse(false), 
														planoAcao.orElse(true))));
		
		return resumoUnidades;
	}
	
	@GetMapping("{ano}/{unidadeId}/data-chart-acompanhamentos")
	@ResponseBody
	public 	String chartAcompanhementoBimestre(@PathVariable Integer ano, @PathVariable Integer unidadeId){
		try {
			UnidadeGerencial unidade = unidadeGerencialRepository.findOne(unidadeId);
			return unidadeGerencialRepository.dataChartAcompanhamentosBimestre(ano, unidade).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@GetMapping("{ano}/{unidadeId}/data-chart-acoes")
	@ResponseBody
	public 	String chartAcoesBimestre(@PathVariable Integer ano, @PathVariable Integer unidadeId){
		try {
			UnidadeGerencial unidade = unidadeGerencialRepository.findOne(unidadeId);
			return unidadeGerencialRepository.dataChartAcoesBimestre(ano, unidade).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@RequestMapping(value="dashboard",method = RequestMethod.GET)
	public ModelAndView dashboard(@RequestParam Optional<Integer> unidadeId){
		ModelAndView modelAndView = new ModelAndView("app/unidade/dashboard");
		return modelAndView;
	}
	
	@RequestMapping(value="pendencias",method = RequestMethod.GET)
	public ModelAndView pendencias(){
		ModelAndView modelAndView = new ModelAndView("app/unidade/pendencias");
		return modelAndView;
	}
	
	
	
	
}
