package org.shenjia.mybatis.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Strings {

	public static String join(String... args) {
		return Stream.of(args).collect(Collectors.joining());
	}
}
