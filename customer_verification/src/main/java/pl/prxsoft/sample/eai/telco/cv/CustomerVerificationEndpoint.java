package pl.prxsoft.sample.eai.telco.cv;

/**
 * Created with IntelliJ IDEA.
 * User: pmasko
 * Date: 15.06.2013
 * Time: 23:20
 * To change this template use File | Settings | File Templates.
 */

import org.dom4j.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.HashMap;
import java.util.Map;

@Endpoint
@org.springframework.ws.server.endpoint.annotation.Namespace(prefix = "cv", uri = CustomerVerificationEndpoint.CV_NAMESPACE_URI)
public class CustomerVerificationEndpoint {

  private static final String TEL_NAMESPACE_URI = "http://prxsoftware.pl/sample/eai/telco";
  public  static final String CV_NAMESPACE_URI = "http://prxsoftware.pl/sample/eai/telco/cv";

  private Map<String,String> namespacesMap = new HashMap<>() ;

  private Namespace namespaceCV;
  private Namespace namespaceTEL;

  public CustomerVerificationEndpoint() {
    namespacesMap.put("tel", TEL_NAMESPACE_URI);
    namespacesMap.put("cv", CV_NAMESPACE_URI);

    namespaceCV = new Namespace("cv", CV_NAMESPACE_URI);
    namespaceTEL = new Namespace("tel", TEL_NAMESPACE_URI);
  }

  private XPath createXPath(final String xpath) {
    XPath xPath = DocumentHelper.createXPath(xpath);
    xPath.setNamespaceURIs(namespacesMap);

    return xPath;
  }

  @PayloadRoot(namespace = CV_NAMESPACE_URI, localPart = "CustomerVerificationRequest")
  @ResponsePayload
  public org.dom4j.Element handleCustomerVerificationRequest(@RequestPayload org.dom4j.Element customerVerificationRequest) {
    XPath customerIdXPath = createXPath("//tel:customerId");
    XPath idNumberXPath = createXPath("//tel:idNumber");
    XPath idNumberTypeXPath = createXPath("//tel:idNumberType");

    String customerId = customerIdXPath.selectSingleNode(customerVerificationRequest).getText();
    String idNumber = idNumberXPath.selectSingleNode(customerVerificationRequest).getText();
    String idNumberType = idNumberTypeXPath.selectSingleNode(customerVerificationRequest).getText();

    return createResponseElement(idNumber.startsWith("8"), "Thanks " + customerId);
  }

  private Element createResponseElement(boolean responseCode, String responseText) {
    Document document = DocumentHelper.createDocument();

    Element root = document.addElement("CustomerVerificationResponse");
    document.setRootElement(root);
    root.add(namespaceCV);

    Element desc = root.addElement("resultDescription");
    desc.setText(responseText);

    Element code = root.addElement("activationAllowed");
    code.setText("" + responseCode);

    return root;
  }

}
