package connection;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @Author loux
 */
public class akkaConection extends UntypedActor {
	LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

	public static enum Msg{
		WORKING, DONE, CLOSE;
	}

	@Override
	public void onReceive(Object message) throws Exception {
		try {
			if (message == Msg.WORKING){
				logger.info("i am working");
			}else if(message == Msg.DONE){
				logger.info("stop working");
			}else if(message == Msg.CLOSE){
				logger.info("stop close");
				getSender().tell(Msg.CLOSE, getSelf());
				getContext().stop(getSelf());
			}else {
				unhandled(message);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void preStart() throws Exception {
		logger.info("myWork starting...");
	}

	@Override
	public void postStop() throws Exception {
		logger.info("myWork Stopping...");
	}
}
