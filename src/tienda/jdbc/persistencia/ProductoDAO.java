package tienda.jdbc.persistencia;

import java.util.ArrayList;
import java.util.Collection;
import tienda.jdbc.entidades.Producto;

public class ProductoDAO extends DAO {

    public Collection<Producto> listarProductos() throws Exception {
        try {
            String sql = "SELECT * FROM tienda.producto p;";
            consultarBase(sql);
            Producto producto = null;
            ArrayList<Producto> productos = new ArrayList();

            while (result.next()) {
                producto = new Producto();
                producto.setCodigo(result.getInt(1));
                producto.setNombre(result.getString(2));
                producto.setPrecio(result.getDouble(3));
                producto.setCodigoFabricante(result.getInt(4));
                productos.add(producto);
            }
            desconectarBase();
            return productos;
        } catch (Exception ex) {
            desconectarBase();
            throw new Exception("No se pudo obtener el listado de productos");
        }
    }

    public Collection<Producto> listarProductosEntrePrecios(double precioMin, double precioMax) throws Exception {
        try {
            String sql = "SELECT * FROM tienda.producto p WHERE p.precio between " + precioMin + " and " + precioMax + ";";
            consultarBase(sql);
            Producto producto = null;
            ArrayList<Producto> productos = new ArrayList();

            while (result.next()) {
                producto = new Producto();
                producto.setCodigo(result.getInt(1));
                producto.setNombre(result.getString(2));
                producto.setPrecio(result.getDouble(3));
                producto.setCodigoFabricante(result.getInt(4));
                productos.add(producto);
            }
            desconectarBase();
            return productos;
        } catch (Exception ex) {
            desconectarBase();
            throw new Exception("No se pudo obtener el listado de productos");
        }
    }

    public Collection<Producto> listarProductosFiltradosPorNombre(String filtro) throws Exception {
        try {
            conectarBase();
            String sql = "SELECT * FROM tienda.producto p where p.nombre like ?";
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, "%" + filtro + "%");
            consultarBasePreparedStatement();

            Producto producto = null;
            ArrayList<Producto> productos = new ArrayList();

            while (result.next()) {
                producto = new Producto();
                producto.setCodigo(result.getInt(1));
                producto.setNombre(result.getString(2));
                producto.setPrecio(result.getDouble(3));
                producto.setCodigoFabricante(result.getInt(4));
                productos.add(producto);
            }
            desconectarBase();
            return productos;
        } catch (Exception e) {
            desconectarBase();
            System.out.println(e.getMessage());
            throw new Exception("No se pudo obtener el listado de productos filtrados por " + filtro);
        }
    }

    public Producto buscarProductoMasBarato() throws Exception {
        try {
            String sql = "SELECT * FROM tienda.producto p order by p.precio asc limit 1;";
            consultarBase(sql);

            Producto producto = null;

            while (result.next()) {
                producto = new Producto();
                producto.setCodigo(result.getInt(1));
                producto.setNombre(result.getString(2));
                producto.setPrecio(result.getDouble(3));
                producto.setCodigoFabricante(result.getInt(4));
            }
            desconectarBase();
            return producto;
        } catch (Exception e) {
            desconectarBase();
            System.out.println(e.getMessage());
            throw new Exception("No se pudo obtener el producto mas barato");
        }
    }

    public void guardarProducto(Producto producto) throws ClassNotFoundException, Exception {
        try {
            if (producto == null) {
                throw new Exception("Debe indicar el producto");
            }

            String sql = "INSERT INTO tienda.producto (nombre, precio, codigo_fabricante)"
                    + "VALUES ( '" + producto.getNombre() + "' , " + producto.getPrecio() + " , " + producto.getCodigoFabricante() + ");";

            insertarModificarEliminar(sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public void modificarProducto(Producto producto) throws Exception {
        try {
            if (producto == null) {
                throw new Exception("Debe indicar el producto que desea modificar");
            }
            String sql = "UPDATE tienda.producto SET "
                    + "nombre = '" + producto.getNombre() + "' , "
                    + "precio = " + producto.getPrecio() + " , "
                    + "codigo_fabricante = " + producto.getCodigoFabricante()
                    + " WHERE codigo = '" + producto.getCodigo() + "'";

            insertarModificarEliminar(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public Producto buscarProductoPorCodigo(int codigo) throws Exception {
        try {

            String sql = "SELECT * FROM tienda.producto "
                    + " WHERE codigo = '" + codigo + "'";

            consultarBase(sql);

            Producto producto = null;
            while (result.next()) {
                producto = new Producto();
                producto.setCodigo(result.getInt(1));
                producto.setNombre(result.getString(2));
                producto.setPrecio(result.getDouble(3));
                producto.setCodigoFabricante(result.getInt(4));
            }
            desconectarBase();
            return producto;
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }

}
