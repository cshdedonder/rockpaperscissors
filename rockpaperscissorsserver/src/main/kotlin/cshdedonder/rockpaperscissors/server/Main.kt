package cshdedonder.rockpaperscissors.server

import cshdedonder.rockpaperscissors.server.handling.ServerHandler
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory
import org.apache.mina.filter.logging.LoggingFilter
import org.apache.mina.transport.socket.nio.NioSocketAcceptor
import java.net.InetSocketAddress

internal const val PORT: Int = 9456

fun main() {
    with(NioSocketAcceptor()){
        with(filterChain){
            addLast("logger", LoggingFilter())
            addLast("codec", ProtocolCodecFilter(ObjectSerializationCodecFactory()))
        }
        handler = ServerHandler
        with(sessionConfig){
            readBufferSize = 2048
//            setIdleTime(IdleStatus.BOTH_IDLE, 10)
        }
        bind(InetSocketAddress(PORT))
    }
}