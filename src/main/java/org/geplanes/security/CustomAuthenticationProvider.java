package org.geplanes.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

import org.geplanes.models.Usuario;
import org.geplanes.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	UsuarioService usuarioService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName().trim();
		String password = authentication.getCredentials().toString().trim();
		
		Usuario usuario = usuarioService.findUsuarioByEmail(userName);
		if(usuario == null)
			usuario = usuarioService.findUsuarioByLogin(userName);

		Collection<GrantedAuthority> grantedAuths = new HashSet<GrantedAuthority>();
		usuario.getUnidades().stream().forEach(u -> grantedAuths.add(new SimpleGrantedAuthority(u.getSigla())));
		
		Authentication auth = new UsernamePasswordAuthenticationToken(usuario, password, grantedAuths);
		return auth;

	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
