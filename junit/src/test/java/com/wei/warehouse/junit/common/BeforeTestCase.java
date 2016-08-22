package com.wei.warehouse.junit.common;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * mock网络请求
 * Created by wei on 16/8/17.
 */
public class BeforeTestCase {
	@ClassRule
	@Rule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(RemoteMockServer.PORT);

	@Before
	public void init () {
		stubFor(get(urlEqualTo("/hello"))
				.willReturn(aResponse().withHeader("Content-Type", RemoteMockServer.CONTENT_TYPE).withStatus(200)
						.withBody("hello mock")));
	}
}
