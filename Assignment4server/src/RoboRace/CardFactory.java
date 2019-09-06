package RoboRace;

public class CardFactory {

	public Card createCard() {
		int r = (int) Math.floor(84*Math.random()+1);
		if (r<=18) return new CardTurn(r,true);
		if (r<=36) return new CardTurn(r,false);
		if (r<=42) return new CardHalfTurn(r);
		if (r<=48) return new CardBack(r);
		if (r<=66) return new CardMove(r,1);
		if (r<=78) return new CardMove(r,2);
		return new CardMove(r,3);
	}
	
}