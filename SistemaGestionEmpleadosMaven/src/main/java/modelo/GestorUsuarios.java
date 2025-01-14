package modelo;

import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class GestorUsuarios {

    private Db db; // Referencia a la clase Db para gestionar la conexión a la base de datos

    public GestorUsuarios(Db db) {
        this.db = db;
    }

    // Método para insertar un usuario en la base de datos
    public boolean insertarUsuario(Usuario usuario) {
        Session session = null;
        try {
            // Abre una sesión de Hibernate
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            // Guarda el usuario
            session.save(usuario);

            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null) {
                session.getTransaction().rollback(); // Revertir si ocurre un error
            }
            return false;
        } finally {
            if (session != null) {
                session.close(); // Cierra la sesión de Hibernate
            }
        }

        // Código viejo con JDBC (comentado para referencia futura)
        /*
        String sql = "INSERT INTO Usuarios (DNI, Nombre, Apellidos, Email, Telefono, Rol, Password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = db.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, usuario.getDni());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getApellidos());
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getTelefono());
            stmt.setString(6, usuario.getRol().name());
            stmt.setString(7, usuario.getPassword());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        */
    }

    // Método para obtener todos los usuarios de la base de datos
    public List<Usuario> obtenerTodosLosUsuarios() {
        Session session = null;
        try {
            // Abre una sesión de Hibernate
            session = HibernateUtil.getSessionFactory().openSession();

            // Consulta HQL para obtener todos los usuarios
            String hql = "FROM Usuario";
            Query<Usuario> query = session.createQuery(hql, Usuario.class);

            // Ejecuta la consulta y obtiene la lista de usuarios
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); // Cierra la sesión de Hibernate
            }
        }
        return null;

        // Código viejo con JDBC (comentado para referencia futura)
        /*
        String sql = "SELECT * FROM Usuarios";
        try (Connection conn = db.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {
            List<Usuario> usuarios = new ArrayList<>();
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("ID"),
                        rs.getString("DNI"),
                        rs.getString("Nombre"),
                        rs.getString("Apellidos"),
                        rs.getString("Email"),
                        rs.getString("Telefono"),
                        Usuario.Rol.valueOf(rs.getString("Rol")),
                        rs.getString("Password")
                );
                usuarios.add(usuario);
            }
            return usuarios;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
        */
    }

    // Método para actualizar un usuario existente en la base de datos
    public boolean actualizarUsuario(Usuario usuario) {
        Session session = null;
        try {
            // Abre una sesión de Hibernate
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            // Actualiza el usuario
            session.update(usuario);

            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null) {
                session.getTransaction().rollback(); // Revertir si ocurre un error
            }
            return false;
        } finally {
            if (session != null) {
                session.close(); // Cierra la sesión de Hibernate
            }
        }

        // Código viejo con JDBC (comentado para referencia futura)
        /*
        String sql = "UPDATE Usuarios SET DNI = ?, Nombre = ?, Apellidos = ?, Email = ?, Telefono = ?, Rol = ?, Password = ? WHERE ID = ?";
        try (Connection conn = db.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, usuario.getDni());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getApellidos());
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getTelefono());
            stmt.setString(6, usuario.getRol().name());
            stmt.setString(7, usuario.getPassword());
            stmt.setInt(8, usuario.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        */
    }

    // Método para eliminar un usuario por su ID
    public boolean eliminarUsuario(int id) {
        Session session = null;
        try {
            // Abre una sesión de Hibernate
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            // HQL para eliminar un usuario por ID
            String hql = "DELETE FROM Usuario WHERE id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);

            // Ejecuta la eliminación
            int result = query.executeUpdate();
            session.getTransaction().commit();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null) {
                session.getTransaction().rollback(); // Revertir si ocurre un error
            }
            return false;
        } finally {
            if (session != null) {
                session.close(); // Cierra la sesión de Hibernate
            }
        }

        // Código viejo con JDBC (comentado para referencia futura)
        /*
        String sql = "DELETE FROM Usuarios WHERE ID = ?";
        try (Connection conn = db.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        */
    }

    // Método para buscar un usuario por su ID
    public Usuario buscarUsuarioPorId(int id) {
        Session session = null;
        try {
            // Abre una sesión de Hibernate
            session = HibernateUtil.getSessionFactory().openSession();

            // Consulta HQL para buscar un usuario por ID
            String hql = "FROM Usuario WHERE id = :id";
            Query<Usuario> query = session.createQuery(hql, Usuario.class);
            query.setParameter("id", id);

            // Ejecuta la consulta y devuelve el resultado
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); // Cierra la sesión de Hibernate
            }
        }
        return null;

        // Código viejo con JDBC (comentado para referencia futura)
        /*
        String sql = "SELECT * FROM Usuarios WHERE ID = ?";
        try (Connection conn = db.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("ID"),
                            rs.getString("DNI"),
                            rs.getString("Nombre"),
                            rs.getString("Apellidos"),
                            rs.getString("Email"),
                            rs.getString("Telefono"),
                            Usuario.Rol.valueOf(rs.getString("Rol")),
                            rs.getString("Password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
        */
    }

    // Método para autenticar un usuario por nombre y contraseña
    public boolean autenticarUsuario(String nombre, String password) {
        Session session = null;
        try {
            // Abre una sesión de Hibernate
            session = HibernateUtil.getSessionFactory().openSession();

            // HQL para autenticar un usuario
            String hql = "SELECT COUNT(*) FROM Usuario WHERE nombre = :nombre AND password = :password";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("nombre", nombre);
            query.setParameter("password", password);

            // Ejecuta la consulta y devuelve si el usuario existe
            return query.uniqueResult() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); // Cierra la sesión de Hibernate
            }
        }
        return false;

        // Código viejo con JDBC (comentado para referencia futura)
        /*
        String sql = "SELECT COUNT(*) FROM Usuarios WHERE Nombre = ? AND Password = ?";
        try (Connection conn = db.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, nombre);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) { 
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
        */
    }
}
