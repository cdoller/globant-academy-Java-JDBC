package tienda.jdbc.servicios;

import java.util.Collection;
import tienda.jdbc.entidades.Producto;
import tienda.jdbc.persistencia.ProductoDAO;

public class ProductoService {

    private ProductoDAO dao;

    public ProductoService() {
        dao = new ProductoDAO();
    }

    public void imprimirNombreProductos() throws Exception {
        try {
            Collection<Producto> productos = dao.listarProductos();
            for (Producto producto : productos) {
                System.out.println(producto.getNombre());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void imprimirNombrePrecioProductos() throws Exception {
        try {
            Collection<Producto> productos = dao.listarProductos();
            for (Producto producto : productos) {
                System.out.println(producto.getNombre() + " | " + producto.getPrecio());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void imprimirProductosFiltradoPorPrecios(double precioMin, double precioMax) throws Exception {
        try {
            validarPrecio(precioMax);
            validarPrecio(precioMin);
            if (precioMax < precioMin) {
                throw new Exception("El precio maximo no puede ser menor al minimo");
            }
            Collection<Producto> productos = dao.listarProductosEntrePrecios(precioMin, precioMax);
            for (Producto producto : productos) {
                System.out.println(producto.getCodigo() + " | " + producto.getNombre() + " | " + producto.getPrecio() + " | " + producto.getCodigoFabricante());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void imprimirProductosFiltradosPorNombre(String filtro) throws Exception {
        try {
            Collection<Producto> productos = dao.listarProductosFiltradosPorNombre(filtro);
            for (Producto producto : productos) {
                System.out.println(producto.getCodigo() + " | " + producto.getNombre() + " | " + producto.getPrecio() + " | " + producto.getCodigoFabricante());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void imprimirProductoMasBarato() throws Exception {
        try {
            Producto producto = dao.buscarProductoMasBarato();
            System.out.println(producto.getNombre() + " | " + producto.getPrecio());
        } catch (Exception e) {
            throw e;
        }
    }

    public String crearProducto(String nombre, double precio, int codigoFabricante) throws Exception {
        try {
            validarNombre(nombre);
            validarPrecio(precio);
            FabricanteService fabricanteService = new FabricanteService();
            fabricanteService.buscarFabricantePorId(codigoFabricante);

            Producto producto = new Producto();
            producto.setCodigoFabricante(codigoFabricante);
            producto.setNombre(nombre);
            producto.setPrecio(precio);

            dao.guardarProducto(producto);

            return "Producto creado correctamente";
        } catch (Exception e) {
            return "No se pudo crear el producto: " + e.getMessage();
        }
    }

    public String modificarProducto(int codigo, String nombre, double precio, int codigoFabricante) throws Exception {
        try {
            validarCodigo(codigo);
            Producto productoAModificar = buscarProducto(codigo);
            validarNombre(nombre);
            validarPrecio(precio);
            validarCodigoFabricante(codigoFabricante);

            productoAModificar.setNombre(nombre);
            productoAModificar.setPrecio(precio);
            productoAModificar.setCodigoFabricante(codigoFabricante);

            dao.modificarProducto(productoAModificar);

            return ("El producto con codigo " + codigo + " ha sido modificado correctamente");
        } catch (Exception e) {
            return ("El producto [codigo]=" + codigo + " no se pudo modificar. " + e.getMessage() );
        }
    }

    public Producto buscarProducto(int codigo) throws Exception {
        try {
            validarCodigo(codigo);
            Producto productoAModificar = dao.buscarProductoPorCodigo(codigo);
            if (productoAModificar == null) {
                throw new Exception("El producto no se ha encontrado");
            }
            return productoAModificar;
        } catch (Exception e) {
            throw e;
        }
    }

    // METODOS DE VALIDACION INTERNOS
    private void validarNombre(String nombre) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("Debe indicar el nombre");
        }
    }

    private void validarPrecio(double precio) throws Exception {
        if (precio < 0) {
            throw new Exception("El precio debe ser positivo");
        }
    }

    private void validarCodigo(int codigo) throws Exception {
        if (codigo <= 0) {
            throw new Exception("El id debe ser positivo");
        }
    }

    private void validarCodigoFabricante(int codigoFabricante) throws Exception {
        FabricanteService fabricanteService = new FabricanteService();
        fabricanteService.buscarFabricantePorId(codigoFabricante);
    }
}
