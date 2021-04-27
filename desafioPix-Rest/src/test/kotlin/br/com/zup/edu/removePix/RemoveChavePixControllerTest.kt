package br.com.zup.edu.removePix

import br.com.zup.RemoveChavePixResponse
import br.com.zup.RemoveChavePixServiceGrpc
import org.junit.jupiter.api.Assertions.*


import br.com.zup.edu.shared.FactoryGrpcClient
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class RemoveChavePixControllerTest {

    @field:Inject
    lateinit var removeStub: RemoveChavePixServiceGrpc.RemoveChavePixServiceBlockingStub

    @Factory
    @Replaces(factory = FactoryGrpcClient::class)
    internal class RemoveStubFactory {

        @Singleton
        fun deletaChave() = mock(RemoveChavePixServiceGrpc.RemoveChavePixServiceBlockingStub::class.java)
    }

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `Deve deletar uma chave pix existente`() {

        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val respostaGrpc = RemoveChavePixResponse.newBuilder()
            .setMensagem("Chave Removida")
            .build()
        given(removeStub.remove(any())).willReturn(respostaGrpc)


        val request = HttpRequest.DELETE<Any>("/api/clientes/$clienteId/pix/$pixId")
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.OK, response.status)

    }
}