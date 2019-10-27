package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.GestorInterfaz;

import controlador.Controlador;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cliente;
import modelo.Factura;
import modelo.Producto;
import modelo.database;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.Vista;

public class Controlador extends database implements ActionListener, MouseListener {
    
	private Vista vista;
	private GestorInterfaz gui;
        private Producto producto=new Producto();
        private Cliente cliente=new Cliente();
        private Factura factura=new Factura();
        private String nombre;
        private int stock;
        private double precio;
        int datoNumerico=0;
	
	public enum AccionMVC {
		__GOTO_PRODUCTOS, __GOTO_INICIO, __INSERTAR_PRODUCTO, __ELIMINAR_PRODUCTO, __GENERAR_INFORME_PRODUCTOS, __GENERAR_LISTADO_STOCK,
                __GOTO_CLIENTES, __GENERAR_LISTADO_CLIENTES,
                __GOTO_FACTURAS, __GENERAR_FACTURA
	}
	
	public Controlador(Vista vista) {
		this.vista = vista;
		this.gui = new GestorInterfaz(vista);
	}
	
	public void iniciar() {
		// Skin tipo WINDOWS
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(vista);
			vista.setVisible(true);
		} catch (UnsupportedLookAndFeelException ex) {
		} catch (ClassNotFoundException ex) {
		} catch (InstantiationException ex) {
		} catch (IllegalAccessException ex) {
		}
		
		//gui.cambiarPanel(vista.pRelleno);
		gui.cambiarPanel(vista.pInicio);

		// declara una acciﾃｳn y aﾃｱade un escucha al evento producido por el componente
		this.vista.btnVolverMenuVentas.setActionCommand("__GOTO_INICIO");
		this.vista.btnVolverMenuVentas.addActionListener(this);
                
                this.vista.btnVolverMenuClientes.setActionCommand("__GOTO_INICIO");
		this.vista.btnVolverMenuClientes.addActionListener(this);
                
                this.vista.btnVolverMenuFacturas.setActionCommand("__GOTO_INICIO");
		this.vista.btnVolverMenuFacturas.addActionListener(this);
                
                this.vista.btnProductos.setActionCommand("__GOTO_PRODUCTOS");
		this.vista.btnProductos.addActionListener(this);
                
                 this.vista.btnClientes.setActionCommand("__GOTO_CLIENTES");
		this.vista.btnClientes.addActionListener(this);
                
                this.vista.btnFacturas.setActionCommand("__GOTO_FACTURAS");
		this.vista.btnFacturas.addActionListener(this);
                
                this.vista.btnInsertarProducto.setActionCommand("__INSERTAR_PRODUCTO");
		this.vista.btnInsertarProducto.addActionListener(this);
                
                this.vista.btnEliminarProducto.setActionCommand("__ELIMINAR_PRODUCTO");
		this.vista.btnEliminarProducto.addActionListener(this);
                
                this.vista.btnGenerarInformeVentas.setActionCommand("__GENERAR_INFORME_PRODUCTOS");
		this.vista.btnGenerarInformeVentas.addActionListener(this);
                
                this.vista.btnGenerarListadoStock.setActionCommand("__GENERAR_LISTADO_STOCK");
		this.vista.btnGenerarListadoStock.addActionListener(this);
		
                this.vista.btnGenerarListadoClientes.setActionCommand("__GENERAR_LISTADO_CLIENTES");
		this.vista.btnGenerarListadoClientes.addActionListener(this);
                
                this.vista.btnGenerarFactura.setActionCommand("__GENERAR_FACTURA");
		this.vista.btnGenerarFactura.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e)  {
		switch (AccionMVC.valueOf(e.getActionCommand())) {
		case __GOTO_INICIO:
			gui.cambiarPanel(vista.pInicio);
			break;
		case __GOTO_PRODUCTOS:
			gui.cambiarPanel(vista.pVentas);
                        vista.tablaProductos.setModel(producto.listarProductos());
			break;
                case __GOTO_CLIENTES:
			gui.cambiarPanel(vista.pClientes);
                        vista.tablaClientes.setModel(cliente.listarClientes());
			break;
                case __INSERTAR_PRODUCTO:
                    nombre=vista.campoNombreProducto.getText();
                    stock=Integer.parseInt(vista.campoStockProducto.getText());
                    precio=Double.parseDouble(vista.campoPrecio.getText());
                    producto.insertarProducto(nombre, precio, stock);
                    vista.tablaProductos.setModel(producto.listarProductos());
                    break;
                case __ELIMINAR_PRODUCTO:
                    datoNumerico=Integer.parseInt(vista.campoIdProducto.getText());
                    producto.borrarProducto(datoNumerico);
                    vista.tablaProductos.setModel(producto.listarProductos());
                    break;
                case __GENERAR_INFORME_PRODUCTOS:
                        producto.generarInformes();
			break;
                case __GENERAR_LISTADO_STOCK:
                        datoNumerico=Integer.parseInt(vista.campoStock.getText());
                {
                    try {
                        producto.generarInformeProductoStock(datoNumerico);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JRException ex) {
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                        break;
                case __GENERAR_LISTADO_CLIENTES:
                        cliente.generarInformes();
                        break;
                case __GOTO_FACTURAS:
                    gui.cambiarPanel(vista.pFacturas);
                    vista.tablaFacturas.setModel(factura.listarFacturas());
                    break;
                case __GENERAR_FACTURA:
                    datoNumerico=Integer.parseInt(vista.campoFactura.getText());
                {
                    try {
                        factura.generarFactura(datoNumerico);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JRException ex) {
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    break;
		}
		}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	
}