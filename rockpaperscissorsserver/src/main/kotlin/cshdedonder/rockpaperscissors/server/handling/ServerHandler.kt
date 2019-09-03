package cshdedonder.rockpaperscissors.server.handling

import org.apache.mina.core.service.IoHandlerAdapter
import org.apache.mina.core.session.IoSession

object ServerHandler : IoHandlerAdapter() {

    private val HANDLER_MAP : MutableMap<Long, SessionHandler> = HashMap()

    override fun exceptionCaught(session: IoSession?, cause: Throwable?) {
        cause?.printStackTrace()
    }

    override fun messageReceived(session: IoSession?, message: Any?) {

    }

    override fun sessionClosed(session: IoSession?) {
        session?.let {
            HANDLER_MAP[it.id] = SessionHandler(it)
        }
    }

    override fun sessionOpened(session: IoSession?) {
        session?.let {
            HANDLER_MAP.remove(it.id)?.dispose()
        }
    }
}