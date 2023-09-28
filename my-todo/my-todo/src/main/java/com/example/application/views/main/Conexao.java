package com.example.application.views.main;

import java.sql.DriverManager;

public class Conexao {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/banco_livros";
        //Não estava conectando com a URL "jdbc:mysql:localhost:3306"

        //Adicionar biblioetaca mysql:mysql-connector-java

        try {
            DriverManager.getConnection(url, "root","root");
            System.out.println("Conexão estabelecida");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Falha ao estabelecer conexão");
        }
        
    }
}