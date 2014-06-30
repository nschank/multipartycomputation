package nschank.crypto.ot.oneoftwo_gmw87;

import nschank.crypto.history.History;
import nschank.crypto.history.HistoryImpl;
import nschank.crypto.player.AbstractParticipant;
import nschank.crypto.player.Participant;
import nschank.crypto.protocol.Protocol;
import nschank.crypto.protocol.instruct.Instruction;
import nschank.crypto.protocol.instruct.Instructions;
import nschank.crypto.scheme.Scheme;
import nschank.crypto.scheme.Schemes;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;


/**
 * Created by Nicolas Schank for package nschank.crypto.ot.oneoftwo_gmw87
 * Created on 30 Jun 2014
 * Last updated on 30 Jun 2014
 *
 * A {@code Protocol} which transfers a single bit from Sender to Receiver. Sender never learns which of their bits was
 * sent, and Receiver never learns the other bits not sent.
 *
 * @author nschank, Brown University
 * @version 1.1
 */
public class ObliviousBitTransfer implements Protocol
{
	private final int k;
	private final Set<Participant> participants;

	/**
	 * Implements a Protocol for exchanging a bit obliviously
	 */
	public ObliviousBitTransfer(final Participant sender, final Participant receiver, final int k)
	{
		this.participants = new HashSet<>();
		this.participants.add(sender);
		this.participants.add(receiver);

		this.k = k;

		sender.receive(getSender(sender, receiver));
		sender.expect(getSenderExpects(receiver));
		receiver.receive(getReceiver(sender, receiver));
		receiver.expect(getReceiverExpects(sender));
	}

	/**
	 *
	 */
	public static void main(String[] args) throws InterruptedException
	{
		History h = new HistoryImpl();

		Participant receiver = new OBTReceiver(h, false);

		Participant sender = new OBTSender(h, false, true);

		Protocol pr = new ObliviousBitTransfer(sender, receiver, 128);
		Thread a = new Thread(receiver);
		Thread b = new Thread(sender);

		a.start();
		b.start();

		a.join();
		b.join();

		for(String event : h.eventsVisibleTo(receiver))
			System.out.println(event);
		System.out.println("\n\n\n");
		for(String event : h.eventsVisibleTo(sender))
			System.out.println(event);
	}

	@Override
	public Set<Participant> getParticipants()
	{
		return Collections.unmodifiableSet(this.participants);
	}

	private Iterable<Instruction> getReceiver(final Participant sender, final Participant receiver)
	{
		List<Instruction> protocol = new ArrayList<>();

		protocol.add(Instructions.createFrom("security parameter", "choose a security parameter", (i, r) -> this.k));
		protocol.add(Instructions.createFrom("r0", "choose a random value in the encryption domain",
											 (i, r) -> BigInteger.probablePrime((int) i.get("security parameter") - 1,
																				r), "security parameter"));
		protocol.add(Instructions.createFrom("r1", "choose a random value in the encryption domain",
											 (i, r) -> BigInteger.probablePrime((int) i.get("security parameter") - 1,
																				r), "security parameter"));
		protocol.add(Instructions.createFrom("u", "if alpha=false, Ea(r0); if alpha=true, r0", (i, r) -> {
			Function<BigInteger, BigInteger> Ea = ((Function<BigInteger, BigInteger>) i.get("Ea"));
			BigInteger r0 = (BigInteger) i.get("r0");
			boolean alpha = (Boolean) i.get("alpha");
			if(alpha) return r0;
			else return Ea.apply(r0);
		}, "alpha", "r0", "Ea"));
		protocol.add(Instructions.createFrom("v", "if alpha=false, r1; if alpha=true, Ea(r1)", (i, r) -> {
			Function<BigInteger, BigInteger> Ea = ((Function<BigInteger, BigInteger>) i.get("Ea"));
			BigInteger r1 = (BigInteger) i.get("r1");
			boolean alpha = (Boolean) i.get("alpha");
			if(alpha) return Ea.apply(r1);
			else return r1;
		}, "r1", "Ea", "alpha"));
		protocol.add(Instructions.send("u", "a value", receiver, sender));
		protocol.add(Instructions.send("v", "a value", receiver, sender));
		protocol.add(Instructions.createFrom("Bf(ralpha)", "the hardcore bit of ralpha", (i, r) -> {
			Function<BigInteger, BigInteger> Bf = ((Function<BigInteger, BigInteger>) i.get("Bf"));
			BigInteger r0 = (BigInteger) i.get("r0");
			BigInteger r1 = (BigInteger) i.get("r1");
			boolean alpha = (Boolean) i.get("alpha");
			if(alpha) return Bf.apply(r1);
			else return Bf.apply(r0);
		}, "r0", "r1", "Bf", "alpha"));
		protocol.add(Instructions.createFrom("balpha", "Bf(ralpha) XOR dalpha", (i, r) -> {
			boolean bf = (Boolean) i.get("Bf(ralpha)");
			boolean d0 = (Boolean) i.get("d0");
			boolean d1 = (Boolean) i.get("d1");
			boolean alpha = (Boolean) i.get("alpha");
			return (alpha && (bf != d1)) || (!alpha && (bf != d0));
		}, "d0", "d1", "Bf(ralpha)", "alpha"));

		protocol.add(Instructions.rename("output", "set output to balpha", "balpha"));

		return protocol;
	}

	private Map<Participant, Collection<String>> getReceiverExpects(final Participant sender)
	{
		final Map<Participant, Collection<String>> expects = new HashMap<>();
		final Collection<String> fromSender = new ArrayList<>();
		fromSender.add("Ea");
		fromSender.add("Bf");
		fromSender.add("d0");
		fromSender.add("d1");

		expects.put(sender, fromSender);
		return expects;
	}

	private Iterable<Instruction> getSender(final Participant sender, final Participant receiver)
	{
		List<Instruction> protocol = new ArrayList<>();

		protocol.add(Instructions.createFrom("security parameter", "choose a security parameter", (i, r) -> this.k));
		protocol.add(Instructions.createFrom("scheme", "create an encryption and decryption scheme (type Scheme)",
											 (a, b) -> Schemes.RSA(b, (int) a.get("security parameter")),
											 "security parameter"));
		protocol.add(Instructions.createFrom("Ea", "specify the encryption function of scheme", (i,
																								 r) -> (Function<BigInteger, BigInteger>) (((Scheme<BigInteger, BigInteger>) i
				.get("scheme"))::encrypt), "scheme"));
		protocol.add(Instructions.send("Ea", "send receiver the encryption function", sender, receiver));
		protocol.add(Instructions.createFrom("Da", "specify the decryption function", (i,
																					   r) -> (Function<BigInteger, BigInteger>) (((Scheme<BigInteger, BigInteger>) i
				.get("scheme"))::decrypt), "scheme"));
		protocol.add(Instructions.createFrom("Bf", "specify the hardcore predicate", (i,
																					  r) -> (Function<BigInteger, Boolean>) (((Scheme<BigInteger, BigInteger>) i
				.get("scheme"))::hardcoreBit), "scheme"));
		protocol.add(Instructions.send("Bf", "send receiver the hardcore predicate", sender, receiver));

		protocol.add(Instructions.createFrom("c0", "c0=Bf(Da(u))", (i, r) -> {
			Function<BigInteger, BigInteger> Da = (Function<BigInteger, BigInteger>) i.get("Da");
			Function<BigInteger, Boolean> Bf = (Function<BigInteger, Boolean>) i.get("Bf");
			return Bf.apply(Da.apply((BigInteger) i.get("u")));
		}, "u", "Da", "Bf"));
		protocol.add(Instructions.createFrom("c1", "c1=Bf(Da(v))", (i, r) -> {
			Function<BigInteger, BigInteger> Da = (Function<BigInteger, BigInteger>) i.get("Da");
			Function<BigInteger, Boolean> Bf = (Function<BigInteger, Boolean>) i.get("Bf");
			return Bf.apply(Da.apply((BigInteger) i.get("v")));
		}, "v", "Da", "Bf"));
		protocol.add(Instructions.createFrom("d0", "d0 = c0 XOR b0", (i, r) -> !(i.get("c0")).equals(i.get("b0")), "c0",
											 "b0"));
		protocol.add(Instructions.createFrom("d1", "d1 = c1 XOR b1", (i, r) -> !(i.get("c1")).equals(i.get("b1")), "c1",
											 "b1"));
		protocol.add(Instructions.send("d0", "send receiver d0", sender, receiver));
		protocol.add(Instructions.send("d1", "send receiver d1", sender, receiver));

		protocol.add(Instructions.createFrom("output", "no output", (i, r) -> ""));
		return protocol;
	}

	private Map<Participant, Collection<String>> getSenderExpects(final Participant receiver)
	{
		final Map<Participant, Collection<String>> expects = new HashMap<>();
		final Collection<String> fromReceiver = new ArrayList<>();

		fromReceiver.add("u");
		fromReceiver.add("v");

		expects.put(receiver, fromReceiver);
		return expects;
	}

	/**
	 * A Participant for the LessThan protocol. The byte i is set to the valueName "input"
	 */
	public static class OBTReceiver extends AbstractParticipant
	{
		public OBTReceiver(History h, boolean alpha)
		{
			super(h);
			this.values.put("alpha", new Wrapper(alpha));
		}

		@Override
		public String getName()
		{
			return "Receiver";
		}
	}

	/**
	 * A Participant for the LessThan protocol. The byte i is set to the valueName "input"
	 */
	public static class OBTSender extends AbstractParticipant
	{
		public OBTSender(History h, boolean b0, boolean b1)
		{
			super(h);
			this.values.put("b0", new Wrapper(b0));
			this.values.put("b1", new Wrapper(b1));
		}

		@Override
		public String getName()
		{
			return "Sender";
		}
	}
}
