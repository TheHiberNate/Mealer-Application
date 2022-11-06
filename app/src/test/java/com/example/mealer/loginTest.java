package com.example.mealer;

import static org.junit.Assert.*;

import com.example.mealer.functions.Validation;

import org.junit.Test;

public class loginTest {
    @Test
    public void succesLogin(){
        Boolean verify= Validation.validateAdmin("user1@mail.ca", "secret");
        assertTrue("l'email et le mot de passe sont verifies", verify);
    }

    @Test
    public void echecLogin(){
        Boolean verify=Validation.validateEmail("usermail.ca");
        assertFalse("l'email n'est pas verifie",  verify);
    }
}
