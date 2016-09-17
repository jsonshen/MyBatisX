/**
 * Copyright 2015-2016 the original author or authors.
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
package org.shenjia.mybatis.generator.plugins;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.internal.util.messages.Messages;

/**
 * 
 * @author json
 *
 */
public class JavaCommentPlugin extends PluginAdapter {

	private String licenseComment;
	private String classComment;

	public boolean validate(List<String> warnings) {
		boolean valid = true;
		if (StringUtility.stringHasValue(properties.getProperty("licenseCommentFile"))) {
			File lcf = new File(properties.getProperty("licenseCommentFile"));
			if (lcf.exists() && lcf.isFile()) {
				licenseComment = copyFileToString(lcf);
			} else {
				System.err.println("[ERROR] ----- LICENSE-COMMENT-FILE -----:" + lcf.getPath());
				warnings.add(Messages.getString("ValidationError.18", "JavaCommentPlugin", "licenseCommentFile"));
				valid = false;
			}
		}
		if (StringUtility.stringHasValue(properties.getProperty("classCommentFile"))) {
			File ccf = new File(properties.getProperty("classCommentFile"));
			if (ccf.exists() && ccf.isFile()) {
				classComment = copyFileToString(ccf);
			} else {
				System.err.println("[ERROR] ----- CLASS-COMMENT-FILE -----:" + ccf.getPath());
				warnings.add(Messages.getString("ValidationError.18", "JavaCommentPlugin", "classCommentFile"));
				valid = false;
			}
		}
		return valid;
	}

	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		if (null != licenseComment) {
			interfaze.addFileCommentLine(licenseComment);
		}
		if (null != classComment) {
			interfaze.addJavaDocLine(classComment);
		}
		return true;
	}

	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		return addJavaComment(topLevelClass);
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		return addJavaComment(topLevelClass);
	}

	@Override
	public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		return addJavaComment(topLevelClass);
	}

	@Override
	public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		return addJavaComment(topLevelClass);
	}

	@Override
	public boolean providerGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		return addJavaComment(topLevelClass);
	}

	private boolean addJavaComment(TopLevelClass tlc) {
		if (null != licenseComment) {
			tlc.addFileCommentLine(licenseComment);
		}
		if (null != classComment) {
			tlc.addJavaDocLine(classComment);
		}
		return true;
	}

	private String copyFileToString(File file) {
		InputStream in = null;
		try {
			byte[] bytes = new byte[(int) file.length()];
			in = new FileInputStream(file);
			in.read(bytes);
			return new String(bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
