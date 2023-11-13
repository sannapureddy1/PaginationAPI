package com.pagination.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pagination.entity.Books;

@Repository
public interface IBookRepository extends JpaRepository<Books, Long>{

}
