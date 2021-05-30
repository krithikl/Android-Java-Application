package ac.auckland.componentcompanion;

public class DataProvider {
	/* An array containing an array of strings. Each element is made up of strings
	 * that describe a particular item
	 */
	/*
	 * Components data from https://octopart.com/ (including images)
	 *
	 */
	private static final String[][] data = {
		/* Category 1: Resistors  */
		/* Dataformat:
		 * {Name; Make; Model; Price (NZ Cents); Mount Type; Resistance (Ohms)}
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

		/* Category 2: Capacitors */
		/* Dataformat:
		 * {Name; Make; Model; Price (NZ Cents); Mount Type; Capacitance (Micro Farad)}
		*/
		{"Capacitor", "Panasonic", "EEEFK1V220R", "17.1", "SM", "22"},
		{"Capacitor", "KEMET", "C0805C104K5RACTU", "7.8", "SM", "0.1"},
		{"Capacitor", "Murata", "CSTCR6M00G53-R0", "21.8", "SM", "0.015"},
		{"Capacitor", "TDK", "CGA2B3X7R1H473K050BB", "2.7", "SM", "0.047"},
		{"Capacitor", "AVX", "08055C104KAT2A", "2.6", "SM", "0.1"},
		{"Capacitor", "Vishay", "293D226X9016D2TE3", "48.9", "SM", "22"},
		{"Capacitor", "Vishay BCcomponents", "MKT1818210635", "48.1", "TH", "0.001"},
		{"Capacitor", "KEMET", "T356F226K016AT", "100", "TH", "22"},
		{"Capacitor", "Panasonic", "ECA-1HHG100", "7.6", "TH", "10"},
		{"Capacitor", "Panasonic", "6TPE100MAZB", "97.5", "SM", "100"},

		/* Category 3: Inductors */
		/* Dataformat:
		 * {Name; Make; Model; Price (NZ Cents); Mount Type; Inductance (Micro Henries)}
		 */
		{"Inductor", "Eaton", "DR74-470-R", "97.8", "SM", "47"},
		{"Inductor", "Eaton", "DR127-100-R", "99.8", "SM", "10"},
		{"Inductor", "KEMET", "MPX1D0630L1R0", "140.5", "SM", "1"},
		{"Inductor", "Murata Power Solutions", "22R474C", "65.1", "TH", "470"},
		{"Inductor", "Multicomp", "MCFT000095", "1.8", "SM", "0.1"},
		{"Inductor", "Mag Layers USA", "MMD-06CZ-3R3M-V1-RU", "130.3", "SM", "3.3"},
		{"Inductor", "Pulse", "PE-53912NLT", "183.2", "SM", "1800"},
		{"Inductor", "Bourns", "SRF3216-222Y", "43.5", "SM", "2200"},
		{"Inductor", "Bourns", "RLB0812-103KL", "19.2", "TH", "10000"},
		{"Inductor", "Bourns", "RLB9012-222KL", "43.2", "TH", "2200"}
	};

	public static String[][] getData() {
		 return data;
	}
}
