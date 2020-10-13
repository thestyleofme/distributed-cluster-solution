package com.github.codingdebugallday.logindemo.service;

import java.util.List;

import com.github.codingdebugallday.logindemo.pojo.Resume;


/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/09/22 10:23
 * @since 1.0.0
 */
public interface ResumeService {

    /**
     * 查询所有简历
     *
     * @return List<Resume>
     */
    List<Resume> list();

    /**
     * 根据主键获取简历信息
     *
     * @param id id
     * @return Resume
     */
    Resume getById(Long id);

    /**
     * 新增简历
     *
     * @param resume Resume
     */
    void insert(Resume resume);

    /**
     * 删除简历
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 更新简历
     *
     * @param resume Resume
     */
    void update(Resume resume);
}
