package br.com.gerenciamento.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }


@Test
@Transactional
public void salvarSemId() {
    Aluno aluno = new Aluno();

    aluno.setNome("Josue");
    aluno.setTurno(Turno.MATUTINO);
    aluno.setCurso(Curso.ENFERMAGEM);
    aluno.setStatus(Status.ATIVO);
    aluno.setMatricula("789");

    this.serviceAluno.save(aluno);

    if (aluno.getId() == null) {
        Assert.fail("Aluno salvo sem id");
    }
}

@Test
@Transactional
public void findAll() {
    Aluno aluno1 = new Aluno();
    Aluno aluno2 = new Aluno();

    aluno1.setNome("Alerreandro");
    aluno1.setTurno(Turno.MATUTINO);
    aluno1.setCurso(Curso.ADMINISTRACAO);
    aluno1.setStatus(Status.ATIVO);
    aluno1.setMatricula("157");

    aluno2.setNome("Isaac");
    aluno2.setTurno(Turno.NOTURNO);
    aluno2.setCurso(Curso.INFORMATICA);
    aluno2.setStatus(Status.ATIVO);
    aluno2.setMatricula("234");

    this.serviceAluno.save(aluno1);
    this.serviceAluno.save(aluno2);

    List<Aluno> alunos = this.serviceAluno.findAll();

    Assert.assertEquals(2, alunos.size());

    if (alunos.get(0).getId() == aluno1.getId()) {
        Assert.assertEquals(alunos.get(1).getId(), aluno2.getId());
    } else if (alunos.get(0).getId() == aluno2.getId()) {
        Assert.assertEquals(alunos.get(2).getId(), aluno1.getId());
    } else {
        Assert.fail();
    }
}

@Test
@Transactional
public void deleteById() {
    Aluno aluno = new Aluno();

    aluno.setId(1L);
    aluno.setNome("Jose Costa");
    aluno.setTurno(Turno.MATUTINO);
    aluno.setCurso(Curso.BIOMEDICINA);
    aluno.setStatus(Status.ATIVO);
    aluno.setMatricula("345");

    this.serviceAluno.save(aluno);

    if (aluno.getId() == null) {
        Assert.fail("Aluno salvo sem id");
    } else {
        this.serviceAluno.deleteById(1L);
    }
}

@Test
@Transactional
public void findByStatusInativo() {
    Aluno aluno1 = new Aluno();
    Aluno aluno2 = new Aluno();

    aluno1.setNome("Arthur");
    aluno1.setTurno(Turno.NOTURNO);
    aluno1.setCurso(Curso.DIREITO);
    aluno1.setStatus(Status.INATIVO);
    aluno1.setMatricula("321");

    aluno2.setNome("Mateus");
    aluno2.setTurno(Turno.NOTURNO);
    aluno2.setCurso(Curso.CONTABILIDADE);
    aluno2.setStatus(Status.ATIVO);
    aluno2.setMatricula("456");

    this.serviceAluno.save(aluno1);
    this.serviceAluno.save(aluno2);

    List<Aluno> alunosInativos = this.serviceAluno.findByStatusInativo();

    if (alunosInativos.isEmpty()) {
        Assert.fail("Lista de alunos inativos está vazia");
    } else {
        assertTrue(alunosInativos.stream().allMatch(a -> Status.INATIVO.equals(a.getStatus())));
    }
}
}
