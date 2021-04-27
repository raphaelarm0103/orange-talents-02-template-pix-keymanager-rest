package br.com.zup.edu.registraPix

import br.com.zup.RegistraChavePixRequest
import br.com.zup.TipoChaveEnum
import br.com.zup.TipoConta
import io.micronaut.core.annotation.Introspected
import io.micronaut.validation.validator.constraints.EmailValidator
import java.util.*


@Introspected
class NovaChavePixRequest(
    val tipoConta: TipoContaRequest,
    val chave: String?,
    val tipoChave: TipoChaveRequest
) {
    fun toModelGrpc(clientId: UUID): RegistraChavePixRequest?{
        return RegistraChavePixRequest.newBuilder()
            .setClienteId(clientId.toString())
            .setTipoConta(tipoConta.atributoGrpc)
            .setTipoChaveEnum(tipoChave.atributoGrpc)
            .setChave(chave ?: "")
            .build()
    }

}

enum class TipoChaveRequest(val atributoGrpc: TipoChaveEnum) {

    CPF(TipoChaveEnum.CPF) {
        override fun valida(chave: String?): Boolean {
            return chave.isNullOrBlank() //CPF não deve ser preenchido pois é o próprio CPF cadastrado do cliente
        }
    },

    CELULAR(TipoChaveEnum.CELULAR) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) {
                return false
            }
            return chave.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
    },

    EMAIL(TipoChaveEnum.EMAIL) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) {
                return false
            }
            return EmailValidator().run {
                initialize(null)
                isValid(chave, null)
            }
        }
    },


    ALEATORIA(TipoChaveEnum.ALEATORIA) {
        override fun valida(chave: String?) =
            chave.isNullOrBlank() // chave aleatória não deve ser preenchida pois é criada automaticamente
    };

    abstract fun valida(chave: String?): Boolean

}


enum class TipoContaRequest(val atributoGrpc: TipoConta) {

    CONTA_CORRENTE(TipoConta.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoConta.CONTA_POUPANCA)
}
