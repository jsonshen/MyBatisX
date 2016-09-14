package org.shenjia.mybatis.generator.plugins;

import java.lang.reflect.Field;
import java.util.List;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

public class MapperFileOverwritePlugin extends PluginAdapter {

	public boolean validate(List<String> warnings) {
		return true;
	}
	
	@Override
	public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
		List<GeneratedXmlFile> gxfs = introspectedTable.getGeneratedXmlFiles();
		System.err.println("GXFS size is:" + gxfs.size());
		for (GeneratedXmlFile gxf : gxfs) {
			System.err.println("Before GXF is mergeable:" + gxf.isMergeable());
			try {
				Field field = gxf.getClass().getDeclaredField("isMergeable");
				field.setAccessible(true);
				field.setBoolean(gxf, false);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			System.err.println("After GXF is mergeable:" + gxf.isMergeable());
		}
		return gxfs;
	}
}
