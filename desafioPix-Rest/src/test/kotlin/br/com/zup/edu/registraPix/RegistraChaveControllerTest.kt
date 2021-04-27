package br.com.zup.edu.registraPix

import br.com.zup.RegistraChavePixResponse
import br.com.zup.RegistraChavePixServiceGrpc
import br.com.zup.edu.shared.FactoryGrpcClient
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class RegistraChaveControllerTest{

    @field:Inject
    lateinit var registraStub: RegistraChavePixServiceGrpc.RegistraChavePixServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient


    @Factory
    @Replaces(factory = FactoryGrpcClient::class)
    internal class RegistraStubFactory {
        @Singleton
        fun stubMock() = Mockito.mock(RegistraChavePixServiceGrpc.RegistraChavePixServiceBlockingStub::class.java)
    }

    @Test
    fun `Deve registrar uma chave pix`() {
        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val responseGrpc = RegistraChavePixResponse.newBuilder()
            .setPixId(pixId)
            .build()

        given(registraStub.send(Mockito.any())).willReturn(responseGrpc)

        val novaChavePix = NovaChavePixRequest(
            tipoConta = TipoContaRequest.CONTA_CORRENTE,
            chave = "teste@teste.com",
            tipoChave = TipoChaveRequest.EMAIL
        )

        val request = HttpRequest.POST("api/clientes/$clienteId/pix", novaChavePix)
        val response = client.toBlocking().exchange(request, NovaChavePixRequest::class.java)

        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!.contains(pixId))
    }

}

