package br.com.zup.edu.removePix

import br.com.zup.RemoveChavePixRequest
import br.com.zup.RemoveChavePixServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/api/v1/clientes/{clienteId}")
class RemoveChavePixController(private val removeChavePixClient: RemoveChavePixServiceGrpc.RemoveChavePixServiceBlockingStub) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Delete("/pix/{pixId}")
    fun deletaChave(clienteId: UUID,
                    pixID: UUID): HttpResponse<Any> {

        LOGGER.info("[$clienteId] recomento uma chave pix com $pixID")

        removeChavePixClient.remove(RemoveChavePixRequest.newBuilder()
            .setCliendId(clienteId.toString())
            .setPixID(pixID.toString())
            .build())

        return HttpResponse.ok()

    }
}