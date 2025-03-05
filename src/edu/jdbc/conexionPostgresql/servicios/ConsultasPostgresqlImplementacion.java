package edu.jdbc.conexionPostgresql.servicios;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.jdbc.conexionPostgresql.dtos.LibroDto;
import edu.jdbc.conexionPostgresql.util.ADto;

/**
 * Implementación de la interfaz de consultas sobre Postgresql 220923-rfg
 */
public class ConsultasPostgresqlImplementacion implements ConsultasPostgresqlInterfaz {

	@Override
	public ArrayList<LibroDto> seleccionaTodosLibros(Connection conexionGenerada) {

		Statement declaracionSQL = null;
		ResultSet resultadoConsulta = null;
		ArrayList<LibroDto> listaLibros = new ArrayList<>();
		ADto adto = new ADto();

		// Se abre una declaración
		try {
			declaracionSQL = conexionGenerada.createStatement();
			resultadoConsulta = declaracionSQL.executeQuery("SELECT * FROM \"gbp_almacen\".\"gbp_alm_cat_libros\"");

			// Llamada a la conversión a dtoAlumno
			listaLibros = adto.resultsALibrosDto(resultadoConsulta);
			int i = listaLibros.size();
			System.out.println(
					"[INFORMACIÓN-ConsultasPostgresqlImplementacion-seleccionaTodosLibros] Número libros: " + i);

			System.out.println(
					"[INFORMACIÓN-ConsultasPostgresqlImplementacion-seleccionaTodosLibros] Cierre conexión, declaración y resultado");

			resultadoConsulta.close();
			declaracionSQL.close();
			conexionGenerada.close();
		} catch (SQLException e) {
			System.err.println("[Método seleccionaTodosLibros - ConsultasPostgresqlImplementacion.java] Error en la conexion a la base de datos");
		} catch (Exception e) {
			throw e;
		}

		return listaLibros;

	}

}
