package com.guit.rebind.guitview;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Simplifies instantiation of the w3c XML parser, in just the style that
 * UiBinder likes it. Used by both prod and test.
 */
public class W3cDomHelper {
  private final DocumentBuilderFactory factory;
  private final DocumentBuilder builder;

  public W3cDomHelper(final TreeLogger logger) {
    factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
    factory.setExpandEntityReferences(true);
    try {
      builder = factory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
    builder.setEntityResolver(new GwtResourceEntityResolver());
    builder.setErrorHandler(new ErrorHandler() {

      public void error(SAXParseException exception) throws SAXException {
        logger.log(Type.ERROR, exception.getMessage());
        logger.log(Type.DEBUG, "SAXParseException", exception);
      }

      public void fatalError(SAXParseException exception) throws SAXException {
        /*
         * Fatal errors seem to be no scarier than error errors, and simply
         * happen due to badly formed XML.
         */
        logger.log(Type.ERROR, exception.getMessage());
        logger.log(Type.DEBUG, "SAXParseException", exception);
      }

      public void warning(SAXParseException exception) throws SAXException {
        logger.log(Type.WARN, exception.getMessage());
        logger.log(Type.DEBUG, "SAXParseException", exception);
      }

    });
  }

  /**
   * Creates an XML document model with the given contents. Nice for testing.
   * 
   * @param string the document contents
   */
  public Document documentFor(String string) throws SAXException, IOException {
    return builder.parse(new ByteArrayInputStream(string.getBytes()));
  }

  public Document documentFor(URL url) throws SAXParseException {
    try {
      InputStream stream = url.openStream();
      InputSource input = new InputSource(stream);
      input.setSystemId(url.toExternalForm());

      return builder.parse(input);
    } catch (SAXParseException e) {
      // Let SAXParseExceptions through.
      throw e;
    } catch (SAXException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
