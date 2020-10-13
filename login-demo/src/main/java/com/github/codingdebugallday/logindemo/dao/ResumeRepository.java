package com.github.codingdebugallday.logindemo.dao;

import com.github.codingdebugallday.logindemo.pojo.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/09/22 10:25
 * @since 1.0.0
 */
public interface ResumeRepository extends JpaRepository<Resume, Long>, JpaSpecificationExecutor<Resume> {

}
