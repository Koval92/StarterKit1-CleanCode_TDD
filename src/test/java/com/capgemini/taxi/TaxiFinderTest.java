package com.capgemini.taxi;

import org.junit.Test;

public class TaxiFinderTest {

	@Test
	public void testWithDisplaying() throws Exception {
		TaxiFinder finder = new TaxiFinder();
		finder.addNewTaxi(1, 0.1, 0.2, true);
		finder.addNewTaxi(2, 1, 0.1, true);
		finder.addNewTaxi(3, 0.2, 0.1, false);
		finder.addNewTaxi(4, 0.2, 0.1, true);
		finder.displayTaxis();
		finder.displayAvailableTaxis();
		
		finder.update(1, 0.9, 0.7, false);
		finder.displayTaxis();
		finder.displayAvailableTaxis();
		finder.displayAvailableAndNearbyTaxis(0, 0, 0.5);
	}
	
	@Test(expected=Exception.class)
	public void shouldThrowForDuplicatedTaxiId() throws Exception {
		TaxiFinder finder = new TaxiFinder();
		finder.addNewTaxi(1, 0, 0, true);
		finder.addNewTaxi(1, 1, 0, false);
	}

}
