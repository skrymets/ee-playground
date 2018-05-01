package my.sandbox.service;

import static java.util.Objects.*;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import my.sandbox.GreetingBean;

@Stateless
public class GreetingBeanImpl implements GreetingBean {

    @EJB
    MessageProviderBean messageProvider;

    @Override
    public String sayHello(String guestName) {
        requireNonNull(guestName, "Undefined name parameter");

        return String.format(messageProvider.getMessageTemplate(TemplateType.simple), guestName);
        // return String.format("Hello, %s! Hello my friend!", guestName);

    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
