package dao;

import model.Cadastrar;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CadastrarDAO {
    
    // Método de conexão com o banco Azure PostgreSQL
    private Connection conectar() throws SQLException {
        String url = "jdbc:postgresql://SEU_SERVIDOR_AZURE.postgres.database.azure.com:5432/SEU_BANCO";
        String user = "SEU_USUARIO";
        String password = "SUA_SENHA";
        return DriverManager.getConnection(url, user, password);
    }

    public boolean criar(Cadastrar problema) {
        String sql = "INSERT INTO problema (titulo, descricao, rua, numero, bairro, foto_url, data_criacao, id_usuario, id_cidade, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, problema.getTitulo());
            stmt.setString(2, problema.getDescricao());
            stmt.setString(3, problema.getRua());
            stmt.setString(4, problema.getNumero());
            stmt.setString(5, problema.getBairro());
            stmt.setString(6, problema.getFotoUrl());
            stmt.setTimestamp(7, Timestamp.valueOf(problema.getDataCriacao()));
            stmt.setInt(8, problema.getIdUsuario());
            stmt.setInt(9, problema.getIdCidade());
            stmt.setString(10, problema.getStatus());
            
            int resultado = stmt.executeUpdate();
            return resultado > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao criar problema: " + e.getMessage());
            return false;
        }
    }

    public Cadastrar buscarPorId(Long id) {
        String sql = "SELECT * FROM problema WHERE id_problema = ?";
        
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarProblemaFromResultSet(rs);
            }
            return null;
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar problema por ID: " + e.getMessage());
            return null;
        }
    }

    public List<Cadastrar> listarTodos() {
        List<Cadastrar> problemas = new ArrayList<>();
        String sql = "SELECT * FROM problema ORDER BY data_criacao DESC";
        
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                problemas.add(criarProblemaFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao listar problemas: " + e.getMessage());
        }
        
        return problemas;
    }

    public List<Cadastrar> buscarPorCidade(Integer idCidade) {
        List<Cadastrar> problemas = new ArrayList<>();
        String sql = "SELECT * FROM problema WHERE id_cidade = ? ORDER BY data_criacao DESC";
        
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCidade);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                problemas.add(criarProblemaFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar problemas por cidade: " + e.getMessage());
        }
        
        return problemas;
    }

    public boolean atualizarStatus(Long idProblema, String status) {
        String sql = "UPDATE problema SET status = ? WHERE id_problema = ?";
        
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setLong(2, idProblema);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar status do problema: " + e.getMessage());
            return false;
        }
    }

    public boolean deletar(Long id) {
        String sql = "DELETE FROM problema WHERE id_problema = ?";
        
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao deletar problema: " + e.getMessage());
            return false;
        }
    }

    private Cadastrar criarProblemaFromResultSet(ResultSet rs) throws SQLException {
        Cadastrar problema = new Cadastrar();
        problema.setIdProblema(rs.getLong("id_problema"));
        problema.setTitulo(rs.getString("titulo"));
        problema.setDescricao(rs.getString("descricao"));
        problema.setRua(rs.getString("rua"));
        problema.setNumero(rs.getString("numero"));
        problema.setBairro(rs.getString("bairro"));
        problema.setFotoUrl(rs.getString("foto_url"));
        problema.setDataCriacao(rs.getTimestamp("data_criacao").toLocalDateTime());
        problema.setIdUsuario(rs.getInt("id_usuario"));
        problema.setIdCidade(rs.getInt("id_cidade"));
        problema.setStatus(rs.getString("status"));
        return problema;
    }
}