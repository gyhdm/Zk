package com.zk.sms.common.service.impl;

import com.zk.sms.common.exception.JpaCrudException;
import com.zk.sms.common.model.BaseModel;
import com.zk.sms.common.service.BaseService;
import com.zk.sms.common.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 通用service实现类.
 *
 * @param <T>  实体类
 * @param <ID> 主键
 * @param <R>  repository
 * @author guoying
 * @since 2019 -10-27 19:49:33
 */
@Slf4j
public class BaseServiceImpl<T extends BaseModel, ID, R extends JpaRepository<T, ID>> implements BaseService<T, ID> {

    /**
     * The Repository.
     */
    @Autowired
    protected R repository;

    @Override
    public T findById(ID id) {
        log.info("findById: {}", id);
        Optional<T> ot = repository.findById(id);
        return ot.orElse(null);
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        log.info("findAll PageNumber: {} ---> PageSize: {}", pageable.getPageNumber(), pageable.getPageSize());
        return repository.findAll(pageable);
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        log.info("findAllById: {} ", ids);
        return repository.findAllById(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T save(T t) {
        if (t == null) {
            throw new JpaCrudException("cannot save an empty entity class.");
        }
        if (ObjectUtils.isNotEmpty(t.getId())) {
            throw new JpaCrudException("for save entity, id must be null.");
        }

        log.info("save: {}", t);
        return repository.save(t);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T update(T t) {
        if (t == null) {
            throw new JpaCrudException("cannot update an empty entity class.");
        }
        if (ObjectUtils.isEmpty(t.getId())) {
            throw new JpaCrudException("for update entity, id must be not null.");
        }
        log.info("update: {}", t);
        return repository.save(t);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<T> saveAll(Iterable<T> entities) {
        return repository.saveAll(entities);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(T t) {
        if (t == null) {
            throw new JpaCrudException("cannot delete an empty entity class.");
        }
        log.info("delete:{}", t);
        repository.delete(t);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAll(Iterable<T> entities) {
        repository.deleteAll(entities);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(ID id) {
        if (id == null || !repository.existsById(id)) {
            throw new JpaCrudException("Unable to delete data whose ID does not exist");
        }
        log.info("deleteById: {}", id);
        repository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteInBatch(Iterable<T> entities) {
        repository.deleteInBatch(entities);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
    }

    @Override
    public long count() {
        return repository.count();
    }
}
