package br.com.zup.edu

import br.com.zup.CarregaChavePixServiceGrpc
import br.com.zup.ListaChavePixServiceGrpc
import br.com.zup.RegistraChavePixServiceGrpc
import br.com.zup.RemoveChavePixServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class FactoryGrpcClient(@GrpcChannel("http://localhost:50051") val channel: ManagedChannel) {

    @Singleton
    fun registraChave() = RegistraChavePixServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun deletaChave() = RemoveChavePixServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun carregaChave() = CarregaChavePixServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaChave() = ListaChavePixServiceGrpc.newBlockingStub(channel)
}