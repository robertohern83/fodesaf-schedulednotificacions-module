package com.fodesaf.scheduledtask.module.reports;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

public class GenerateReportFromTemplate {

	public static void main(String[] args) throws Exception, JRException {
		Connection conn = null;
        	try {
        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        	conn = DriverManager.getConnection("jdbc:sqlserver://fodesafcampaingsdb.c31iwj6og8sy.us-east-1.rds.amazonaws.com:1433;databaseName=DBCobrosFodesaf","admin","satelite");
        	} catch (SQLException ex) {
        		ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
        	ex.printStackTrace();
        }
		
		
		Map<String, Object> params = new HashMap<>();
		//params.put("pSegregadoPrincipal", "000100575474001001");
		params.put("pCedula", "00100596918");
		
		byte[] file = createReportFromDatabase(conn, params, "/Campana2_BD.jasper", "pdf");

		try (FileOutputStream fos = new FileOutputStream("reporte.pdf")) {
			fos.write(file);
			fos.flush();
		}
	}

	public static byte[] createReportFromDatabase(Connection sqlConnection, Map<String, Object> reportParameters, String templateId, String reportFormat)
			throws IOException, JRException {
		byte[] file = null;
		InputStream in = GenerateReportFromTemplate.class.getResourceAsStream(templateId);
		if (in.available() > 0) {			
			JasperPrint jasperPrint = JasperFillManager.fillReport(in, reportParameters, sqlConnection);
			file = exportReport(reportFormat, jasperPrint);
		} 
		return file;
	}
	
	/**
	 * Exporta el reporte de acuerdo al tipo de archivo que sea necesario.
	 * @param format tipo de archivo a exportar
	 * @param jasperPrint plantilla a exportar
	 * @return byte[] Array de byte
	 * @throws JRException excepcion
	 * @throws ReportServiceException excepcion
	 */
	private static byte[] exportReport(String format, JasperPrint jasperPrint)
			throws JRException {
		final ByteArrayOutputStream stream = new ByteArrayOutputStream();

		switch (format.toLowerCase()) {
		case "pdf":
			final JRPdfExporter exporterPdf = new JRPdfExporter();
			exporterPdf.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(stream));
			exporterPdf.exportReport();
			break;
		case "txt":
			final JRTextExporter exporterTxt = new JRTextExporter();
			exporterTxt.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporterTxt.setExporterOutput(new SimpleWriterExporterOutput(stream));
			exporterTxt.exportReport();
			break;
		case "csv":
			final JRCsvExporter exporterCsv = new JRCsvExporter();
			exporterCsv.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporterCsv.setExporterOutput(new SimpleWriterExporterOutput(stream));
			exporterCsv.exportReport();
			break;
			// Para compatibilidad con versiones anteriores.
		
		default:
			throw new JRException("No existe el tipo de reporte solicitado");
		}

		return stream.toByteArray();
	}

}
