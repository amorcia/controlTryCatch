package edu.jdbc.conexionPostgresql.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.jdbc.conexionPostgresql.dtos.LibroDto;
/**
 * Clase de utilidad que contiene los métodos de paso a DTO
 * 220923 - rfg
 */
public class ADto {
	
	/**
	 * Método que pasa un resultSet con libros a lista de LibroDto
	 * @param resultadoConsulta
	 * 220923 - rfg
	 * @return lista de libros
	 */
	public ArrayList<LibroDto> resultsALibrosDto(ResultSet resultadoConsulta) throws SQLException{
		
		ArrayList<LibroDto> listaLibros = new ArrayList<>();
		
		//Leemos el resultado de la consulta hasta que no queden filas
		try {
			while (resultadoConsulta.next()) {
					
				listaLibros.add(new LibroDto(resultadoConsulta.getLong("id_libro"),
						resultadoConsulta.getString("titulo"),
						resultadoConsulta.getString("autor"),
						resultadoConsulta.getString("isbn"),
						resultadoConsulta.getInt("edicion"))
						);
			}	
		int i = listaLibros.size();
		System.out.println("[INFORMACIÓN-ADto-resultsALibrosDto] Número libros: "+i);
		} catch (SQLException e) {
			System.err.println("[Método resultsALibrosDto - ADto.java] Error en la conexion a la base de datos");
		} catch (Exception e) {
			throw e;
		}	
		return listaLibros;
		
	}
}
