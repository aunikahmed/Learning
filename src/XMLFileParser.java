import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    private  final String RegExToreplace="(?<!Oracle )(java)";
    private  final String replaceWith="Oracle java";
    private final String attributeToModify="title";

    public Document parseAndModify(File file) {

        Document document=getDocument(file);
        NodeList nodeList = document.getElementsByTagName("*");


        for (int i = 0; i < nodeList.getLength(); i++) {

            Element element = (Element)nodeList.item(i);
            modifyElement(element);
        }

        return document;


    }

    private  Document getDocument(File file){

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;

        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }

        Document document = null;

        try {
            document = documentBuilder.parse(file);
        } catch (SAXException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return  document;
    }



    private void modifyElement(Element element){

        NodeList nodeList=element.getChildNodes();
        Node node=nodeList.item(0);

        String text=node.getNodeValue();
        String modifiedText=modifyText(text);
        node.setNodeValue(modifiedText);

        modifyAttributeValue(element);

    }



    private void  modifyAttributeValue(Element element ){
        String attributeValueForTitle = element.getAttribute(attributeToModify);

        if(!attributeValueForTitle.isEmpty())
        {
            String modifiedText= modifyText(attributeValueForTitle);
            element.setAttribute("title",modifiedText);
        }

    }


    private String modifyText(String text){

        String modifiedText=text.replaceAll(RegExToreplace,replaceWith);

        return modifiedText;
    }



    public void writeModifiedXMLInFile(String fileName,Document document) {
        //NodeList list = document.getElementsByTagName("*");
int i;
        NodeList list = null;
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