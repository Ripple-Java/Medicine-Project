package com.rippletec.medicine.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.Sides;

import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeConnectionProtocol;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.w3c.dom.DOMImplementation;


public class PPTUtil {
    
    public static String separator = File.separator;
    public static String rootPath  = System.getProperty("medicine.root")+separator+"enterprise"+separator+"ppt";
    public static String pdfRootPath  = System.getProperty("medicine.root")+separator+"pdfs";
    
    public static int saveToImg(String name, File pptFile, double scale, String format) throws FileNotFoundException, IOException {
	if (isPPTFile(pptFile))
	    return ppt2Img(name, pptFile, scale, format);
	if (isPPTXFile(pptFile))
	    return pptx2Img(name, pptFile, scale, format);
	return 0;
    }

    public static boolean saveToPdf(String name, File pptFile) {
	File pdfRootFile = new File(pdfRootPath);
	if(!pdfRootFile.exists())
	    pdfRootFile.mkdirs();
	OfficeManager officeManager = new DefaultOfficeManagerConfiguration().buildOfficeManager();
	officeManager.start();
	OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
	converter.convert(pptFile, new File(pdfRootFile,name+".pdf"));
	officeManager.stop();
	return true;
    }
    
    public static boolean saveToHtml(String name, File pptFile) {
   	File pdfRootFile = new File(pdfRootPath);
   	if(!pdfRootFile.exists())
   	    pdfRootFile.mkdirs();
   	OfficeManager officeManager = new DefaultOfficeManagerConfiguration().buildOfficeManager();
   	officeManager.start();
   	OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
   	converter.convert(pptFile, new File(pdfRootFile,name+".html"));
   	officeManager.stop();
   	return true;
       }
    
    public static OfficeManager getWinOfficeManager() {
	OfficeManager officeManager = new DefaultOfficeManagerConfiguration()
	      .setOfficeHome("C:\\Program Files (x86)\\OpenOffice.org 3")
	      .setConnectionProtocol(OfficeConnectionProtocol.PIPE)
	      .setPipeNames("office1", "office2")
	      .setTaskExecutionTimeout(30000L)
	      .buildOfficeManager();
	return officeManager;
    }

    public static int pptx2Img(String name, File pptFile, double scale,
	    String format) throws FileNotFoundException, IOException {
	if (!isPPTXFile(pptFile))
	    return 0;
	File rootFile = new File(rootPath+separator+name);
	if(!rootFile.exists())
	    rootFile.mkdirs();
	FileInputStream in = new FileInputStream(pptFile);
	XMLSlideShow   show = new XMLSlideShow(in);
	in.close();
	Dimension pgsize = show.getPageSize();
	int width = (int) (pgsize.width * scale);
	int height = (int) (pgsize.height * scale);
	XSLFSlide[] slides = show.getSlides();
	
	
	for (int i = 0; i < slides.length; i++) {
	    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics = img.createGraphics();
	    // default rendering options 去除锯齿
	    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		    RenderingHints.VALUE_ANTIALIAS_ON);
	    graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
		    RenderingHints.VALUE_RENDER_QUALITY);
	    graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	    graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
		    RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	    
	    graphics.setPaint(Color.white);
	    
	    graphics.fill(new Rectangle2D.Float(0, 0, width, height));
	    
	    graphics.scale(scale,scale);
	    
	    slides[i].draw(graphics);
	    
	    FileOutputStream fout = new FileOutputStream(new File(rootFile,i+"."+format));
	    ImageIO.write(img, format, fout);
	    fout.close();
	}
	return slides.length;
    }
    
    public static int ppt2Img(String name, File pptFile, double scale ,String format) throws FileNotFoundException, IOException {
   	if(!isPPTFile(pptFile))
   	    return 0;
   	File rootFile = new File(rootPath+separator+name);
   	if(!rootFile.exists())
   	    rootFile.mkdirs();
   	FileInputStream in = new FileInputStream(pptFile);
   	HSLFSlideShow  ppt = new HSLFSlideShow(in);
   	in.close();
   	SlideShow show = new SlideShow(ppt);
   	Dimension pgsize = show.getPageSize();
   	int width = (int) (pgsize.width * scale);
   	int height = (int) (pgsize.height * scale);
   	Slide[] slides = show.getSlides();

   	for (int i = 0; i < slides.length; i++) {
   	    Slide slide = slides[i];
       	    
   	    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
   	    Graphics2D graphics = img.createGraphics();
   	    
   	    // default rendering options 去除锯齿
   	    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
   		    RenderingHints.VALUE_ANTIALIAS_ON);
   	    graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
   		    RenderingHints.VALUE_RENDER_QUALITY);
   	    graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
   		    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
   	    graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
   		    RenderingHints.VALUE_FRACTIONALMETRICS_ON);
   	    
   	    graphics.setPaint(Color.white);
   	    graphics.fill(new Rectangle2D.Float(0, 0, width, height));
   	    
   	    graphics.scale(scale,scale);
   	    
   	    // render the background
   	    slide.getBackground().draw(graphics);
	    slide.draw(graphics);
   	    
	    FileOutputStream fout = new FileOutputStream(new File(rootFile,i+"."+format));
   	    ImageIO.write(img, format, fout);
   	    fout.close();
   	}
   	return slides.length;
       }
   
       
       
    
    public static boolean isPPTFile(File file) {
	if(!file.isFile())
	    return false;
	String fileName = file.getName();
	return fileName.endsWith(".ppt");
    }
    
    public static boolean isPPTXFile(File file) {
   	if(!file.isFile())
   	    return false;
   	String fileName = file.getName();
   	return fileName.endsWith(".pptx");
       }
    
    

}
