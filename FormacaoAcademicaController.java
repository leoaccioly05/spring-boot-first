package com.seuapp.first_spring_app.controller;

import com.seuapp.first_spring_app.dto.FormacaoAcademicaDTO;
import com.seuapp.first_spring_app.enums.NivelFormacao;
import com.seuapp.first_spring_app.mapper.FormacaoAcademicaMapper;
import com.seuapp.first_spring_app.model.FormacaoAcademica;
import com.seuapp.first_spring_app.repository.FormacaoAcademicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/formacoes-academicas")
public class FormacaoAcademicaController {

    @Autowired
    private FormacaoAcademicaRepository formacaoAcademicaRepository;

    @Autowired
    private FormacaoAcademicaMapper mapper;

    @GetMapping
    public List<FormacaoAcademicaDTO> listarFormacoes() {
        return formacaoAcademicaRepository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public FormacaoAcademicaDTO criarFormacao(@RequestBody FormacaoAcademicaDTO dto) {
        FormacaoAcademica entity = mapper.toEntity(dto);
        return mapper.toDTO(formacaoAcademicaRepository.save(entity));
    }

    @GetMapping("/{id}")
    public FormacaoAcademicaDTO buscarPorId(@PathVariable Long id) {
        FormacaoAcademica formacao = formacaoAcademicaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Formação não encontrada: " + id));
        return mapper.toDTO(formacao);
    }

    @PutMapping("/{id}")
    public FormacaoAcademicaDTO atualizarFormacao(@PathVariable Long id, @RequestBody FormacaoAcademicaDTO atualizada) {
        FormacaoAcademica formacao = formacaoAcademicaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Formação não encontrada: " + id));

        formacao.setInstituicao(atualizada.getInstituicao());
        formacao.setCurso(atualizada.getCurso());
        formacao.setNivel(atualizada.getNivel());

        return mapper.toDTO(formacaoAcademicaRepository.save(formacao));
    }

    @DeleteMapping("/{id}")
    public void deletarFormacao(@PathVariable Long id) {
        formacaoAcademicaRepository.deleteById(id);
    }

    @GetMapping("/nivel/{nivel}")
    public List<FormacaoAcademicaDTO> buscarPorNivel(@PathVariable NivelFormacao nivel) {
        return formacaoAcademicaRepository.findAll().stream()
                .filter(f -> f.getNivel().equals(nivel))
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}