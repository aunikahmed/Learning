import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLFileParser {


    public XMLFileParser() {

    }



    public Document parseAndModify(File file) {

        Document document=getDocument(file);
        NodeList nodeList = document.getElementsByTagName("*");


        for (int i = 0; i < nodeList.getLength(); i++) {

            Element element = (Element)nodeList.item(i);
            modifyElement(element);

        }

        return document;


    }

    private void modifyElement(Element element){

        int parentIndex=0;

        String text=modifyString(element.getChildNodes().item(parentIndex).getNodeValue());
        element.getChildNodes().item(parentIndex).setNodeValue(text);
        String attributeValueForTitle = element.getAttribute("title");

        if(!attributeValueForTitle.isEmpty())
        {
            String modifiedText=modifyString(attributeValueForTitle);
            element.setAttribute("title",modifiedText);
        }
    }

    private Document getDocument(File file){

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;

        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }

        Document doc = null;

        try {
            doc = docBuilder.parse(file);
        } catch (SAXException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return doc;

    }


    private String modifyString(String text){

        if(text.contains("Java")){

            String tempModified=text.replaceAll("[ ^]Java[ $]"," Oracle java "); //replace all "Java" with "Oracle Java"
            String finalModified=tempModified.replaceAll("Oracle Oracle","Oracle");// replace all "Oracle Oracle" by "Oracle"
            //System.out.println("main text:"+text);
            //System.out.println("modified test:"+finalModified);
            return  finalModified;
        }

        return text;
    }



    public void writeModifiedXMLInFile(String fileName,Document document) {
        NodeList list = document.getElementsByTagName("*");
        try {
            TransformerFactory tFactory =TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(list.item(0));
            StreamResult result = new StreamResult(fileName);
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


    /*
    private void showXMLContent(Document doc){
        NodeList list = doc.getElementsByTagName("*");
        int bookCount = 0;

        for (int i = 0; i < list.getLength(); i++) {
            // Get the elements book (attribute isbn), title, author
            Element element = (Element)list.item(i);
            String nodeName = element.getNodeName();

            if (nodeName.equals("test")) {

                System.out.println("test " + testcount);
                String title = element.getAttribute("title");
                System.out.println("\tTitle:\t" + title);

            }
            else if(nodeName.equals("line1")){
                System.out.println("line:"+element.getTextContent());

            }


        }

    }
*/


}