package name_pending;

public class Resistance {
	//Type of buff, how much of it, and the time of it
		String name = "none";
		int amount = 0;
		
		public Resistance(String name, int amount)
		{
			this.name = name;
			this.amount = amount;
		}
		
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAmount() {
			return amount;
		}
		public void setAmount(int amount) {
			this.amount = amount;
		}
		
		//Stuff for the gui
}
