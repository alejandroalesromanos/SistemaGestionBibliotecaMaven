package modelo;

import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;

public class GestorPrestamos {
    private Db db;

    public GestorPrestamos(Db db) {
        this.db = db;
    }

    // Método para insertar un préstamo
    public boolean insertarPrestamo(Prestamo prestamo) {
        Session session = null;
        try {
            // Abre una sesión de Hibernate
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            // Inserta el préstamo
            session.save(prestamo);

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
        String sql = "INSERT INTO Prestamos (ID_Libro, ID_Usuario, Fecha_Prestamo, Fecha_Devolucion, Multa) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, prestamo.getIdLibro());
            stmt.setInt(2, prestamo.getIdUsuario());
            stmt.setDate(3, new java.sql.Date(prestamo.getFechaPrestamo().getTime()));
            stmt.setDate(4, prestamo.getFechaDevolucion() != null ? new java.sql.Date(prestamo.getFechaDevolucion().getTime()) : null);
            stmt.setFloat(5, prestamo.getMulta());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        */
    }

    // Método para obtener todos los préstamos
    public List<Prestamo> obtenerTodosLosPrestamos() {
        List<Prestamo> prestamos = new ArrayList<>();
        Session session = null;
        try {
            // Abre una sesión de Hibernate
            session = HibernateUtil.getSessionFactory().openSession();

            // HQL para obtener todos los préstamos
            String hql = "FROM Prestamo";
            Query<Prestamo> query = session.createQuery(hql, Prestamo.class);

            // Ejecuta la consulta y obtiene el resultado
            prestamos = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); // Cierra la sesión de Hibernate
            }
        }
        return prestamos;

        // Código viejo con JDBC (comentado para referencia futura)
        /*
        String sql = "SELECT * FROM Prestamos";
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Prestamo prestamo = new Prestamo(
                        rs.getInt("ID"),
                        rs.getInt("ID_Libro"),
                        rs.getInt("ID_Usuario"),
                        rs.getDate("Fecha_Prestamo"),
                        rs.getDate("Fecha_Devolucion"),
                        rs.getFloat("Multa")
                );
                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
        */
    }

    // Método para actualizar un préstamo
    public boolean actualizarPrestamo(Prestamo prestamo) {
        Session session = null;
        try {
            // Abre una sesión de Hibernate
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            // Actualiza el préstamo
            session.update(prestamo);

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
        String sql = "UPDATE Prestamos SET ID_Libro = ?, ID_Usuario = ?, Fecha_Prestamo = ?, Fecha_Devolucion = ?, Multa = ? WHERE ID = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, prestamo.getIdLibro());
            stmt.setInt(2, prestamo.getIdUsuario());
            stmt.setDate(3, new java.sql.Date(prestamo.getFechaPrestamo().getTime()));
            stmt.setDate(4, prestamo.getFechaDevolucion() != null ? new java.sql.Date(prestamo.getFechaDevolucion().getTime()) : null);
            stmt.setFloat(5, prestamo.getMulta());
            stmt.setInt(6, prestamo.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        */
    }

    // Método para eliminar un préstamo
    public boolean eliminarPrestamo(int id) {
        Session session = null;
        try {
            // Abre una sesión de Hibernate
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            // HQL para eliminar el préstamo
            String hql = "DELETE FROM Prestamo WHERE id = :id";
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
        String sql = "DELETE FROM Prestamos WHERE ID = ?";
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

    // Método para buscar un préstamo por ID
    public Prestamo buscarPrestamoPorId(int id) {
        Session session = null;
        try {
            // Abre una sesión de Hibernate
            session = HibernateUtil.getSessionFactory().openSession();

            // HQL para buscar un préstamo por ID
            String hql = "FROM Prestamo WHERE id = :id";
            Query<Prestamo> query = session.createQuery(hql, Prestamo.class);
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

        // Código viejo con JDBC (comentado para referencia futura)
        /*
        String sql = "SELECT * FROM Prestamos WHERE ID = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Prestamo(
                            rs.getInt("ID"),
                            rs.getInt("ID_Libro"),
                            rs.getInt("ID_Usuario"),
                            rs.getDate("Fecha_Prestamo"),
                            rs.getDate("Fecha_Devolucion"),
                            rs.getFloat("Multa")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
        */
    }
}
