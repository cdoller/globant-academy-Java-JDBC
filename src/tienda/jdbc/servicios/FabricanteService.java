package tienda.jdbc.servicios;

import tienda.jdbc.entidades.Fabricante;
import tienda.jdbc.persistencia.FabricanteDAO;

public class FabricanteService {

    private FabricanteDAO dao;

    public FabricanteService() {
        dao = new FabricanteDAO();
    }

    public Fabricante buscarFabricantePorId(int codigo) throws Exception {

        try {
            if (codigo <= 0) {
                throw new Exception("El id no es valido");
            }
            Fabricante fabricante = dao.buscarFabricantePorCodigo(codigo);
            
            if (fabricante == null) {
                throw new Exception("El codigo fabricante es invalido");
            }

            return fabricante;
        } catch (Exception e) {
            throw e;
        }
    }

    public String crearFabricante(String nombre) throws Exception{
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new Exception("Debe indicar el nombre del fabricante");
            }
            
            if(dao.buscarFabricantePorNombre(nombre) != null){
                throw new Exception("Ya existe un fabricante con dicho nombre");
            }
           
            Fabricante fabricante = new Fabricante();
            fabricante.setNombre(nombre);

            dao.guardarFabricante(fabricante);
            return ("Fabricante creado correctamente");
        } catch (Exception e) {
            return "No se pudo crear el fabricante" + e.getMessage();
        }
    }
}
