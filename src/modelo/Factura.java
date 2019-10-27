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
public class Factura extends database{
    
ResultSet miResultSet = null;
    PreparedStatement prepared = null;
    
    public void generarFactura(int id) throws ClassNotFoundException, JRException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection con=this.getConexion();
        InputStream report=getClass().getResourceAsStream("/plantillas/FacturaReport.jrxml");
        JasperDesign jd=JRXmlLoader.load(report);
        JRDesignQuery query=new JRDesignQuery();
        query.setText("SELECT C.nombre, C.apellido, C.dni, F.fecha, P.nombre as 'Producto', D.cantidad, D.precio, (D.cantidad*D.precio) AS 'Total' FROM productos P JOIN detalles D ON P.id=D.id_producto JOIN facturas F ON D.id_factura=F.id JOIN clientes C ON F.id_cliente=C.id WHERE F.id="+id+"");
        jd.setQuery(query);
        JasperReport jr=JasperCompileManager.compileReport(jd);
        JasperPrint jp=JasperFillManager.fillReport(jr, null, con);
        JasperViewer.viewReport(jp,false);
    }
    
    public DefaultTableModel listarFacturas() {

		String[] headers = { "ID", "Dni","Nombre", "Apellido", "Fecha"};
		String nombre,apellido,fecha,dni;
                int total=0;
		int id;
		DefaultTableModel plantilla = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		try {
			this.getConexion();
			prepared = this.getConexion().prepareStatement(
					"SELECT COUNT(*) FROM facturas");
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
					"SELECT F.id, C.dni, C.nombre, C.apellido, F.fecha FROM facturas F JOIN clientes C ON C.id=F.id_cliente ORDER BY F.id");
			miResultSet = prepared.executeQuery();
			int i = 0;
			while (miResultSet.next()) {
				filas[i][0] = miResultSet.getString("F.id");
				filas[i][1] = miResultSet.getString("C.dni");
				filas[i][2] = miResultSet.getString("C.nombre");
				filas[i][3] = miResultSet.getString("C.apellido");
				filas[i][4] = miResultSet.getString("F.fecha");
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

