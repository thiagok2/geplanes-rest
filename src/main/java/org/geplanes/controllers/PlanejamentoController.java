package org.geplanes.controllers;

import java.util.List;

import org.geplanes.models.MapaEstrategico;
import org.geplanes.models.ObjetivoEstrategico;
import org.geplanes.models.Perspectiva;
import org.geplanes.models.UnidadeGerencial;
import org.geplanes.repository.MapaEstrategicoRepository;
import org.geplanes.repository.ObjetivoEstrategicoRepository;
import org.geplanes.repository.UnidadeGerencialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/planejamento")
public class PlanejamentoController {
	
	@Autowired
	MapaEstrategicoRepository mapaEstrategicoRepository;
	
	@Autowired
	ObjetivoEstrategicoRepository objetivoEstrategicoRepository;
	
	@Autowired
	UnidadeGerencialRepository unidadeGerencialRepository;
	
	@GetMapping("mapas-estrategicos")
	@ResponseBody
	public List<MapaEstrategico> listMapasEstrategicos(){
		return mapaEstrategicoRepository.findAll();
	}
	
	@GetMapping("{ano}/mapa-estrategico")
	@ResponseBody
	public MapaEstrategico listMapasEstrategicosByAno(@PathVariable Integer ano){
		return mapaEstrategicoRepository.findByUnidadeGerencialPlanoGestaoAnoExercicio(ano);
	}
	
	@GetMapping("{ano}/unidades")
	@ResponseBody
	public List<UnidadeGerencial> listUnidadesByMapa(@PathVariable Integer ano){
		MapaEstrategico mapaEstrategico =  mapaEstrategicoRepository.findByUnidadeGerencialPlanoGestaoAnoExercicio(ano);
		UnidadeGerencial unidadeGerencial = mapaEstrategico.getUnidadeGerencial();
		return unidadeGerencialRepository.findByUnidadeSuperiorId(unidadeGerencial.getId());
		
	}
	
	@GetMapping("{ano}/perspectivas")
	@ResponseBody
	public List<Perspectiva> listPerspectivas(@PathVariable Integer ano){
		return mapaEstrategicoRepository.findPerspectivasByAno(ano);
	}
	
	@GetMapping("{ano}/objetivos-estrategicos")
	@ResponseBody
	public List<ObjetivoEstrategico> listObjetivosEstrategicosByAno(
			@PathVariable Integer ano){
		return objetivoEstrategicoRepository.findByAno(ano);
	}
	
	@GetMapping("{ano}/perspectiva/{perspectivaId}/{perspectivaurl}/objetivos-estrategicos")
	@ResponseBody
	public List<ObjetivoEstrategico> listObjetivosEstrategicosByPerspectivaAno(
			@PathVariable Integer ano, 
			@PathVariable Integer perspectivaId){
		return objetivoEstrategicoRepository.findByPerspectivaAndAno(perspectivaId, ano);
	}
	
	
	
}
