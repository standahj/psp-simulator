package com.stanhejny.psp.validator;

import com.stanhejny.psp.model.CardTransaction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Normalize the incoming values to accept wider range of inputs, including common separators included to make data human readable.
 */
public class TransactionValuesNormalizer {
    Pattern EXPIRY_DATE_NO_LEADING_ZERO = Pattern.compile("^[1-9][\\/\\. ]*\\d\\d{1,3}$");
    Pattern EXPIRY_DATE_NO_SLASH_OR_FULL_YEAR = Pattern.compile("^\\d\\d[\\/\\. ]*\\d\\d{1,3}$");

    /**
     * Noralize the values, update the input object, because for further processing the original input is not needed.
     * @param cardTransaction values to be normalized, any changes are stored back here.
     */
    public void normalizeValues(CardTransaction cardTransaction) {
        cardTransaction.setCardNumber(cardTransaction.getCardNumber().trim().replaceAll("[ -]", ""));
        String expiryDate = cardTransaction.getExpiryDate().trim();
        Matcher noLeadingZero = EXPIRY_DATE_NO_LEADING_ZERO.matcher(expiryDate);
        if (noLeadingZero.matches()) {
            expiryDate = "0" + expiryDate;
        }
        Matcher noSlash = EXPIRY_DATE_NO_SLASH_OR_FULL_YEAR.matcher(expiryDate);
        if (noSlash.matches()) {
            String month = expiryDate.substring(0, 2);
            String year = expiryDate.substring(expiryDate.length() - 2);
            expiryDate = "%s/%s".formatted(month, year);
        }
        cardTransaction.setExpiryDate(expiryDate);
        cardTransaction.setCurrency(cardTransaction.getCurrency().trim());
        cardTransaction.setCVV(cardTransaction.getCVV().trim());
        cardTransaction.setCurrency(cardTransaction.getCurrency().trim());
        cardTransaction.setAmount(cardTransaction.getAmount().trim());
        cardTransaction.setMerchantId(cardTransaction.getMerchantId().trim());
        String prefix = cardTransaction.getCardNumber().substring(0,2);
        cardTransaction.setAmex("34".equals(prefix) || "37".equals(prefix));
    }
}
