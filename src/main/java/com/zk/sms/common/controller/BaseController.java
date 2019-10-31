package com.zk.sms.common.controller;

import com.zk.sms.common.model.BaseModel;
import com.zk.sms.common.model.ResultBody;
import com.zk.sms.common.service.BaseService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Base controller.
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 * @param <S>  the type parameter
 * @author guoying
 * @since 2019 -10-27 22:39:09
 */
//@Slf4j
public abstract class BaseController<T extends BaseModel, ID, S extends BaseService<T, ID>> {
    private Logger log = LoggerFactory.getLogger(getClass());
    /**
     * The Service.
     */
    @Autowired
    protected S service;

    /**
     * Find by id result body.
     *
     * @param id the id
     * @return the result body
     * @author guoying
     * @since 2019 /10/27
     */
    @ApiOperation(value = "单条查询", notes = "根据ID查询")
    @GetMapping("/{id}")
    public ResultBody<T> findById(@PathVariable ID id) {
        return ResultBody.success(service.findById(id));
    }

    /**
     * Find all result body.
     *
     * @return the result body
     * @author guoying
     * @since 2019 /10/27
     */
    @ApiOperation(value = "查询全部", notes = "查询全部")
    @GetMapping("/list")
    public ResultBody<List<T>> findAll() {
        return ResultBody.success(service.findAll());
    }

    /**
     * Find all result body.
     * http://localhost:8080/demo?page=1&size=15&sort=myfield,DESC&sort=yourfield,ASC
     *
     * @param pageable the pageable
     * @return the result body
     * @author guoying
     * @since 2019 /10/27
     */
    @ApiOperation(value = "分页查询全部", notes = "分页查询全部")
    @GetMapping("/page")
    public ResultBody<Page<T>> findAll(@PageableDefault(sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResultBody.success(service.findAll(pageable));
    }

    /**
     * Save result body.
     *
     * @param entity the entity
     * @return the result body
     * @author guoying
     * @since 2019 /10/27
     */
    @ApiOperation(value = "新增", notes = "ID必须为空")
    @PostMapping("create")
    public ResultBody save(@RequestBody T entity) {
        ResultBody resultBody = null;
        try {
            resultBody = ResultBody.success(service.save(entity));
        } catch (Exception e) {
            log.error("保存实体发生错误: {}", entity, e);
            resultBody = ResultBody.failure(e.getMessage(), e);
        }
        return resultBody;
    }

    /**
     * Update result body.
     *
     * @param entity the entity
     * @return the result body
     * @author guoying
     * @since 2019 /10/27
     */
    @ApiOperation(value = "修改", notes = "ID不能为空")
    @PutMapping("update")
    public ResultBody update(@RequestBody T entity) {
        ResultBody resultBody = null;
        try {
            resultBody = ResultBody.success(service.update(entity));
        } catch (Exception e) {
            log.error("更新实体发生错误: {}", entity, e);
            resultBody = ResultBody.failure(e.getMessage(), e);
        }
        return resultBody;
    }

    /**
     * Delete result body.
     *
     * @param id the id
     * @return the result body
     * @author guoying
     * @since 2019 /10/27
     */
    @ApiOperation(value = "删除", notes = "只需要id即可")
    @DeleteMapping("/delete/{id}")
    public ResultBody delete(@PathVariable ID id) {
        ResultBody resultBody = null;
        try {
            service.deleteById(id);
            resultBody = ResultBody.success("删除成功");
        } catch (Exception e) {
            log.error("根据主键删除实体发生错误: {}", id, e);
            resultBody = ResultBody.failure(e.getMessage(), null);
        }
        return resultBody;
    }
}
