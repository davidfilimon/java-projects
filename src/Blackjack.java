import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Blackjack{

    static Random random = new Random(); // for deck shuffling
    static ArrayList<Card> deck;


    // dealer
    static Card hiddenCard;
    static ArrayList<Card> dealerHand;
    static int dealerSum;
    static int dealerAceCount;

    // player
    static ArrayList<Card> playerHand;
    static int playerSum;
    static int playerAceCount;
    static JFrame myFrame;
    static myFrame frame;

    Blackjack(){
        startGame();

    }

    public static void startGame(){

        // deck
        buildDeck();
        shuffleDeck();

        // dealer
        dealerHand();

        // player
        playerHand();

        // frame
        frame = new myFrame(dealerHand, playerHand, deck, playerSum, playerAceCount, dealerSum, dealerAceCount, hiddenCard);

    }

    public static void resetGame() {
        // deck

        boolean previousGame = (dealerHand != null && playerHand != null && deck != null);

        if (previousGame) {
            dealerHand.clear();
            playerHand.clear();
            deck.clear();
        }

        playerSum = 0;
        playerAceCount = 0;
        dealerSum = 0;
        dealerAceCount = 0;

        buildDeck();
        shuffleDeck();

        // dealer
        dealerHand();

        // player
        playerHand();

        if (frame != null) {
            frame.updateGameState(dealerHand, playerHand, deck, playerSum, playerAceCount, dealerSum, dealerAceCount, hiddenCard);
        } else {
            System.out.println("Frame is null. Cannot reset game.");
        }

    }

    public static void buildDeck(){
        deck = new ArrayList<Card>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};

        for(int i = 0; i<=types.length-1; i++){
            for(int j=0; j<= values.length-1; j++) {
                Card card = new Card(values[j], types[i]);
                deck.add(card);
            }
        }
        System.out.println("Deck build:");
        System.out.println(deck.toString());
    }

    public static void shuffleDeck(){

        for(int i=0; i<deck.size(); i++){ // for each card
            int j = random.nextInt(deck.size()); // random card
            Card currCard = deck.get(i);
            Card randomCard = deck.get(j);
            deck.set(i, randomCard); // swap places
            deck.set(j, currCard);

        }

        System.out.println("After shuffle:");
        System.out.println(deck);

    }

    public static void dealerHand(){
        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAceCount = 0;

        hiddenCard = deck.remove(deck.size()-1); // remove card at last index
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = deck.remove(deck.size() - 1);
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);

        System.out.println("Dealer hand:");
        System.out.println(hiddenCard);
        System.out.println(card);
        System.out.println(dealerAceCount);
    }

    public static void playerHand(){
        playerHand = new ArrayList<Card>();
        playerSum = 0;
        playerAceCount = 0;

        for(int i=0; i<2; i++){
            Card card = deck.remove(deck.size() - 1);
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }

        System.out.println("Player Hand:");
        System.out.println(playerHand);
        System.out.println(playerAceCount);
    }



}
