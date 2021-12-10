package com.sierra.files.upload.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sierra.files.upload.model.ResourceEntity;

@Transactional
public interface ResourceRepository extends CrudRepository <ResourceEntity, String> {

}
