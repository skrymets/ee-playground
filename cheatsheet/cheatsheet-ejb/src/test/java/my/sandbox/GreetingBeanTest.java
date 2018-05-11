/*
 * Copyright 2018 skrymets.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package my.sandbox;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import my.sandbox.service.*;

import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
public class GreetingBeanTest {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingBeanTest.class);

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap
                .create(JavaArchive.class)
                .addClasses(
                        GreetingBean.class,
                        GreetingBeanRemote.class,
                        GreetingBeanImpl.class //,
                        // MessageProviderBean.class,
                        // TemplateType.class
                )
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        LOG.info(jar.toString(true));
        return jar;
    }

    // @Inject
    @EJB
    GreetingBean bean;

    @Test(expected = EJBException.class)
    public void startAndHealthCheck() {
        assertNotNull(bean);

        String helloMessage = bean.sayHello("Sergii");
        assertEquals(helloMessage, "Hello, Sergii! Hello my friend!");
        LOG.info(helloMessage);

        bean.sayHello(null);
        fail();
    }

}
