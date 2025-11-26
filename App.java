package com.mapeando;

import static spark.Spark.port;
import com.mapeando.controller.UsuarioPrefeituraController;
import com.mapeando.controller.CadastrarController;

public class App {
    public static void main(String[] args) {
        // Configurar porta
        port(4567);
        
        // Registrar controllers
        UsuarioPrefeituraController usuarioPrefeituraController = new UsuarioPrefeituraController();
        usuarioPrefeituraController.registerRoutes();
        
        CadastrarController cadastrarController = new CadastrarController();
        cadastrarController.registerRoutes();
        
        // Servir arquivos estáticos
        staticFiles.location("/public");
        
        System.out.println("🚀 Servidor Spark Java rodando na porta 4567");
        System.out.println("📍 Acesse: http://localhost:4567");
        System.out.println("📋 API Base: http://localhost:4567/api/");
    }
}