package com.twit4life.modele;

public enum Twit4lifeKeys {

	consumerKeys("LRFfm5OXSPpyhB9nWJb1nl7h2","ArseMLsJNv3dSvCnDhebE3cfNX03hHJOobe0bcX9IbIBEGB8GA"),
	accessTokenKeys("3806038342-iGej3rEhvkZeVFo7gY9TFCvfjbheTYEYbWVynfF","QNKsis0mrxT7DJuROwXl58VhfKqi0dc2W53JrpzwEkQt1");

	String key= "";
	String secret= "";


	private Twit4lifeKeys(String name,String editor) {

		this.key = name;
		this.secret = editor;

	}
	/**
	 * Retourne le code secret de l'application par rapport a twitter
	 * @return
	 */
	public String getSecret(){


		return secret;

	}
	/**
	 * Retourne la clef  de l'application via twitter
	 * @return
	 */
	public String getKey(){

		return key;

	}

}
