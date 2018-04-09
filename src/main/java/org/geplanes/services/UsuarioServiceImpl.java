package org.geplanes.services;

import org.geplanes.models.Usuario;
import org.geplanes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("usuarioService")
@Transactional
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	public Usuario findUsuarioByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

	@Override
	public Usuario findUsuarioByLogin(String login) {
		return usuarioRepository.findByLogin(login);
	}

	@Override
	public void salvarUsuario(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

}
