package com.seuapp.first_spring_app.controller;

import com.seuapp.first_spring_app.model.Curriculum;
import com.seuapp.first_spring_app.repository.CurriculumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/curriculos")
public class CurriculumController {

    @Autowired
    private CurriculumRepository curriculumRepository;

    @GetMapping
    public List<Curriculum> listarCurriculos() {
        return curriculumRepository.findAll();
    }

    @PostMapping
    public Curriculum criarCurriculo(@RequestBody Curriculum curriculum) {
        return curriculumRepository.save(curriculum);
    }

    @GetMapping("/{id}")
    public Curriculum buscarPorId(@PathVariable Long id) {
        return curriculumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Currículo não encontrado: " + id));
    }

    @PutMapping("/{id}")
    public Curriculum atualizarCurriculo(@PathVariable Long id, @RequestBody Curriculum atualizado) {
        Curriculum curriculum = curriculumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Currículo não encontrado: " + id));

        curriculum.setNome(atualizado.getNome());
        curriculum.setEndereco(atualizado.getEndereco());
        curriculum.setTelefone(atualizado.getTelefone());

        return curriculumRepository.save(curriculum);
    }

    @DeleteMapping("/{id}")
    public void deletarCurriculo(@PathVariable Long id) {
        curriculumRepository.deleteById(id);
    }
}