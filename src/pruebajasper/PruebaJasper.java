/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajasper;

import controlador.Controlador;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.Vista;


/**
 *
 * @author DAM
 */
public class PruebaJasper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        
        new Controlador(new Vista()).iniciar();
        // TODO code application logic here
        /*Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/ventas", "root","root");
        String report="VentasReport.jrxml";
        JasperReport jr=JasperCompileManager.compileReport(report);
        JasperPrint jp=JasperFillManager.fillReport(jr, null, con);
        JasperViewer.viewReport(jp);*/
        
        
        
        /*int id=1;
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/ventas", "root","root");
        JasperDesign jd=JRXmlLoader.load("VentasReportQuery.jrxml");
        JRDesignQuery query=new JRDesignQuery();
        query.setText("select V.ID, C.NOMBRE, P.NOMBRE AS PRODUCTO, P.PRECIO from CLIENTES C JOIN VENTAS V ON C.ID=V.CLIENTE\n" +
"JOIN PRODUCTOS P ON V.PRODUCTO=P.ID WHERE C.ID="+id+"");
        jd.setQuery(query);
        JasperReport jr=JasperCompileManager.compileReport(jd);
        JasperPrint jp=JasperFillManager.fillReport(jr, null, con);
        JasperViewer.viewReport(jp);*/
    }
    
}
