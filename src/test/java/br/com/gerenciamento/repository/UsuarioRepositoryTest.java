package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @Transactional
    public void save() {
        Usuario usuario = new Usuario();

        usuario.setEmail("alerreandro@gmail.com");
        usuario.setSenha("123456");
        usuario.setUser("Alerreandro");

        usuarioRepository.save(usuario);

        Usuario usuarioIdEncontrado = usuarioRepository.findById(usuario.getId()).orElse(null);

        Assert.assertTrue(usuarioIdEncontrado.getUser().equals("Alerreandro"));
    }

    @Test
    @Transactional
    public void findByEmail() {
        Usuario usuario = new Usuario();

        usuario.setEmail("margarida@gmail.com");
        usuario.setSenha("784651");
        usuario.setUser("Margarida");

        usuarioRepository.save(usuario);

        Usuario usuarioEncontrado = usuarioRepository.findByEmail("margarida@gmail.com");

        Assert.assertTrue(usuarioEncontrado.getUser().equals("Margarida"));
    }

    @Test
    @Transactional
    public void saveInvalidUser() {
        Usuario usuario = new Usuario();

        usuario.setEmail("italo@gmail.com");
        usuario.setSenha("214614");
        usuario.setUser("i");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.usuarioRepository.save(usuario);
        });
    }

    @Test
    @Transactional
    public void saveInvalidEmail() {
        Usuario usuario = new Usuario();

        usuario.setEmail("osvaldo@");
        usuario.setSenha("695847");
        usuario.setUser("Osvaldo");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.usuarioRepository.save(usuario);
        });
    }
}