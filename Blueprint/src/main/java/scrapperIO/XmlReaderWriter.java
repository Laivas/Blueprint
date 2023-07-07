package scrapperIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.glassfish.jaxb.runtime.v2.JAXBContextFactory;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class XmlReaderWriter {

//	private String userHome = System.getProperty("user.home").replaceAll("\\\\", "/");

	private static final String xmlDir = "/ScrappingApp/";

	public <T> void toXml(T toXml, String xmlSettingsName) {

		String userHome = System.getProperty("user.home").replaceAll("\\\\", "/");

		File xmlFile = new File(userHome + xmlDir + xmlSettingsName);

		if (!xmlFile.exists()) {

			xmlFile = new File(userHome + xmlDir);

			xmlFile.mkdir();

			xmlFile = new File(userHome + xmlDir + xmlSettingsName);

		}

		try {
			JAXBContext context = new JAXBContextFactory().createContext(new Class[] { toXml.getClass() }, null);

			Marshaller marshaller = context.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			marshaller.marshal(toXml, xmlFile);

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public <T> T fromXml(T classType, String xmlSettingsName) {

		String userHome = System.getProperty("user.home").replaceAll("\\\\", "/");

		T fromXml = null;

		if (!new File(userHome + xmlDir).exists()) {

			new File(userHome + xmlDir).mkdir();

		}

		if (new File(userHome + xmlDir + xmlSettingsName).exists()) {

			try {

				JAXBContext context = new JAXBContextFactory().createContext(new Class[] { classType.getClass() },
						null);

				fromXml = (T) context.createUnmarshaller()
						.unmarshal(new FileReader(userHome + xmlDir + xmlSettingsName));

			} catch (JAXBException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return fromXml;

	}

}
