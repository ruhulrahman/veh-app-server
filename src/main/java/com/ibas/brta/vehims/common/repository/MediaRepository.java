package com.ibas.brta.vehims.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.common.model.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

}
