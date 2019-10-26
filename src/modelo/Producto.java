/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.Controlador;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author DAM
 */
public class Producto extends database {
    
    ResultSet miResultSet = null;
    PreparedStatement prepared = null;
    
    public void generarInformes(){
                try {
                        //Class.forName("org.apache.derby.jdbc.ClientDriver");
                        Class.forName("com.mysql.jdbc.Driver");
                        //Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/ventas", "root","root");
                        Connection con=this.getConexion();
                        //String report="src/modelo/VentasReport.jrxml";
                        
                        InputStream report=getClass().getResourceAsStream("/plantillas/ProductosReport.jrxml");
                        JasperDesign jd=JRXmlLoader.load(report);
                        JasperReport jr = JasperCompileManager.compileReport(jd);
                        //JasperReport jr=JasperCompileManager.compileReport(report);
                        JasperPrint jp=JasperFillManager.fillReport(jr, null, con);
                        JasperViewer.viewReport(jp,false);
                    } catch (Exception ex) {
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
    }
    
    public void generarInformeProductoStock(int stock) throws ClassNotFoundException, JRException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection con=this.getConexion();
        InputStream report=getClass().getResourceAsStream("/plantillas/ProductosReport.jrxml");
        JasperDesign jd=JRXmlLoader.load(report);
        JRDesignQuery query=new JRDesignQuery();
        query.setText("SELECT P.id, P.nombre,P.precio, P.stock from productos P WHERE P.stock<"+stock+"");
        jd.setQuery(query);
        JasperReport jr=JasperCompileManager.compileReport(jd);
        JasperPrint jp=JasperFillManager.fillReport(jr, null, con);
        JasperViewer.viewReport(jp,false);
    }
    
    public DefaultTableModel listarProductos() {

		String[] headers = { "ID", "Nombre", "Precio", "Stock", "Categoria"};
		String nombre, categoria;
                int total=0;
		int id,precio,stock;
		DefaultTableModel plantilla = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		try {
			this.getConexion();
			prepared = this.getConexion().prepareStatement(
					"SELECT COUNT(*) FROM productos");
			miResultSet = prepared.executeQuery();
			miResultSet.next();
			total = miResultSet.getInt(1);
			prepared.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String[][] filas = new String[total][5];

		try {
			this.getConexion();
			prepared = this.getConexion().prepareStatement(
					"SELECT P.id, P.nombre, P.precio, P.stock, C.nombre FROM productos P JOIN categorias C ON P.categoria=C.codigo");
			miResultSet = prepared.executeQuery();
			int i = 0;
			while (miResultSet.next()) {
				filas[i][0] = miResultSet.getString("P.id");
				filas[i][1] = miResultSet.getString("P.nombre");
				filas[i][2] = miResultSet.getString("P.precio");
				filas[i][3] = miResultSet.getString("P.stock");
				filas[i][4] = miResultSet.getString("C.nombre");
				i++;
			}
			plantilla.setDataVector(filas, headers);
			prepared.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plantilla;

	}
    
}
