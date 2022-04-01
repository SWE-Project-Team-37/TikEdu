package tikedu.app.tikedu;

import static org.junit.Assert.*;

import org.junit.Test;

public class SignInUnitTest
{
/*
Username tests:
    *Test empty string is not allowed ^
    *Test more than 16 characters is not allowed ^
    *Test non-alphanumeric characters are not allowed ^
    *Test alphanumeric strings between 1-16 characters are allowed ^
        *all letters
        *all numbers
        *1 character
        *16 characters
        *random strings meeting specs

Password tests:
    *Test empty string is not allowed ^
    *Test less than 8 characters is not allowed ^
    *Test more than 20 characters is not allowed ^
    *test only letters strings are not allowed ^
    *test only numeric strings are not allowed ^
    *test only alphanumeric strings are not allowed ^
    *test only special characters (i.e. @#$%^&+=) are not allowed ^
    *test strings with lowercase letters, uppercase letters, numbers, and special characters that are between 8 and 20 characters are allowed ^
        *strings meeting specs
 */
    @Test
    public void usernameIsValid_EmptyString_NotAllowed()
    {
        assertFalse(SignInFragment.usernameIsValid(""));
    }

    @Test
    public void usernameIsValid_More16_NotAllowed()
    {
        assertFalse(SignInFragment.usernameIsValid("a2b4c6d8e10f13g16h19"));
        assertFalse(SignInFragment.usernameIsValid("Z9eHFbNtcL1plMtcPAWi7VacdO2a8M"));
        assertFalse(SignInFragment.usernameIsValid("aIpATM5nsXt4IcJuF"));
    }

    @Test
    public void usernameIsValid_NonAlpha_NotAllowed()
    {
        assertFalse(SignInFragment.usernameIsValid("ab12%sdf"));
        assertFalse(SignInFragment.usernameIsValid("fadsa51f^&"));
        assertFalse(SignInFragment.usernameIsValid("NonSenseUser;[."));
    }

    @Test
    public void usernameIsValid_Alphanumeric_IsAllowed()
    {
        assertTrue(SignInFragment.usernameIsValid("afsd123sdf"));
        assertTrue(SignInFragment.usernameIsValid("strangeuser27"));
        assertTrue(SignInFragment.usernameIsValid("D0g30wn3r"));
    }

    @Test
    public void passwordIsValid_EmptyString_NotAllowed()
    {
        assertFalse(SignInFragment.passwordIsValid(""));
    }

    @Test
    public void passwordIsValid_Less8_NotAllowed()
    {
        assertFalse(SignInFragment.passwordIsValid("123abc7"));
        assertFalse(SignInFragment.passwordIsValid("pa$%9"));
        assertFalse(SignInFragment.passwordIsValid("vIayr"));
    }

    @Test
    public void passwordIsValid_More20_NotAllowed()
    {
        assertFalse(SignInFragment.passwordIsValid("kNnkSuA2JX@X8gCFSn5#h7WirXw"));
        assertFalse(SignInFragment.passwordIsValid("NfFlvPcQCr+++RVJiUE%Omfn+8C^jcjOhKXn94P"));
        assertFalse(SignInFragment.passwordIsValid("123456789101214161820222426"));
    }

    @Test
    public void passwordIsValid_OnlyOne_NotAllowed()
    {
        assertFalse(SignInFragment.passwordIsValid("kNnkSuA2JX@X8gCFSn5#h7WirXw"));
        assertFalse(SignInFragment.passwordIsValid("abcDeFghiJk"));
        assertFalse(SignInFragment.passwordIsValid("sgas0fri9c9ll"));
        assertFalse(SignInFragment.passwordIsValid("@#$%^&+=@#$%^&+="));
    }

    @Test
    public void PasswordIsValid_Valid_IsAllowed()
    {
        assertTrue(SignInFragment.passwordIsValid("This1sV@lid"));
        assertTrue(SignInFragment.passwordIsValid("Another2022@#"));
        assertTrue(SignInFragment.passwordIsValid("L2st11+=+=+=Mo"));
    }

}