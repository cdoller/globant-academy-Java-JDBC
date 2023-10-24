package tienda.jdbc;

import tienda.jdbc.servicios.FabricanteService;
import tienda.jdbc.servicios.ProductoService;

public class TiendaJDBC {

    public static void main(String[] args) {
        ProductoService productoService = new ProductoService();
        FabricanteService fabricanteService = new FabricanteService();

        try {
            productoService.imprimirNombreProductos();
            System.out.println("---------------");
            productoService.imprimirNombrePrecioProductos();
            System.out.println("---------------");
            productoService.imprimirProductosFiltradoPorPrecios(120, 202);
            System.out.println("---------------");
            productoService.imprimirProductosFiltradosPorNombre("portatil");
            System.out.println("---------------");
            productoService.imprimirProductoMasBarato();
            System.out.println("---------------");
            System.out.println(productoService.crearProducto("Notebook ASUS", 945, 9));
            System.out.println("---------------");
            System.out.println(fabricanteService.crearFabricante("Asusss"));
            System.out.println("---------------");
            System.out.println(productoService.modificarProducto(2, "Impresora HP Laserjet Pro M26nw MODIFICADAx2", -180, 3));
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

}
