package com.simel.conexion;

// --- Imports Necesarios ---
import com.zaxxer.hikari.HikariConfig;       // Clase para configurar los parámetros del pool HikariCP.
import com.zaxxer.hikari.HikariDataSource;   // La implementación del pool de conexiones HikariCP (que es un DataSource).
import javax.sql.DataSource;               // Interfaz estándar de Java para representar un pool de conexiones o una fábrica de conexiones.
import java.sql.Connection;                // La conexión a la base de datos que usaremos.
import java.sql.SQLException;              // Para manejar errores relacionados con SQL o la base de datos.
// -------------------------

public class DataSourceProvider {

    // Objeto para guardar la configuración de HikariCP.
    private static HikariConfig config = new HikariConfig();
    // La instancia del pool de conexiones (DataSource). Será única para toda la aplicación.
    private static HikariDataSource ds;

    // --- Bloque de inicialización estático ---
    // Este bloque se ejecuta UNA SOLA VEZ, automáticamente, cuando la clase 
    // DataSourceProvider se carga por primera vez en la memoria de la JVM.
    // Es el lugar ideal para configurar y crear el pool, asegurando que solo exista una instancia.
    static {
        // --- Configuración básica de la conexión a la BD ---
        config.setJdbcUrl("jdbc:mysql://localhost:3306/simel?serverTimezone=America/Lima&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL"); // La URL de tu base de datos MySQL.
        config.setUsername("root");        // Usuario de la base de datos.
        config.setPassword("chalan123");    // Contraseña del usuario de la base de datos.
        config.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Opcional: HikariCP suele detectar el driver automáticamente si está en el classpath (JDBC 4.0+).

        // --- Configuración del tamaño y comportamiento del Pool ---
        // Número máximo de conexiones (físicas) que el pool puede mantener abiertas simultáneamente.
        // Ajusta este valor según la carga esperada de tu aplicación y los límites de tu servidor de BD.
        config.setMaximumPoolSize(10);
        // Número mínimo de conexiones que HikariCP intentará mantener inactivas en el pool, listas para ser usadas.
        // Ayuda a responder rápidamente a picos de demanda.
        config.setMinimumIdle(5);
        // Tiempo máximo (en milisegundos) que una petición esperará por una conexión del pool si todas están ocupadas.
        // Si se supera, se lanzará una SQLException. 30 segundos es un valor común.
        config.setConnectionTimeout(30000);
        // Tiempo máximo (en milisegundos) que una conexión puede permanecer inactiva en el pool antes de ser retirada (si el pool supera MinimumIdle).
        // 10 minutos (600000 ms) es un valor común.
        config.setIdleTimeout(600000);
        // Tiempo máximo (en milisegundos) de vida de una conexión en el pool (incluso si está activa).
        // Después de este tiempo, será retirada y reemplazada por una nueva. Ayuda a prevenir problemas con conexiones "viejas".
        // 30 minutos (1800000 ms) es un valor común.
        config.setMaxLifetime(1800000);

        // --- Optimizaciones: Caché de Prepared Statements (Recomendado para rendimiento) ---
        // Habilita el caché de PreparedStatements a nivel de conexión (si el driver JDBC lo soporta).
        config.addDataSourceProperty("cachePrepStmts", "true");
        // Número máximo de PreparedStatements que se guardarán en caché por conexión.
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        // Tamaño máximo (en bytes) del SQL de un PreparedStatement que se guardará en caché.
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        // --- Creación de la instancia del Pool (DataSource) ---
        try {
            // Creamos la instancia de HikariDataSource usando la configuración definida.
            ds = new HikariDataSource(config);
            // Mensaje para confirmar en los logs que el pool se inicializó.
            System.out.println("HikariCP DataSource inicializado.");
        } catch (Exception e) {
            System.err.println("Error fatal inicializando HikariDataSource: " + e);
            e.printStackTrace();  // Esto ya lo tienes, pero asegurate que se imprima bien.
        }

    } // --- Fin del bloque estático ---

    /**
     * Método público y estático para obtener una conexión del pool. Los DAOs
     * llamarán a este método.
     *
     * @return Una instancia de Connection lista para usar.
     * @throws SQLException Si no se puede obtener una conexión (ej: pool lleno
     * y timeout superado, o pool no inicializado).
     */
    public static Connection getConnection() throws SQLException {
        // Verificación de seguridad: asegura que el pool (ds) fue inicializado correctamente.
        if (ds == null) {
            System.err.println("¡ERROR! HikariDataSource no fue inicializado correctamente.");
            throw new SQLException("HikariDataSource no inicializado.");
        }
        // Pide una conexión al pool HikariCP.
        return ds.getConnection();
    }

    /**
     * Método estático para obtener la instancia del DataSource (el pool en sí).
     * Útil principalmente para el ServletContextListener, para poder cerrar el
     * pool al detener la aplicación.
     *
     * @return La instancia del HikariDataSource.
     */
    public static DataSource getDataSource() {
        // Verificación simple para logs si se intenta acceder antes de tiempo.
        if (ds == null) {
            System.err.println("¡ADVERTENCIA! Intentando obtener DataSource antes de inicializar.");
        }
        return ds;
    }

    // Constructor privado para prevenir que alguien cree instancias de esta clase.
    // Es una clase de utilidad con métodos estáticos, no necesita ser instanciada.
    private DataSourceProvider() {
    }
}
