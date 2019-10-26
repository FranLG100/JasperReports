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
import modelo.database;
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
	
	public enum AccionMVC {
		__GOTO_VENTAS, __GOTO_INICIO, __GENERAR_INFORME_VENTAS
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
                
                this.vista.btnVentas.setActionCommand("__GOTO_VENTAS");
		this.vista.btnVentas.addActionListener(this);
                
                this.vista.btnGenerarInformeVentas.setActionCommand("__GENERAR_INFORME_VENTAS");
		this.vista.btnGenerarInformeVentas.addActionListener(this);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e)  {
		switch (AccionMVC.valueOf(e.getActionCommand())) {
		case __GOTO_INICIO:
			gui.cambiarPanel(vista.pInicio);
			break;
		case __GOTO_VENTAS:
			gui.cambiarPanel(vista.pVentas);
			break;
                case __GENERAR_INFORME_VENTAS:
                {
                    try {
                        //Class.forName("org.apache.derby.jdbc.ClientDriver");
                        Class.forName("com.mysql.jdbc.Driver");
                        //Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/ventas", "root","root");
                        Connection con=this.getConexion();
                        //String report="src/modelo/VentasReport.jrxml";
                        
                        InputStream report=getClass().getResourceAsStream("/modelo/ClientesReport.jrxml");
                        JasperDesign jd=JRXmlLoader.load(report);
                        JasperReport jr = JasperCompileManager.compileReport(jd);
                        //JasperReport jr=JasperCompileManager.compileReport(report);
                        JasperPrint jp=JasperFillManager.fillReport(jr, null, con);
                        JasperViewer.viewReport(jp);
                    } catch (Exception ex) {
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
			break;
                }
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