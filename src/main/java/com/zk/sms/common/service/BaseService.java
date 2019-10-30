package com.zk.sms.common.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 通用service接口.
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 * @author guoying
 * @since 2019 -10-27 19:46:30
 */
public interface BaseService<T, ID> {
    /**
     * Gets one.
     *
     * @param id the id
     * @return the one
     */
    T findById(ID id);

    /**
     * Exists by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @author guoying
     * @since 2019 /10/27
     */
    boolean existsById(ID id);

    /**
     * Find all list.
     *
     * @return the list
     * @author guoying
     * @since 2019 /10/27
     */
    List<T> findAll();

    /**
     * Find all list.
     *
     * @param sort the sort
     * @return the list
     * @author guoying
     * @since 2019 /10/27
     */
    List<T> findAll(Sort sort);

    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     * @author guoying
     * @since 2019 /10/27
     */
    Page<T> findAll(Pageable pageable);

    /**
     * Find all by id list.
     *
     * @param ids the ids
     * @return the list
     * @author guoying
     * @since 2019 /10/27
     */
    List<T> findAllById(Iterable<ID> ids);

    /**
     * Save t.
     *
     * @param t the t
     * @return the t
     * @author guoying
     * @since 2019 /10/27
     */
    T save(T t);

    @Transactional(rollbackFor = Exception.class)
    T update(T t);

    /**
     * Save all list.
     *
     * @param entities the entities
     * @return the list
     * @author guoying
     * @since 2019 /10/27
     */
    List<T> saveAll(Iterable<T> entities);

    /**
     * Delete.
     *
     * @param t the t
     * @author guoying
     * @since 2019 /10/27
     */
    void delete(T t);

    /**
     * Delete all.
     *
     * @author guoying
     * @since 2019 /10/27
     */
    void deleteAll();

    /**
     * Delete all.
     *
     * @param entities the entities
     * @author guoying
     * @since 2019 /10/27
     */
    void deleteAll(Iterable<T> entities);

    /**
     * Delete by id.
     *
     * @param id the id
     * @author guoying
     * @since 2019 /10/27
     */
    void deleteById(ID id);

    /**
     * Delete in batch.
     *
     * @param entities the entities
     * @author guoying
     * @since 2019 /10/27
     */
    void deleteInBatch(Iterable<T> entities);

    /**
     * Delete all in batch.
     *
     * @author guoying
     * @since 2019 /10/27
     */
    void deleteAllInBatch();

    /**
     * Count long.
     *
     * @return the long
     * @author guoying
     * @since 2019 /10/27
     */
    long count();
}
