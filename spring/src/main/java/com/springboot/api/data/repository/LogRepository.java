package com.springboot.api.data.repository;

import com.springboot.api.data.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log,Long> {

}
