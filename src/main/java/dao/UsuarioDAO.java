package dao;

import model.Usuario;
import org.hibernate.Session;

import java.util.List;

public class UsuarioDAO {

    private Session session;

    public UsuarioDAO(Session session) {
        this.session = session;
    }

    public void salvar(Usuario usuario) {
        session.save(usuario);
    }

    public void atualizar(Usuario usuario) {
        session.update(usuario);
    }

    public void deletar(Usuario usuario) {
        session.delete(usuario);
    }

    public Usuario buscarPorId(int id) {
        return (Usuario)  session.get(Usuario.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Usuario> listarTodos() {
        return session.createQuery("FROM Usuario").list();
    }
}
