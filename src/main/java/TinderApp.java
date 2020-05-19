import org.eclipse.jetty.server.handler.ContextHandler;
import utils.ResourceHandlerGenerator;

public class TinderApp {
    public static void main(String[] args) throws Exception {

        ResourceHandlerGenerator rhg = new ResourceHandlerGenerator();

        ContextHandler jsHandler = rhg.generateResourceHandler("src/main/resources/templates/js", "/js");
    }
}
