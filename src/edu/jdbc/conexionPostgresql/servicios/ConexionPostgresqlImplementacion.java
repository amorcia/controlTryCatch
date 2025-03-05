package edu.jdbc.conexionPostgresql.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Implementación de la interfaz de conexión a PostgreSQL 220923 - rfg
 */
public class ConexionPostgresqlImplementacion implements ConexionPostgresqlInterfaz {

	@Override
	public Connection generaConexion() {

		Connection conexion = null;
		String[] parametrosConexion;
		try {
			parametrosConexion = configuracionConexion(); // url, user, pass
			boolean esValida;

			if (!parametrosConexion[2].isEmpty()) { // Se controla que los parámetros de conexión se completen
				// Instancia un objeto de la clase interfaz que se le pasa
				Class.forName("org.postgresql.Driver");

				// Se establece la conexión
				// Si pgadmin no tiene abierta la bd, no será posible establecer conexion contra
				// ella

				conexion = DriverManager.getConnection(parametrosConexion[0], parametrosConexion[1],
						parametrosConexion[2]);
				esValida = conexion.isValid(50000);
				if (esValida == false) {
					conexion = null;
				}
				System.out.println(esValida
						? "[INFORMACIÓN-ConexionPostgresqlImplementacion-generaConexion] Conexión a PostgreSQL válida"
						: "[ERROR-ConexionPostgresqlImplementacion-generaConexion] Conexión a PostgreSQL no válida");

			} else {
				System.out.println(
						"[ERROR-ConexionPostgresqlImplementacion-generaConexion] Los parametros de conexion no se han establecido correctamente");
				conexion = null;
			}
		} catch (SQLException e) {
			System.err.println("[Método generarConexion - ConexionPostgresqlImplementacion.java] Error en la conexion a la base de datos");
		} catch (ClassNotFoundException e) {
			System.err.println("[Método generarConexion - ConexionPostgresqlImplementacion.java] La clase no ha sido bien creada o encontrada");
		} catch (Exception e) {
			throw e;
		}
		return conexion;
	}

	/**
	 * Método configura los parámetros de la conexión de
	 * conexion_postgresql.properties 221023 - rfg return ventor de string con: url,
	 * user, pass
	 */
	private String[] configuracionConexion() {

		String user = "", pass = "", port = "", host = "", db = "", url = "";
		String[] stringConfiguracion = { "", "", "" };

		Properties propiedadesConexion = new Properties();

		try {
			propiedadesConexion.load(new FileInputStream(new File(
					"C:\\Users\\Trabajo\\dws-workspace\\edu.jdbc.crud\\src\\edu\\jdbc\\crud\\util\\conexion_postgresql.properties")));
			user = propiedadesConexion.getProperty("user");
			pass = propiedadesConexion.getProperty("pass");
			port = propiedadesConexion.getProperty("port");
			host = propiedadesConexion.getProperty("host");
			db = propiedadesConexion.getProperty("db");
			url = "jdbc:postgresql://" + host + ":" + port + "/" + db;
			stringConfiguracion[0] = url;
			stringConfiguracion[1] = user;
			stringConfiguracion[2] = pass;
		} catch (IOException e) {
			System.err.println("[Método configuracionConexion - ConexionPostgresqlImplementacion.java] Error al cargar el archivo/fichero");
		} catch (Exception e) {
			throw e;
		}
		return stringConfiguracion;
	}
}
