package my.sandbox;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/topic/dtss/notification")
})
public class BridgedTopicConsumer implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(BridgedTopicConsumer.class);
    
    public void businessMethod() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            LOG.info("A new message received: {}:{}", message.getJMSType(), message.getJMSCorrelationID());
        } catch (JMSException e) {
            LOG.error(null, e);
        }
    }

}
