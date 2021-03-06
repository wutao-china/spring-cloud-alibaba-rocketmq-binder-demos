/*
 * Copyright (C) 2018 the original author or authors.
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

package org.springframework.cloud.alibaba.cloud.demo;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

/**
 * @author wut
 */
@Service
public class OrderlyRetryReceiveService {

	private static final Logger log = LoggerFactory
			.getLogger(OrderlyRetryReceiveService.class);

	private AtomicInteger count = new AtomicInteger(1);

	@StreamListener(Sink.INPUT)
	public void receiveOrderlyMsg(String receiveMsg) {
	    log.info("invoke: " + count.get());
		if (count.getAndIncrement() <= 6) {
            throw new RuntimeException("Oops: " + receiveMsg);
		}
		else {
			log.info("receiveOrderlyMsg: " + receiveMsg);
		}
	}

}
