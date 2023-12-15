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
package org.shenjia.mybatis.generator.types;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

public class MyBatisXJavaTypeResolver extends JavaTypeResolverDefaultImpl {

	public static final String TYPE_RESOLVER_TYPE_MAPPINGS_FILE = "typeMappingsFile";

	private static Map<String, Integer> sqlTypes = new HashMap<>();

	public MyBatisXJavaTypeResolver() {
		for (Field field : Types.class.getFields()) {
			try {
				sqlTypes.put(field.getName(), field.getInt(Types.class));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void setContext(Context context) {
		super.setContext(context);
		String filePath = properties.getProperty(TYPE_RESOLVER_TYPE_MAPPINGS_FILE);
		if (null == filePath) {
			return;
		}
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			Properties mappings = new Properties();
			try (InputStream in = new FileInputStream(file)) {
				mappings.load(in);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			for (String typeName : mappings.stringPropertyNames()) {
				if (!sqlTypes.containsKey(typeName)) {
					System.err.println("Unsupported sql type " + typeName);
					continue;
				}
				int sqlType = sqlTypes.get(typeName);
				String javaType = mappings.getProperty(typeName);
				typeMap.put(sqlType, new JdbcTypeInformation(typeName, new FullyQualifiedJavaType(javaType)));
			}
		}
	}
}
