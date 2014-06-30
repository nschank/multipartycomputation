//package nschank.crypto.ot.combined_gmw87;
//
//import nschank.crypto.Participant;
//import nschank.crypto.Protocol;
//
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//
//
///**
//* Created by Nicolas Schank for package nschank.crypto.ot.combined_gmw87
//* Created on 27 Jun 2014
//* Last updated on 27 Jun 2014
//*
//*
//*
//* @author nschank, Brown University
//* @version 1.1
//*/
//public class COTGate87 implements Protocol<String>
//{
//	private final Participant alice;
//	private final Participant bob;
//	private final String akey;
//	private final String bkey;
//	private final String ainputkey;
//	private final String btruekey;
//	private final String bfalsekey;
//	private final Set<Participant> participants;
//
//	/**
//	 *
//	 */
//	public COTGate87(Participant Alice, Participant Bob, String akey, String bkey, String ainputkey, String btruekey, String bfalsekey)
//	{
//		alice = Alice;
//		bob = Bob;
//		this.akey = akey;
//		this.bkey = bkey;
//		this.ainputkey = ainputkey;
//		this.btruekey = btruekey;
//		this.bfalsekey = bfalsekey;
//		this.participants = new HashSet<>();
//		this.participants.add(Alice);
//		this.participants.add(Bob);
//	}
//
//	/**
//	 *
//	 */
//	public static void main(String[] args)
//	{
//
//	}
//
//	/**
//	 * Computes a result, or throws an exception if unable to do so.
//	 *
//	 * @return computed result
//	 *
//	 * @throws Exception
//	 * 		if unable to compute a result
//	 */
//	@Override
//	public String call() throws Exception
//	{
//		boolean a = (Boolean)alice.get(akey);
//		boolean b = (Boolean)bob.get(bkey);
//
//		String[] astr = new String[15];
//		String[] bstr = new String[15];
//
//		return "";
//	}
//
//	@Override
//	public Set<Participant> getParticipants()
//	{
//		return Collections.unmodifiableSet(this.participants);
//	}
//}
