package com.github.codingdebugallday.logindemo.service.impl;

import java.util.List;
import java.util.Optional;

import com.github.codingdebugallday.logindemo.dao.ResumeRepository;
import com.github.codingdebugallday.logindemo.pojo.Resume;
import com.github.codingdebugallday.logindemo.service.ResumeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/09/22 10:24
 * @since 1.0.0
 */
@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeServiceImpl(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    @Override
    public List<Resume> list() {
        return resumeRepository.findAll();
    }

    @Override
    public Resume getById(Long id) {
        Optional<Resume> optional = resumeRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new IllegalArgumentException("not find resume by id[" + id + "]");
    }

    @Override
    public void insert(Resume resume) {
        resumeRepository.save(resume);
    }

    @Override
    public void delete(Long id) {
        resumeRepository.deleteById(id);
    }

    @Override
    public void update(Resume resume) {
        resumeRepository.save(resume);
    }
}
