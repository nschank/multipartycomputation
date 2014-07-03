package nschank.crypto.mpc.millionaire_y82;

import nschank.crypto.history.History;
import nschank.crypto.history.HistoryImpl;
import nschank.crypto.player.AbstractParticipant;
import nschank.crypto.player.Participant;
import nschank.crypto.protocol.Protocol;
import nschank.crypto.protocol.instruct.Instruction;
import nschank.crypto.protocol.instruct.Instructions;
import nschank.crypto.scheme.EncryptionScheme;
import nschank.crypto.scheme.RSAScheme;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;


/**
 * Created by Nicolas Schank for package nschank.crypto.ot.millionaire_y82
 * Created on 27 Jun 2014
 * Current version implemented 30 Jun 2014
 * Moved to package nschank.crypto.mpc.millionaire_y82
 * Last updated on 30 Jun 2014
 *
 * A Multiparty Computation {@code Protocol} based on Yao's 1982 Millionaires' Protocol in order to determine whether the
 * first party has a lower value {@literal i} than the other party's value {@literal j}.
 *
 * @author nschank, Brown University
 * @version 2.1
 */
public class LessThan implements Protocol
{
	private final int k;
	private final Set<Participant> participants;

	/**
	 * Tells Alice and Bob how to participate in this {@code Protocol}. After running the Protocol, Alice and Bob
	 * will both have their {@literal output}s set to the boolean value of i<j.
	 *
	 * @param alice
	 * 		A party with value {@literal i} between 1 and 255
	 * @param bob
	 * 		A party with value {@literal j} between 1 and 255
	 */
	public LessThan(Participant alice, Participant bob, int k)
	{
		this.participants = new HashSet<>();
		this.participants.add(alice);
		this.participants.add(bob);

		this.k = k;

		alice.receive(getAlice(alice, bob));
		alice.expect(getAliceExpect(bob));
		bob.receive(getBob(alice, bob));
		bob.expect(getBobExpect(alice));
	}

	/**
	 * A mapping from Bob to the values Alice should expect from Bob.
	 *
	 * @param bob
	 * 		The participant besides Alice
	 *
	 * @return What Alice should expect from Bob
	 */
	private static Map<Participant, Collection<String>> getAliceExpect(final Participant bob)
	{
		final Map<Participant, Collection<String>> expected = new HashMap<>();
		final List<String> fromBob = new ArrayList<>();
		fromBob.add("k-j+1");
		fromBob.add("i<j");

		expected.put(bob, fromBob);
		return expected;
	}

	/**
	 * A mapping from Alice to the values Bob should expect from Alice
	 *
	 * @param alice
	 * 		The participant besides Bob
	 *
	 * @return What Bob should expect from Alice
	 */
	private static Map<Participant, Collection<String>> getBobExpect(final Participant alice)
	{
		final Map<Participant, Collection<String>> expected = new HashMap<>();
		final List<String> fromAlice = new ArrayList<>();
		fromAlice.add("Ea");
		fromAlice.add("p");
		fromAlice.add("zu");

		expected.put(alice, fromAlice);
		return expected;
	}

	/**
	 * Does a sample protocol
	 *
	 * @param args
	 * 		unused
	 *
	 * @throws InterruptedException
	 * 		If Alice or Bob's threads are interrupted (which they shouldn't be)
	 */
	public static void main(String[] args) throws InterruptedException
	{
		History h = new HistoryImpl();

		Participant alice = new LessThan.LessThanParticipant(h, (byte) 120, "Alice");

		Participant bob = new LessThan.LessThanParticipant(h, (byte) 90, "Bob");

		Protocol pr = new LessThan(alice, bob, 24);
		Thread a = new Thread(alice);
		Thread b = new Thread(bob);

		a.start();
		b.start();

		a.join();
		b.join();

		for(String event : h.eventsVisibleTo(alice))
			System.out.println(event);
		System.out.println("\n\n\n");
		for(String event : h.eventsVisibleTo(bob))
			System.out.println(event);
	}

	/**
	 * Creates a protocol for Alice (the owner of {@literal i}) to follow.
	 *
	 * @param bob
	 * 		The other participant in the protocol
	 *
	 * @return A list of {@code Instruction}s to follow
	 */
	private Iterable<Instruction> getAlice(Participant alice, Participant bob)
	{
		final List<Instruction> protocol = new ArrayList<>();

		protocol.add(Instructions.createFrom("security parameter", "choose a security parameter", (i, r) -> this.k));
		protocol.add(Instructions.rename("i", "choose a number between 1 and 127 (type byte)", "input"));
		protocol.add(Instructions.createFrom("scheme", "create an encryption and decryption scheme (type Scheme)",
				(a, b) -> new RSAScheme((int) a.get("security parameter"), b), "security parameter"));
		protocol.add(Instructions.createFrom("Ea", "specify the encryption function of scheme",
				(i, r) -> (Function<BigInteger, BigInteger>) (((EncryptionScheme<BigInteger, BigInteger>) i
						.get("scheme"))::encrypt), "scheme"));
		protocol.add(Instructions.createFrom("Da", "specify the decryption function",
				(i, r) -> (Function<BigInteger, BigInteger>) (((EncryptionScheme<BigInteger, BigInteger>) i
						.get("scheme"))::decrypt), "scheme"));
		protocol.add(Instructions.send("Ea", "send the encryption function to the other participant", alice, bob));
		protocol.add(Instructions
				.createFrom("yu", "calculate the values Da(k-j+u) for u=1...127 (type List<BigInteger>)", (i, r) -> {
							final List<BigInteger> b = new ArrayList<>();
							final BigInteger kj1 = (BigInteger) i.get("k-j+1");
							final Function<BigInteger, BigInteger> Da = (Function<BigInteger, BigInteger>) i.get("Da");
							for(int u = 1; u <= 127; u++)
								b.add(Da.apply(kj1.add(BigInteger.valueOf(u - 1))));
							return b;
						}, "k-j+1", "Da"));
		protocol.add(Instructions.createFrom("p", "create a prime of length N/2",
				(i, r) -> BigInteger.probablePrime(((int) i.get("security parameter")) / 2, r), "security parameter"));
		protocol.add(Instructions.createFrom("zu", "calculate zu = yu mod p, plus 1 if u > i", (i, r) -> {
			final List<BigInteger> yu = (List<BigInteger>) i.get("yu");
			final BigInteger p = (BigInteger) i.get("p");
			final byte I = (Byte) i.get("i");
			final List<BigInteger> zu = new ArrayList<>();

			for(int u = 1; u <= 127; u++)
			{
				zu.add(yu.get(u - 1).add(u <= I ? BigInteger.ONE : BigInteger.ZERO).mod(p));
			}

			return zu;
		}, "yu", "p", "i"));

		protocol.add(Instructions.send("p", "send p to Bob", alice, bob));
		protocol.add(Instructions.send("zu", "send zu to Bob", alice, bob));
		protocol.add(
				Instructions.createFrom("output", "receive output from Bob", (i, r) -> (boolean) i.get("i<j"), "i<j"));

		return protocol;
	}

	/**
	 * Creates a protocol for Bob (the owner of {@literal j}) to follow.
	 *
	 * @param alice
	 * 		The other participant in the protocol
	 *
	 * @return A list of {@code Instruction}s to follow
	 */
	private Iterable<Instruction> getBob(Participant alice, Participant bob)
	{
		final List<Instruction> protocol = new ArrayList<>();

		protocol.add(Instructions.createFrom("security parameter", "choose a security parameter", (i, r) -> this.k));
		protocol.add(Instructions.rename("j", "choose a number between 1 and 127 (type byte)", "input"));
		protocol.add(Instructions.createFrom("x", "choose a random N-bit prime (type BigInteger)",
				(i, r) -> BigInteger.probablePrime((int) i.get("security parameter") - 1, r), "security parameter"));
		protocol.add(Instructions.createFrom("k", "calculate the encryption of x under Ea", (i, r) -> {
			final Function<BigInteger, BigInteger> Ea = (Function<BigInteger, BigInteger>) i.get("Ea");
			return Ea.apply((BigInteger) i.get("x"));
		}, "Ea", "x"));
		protocol.add(Instructions.createFrom("k-j+1", "calculate k-j+1",
				(i, r) -> ((BigInteger) i.get("k")).add(BigInteger.ONE).subtract(BigInteger.valueOf((byte) i.get("j"))),
				"k", "j"));
		protocol.add(Instructions.send("k-j+1", "sending Alice k-j+1", bob, alice));
		protocol.add(Instructions.createFrom("xmodp", "calculate x mod p",
				(i, r) -> ((BigInteger) i.get("x")).mod((BigInteger) i.get("p")), "x", "p"));
		protocol.add(Instructions.createFrom("i<j", "check x mod p = z_j",
				(i, r) -> ((List<BigInteger>) i.get("zu")).get((byte) i.get("j") - 1)
						.equals((BigInteger) i.get("xmodp")), "zu", "j", "xmodp"));
		protocol.add(Instructions.send("i<j", "sending Alice answer", bob, alice));
		protocol.add(Instructions.createFrom("output", "final answer", (i, r) -> (boolean) i.get("i<j"), "i<j"));
		return protocol;
	}

	@Override
	public Set<Participant> getParticipants()
	{
		return Collections.unmodifiableSet(this.participants);
	}

	/**
	 * A Participant for the LessThan protocol. The byte i is set to the valueName "input"
	 */
	public static class LessThanParticipant extends AbstractParticipant
	{
		private final String name;

		public LessThanParticipant(History h, byte i, String name)
		{
			super(h);
			this.name = name;
			this.values.put("input", new Wrapper(i));
		}

		@Override
		public String getName()
		{
			return name;
		}
	}
}
