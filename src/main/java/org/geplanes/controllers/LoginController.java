package org.geplanes.controllers;

import javax.validation.Valid;

import org.geplanes.models.Usuario;
import org.geplanes.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/registrar", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		Usuario usuario = new Usuario();
		modelAndView.addObject("usuario", usuario);
		modelAndView.addObject("message", "Cadastra-se e observe");
		modelAndView.setViewName("registrar");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registrar", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid Usuario usuario, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		Usuario userExists = usuarioService.findUsuarioByEmail(usuario.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user", "Email já existente.");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registrar");
		} else {
			usuarioService.salvarUsuario(usuario);
			modelAndView.addObject("successMessage", "Usuário registrado com sucesso");
			modelAndView.addObject("usuario", new Usuario());
			modelAndView.setViewName("registrar");
			
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = (Usuario)auth.getPrincipal();
		modelAndView.addObject("message", "Bem vindo " + user.getNome() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Conteúdo para gestores");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
	

}
