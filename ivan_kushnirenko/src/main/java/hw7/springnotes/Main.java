package hw7.springnotes;


import hw7.springnotes.view.Menu;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import hw7.springnotes.service.NotebookService;
import java.util.Locale;

/**
 * Created by ivan on 07.07.15.
 */
public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        ApplicationContext context = new ClassPathXmlApplicationContext("ivan_kushnirenko/src/main/java/hw7/springnotes/context.xml");
        //ApplicationContext context = new ClassPathXmlApplicationContext("");
        NotebookService companyService = context.getBean("notebookServiceImpl", NotebookService.class);
        Menu menu = new Menu(companyService);
        menu.run();
    }
}
