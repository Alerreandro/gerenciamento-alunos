package br.com.gerenciamento.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {
    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    @Transactional
    public void save() {
        Aluno aluno = new Aluno();

        aluno.setNome("Alerreandro");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123");

        this.alunoRepository.save(aluno);

        Aluno alunoEncontrado = this.alunoRepository.findById(aluno.getId()).orElse(null);

        Assert.assertTrue(alunoEncontrado.getNome().equals("Alerreandro"));
    }

    @Test
    @Transactional
    public void findByNomeContainingIgnoreCase() {
        Aluno aluno1 = new Aluno();
        Aluno aluno2 = new Aluno();

        aluno1.setNome("Johany");
        aluno1.setTurno(Turno.MATUTINO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123");

        aluno2.setNome("Marcelino");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("234");

        this.alunoRepository.save(aluno1);
        this.alunoRepository.save(aluno2);

        List<Aluno> alunosEncontrados = alunoRepository.findByNomeContainingIgnoreCase("Marcelino");

        Assert.assertEquals(1, alunosEncontrados.size());

        Assert.assertTrue(alunosEncontrados.stream().anyMatch(aluno -> aluno.getNome().equals("Marcelino")));
    }

    @Test
    @Transactional
    public void saveInvalidName() {
        Aluno aluno = new Aluno();

        aluno.setNome("Tic");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("567");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.alunoRepository.save(aluno);
        });
    }

    @Test
    @Transactional
    public void findByStatusInativo() {
        Aluno aluno1 = new Aluno();
        Aluno aluno2 = new Aluno();

        aluno1.setNome("Gustavo");
        aluno1.setTurno(Turno.MATUTINO);
        aluno1.setCurso(Curso.BIOMEDICINA);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("987");

        aluno2.setNome("Henrique");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.DIREITO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("432");

        this.alunoRepository.save(aluno1);
        this.alunoRepository.save(aluno2);

        List<Aluno> alunosInativos = this.alunoRepository.findByStatusInativo();

        Assert.assertEquals(1, alunosInativos.size());

        Assert.assertTrue(alunosInativos.stream().anyMatch(aluno -> aluno.getNome().equals("Henrique")));
    }
}