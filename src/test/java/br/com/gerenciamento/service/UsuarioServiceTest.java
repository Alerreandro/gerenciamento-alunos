package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.util.Util;

import org.junit.Test;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

import jakarta.transaction.Transactional;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {
    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    @Transactional
    public void salvarUsuario() {
        Usuario usuario = new Usuario();

        usuario.setEmail("jose@gmail.com");
        usuario.setSenha("123456");
        usuario.setUser("José");

        assertDoesNotThrow(() -> {
            this.serviceUsuario.salvarUsuario(usuario);
        });
    }

    @Test
    @Transactional
    public void salvarUsuarioEmailExists() throws Exception {
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();

        usuario1.setEmail("alerreandro@gmail.com");
        usuario1.setSenha("654321");
        usuario1.setUser("Alerreandro");

        usuario2.setEmail("alerreandro@gmail.com");
        usuario2.setSenha("754841");
        usuario2.setUser("Tobias");

        this.serviceUsuario.salvarUsuario(usuario1);

        Assert.assertThrows(EmailExistsException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario2);
        });
    }

    @Test
    @Transactional
    public void loginUser() throws Exception {
        Usuario usuario = new Usuario();

        usuario.setEmail("joeliton@gmail.com");
        usuario.setSenha("556575");
        usuario.setUser("Joeliton");

        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioLogado = this.serviceUsuario.loginUser(usuario.getUser(), (Util.md5("556575")));

        Assert.assertEquals(usuarioLogado.getId(), usuario.getId());
    }

    @Test
    @Transactional
    public void lancarExcecaoCriptografada() {
        Usuario usuario = new Usuario();

        usuario.setEmail("yasmin@gmail.com");
        usuario.setSenha("125457");
        usuario.setUser("Yasmin");

        try {
            this.serviceUsuario.salvarUsuario(usuario);
        } catch (NoSuchAlgorithmException e) {
            assertEquals("Error na criptografia da senha", e.getMessage());
        } catch (Exception e) {
            Assertions.fail("Deveria ter lançado CriptoExistsException");
        }
    }
}