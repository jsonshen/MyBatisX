package org.shenjia.mybatis.spring;

public abstract class JdbcDao<T extends JdbcModel<T>> implements JdbcMapper<T> {

	protected JdbcClient client;

	protected T model;

	public JdbcDao(JdbcClient client, T model) {
		this.client = client;
		this.model = model;
	}

	@Override
	public JdbcClient client() {
		return client;
	}

	@Override
	public T model() {
		return model;
	}

}
