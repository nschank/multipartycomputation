package nschank.crypto.mpc.fourthgraders;

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


/**
 * Created by Nicolas Schank for package nschank.crypto.mpc.fourthgraders
 * Created on 30 Jun 2014
 * Last updated on 30 Jun 2014
 *
 * @author nschank, Brown University
 * @version 1.1
 */
public class FourthGrade implements Protocol
{
	private final int k;
	private final Set<Participant> participants;

	public FourthGrade(final int k, Participant... participants)
	{
		this.k = k;
		Set<Participant> participantSet = new HashSet<>();
		Collections.addAll(participantSet, participants);
		this.participants = participantSet;

		for(Participant p : participants)
		{
			p.expect(expected(p));
			p.receive(protocol(p));
		}
	}

	public static void main(String[] args) throws InterruptedException
	{
		int index = 40;
		History h = new HistoryImpl();
		Participant[] p = new Participant[index];
		Thread[] t = new Thread[index];
		for(int i = 0; i < index; i++)
		{
			int crush;
			do crush = (int) (index * Math.random()); while(crush == i);
			p[i] = new FourthGrader(h, "participant" + crush, "participant" + i);
			System.out.println(i + "->" + crush);
			t[i] = new Thread(p[i]);
		}

		Protocol pr = new FourthGrade(10, p);
		for(Thread a : t)
			a.start();
		for(Thread a : t)
			a.join();

		for(Participant a : p)
		{
			String last = "";
			for(String s : h.eventsVisibleTo(a))
				last = s;
			System.out.println(last);
		}
	}

	private String[] allValues(final Participant except, final String arg)
	{
		String[] all = new String[this.participants.size()];
		int i = 0;
		for(Participant p : this.participants)
			if(p != except) all[i++] = p.getName() + ":" + arg;
		all[i] = "crush";
		return all;
	}

	private void broadcast(final Participant except, final List<Instruction> protocol, final String broadcastName,
						   final String description)
	{
		for(Participant p : this.participants)
		{
			if(p == except) continue;
			protocol.add(Instructions.send(except.getName() + ":" + broadcastName, description, except, p));
		}
	}

	/**
	 * @param except
	 * 		The Participant who is expecting the values
	 *
	 * @return A Map from other participants to the values they will be providing
	 */
	private Map<Participant, Collection<String>> expected(Participant except)
	{
		final Map<Participant, Collection<String>> expect = new HashMap<>();
		for(Participant p : this.participants)
		{
			if(p == except) continue;
			final Collection<String> s = new ArrayList<>();

			s.add(p.getName() + ":pk");
			s.add(p.getName() + ":c");
			s.add(p.getName() + ":r");
			s.add(p.getName() + ":x");

			expect.put(p, s);
		}
		return expect;
	}

	@Override
	public Set<Participant> getParticipants()
	{
		return this.participants;
	}

	private Iterable<Instruction> protocol(final Participant p)
	{
		List<Instruction> protocol = new ArrayList<>();

		protocol.add(Instructions.createFrom("security parameter", "choose a security parameter", (i, r) -> this.k));
		protocol.add(Instructions.createFrom("public scheme",
											 "create an encryption and decryption scheme (type Scheme)",
											 (a, b) -> Schemes.RSA(b, 3 * (int) a.get("security parameter")),
											 "security parameter"));
		protocol.add(Instructions.createFrom(p.getName() + ":pk", "specify the encryption function of scheme",
											 (i, r) -> (((Scheme<BigInteger, BigInteger>) i.get("public scheme"))
													 .publicKey()), "public scheme"));
		protocol.add(Instructions.createFrom("sk", "specify the decryption function",
											 (i, r) -> ((Scheme<BigInteger, BigInteger>) i.get("public scheme"))
													 .privateKey(), "public scheme"));

		broadcast(p, protocol, "pk", p.getName() + " broadcasts a public key");

		protocol.add(Instructions.createFrom("private scheme",
											 "create an encryption and decryption scheme (type Scheme)",
											 (a, b) -> Schemes.RSA(b, (int) a.get("security parameter")),
											 "security parameter"));
		protocol.add(Instructions.createFrom("pik", "specify the encryption function of scheme",
											 (i, r) -> (((Scheme<BigInteger, BigInteger>) i.get("private scheme"))
													 .publicKey()), "private scheme"));
		protocol.add(Instructions.createFrom("sigmak", "specify the decryption function",
											 (i, r) -> ((Scheme<BigInteger, BigInteger>) i.get("private scheme"))
													 .privateKey(), "private scheme"));

		protocol.add(Instructions.createFrom("crushpk", "looks at crushes public key", (i, r) -> i.get(i.get("crush")
																											   .toString()
																											   + ":pk"),
											 allValues(p, "pk")));
		protocol.add(Instructions.createFrom(p.getName() + ":c",
											 "calculates the encryption of pik under crushes public key",
											 (i, r) -> Schemes.encryptRSA(i.get("crushpk").toString(), 3 * (int) i.get(
													 "security parameter")).apply(new BigInteger(i.get("pik")
																										 .toString(),
																								 2)), "crushpk",
											 "security parameter", "pik"));
		broadcast(p, protocol, "c", p.getName() + " broadcasts c");
		protocol.add(Instructions.createFrom("crushc", "looks at crushes c", (i, r) -> i.get(i.get("crush").toString()
																									 + ":c"), allValues(
													 p, "c")));
		protocol.add(Instructions.createFrom(p.getName() + ":r", "creates a random confirmation value",
											 (i, r) -> BigInteger.probablePrime((int) i.get("security parameter") / 2,
																				r), "security parameter"));
		broadcast(p, protocol, "r", p.getName() + " broadcasts r");
		protocol.add(Instructions.createFrom("crushr", "looks at crushes r", (i, r) -> i.get(i.get("crush").toString()
																									 + ":r"), allValues(
				p, "r")));
		protocol.add(Instructions.createFrom("delta", "decryption of crushc under sk", (i, r) -> Schemes.decryptRSA(
													 i.get("sk").toString(), 3 * (int) i.get("security parameter"))
													 .apply((BigInteger) i.get("crushc")).toString(2),
											 "security parameter", "sk", "crushc"));
		protocol.add(Instructions.createFrom(p.getName() + ":x", "encryption of crushr under delta",
											 (i, r) -> Schemes.encryptRSA(i.get("delta").toString(), (int) i.get(
													 "security parameter")).apply((BigInteger) i.get("crushr"))
													 .toString(2), "security parameter", "delta", "crushr"));
		broadcast(p, protocol, "x", p.getName() + " broadcasts x");
		protocol.add(Instructions.createFrom("crushx", "looks at crushes x", (i, r) -> i.get(i.get("crush").toString()
																									 + ":x"), allValues(
				p, "x")));
		protocol.add(Instructions.createFrom("s", "decryption of crushx under sigmak", (i, r) -> Schemes.decryptRSA(
													 i.get("sigmak").toString(), (int) i.get("security parameter"))
													 .apply(new BigInteger(i.get("crushx").toString(), 2)), "crushx",
											 "sigmak", "security parameter"));
		protocol.add(Instructions.createFrom("output", "s = r", (i, r) -> i.get(p.getName() + ":r").equals(i.get("s")),
											 "s", p.getName() + ":r"));

		return protocol;
	}

	public static class FourthGrader extends AbstractParticipant
	{
		private String name;

		public FourthGrader(History h, String crushname, String name)
		{
			super(h);
			this.values.put("crush", new Wrapper(crushname));
			h.add(name + " has a crush on " + crushname);
			this.name = name;
		}

		@Override
		public String getName()
		{
			return this.name;
		}
	}
}
