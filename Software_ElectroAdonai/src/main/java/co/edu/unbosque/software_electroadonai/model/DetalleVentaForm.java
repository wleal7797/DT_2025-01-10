package co.edu.unbosque.software_electroadonai.model;

import java.util.List;

public class DetalleVentaForm {
    private int cntProductoVenta;
    private double precioVentaProducto;
    private String serialesTexto;
    private int idProducto;
    private int idBodega;

    public int getCntProductoVenta() {
        return cntProductoVenta;
    }

    public void setCntProductoVenta(int cntProductoVenta) {
        this.cntProductoVenta = cntProductoVenta;
    }

    public double getPrecioVentaProducto() {
        return precioVentaProducto;
    }

    public void setPrecioVentaProducto(double precioVentaProducto) {
        this.precioVentaProducto = precioVentaProducto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(int idBodega) {
        this.idBodega = idBodega;
    }

    public String getSerialesTexto() {
        return serialesTexto;
    }

    public void setSerialesTexto(String serialesTexto) {
        this.serialesTexto = serialesTexto;
    }
}


