package br.com.zup.edu.registraPix

import br.com.zup.RegistraChavePixServiceGrpc
import io.micronaut.http.HttpResponse.uri
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.net.URI
import io.micronaut.http.HttpResponse
import java.util.*
import javax.validation.Valid

@Validated
@Controller("/api/v1/clientes/{clienteId}")
class RegistraChaveController(private val registraChavePixClient: RegistraChavePixServiceGrpc.RegistraChavePixServiceBlockingStub) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)


    @Post("/pix")
    fun registraChave(@PathVariable clienteId: UUID, @Valid @Body request: NovaChavePixRequest): HttpResponse<Any> {

        LOGGER.info("[$clienteId] criando uma nova chave pix com $request")


        val grpcResponse = registraChavePixClient.send(request.toModelGrpc(clienteId))

        return io.micronaut.http.HttpResponse.created(location(clienteId, grpcResponse.pixId))
    }

}

private fun location(clienteId: UUID, pixId: String): URI {
    return HttpResponse.uri("api/pix/clientes/$clienteId/pix/$pixId")
}