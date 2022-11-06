package com.example.mealer;

import com.example.mealer.structure.Chef;
import static org.junit.Assert.*;
import org.junit.Test;

public class suspensionChef {

    @Test
    public void chefSuspendu(){
        Chef moi= new Chef("Astrid", "Matagne", "ama@mail.ca", "62RueLaval", 5203, "auto");
        moi.setSuspended(true);
        Boolean t= moi.getSuspended();
        assertTrue("le chef est suspendu", t);
    }

    @Test
    public void chefNonSuspendu() {
        Chef yves=new Chef("yves", "Laurent", "ylau@mail.ca", "26POBallum", 8095, "instantane");
        yves.setSuspended(false);
        Boolean s=yves.getSuspended();
        assertFalse("le chef n'est pas suspendu", s);
    }
}
