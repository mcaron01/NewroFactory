package com.oxyl.NewroFactory.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oxyl.NewroFactory.dto.back.PromotionEntity;

public interface PromotionRepository extends JpaRepository<PromotionEntity, Integer>{

}
