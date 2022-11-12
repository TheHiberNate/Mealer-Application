package com.example.mealer;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.mealer.structure.Chef;
import com.example.mealer.structure.Menu;

import org.hamcrest.core.Is;
import org.junit.Test;

public class ChefSuspensionFunctionalityTesting {

    @Test
    public void verifyNoChefSuspension() {
        Chef chef = new Chef("Nathan", "Nathan", "nate@cool.ca", "45 Cool Street", "902902", "Super Cool", new Menu("Cool", "Spanish Food"));
        Boolean IsSuspended = chef.getSuspended();
        assertFalse("Verify chef has no suspension", IsSuspended);
    }

    @Test
    public void verifyChefHasSuspension() {
        Chef chef = new Chef("Cool", "Dude", "nate@cool.ca", "45 Cool Street", "902902", "Super Cool", new Menu());
        chef.setIsSuspended(true);
        Boolean n = chef.getSuspended();
        assertTrue("Verify chef has a suspension", n);
    }

    @Test
    public void verifyChefNullSuspension() {
        Chef chef = new Chef("Nathan", "Nathan", "nate@cool.ca", "45 Cool Street", "902902", "Super Cool", new Menu());
        String suspensionLength = chef.getSuspensionLength();
        assertEquals("Verify a null suspension length", "none", suspensionLength);
    }

    @Test
    public void verifyChefSpecificSuspension() {
        Chef chef = new Chef("Nathan", "Nathan", "nate@cool.ca", "45 Cool Street", "902902", "Super Cool", new Menu());
        chef.setSuspensionLength("15 days");
        String suspensionLength = chef.getSuspensionLength();
        assertEquals("Verify a null suspension length", "15 days", suspensionLength);
    }




}
