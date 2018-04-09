package org.geplanes.services;

import org.geplanes.models.Usuario;

public interface UsuarioService {
	public Usuario findUsuarioByEmail(String email);
	public Usuario findUsuarioByLogin(String login);
	public void salvarUsuario(Usuario usuario);
	
}
