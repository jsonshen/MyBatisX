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
