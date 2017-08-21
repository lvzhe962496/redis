package com.bawei.ssm.user.test;

import redis.clients.jedis.Jedis;

public class Test {
		public static void main(String[] args) {
			Jedis js=new Jedis("192.168.127.128");
			js.auth("962496");
			System.out.println(js.ping());
		}
}
