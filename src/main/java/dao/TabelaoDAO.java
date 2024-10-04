package dao;

import model.Tabelao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TabelaoDAO {

    private Session session;

    public TabelaoDAO(Session session) {
        this.session = session;
    }

    public void salvar(Tabelao tabelao) {
        session.save(tabelao);
    }

    public void atualizar(Tabelao tabelao) {
        session.update(tabelao);
    }

    public void deletar(Tabelao tabelao) {
        session.delete(tabelao);
    }

    public Tabelao buscarPorId(int id) {
        return (Tabelao) session.get(Tabelao.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Tabelao> listarTodos() {
        return session.createQuery("FROM Tabelao").list();
    }

    // Outros métodos específicos, se necessário
}
