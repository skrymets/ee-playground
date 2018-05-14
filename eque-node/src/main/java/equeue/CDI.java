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
package equeue;

/**
 *
 * @author skrymets
 */
public final class CDI {

    public static final class Profiles {

        public static final String SHELL = "intercactive-shell";
        public static final String BROKER = "broker-node";
        public static final String KIOSK = "kiosk-node";
        public static final String PROVIDER = "service-provider-node";
        /**
         * Service Level Inspector role node
         */
        public static final String SLI = "service-inspector-node";
    }
}
