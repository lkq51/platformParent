package connection;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.typesafe.config.ConfigFactory;

/**
 * @Author lou
 */
public class WatchActor extends UntypedActor {
	private LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

	public WatchActor(ActorRef actorRef){
		getContext().watch(actorRef);
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Terminated){
			logger.error(((Terminated)message).getActor().path() + "has Terminated. now shutdown the system");
		}else {
			unhandled(message);
		}
	}


	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("Hello", ConfigFactory.load("akka.config"));
		ActorRef myWork = system.actorOf(Props.create(akkaConection.class), "MyWork");
		ActorRef watchActor = system.actorOf(Props.create(WatchActor.class, myWork), "WatchActor");

		myWork.tell(akkaConection.Msg.WORKING, ActorRef.noSender());
		myWork.tell(akkaConection.Msg.DONE, ActorRef.noSender());

		myWork.tell(PoisonPill.getInstance(), ActorRef.noSender());
	}
}
