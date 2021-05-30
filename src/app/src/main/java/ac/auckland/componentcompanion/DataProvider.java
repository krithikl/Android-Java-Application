package ac.auckland.componentcompanion;

public class DataProvider {
	/* An array containing an array of strings. Each element is made up of strings
	 * that describe a particular item
	 */
	/*
	 * Components data from https://octopart.com/ (including images)
	 *
	 */
	static String[][] data = {
		/* Cat 1: Passive */
		/* Resistors  */
		/* Dataformat:
		 * Name; Make; Model; Price (NZ Cents); Mount Type; Resistance (Ohms)}
		*/
		{"Resistor", "Panasonic", "ERJ-6GEY0R00V", "1.4", "SM", "0"},
		{"Resistor", "TE Connectivity", "CFR16J120R", "1.4", "TH", "120"},
		{"Resistor", "Vishay", "CRCW06034K70FKEAHP", "3.3", "SM", "4700"},
		{"Resistor", "BI Technologies", "93PR100KLF", "210.8", "TH", "100000"},
		{"Resistor", "Ohmite", "13FR050E", "147.4", "TH", "0.05"},
		{"Resistor", "KOA Speer", "RK73H2ATTD2R00F", "1", "SM", "2"},
		{"Resistor", "Vishay Dale", "RLR20C1001GRB14", "206.6", "TH", "1000"},
		{"Resistor", "TT Electronics", "PFC-W0805LF-03-1000-B", "86.8", "SM", "100"},
		{"Resistor", "Venkel", "TFCR2512-2W-C-1434BT-1K", "86.8", "SM", "100"},
		{"Resistor", "ATE", "7CS-6R8-J", "251.9", "TH", "6.8"},

		/* Capacitors */
		/* Inductors */

		/* Cat 2: Active */
		/* Transistors */
		/* Voltage Source *
		/* Current Source */


		/* Cat 3: Integrated Circuits */
		/* Operational Amplifiers */
		/* Microcontrollers */
		/* Timers */
	};

	public static String[][] getData() {
		 return data;
	}
}
