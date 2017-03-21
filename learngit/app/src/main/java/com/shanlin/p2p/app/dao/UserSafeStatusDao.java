package com.shanlin.p2p.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shanlin.p2p.app.model.UserSafeStatus;

public interface UserSafeStatusDao extends JpaRepository<UserSafeStatus, Long>{

}
