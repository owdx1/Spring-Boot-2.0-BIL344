package com.example.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository
        extends JpaRepository<BmiUser , Integer> {

    @Query(value = "SELECT * FROM nView", nativeQuery = true)
    List<BmiUser> findAllFromNView();

    @Query(value = "SELECT * FROM ascendingView" , nativeQuery = true)
    List<BmiUser> findAllAscendingId();

    @Query(value = "SELECT * FROM filterByName" , nativeQuery = true)
    List<BmiUser> filterByName();

}

