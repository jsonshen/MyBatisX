/*
 * Copyright 2015-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.shenjia.mybatis.sql;

import java.util.Iterator;
import java.util.ServiceLoader;

public interface SqlExecutor<R> {

    R execute();

    @SuppressWarnings("unchecked")
    default <Decorator> Decorator decorator(Class<Decorator> clazz) {
        // Create a PagingDecorator by System property.
        String interfaceName = clazz.getName();
        String implClassName = System.getProperty(interfaceName);
        if (null != implClassName) {
            try {
                return (Decorator) Class.forName(implClassName)
                    .getDeclaredConstructor()
                    .newInstance();
            } catch (Exception e) {
                throw new SqlException(
                    e.getMessage() + ", System.getProperty(\"" + interfaceName + "\"); return is " + implClassName);
            }
        }
        // Create a SqlDecorator by SPI.
        Iterator<Decorator> iterator = ServiceLoader.load(clazz)
            .iterator();
        while (iterator.hasNext()) {
            return iterator.next();
        }
        // Not found PagingDecorator
        throw new SqlException("Please define a decorator, Method 1 has a higher priority. "
            + "Method 1: Call System.setProperty(\"" + interfaceName + "\", decoratorClassName); method."
            + "Method 2: Set the META-INF/services/" + interfaceName + " file.");
    }
}
