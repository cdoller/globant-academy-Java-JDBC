package tienda.jdbc.persistencia;

import tienda.jdbc.entidades.Fabricante;

public class FabricanteDAO extends DAO {

    public Fabricante buscarFabricantePorCodigo(int codigo) throws Exception {
        try {

            String sql = "SELECT * FROM tienda.fabricante "
                    + " WHERE codigo = '" + codigo + "'";

            consultarBase(sql);

            Fabricante fabricante = null;
            while (result.next()) {
                fabricante = new Fabricante();
                fabricante.setCodigo(result.getInt(1));
                fabricante.setNombre(result.getString(2));
            }
            desconectarBase();
            return fabricante;
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }

    public Fabricante buscarFabricantePorNombre(String nombre) throws Exception {
        try {

            String sql = "SELECT * FROM tienda.fabricante "
                    + " WHERE nombre = '" + nombre.trim() + "'";

            consultarBase(sql);

            Fabricante fabricante = null;
            while (result.next()) {
                fabricante = new Fabricante();
                fabricante.setCodigo(result.getInt(1));
                fabricante.setNombre(result.getString(2));
            }
            desconectarBase();
            return fabricante;
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public void guardarFabricante(Fabricante fabricante) throws Exception{
        try {
            if (fabricante == null) {
                throw new Exception("Debe indicar el fabricante");
            }

            String sql = "INSERT INTO tienda.fabricante (nombre)"
                    + "VALUES ( '" + fabricante.getNombre() + "');";

            insertarModificarEliminar(sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            desconectarBase();
        }
    }
}
