package com.sandra.agendadortarefas.infrastructure.security;

import com.sandra.agendadortarefas.business.dto.UsuarioDTO;
import com.sandra.agendadortarefas.infrastructure.client.UsuarioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioClient client;

    // Renomeamos para o padrão que o JwtRequestFilter está tentando usar na sua Imagem 7
    public UserDetails carregaDadosUsuario(String email, String token) {
        UsuarioDTO usuarioDTO = client.buscaUsuarioPorEmail(email, token);
        if (usuarioDTO == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return User.withUsername(usuarioDTO.getEmail())
                .password(usuarioDTO.getSenha())
                .authorities(new ArrayList<>())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Obrigatório pela interface, mas o seu filtro usa o de cima
        return null;
    }
}