package my.sandbox.service;

import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

@Singleton
@LocalBean
public class MessageProviderBean implements Serializable {

	private static final long serialVersionUID = -1L;
    
    public String getMessageTemplate(TemplateType tt) {
        switch (tt) {
            case simple:
                return "Hello, %s! Hello my friend!";
            default:
                return "%s";
        }
        
    }
}