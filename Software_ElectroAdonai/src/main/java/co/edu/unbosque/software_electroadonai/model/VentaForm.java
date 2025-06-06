package co.edu.unbosque.software_electroadonai.model;

import java.util.List;

public class VentaForm {
    private String fechaVenta;
    private double precioVentaTotal;
    private String remision;
    private int idEmpleado;
    private List<DetalleVentaForm> detalles;


    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getPrecioVentaTotal() {
        return precioVentaTotal;
    }

    public void setPrecioVentaTotal(double precioVentaTotal) {
        this.precioVentaTotal = precioVentaTotal;
    }

    public String getRemision() {
        return remision;
    }

    public void setRemision(String remision) {
        this.remision = remision;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public List<DetalleVentaForm> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaForm> detalles) {
        this.detalles = detalles;
    }
}
