/*
 * Rinat Muratidinov. Do not reproduce without permission in writing.
 * Copyright (c) 2022 RM. All rights reserved.
 */

package kg.rinat.dal.repository;

import kg.rinat.dal.entity.MedicationImageEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Medication image repository
 *
 * @author Rinat Muratidinov
 */
@Repository
public interface MedicationImageRepository extends CrudRepository<MedicationImageEntity, Long> {}
