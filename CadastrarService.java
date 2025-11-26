package service;

import dao.CadastrarDAO;
import model.Cadastrar;
import java.util.List;

public class CadastrarService {
    private CadastrarDAO cadastrarDAO;

    public CadastrarService() {
        this.cadastrarDAO = new CadastrarDAO();
    }

    public boolean cadastrarProblema(String titulo, String descricao, String rua, 
                                    String numero, String bairro, String fotoUrl, 
                                    Integer idUsuario, Integer idCidade) {
        
        // Validações básicas
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Título é obrigatório");
        }
        
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição é obrigatória");
        }
        
        if (idUsuario == null || idUsuario <= 0) {
            throw new IllegalArgumentException("ID do usuário é obrigatório");
        }
        
        if (idCidade == null || idCidade <= 0) {
            throw new IllegalArgumentException("ID da cidade é obrigatório");
        }

        Cadastrar problema = new Cadastrar();
        problema.setTitulo(titulo.trim());
        problema.setDescricao(descricao.trim());
        problema.setRua(rua != null ? rua.trim() : "");
        problema.setNumero(numero != null ? numero.trim() : "");
        problema.setBairro(bairro != null ? bairro.trim() : "");
        problema.setFotoUrl(fotoUrl != null ? fotoUrl.trim() : "");
        problema.setIdUsuario(idUsuario);
        problema.setIdCidade(idCidade);

        return cadastrarDAO.criar(problema);
    }

    public Cadastrar buscarProblemaPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return cadastrarDAO.buscarPorId(id);
    }

    public List<Cadastrar> listarTodosProblemas() {
        return cadastrarDAO.listarTodos();
    }

    public List<Cadastrar> buscarProblemasPorCidade(Integer idCidade) {
        if (idCidade == null || idCidade <= 0) {
            throw new IllegalArgumentException("ID da cidade inválido");
        }
        return cadastrarDAO.buscarPorCidade(idCidade);
    }

    public boolean atualizarStatusProblema(Long idProblema, String status) {
        if (idProblema == null || idProblema <= 0) {
            throw new IllegalArgumentException("ID do problema inválido");
        }
        
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status é obrigatório");
        }

        return cadastrarDAO.atualizarStatus(idProblema, status.trim());
    }

    public boolean deletarProblema(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return cadastrarDAO.deletar(id);
    }
}