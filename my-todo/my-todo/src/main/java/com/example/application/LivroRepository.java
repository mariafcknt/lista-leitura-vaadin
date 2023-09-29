package com.example.application;


import com.example.application.views.main.Livro;
import com.example.application.views.main.MainView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
}

