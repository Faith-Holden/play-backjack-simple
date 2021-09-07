package solution;

import resource_classes.BlackjackHand;
import resource_classes.Deck;
import resource_classes.TextIO;

public class PlayBlackJack {
    public static void main(String[]args){
        boolean continueConditionsMet = true;
        int playerMoney = 100;
        while (continueConditionsMet){
            System.out.println("How much would you like to bet? You currently have $"+ playerMoney + ".");
            int bet = TextIO.getlnInt();
            while(bet>playerMoney){
                System.out.println("Sorry, you only have $"+ playerMoney+".");
                bet = TextIO.getlnInt();
            }
            boolean gameWon = playBlackJackRound();
            if(gameWon){
                System.out.println("Congratulations! You won $"+bet+ "!");
                playerMoney+=bet;
                System.out.println("You now have $"+ playerMoney + ".");
                System.out.println("Would you like to play another round? (Y/N)");
                String response = TextIO.getlnString();
                if(response.equals("N")){
                    continueConditionsMet=false;
                }
            }else {
                System.out.println("Sorry, the dealer won and you lost your bet of $" + bet + ".");
                playerMoney-=bet;
                System.out.println("You now have $"+ playerMoney + ".");
                if(playerMoney<=0){
                    continueConditionsMet = false;
                    System.out.println("Sorry, you are out of money. Please come back another time.");
                }else{
                    System.out.println("Would you like to play another round? (Y/N)");
                    String response = TextIO.getlnString();
                    if(response.equals("N")){
                        continueConditionsMet=false;
                    }
                }
            }
        }
    }

    public static boolean playBlackJackRound (){
        Deck gameDeck = new Deck();
        BlackjackHand playerHand = new BlackjackHand();
        BlackjackHand dealerHand = new BlackjackHand();
        gameDeck.shuffle();

        playerHand.addCard(gameDeck.dealCard());
        playerHand.addCard(gameDeck.dealCard());
        dealerHand.addCard(gameDeck.dealCard());
        dealerHand.addCard(gameDeck.dealCard());

        System.out.println("Your cards are the "+ playerHand.getCard(0).toString()+" and the "+ playerHand.getCard(1).toString());

        if(dealerHand.getBlackjackValue()==21){
            System.out.print("The dealer's cards were the " + dealerHand.getCard(0).toString()+ " and the ");
            System.out.println(dealerHand.getCard(1).toString()+ " which is blackjack.");
            return false;
        }else if (playerHand.getBlackjackValue()==21){
            System.out.println("That is blackjack!");
            return true;
        }else{
            System.out.println("One of the dealer's cards is a "+ dealerHand.getCard(0).toString());
            while (true){
                System.out.println("Would you like to hit, or to stand?");
                String playerChoice = TextIO.getlnString();
                if(playerChoice.equals("hit")){
                    playerHand.addCard(gameDeck.dealCard());
                    System.out.println("Your current hand is ");
                    for(int i=0; i<playerHand.getCardCount(); i++){
                        System.out.println("the "+playerHand.getCard(i));
                    }
                    if(playerHand.getBlackjackValue()>21){
                        System.out.println("Your score went over, it was "+ playerHand.getBlackjackValue());
                        return false;
                    }
                }else if(playerChoice.equals("stand")){
                    break;
                }
            }
            while (dealerHand.getBlackjackValue()<17){
                System.out.println("The dealer hits.");
                dealerHand.addCard(gameDeck.dealCard());
                if(dealerHand.getBlackjackValue()>21){
                    System.out.println("The dealer went over. His score was "+ dealerHand.getBlackjackValue());
                    return true;
                }
            }
            System.out.println("The dealer stands.");
        }
        System.out.print("The dealer's score was " + dealerHand.getBlackjackValue() + ". Your score was ");
        System.out.println(playerHand.getBlackjackValue());
        return playerHand.getBlackjackValue() > dealerHand.getBlackjackValue();
    }
}
