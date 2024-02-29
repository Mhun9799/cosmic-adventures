package org.team.b4.cosmicadventures.common.config

import io.netty.channel.ChannelOption
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.http.codec.HttpMessageWriter
import org.springframework.http.codec.LoggingCodecSupport
import org.springframework.web.reactive.function.client.*
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import java.util.function.Consumer
import kotlin.math.log


@Configuration
class WebClientConfig {
    @Bean
    fun webClient(): WebClient {
        //in-memory 값 늘려줌
        val exchangeStrategies = ExchangeStrategies.builder()
            .codecs { configurer: ClientCodecConfigurer ->
                configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 50)
            }
            .build()
        //request/response 정보를 로깅에서 로그확인
        exchangeStrategies
            .messageWriters().stream()
            .filter { obj: HttpMessageWriter<*>? ->
                LoggingCodecSupport::class.java.isInstance(
                    obj
                )
            }
            .forEach { writer: HttpMessageWriter<*> ->
                (writer as LoggingCodecSupport).isEnableLoggingRequestDetails =
                    true
            }
        return WebClient.builder()
            .clientConnector(
                ReactorClientHttpConnector(
                    HttpClient
                        .create()
                        .secure { sslContextSpec ->
                            sslContextSpec.sslContext(
                                SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)
                                    .build()
                            )
                        }
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 120000)
                        .doOnConnected { conn ->
                            conn.addHandlerLast(ReadTimeoutHandler(180))
                                .addHandlerLast(WriteTimeoutHandler(180))
                        }
                )
            )
            .exchangeStrategies(exchangeStrategies)
            .filter(ExchangeFilterFunction.ofRequestProcessor { clientRequest: ClientRequest ->
                log.debug("Request: {} {}", clientRequest.method(), clientRequest.url())
                clientRequest.headers()
                    .forEach { name: String?, values: List<String?> ->
                        values.forEach(
                            Consumer<String?> { value: String? ->
                                log.debug(
                                    "{} : {}",
                                    name,
                                    value
                                )
                            })
                    }
                Mono.just<ClientRequest>(clientRequest)
            })
            .filter(ExchangeFilterFunction.ofResponseProcessor { clientResponse: ClientResponse ->
                clientResponse.headers().asHttpHeaders()
                    .forEach { name: String?, values: List<String?> ->
                        values.forEach(
                            Consumer<String?> { value: String? ->
                                log.debug(
                                    "{} : {}",
                                    name,
                                    value
                                )
                            })
                    }
                Mono.just<ClientResponse>(clientResponse)
            })
            .defaultHeader(
                "user-agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.3"
            )
            .build()
    }
}