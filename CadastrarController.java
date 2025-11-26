package controller;

import service.CadastrarService;
import static spark.Spark.*;
import com.google.gson.Gson;

public class CadastrarController {
    private CadastrarService cadastrarService;
    private Gson gson;

    public CadastrarController() {
        this.cadastrarService = new CadastrarService();
        this.gson = new Gson();
        setupRoutes();
    }

    private void setupRoutes() {
        // Configurar CORS
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "*");
            response.header("Access-Control-Allow-Headers", "*");
            response.type("application/json");
        });

        // Cadastrar novo problema
post("/api/problemas", (request, response) -> {
    try {
        String titulo = request.queryParams("titulo");
        String descricao = request.queryParams("descricao");
        String rua = request.queryParams("rua");
        String numero = request.queryParams("numero");
        String bairro = request.queryParams("bairro");
        String autor = request.queryParams("autor");
        String fotoUrl = request.queryParams("fotoUrl");
        
        // Valores temporários para teste
        Integer idUsuario = 1;
        Integer idCidade = 1;

        boolean sucesso = cadastrarService.cadastrarProblema(
            titulo, descricao, rua, numero, bairro, fotoUrl, idUsuario, idCidade
        );

        if (sucesso) {
            response.status(201);
            return "{\"success\": true, \"message\": \"Problema cadastrado com sucesso\"}";
        } else {
            response.status(400);
            return "{\"success\": false, \"message\": \"Erro ao cadastrar problema\"}";
        }

    } catch (Exception e) {
        response.status(400);
        return "{\"success\": false, \"message\": \"" + e.getMessage() + "\"}";
    }
});

        // Listar todos os problemas
        get("/api/problemas", (request, response) -> {
            try {
                var problemas = cadastrarService.listarTodosProblemas();
                return gson.toJson(problemas);
            } catch (Exception e) {
                response.status(500);
                return "{\"success\": false, \"message\": \"" + e.getMessage() + "\"}";
            }
        });

        // Buscar problema por ID
        get("/api/problemas/:id", (request, response) -> {
            try {
                Long id = Long.parseLong(request.params(":id"));
                var problema = cadastrarService.buscarProblemaPorId(id);
                
                if (problema != null) {
                    return gson.toJson(problema);
                } else {
                    response.status(404);
                    return "{\"success\": false, \"message\": \"Problema não encontrado\"}";
                }
            } catch (Exception e) {
                response.status(400);
                return "{\"success\": false, \"message\": \"" + e.getMessage() + "\"}";
            }
        });

        // Buscar problemas por cidade
        get("/api/problemas/cidade/:idCidade", (request, response) -> {
            try {
                Integer idCidade = Integer.parseInt(request.params(":idCidade"));
                var problemas = cadastrarService.buscarProblemasPorCidade(idCidade);
                return gson.toJson(problemas);
            } catch (Exception e) {
                response.status(400);
                return "{\"success\": false, \"message\": \"" + e.getMessage() + "\"}";
            }
        });

        // Atualizar status do problema
        put("/api/problemas/:id/status", (request, response) -> {
            try {
                Long id = Long.parseLong(request.params(":id"));
                String status = request.queryParams("status");
                
                boolean sucesso = cadastrarService.atualizarStatusProblema(id, status);
                
                if (sucesso) {
                    return "{\"success\": true, \"message\": \"Status atualizado com sucesso\"}";
                } else {
                    response.status(400);
                    return "{\"success\": false, \"message\": \"Erro ao atualizar status\"}";
                }
            } catch (Exception e) {
                response.status(400);
                return "{\"success\": false, \"message\": \"" + e.getMessage() + "\"}";
            }
        });

        // Deletar problema
        delete("/api/problemas/:id", (request, response) -> {
            try {
                Long id = Long.parseLong(request.params(":id"));
                boolean sucesso = cadastrarService.deletarProblema(id);
                
                if (sucesso) {
                    return "{\"success\": true, \"message\": \"Problema deletado com sucesso\"}";
                } else {
                    response.status(400);
                    return "{\"success\": false, \"message\": \"Erro ao deletar problema\"}";
                }
            } catch (Exception e) {
                response.status(400);
                return "{\"success\": false, \"message\": \"" + e.getMessage() + "\"}";
            }
        });
    }

    public void registerRoutes() {
        // Método para registrar as rotas no App principal
        setupRoutes();
    }
}