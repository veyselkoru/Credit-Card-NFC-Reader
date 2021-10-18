package com.pro100svitlo.creditCardNfcReader.enums;

import com.pro100svitlo.creditCardNfcReader.utils.BytesUtils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;


/**
 * Class used to define all supported NFC EMV paycard. <link>http://en.wikipedia.org/wiki/Europay_Mastercard_Visa</link>
 *
 */
public enum EmvCardScheme {

	UNKNOWN("",""),

	ANY_CARD("ANY", "^[0-9]{16}$");
	/**
	 * array of Card AID or partial AID (RID)
	 */
	private final String[] aids;

	/**
	 * array of Aid in byte
	 */
	private final byte[][] aidsByte;

	/**
	 * Card scheme (card number IIN ranges)
	 */
	private final String name;

	/**
	 * Card number pattern regex
	 */
	private final Pattern pattern;

	/**
	 * Constructor using fields
	 * 
	 * @param
	 *            Card AID or RID
	 * @param pScheme
	 *            scheme name
	 * @param pRegex
	 *            Card regex
	 */
	EmvCardScheme(final String pScheme, final String pRegex, final String... pAids) {
		aids = pAids;
		aidsByte = new byte[pAids.length][];
		for (int i = 0; i < aids.length; i++) {
			aidsByte[i] = BytesUtils.fromString(pAids[i]);
		}
		name = pScheme;
		if (StringUtils.isNotBlank(pRegex)) {
			pattern = Pattern.compile(pRegex);
		} else {
			pattern = null;
		}
	}

	/**
	 * Method used to get the field aid
	 * 
	 * @return the aid
	 */
	public String[] getAid() {
		return aids;
	}

	/**
	 * Method used to get the field name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get card type by AID
	 * 
	 * @param pAid
	 *            card AID
	 * @return CardType or null
	 */
	public static EmvCardScheme getCardTypeByAid(final String pAid) {
		EmvCardScheme ret = EmvCardScheme.UNKNOWN ;
		if (pAid != null) {
			String aid = StringUtils.deleteWhitespace(pAid);
			for (EmvCardScheme val : EmvCardScheme.values()) {
				for (String schemeAid : val.getAid()) {
					if (aid.startsWith(StringUtils.deleteWhitespace(schemeAid))) {
						ret = val;
						break;
					}
				}
			}
		}
		return ret;
	}

	/**
	 * Method used to the the card type with regex
	 * 
	 * @param pCardNumber
	 *            card number
	 * @return the type of the card using regex
	 */
	public static EmvCardScheme getCardTypeByCardNumber(final String pCardNumber) {
		EmvCardScheme ret = EmvCardScheme.UNKNOWN;
		if (pCardNumber != null) {
			for (EmvCardScheme val : EmvCardScheme.values()) {
				if (val.pattern != null && val.pattern.matcher(StringUtils.deleteWhitespace(pCardNumber)).matches()) {
					ret = val;
					break;
				}
			}
		}
		return ret;
	}

	/**
	 * Method used to get the field aidByte
	 * 
	 * @return the aidByte
	 */
	public byte[][] getAidByte() {
		return aidsByte;
	}

}
