package dao;

import model.ControleImportacao;
import org.hibernate.Session;

import java.util.List;

public class ControleImportacaoDAO {

    private Session session;

    public ControleImportacaoDAO(Session session) {
        this.session = session;
    }

    public void salvar(ControleImportacao controle) {
        session.save(controle);
    }

    public void atualizar(ControleImportacao controle) {
        session.update(controle);
    }

    public void deletar(ControleImportacao controle) {
        session.delete(controle);
    }

    public ControleImportacao buscarPorId(int id) {
        return (ControleImportacao) session.get(ControleImportacao.class, id);
    }

    public List<Integer> buscarLinhasComErro(String nomeArquivo) {
        String hql = "SELECT c.linha FROM ControleImportacao c WHERE c.nomeArquivo = :nomeArquivo";
        return session.createQuery(hql)
                .setParameter("nomeArquivo", nomeArquivo)
                .list();
    }

    public void removerErro(String nomeArquivo, int linha) {
        String hql = "DELETE FROM ControleImportacao c WHERE c.nomeArquivo = :nomeArquivo AND c.linha = :linha";
        session.createQuery(hql)
                .setParameter("nomeArquivo", nomeArquivo)
                .setParameter("linha", linha)
                .executeUpdate();
    }

}
